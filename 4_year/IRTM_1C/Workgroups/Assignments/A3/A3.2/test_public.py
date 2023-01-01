import random
import time

import pytest
from elasticsearch.client import Elasticsearch

import A3_2 as module

INDEX_NAME = "trec9_index"


@pytest.fixture(scope="module")
def data_path():
    return "data/{}"


@pytest.fixture(scope="module")
def es():
    return Elasticsearch()


@pytest.fixture(scope="module", autouse=True)
def index_toy_documents(es):
    toy_docs = [
        ("d1", {"title": "t3 t6", "body": "t3 t3 t3 t6 t6"}),
        ("d2", {"title": "t1 t3", "body": "t1 t2 t3 t3 t6"}),
        ("d3", {"title": "t4 t3", "body": "t3 t3 t4 t5"}),
        ("d4", {"title": "t6 t6", "body": "t4 t5 t6 t6"}),
        ("d5", {"title": "t2", "body": "t1 t2 t3 t5"}),
    ]

    if es.indices.exists(index="toy_index"):
        es.indices.delete(index="toy_index")

    es.indices.create(index="toy_index", mappings=module.INDEX_SETTINGS)
    for (doc_id, doc) in toy_docs:
        es.index(document=doc, id=doc_id, index="toy_index")
    time.sleep(10)


@pytest.fixture
def toy_query():
    return ["t1 t4", "t2", "t5 t7 t2", "t6 t6"]


@pytest.fixture(scope="module")
def queries(data_path: str):
    return module.load_queries(data_path.format("queries"))


@pytest.fixture(scope="module")
def qrels(data_path: str):
    return module.load_qrels(data_path.format("qrels"))


@pytest.fixture(scope="module")
def train_test_split(queries):
    random.seed(a=1234567)
    query_ids = sorted(list(queries.keys()))
    random.shuffle(query_ids)
    train_size = int(len(query_ids) * 0.8)
    return query_ids[:train_size], query_ids[train_size:][-100:]


@pytest.fixture(scope="module")
def training_data(es, train_test_split, queries, qrels):
    # Prepare training data with labels for learning-to-rank
    train, _ = train_test_split

    return module.prepare_ltr_training_data(
        train[:800], queries, qrels, es, index=INDEX_NAME
    )


@pytest.fixture(scope="module")
def trained_ltr_model(training_data):
    X_train, y_train = training_data
    # Instantiate PointWiseLTRModel.
    ltr = module.PointWiseLTRModel()
    ltr._train(X_train, y_train)
    return ltr


def test_indexing(es):
    """This test is 0 points"""
    es.indices.refresh(INDEX_NAME)
    count = es.cat.count(INDEX_NAME, params={"format": "json"})
    assert int(count[0]["count"]) == 54709


def test_load_queries(queries):
    """This test is 0.5 points"""
    assert len(queries) == 4967


def test_load_qrels(qrels):
    """This test is 0.5 points"""
    assert len(qrels) == 4967
    assert len(qrels["OHSU63"]) == 2


def test_query_extraction(es, toy_query):
    """This test is 1 points"""
    q0_features = module.extract_query_features(
        module.analyze_query(es, toy_query[0], "body"), es, index="toy_index"
    )
    assert q0_features["query_length"] == 2
    assert q0_features["query_avg_idf"] == pytest.approx(0.9163, abs=1e-4)

    q2_features = module.extract_query_features(
        module.analyze_query(es, toy_query[2], "body"), es, index="toy_index"
    )
    assert q2_features["query_sum_idf"] == pytest.approx(1.4271, abs=1e-4)

    q3_features = module.extract_query_features(
        module.analyze_query(es, toy_query[3], "body"), es, index="toy_index"
    )
    assert q3_features["query_avg_idf"] == pytest.approx(0.5108, abs=1e-4)


def test_document_feature_extraction(es):
    """This test is 1 points"""
    d1_features = module.extract_doc_features("d1", es, index="toy_index")
    assert d1_features["doc_length_body"] == 5
    assert d1_features["doc_length_title"] == 2

    d5_features = module.extract_doc_features("d5", es, index="toy_index")
    assert d5_features["doc_length_body"] == 4
    assert d5_features["doc_length_title"] == 1


