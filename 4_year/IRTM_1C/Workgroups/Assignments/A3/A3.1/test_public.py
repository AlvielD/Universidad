import pytest
from elasticsearch import Elasticsearch
import A3_1 as module


INDEX_NAME = "musicians"


@pytest.fixture(scope="module")
def es():
    return Elasticsearch()


@pytest.fixture(scope="module")
def clm():
    class ToyCollectionLM(module.CollectionLM):
        def __init__(self):
            super().__init__(None, [])
            self._probs = {
                "names": {
                    "t1": 1 / 3,
                    "t2": 0,
                    "t3": 1 / 9,
                    "t4": 1 / 9,
                    "t5": 1 / 3,
                    "gospel": 1 / 9,
                    "soul": 2 / 9,
                },
                "types": {
                    "t1": 5 / 18,
                    "t2": 1 / 18,
                    "t3": 1 / 3,
                    "t4": 1 / 6,
                    "t5": 1 / 6,
                    "gospel": 0,
                    "soul": 0,
                },
                "description": {
                    "t1": 1 / 20,
                    "t2": 1 / 20,
                    "t3": 1 / 10,
                    "t4": 1 / 10,
                    "t5": 2 / 10,
                    "gospel": 4 / 10,
                    "soul": 1 / 5,
                },
                "attributes": {},
                "related_entities": {
                    "t1": 1 / 8,
                    "t2": 0,
                    "t3": 0,
                    "t4": 1 / 4,
                    "t5": 0,
                    "gospel": 1 / 2,
                    "soul": 1 / 8,
                },
                "catch_all": {
                    "t1": 2 / 14,
                    "t2": 2 / 14,
                    "t3": 1 / 14,
                    "t4": 0,
                    "t5": 2 / 14,
                    "gospel": 3 / 14,
                    "soul": 4 / 14,
                },
            }

        def prob(self, field, term):
            return self._probs.get(field, {}).get(term, 0)

    return ToyCollectionLM()


def test_dict_based_entity():
    """This test is worth 0.5 point"""
    entity_id_1 = "Al Green"
    entity_dict_1 = module.dict_based_entity(entity_id_1)

    assert entity_dict_1["http://xmlns.com/foaf/0.1/name"] == "Al Green"
    assert (
        "Al Green"
        in entity_dict_1["http://www.w3.org/2000/01/rdf-schema#label"]
    )
    assert sorted(entity_dict_1["http://dbpedia.org/property/occupation"]) == [
        "",
        "Singer",
        "record producer",
        "songwriter",
    ]

    entity_id_2 = "Deadmau5"
    entity_dict_2 = module.dict_based_entity(entity_id_2)

    assert entity_dict_2["http://xmlns.com/foaf/0.1/name"] == "Deadmau5"
    assert (
        "데드마우스" in entity_dict_2["http://www.w3.org/2000/01/rdf-schema#label"]
    )
    assert sorted(entity_dict_2["http://dbpedia.org/ontology/alias"]) == [
        "Halcyon441",
        "Karma K",
        "Testpilot",
    ]


def test_fielded_doc_entity():
    """This test is worth 0.5 point"""
    artist_fielded_1 = module.fielded_doc_entity("Al Green")
    assert "アル・グリーン" in artist_fielded_1["names"]
    assert "Composer109947232" in artist_fielded_1["types"]
    assert "MCA Records artists" in artist_fielded_1["types"]
    assert (
        "one of the most gifted purveyors of soul music"
        in artist_fielded_1["attributes"]
    )
    assert "Quiet Elegance" in artist_fielded_1["related_entities"]

    artist_fielded_2 = module.fielded_doc_entity("Deadmau5")
    assert "Joel Thomas Zimmerman" in artist_fielded_2["names"]
    assert "Canadian house musicians" in artist_fielded_2["types"]
    assert (
        "a Canadian electronic music producer, DJ, and musician"
        in artist_fielded_2["attributes"]
    )
    assert "Mau5trap" in artist_fielded_2["related_entities"]