def test_query_document_feature_extraction(es, toy_query):
    """This test is 1 points"""
    q0d2_features = module.extract_query_doc_features(
        module.analyze_query(es, toy_query[0], "body"),
        "d2",
        es,
        index="toy_index",
    )
    assert q0d2_features["unique_query_terms_in_title"] == 1
    assert q0d2_features["sum_TF_body"] == 1

    q0d3_features = module.extract_query_doc_features(
        module.analyze_query(es, toy_query[0], "body"),
        "d3",
        es,
        index="toy_index",
    )
    assert q0d3_features["avg_TF_title"] == 0.5
    assert q0d3_features["max_TF_body"] == 1

    q2d5_features = module.extract_query_doc_features(
        module.analyze_query(es, toy_query[2], "body"),
        "d5",
        es,
        index="toy_index",
    )
    assert q2d5_features["unique_query_terms_in_body"] == 2
    assert q2d5_features["avg_TF_body"] == 1.0


def test_combined_feature_extraction(es, toy_query):
    """This test is 0.5 points"""
    feature_vect_q0_d1 = module.extract_features(
        module.analyze_query(es, toy_query[0], "body"),
        "d1",
        es,
        index="toy_index",
    )
    assert feature_vect_q0_d1 == pytest.approx(
        [
            2,
            1.8325814637483102,
            0.9162907318741551,
            0.9162907318741551,
            2,
            5,
            0,
            0,
            0,
            0.0,
            0,
            0,
            0,
            0.0,
        ],
        abs=1e-5,
    )

    feature_vect_q1_d2 = module.extract_features(
        module.analyze_query(es, toy_query[1], "body"),
        "d2",
        es,
        index="toy_index",
    )
    assert feature_vect_q1_d2 == pytest.approx(
        [
            1,
            0.9162907318741551,
            0.9162907318741551,
            0.9162907318741551,
            2,
            5,
            0,
            0,
            0,
            0.0,
            1,
            1,
            1,
            1.0,
        ],
        abs=1e-5,
    )

    feature_vect_q3_d3 = module.extract_features(
        module.analyze_query(es, toy_query[3], "body"),
        "d3",
        es,
        index="toy_index",
    )
    assert feature_vect_q3_d3 == pytest.approx(
        [
            2,
            1.0216512475319814,
            0.5108256237659907,
            0.5108256237659907,
            2,
            4,
            0,
            0,
            0,
            0.0,
            0,
            0,
            0,
            0.0,
        ],
        abs=1e-5,
    )


def test_prepare_training_data(training_data):
    """This test is 1 points"""
    X_train, y_train = training_data
    # Test of preparation of training data.
    assert len(X_train) == 59997
    assert X_train[0] == pytest.approx(
        [
            2,
            9.370371738233722,
            5.408525298151982,
            4.685185869116861,
            11,
            324,
            0,
            0,
            0,
            0.0,
            2,
            2,
            1,
            1.0,
        ],
        abs=1e-5,
    )
    assert y_train[0] == 1


def test_learning_to_rank_rankings(
    es, trained_ltr_model, train_test_split, queries
):
    """This test is 1 points"""
    _, test = train_test_split

    rankings_ltr = module.get_rankings(
        trained_ltr_model, test, queries, es, index=INDEX_NAME, rerank=True
    )
    assert len(rankings_ltr["MSH2691"]) == 16
    assert rankings_ltr["MSH2691"].index("87254618") < rankings_ltr[
        "MSH2691"
    ].index("87216812")


def test_mean_rr(es, trained_ltr_model, train_test_split, qrels, queries):
    """This test is 0.5 points"""
    _, test = train_test_split

    rankings_first_pass = module.get_rankings(
        None, test, queries, es, index=INDEX_NAME, rerank=False
    )

    # Final test: Mean reciprocal rank of LTR and first-pass rankings.

    mrr_first_pass = module.get_mean_eval_measure(
        rankings_first_pass, qrels, module.get_reciprocal_rank
    )
    assert mrr_first_pass > 0.16

    rankings_ltr = module.get_rankings(
        trained_ltr_model, test, queries, es, index=INDEX_NAME, rerank=True
    )

    mrr_ltr = module.get_mean_eval_measure(
        rankings_ltr, qrels, module.get_reciprocal_rank
    )
    assert mrr_ltr - mrr_first_pass > 0.015