def test_term_frequency(es: Elasticsearch):
    """This test is worth 0.5 point"""
    tv_1 = es.termvectors(index=INDEX_NAME, id="Al Green", fields="catch_all")
    assert tv_1["term_vectors"]["catch_all"]["terms"]["1946"]["term_freq"] == 34

    tv_2 = es.termvectors(
        index=INDEX_NAME, id="Runhild Gammelsæter", fields="attributes"
    )
    assert (
        tv_2["term_vectors"]["attributes"]["terms"]["khlyst"]["term_freq"] == 1
    )

    tv_3 = es.termvectors(
        index=INDEX_NAME, id="Music of Belarus", fields="description"
    )
    assert (
        tv_3["term_vectors"]["description"]["terms"]["musiqu"]["term_freq"] == 7
    )


def test_baseline_retrieval(es: Elasticsearch):
    """This test is worth 0.5 point"""
    query_1 = "gospel"
    results_1 = module.baseline_retrieval(es, INDEX_NAME, query_1, 5)
    assert results_1 == [
        "Doug Oldham",
        "Lanny Wolfe",
        "Cynthia Clawson",
        "Dottie Rambo",
        "Edwin Hawkins",
    ]

    query_2 = "country punk"
    results_2 = module.baseline_retrieval(es, INDEX_NAME, query_2, 5)
    assert results_2 == [
        "Lydia Loveless",
        "GG Allin",
        "Sarah Borges",
        "Cassadee Pope",
        "Rachid Taha",
    ]


def test_collection_probability(es: Elasticsearch):
    """This test is worth 1 point"""
    clm_1 = module.CollectionLM(es, module.analyze_query(es, "gospel soul"))
    assert clm_1.prob("types", "gospel") == pytest.approx(0.0003, abs=1e-4)
    assert clm_1.prob("types", "soul") == pytest.approx(0.0002, abs=1e-4)
    assert clm_1.prob("description", "gospel") == pytest.approx(
        0.00012, abs=1e-5
    )
    assert clm_1.prob("description", "soul") == pytest.approx(0.00016, abs=1e-4)

    clm_2 = module.CollectionLM(
        es, module.analyze_query(es, "fun time madness")
    )
    assert clm_2.prob("catch_all", "fun") == pytest.approx(0.00002, abs=1e-5)
    assert clm_2.prob("catch_all", "time") == pytest.approx(0.0006, abs=1e-4)
    assert clm_2.prob("catch_all", "madness") == pytest.approx(0.0, abs=1e-1)


def test_term_mapping_prob(clm):
    """This test is worth 1 point"""
    Pf_t_3_1 = module.get_term_mapping_probs(clm, "gospel")
    assert Pf_t_3_1["description"] == pytest.approx(0.32642, abs=1e-5)
    assert Pf_t_3_1["attributes"] == pytest.approx(0, abs=1e-5)
    assert Pf_t_3_1["related_entities"] == pytest.approx(0.40803, abs=1e-5)

    Pf_t_3_2 = module.get_term_mapping_probs(clm, "soul")
    assert Pf_t_3_2["names"] == pytest.approx(0.26679, abs=1e-5)
    assert Pf_t_3_2["types"] == pytest.approx(0, abs=1e-5)
    assert Pf_t_3_2["catch_all"] == pytest.approx(0.34302, abs=1e-5)


def test_prms(es: Elasticsearch):
    """This test is worth 1 point"""
    prms_query_1 = "winter snow"
    prms_retrieval_1 = module.prms_retrieval(es, prms_query_1)
    assert prms_retrieval_1[:5] == [
        "Brian Jones",
        "Kelly Willard",
        "Anita Carter",
        "Hansi Hinterseer",
        "Mozhdah Jamalzadah",
    ]

    prms_query_2 = "summer sun"
    prms_retrieval_2 = module.prms_retrieval(es, prms_query_2)
    assert prms_retrieval_2[:5] == [
        "Sun Nan",
        "Liu Huan",
        "Danielle Bradbery",
        "Lala Hsu",
        "Job for a Cowboy",
    ]
