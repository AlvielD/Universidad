from typing import Any, Dict, List, Union
import json
import math
import requests
import wikipediaapi

from elasticsearch import Elasticsearch
from operator import itemgetter

WIKI_WIKI = wikipediaapi.Wikipedia("en")

TYPE_PREDICATE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
NAME_PREDICATES = set(
    [
        "http://www.w3.org/2000/01/rdf-schema#label",
        "http://xmlns.com/foaf/0.1/name",
        "http://xmlns.com/foaf/0.1/givenName",
        "http://xmlns.com/foaf/0.1/surname",
    ]
)
TYPE_PREDICATES = set([TYPE_PREDICATE, "http://purl.org/dc/terms/subject"])
COMMENT_PREDICATE = "http://www.w3.org/2000/01/rdf-schema#comment"


INDEX_NAME = "musicians"
INDEX_SETTINGS = {
    "mappings": {
        "properties": {
            "names": {
                "type": "text",
                "term_vector": "yes",
                "analyzer": "english",
            },
            "description": {
                "type": "text",
                "term_vector": "yes",
                "analyzer": "english",
            },
            "attributes": {
                "type": "text",
                "term_vector": "yes",
                "analyzer": "english",
            },
            "related_entities": {
                "type": "text",
                "term_vector": "yes",
                "analyzer": "english",
            },
            "types": {
                "type": "text",
                "term_vector": "yes",
                "analyzer": "english",
            },
            "catch_all": {
                "type": "text",
                "term_vector": "yes",
                "analyzer": "english",
            },
        }
    }
}


def get_lists_of_musicians() -> List[str]:
    """Get a list of pages that contain variety of lists for a given group.

    Returns:
        List of pages that contain musicians.
    """
    list_of_lists_page = WIKI_WIKI.page("Lists_of_musicians")
    return [
        list_of
        for list_of in list_of_lists_page.links.keys()
        if list_of.startswith("List of")
    ]


def get_dbpedia_entity(
    entity_id: str, session: requests.Session = None
) -> Dict[str, Any]:
    """Download the available data on entity for a named entity.

    Args:
        entity_id: A string of text.
        session: requests.Session object to provides cookie persistence,
            connection-pooling, and configuration.

    Returns:
        A dictionary (properties) if data has the entity URI as key, or else
        None.
    """
    url = entity_id.replace(" ", "_").replace("&", "and")
    try:
        data = (
            (session or requests)
            .get("http://dbpedia.org/data/{}.json".format(url))
            .json()
        )
    except json.JSONDecodeError:
        data = {}
        print(
            "JSONDecodeError when processing http://dbpedia.org/data/{}.json.".format(  # noqa
                url
            )
        )

    dict_key = "http://dbpedia.org/resource/{}".format(url)
    return data.get(dict_key, None)


def dict_based_entity(entity_id: str) -> Dict[str, Union[str, List]]:
    """Create a simpler dictionary-based entity representation.

    Args:
        entity_id: The ID of the entity.

    Returns:
        A dictionary-based entity representation.
    """
    properties = get_dbpedia_entity(entity_id)

    entity = {}

    for key, value in properties.items():
        # If we have more than one value then append a list to the key
        if len(value) > 1:
            entity.update({key: [element['value'] for element in value]})
        else:
            entity.update({key: value[0]['value']})

    return entity


def has_type(properties: Dict[str, Any], target_type: str) -> bool:
    """Check whether properties contain specific type

    Args:
        properties: Dictionary with properties
        target_type: Type to check for.

    Returns:
        True if target type in properties.
    """
    if TYPE_PREDICATE not in properties:
        return False
    for p in properties[TYPE_PREDICATE]:
        if p["value"] == target_type:
            return True
    return False


def resolve_uri(uri: str) -> str:
    """Resolves uri."""
    uri = uri.split("/")[-1].replace("_", " ")
    if uri.startswith("Category:"):
        uri = uri[len("Category:") :]
    return uri


def fielded_doc_entity(entity_id: str, **kwargs) -> Dict[str, str]:
    """fielded document representation should include the fields `names`,
    `description`, `attributes`, `related_entities`, `types`, and `catch_all`.


    They should contain the following:
            * `names`: the objects of `NAME_PREDICATES`,
            * `description`: the object(s) of `COMMENT_PREDICATE`, 
            * `attributes`: objects that are literal values, 
            * `related_entities`: objects that are entities, 
            * `types`: the objects of `TYPE_PREDICATES`, and
            * `catch_all`: all of the above. 
                properties = get_dbpedia_entity(entity_id, **kwargs)

    Args:
        entity_id: The ID of the entity.
        **kwargs: Additional keyword arguments. Notably, session to provide to
            get_dbpedia_entity() function

    Returns:
        Dictionary with the above stated keys.
    """
    properties = get_dbpedia_entity(entity_id, **kwargs)
    doc_entity = {'names': "", 'description': "", 'attributes': "", 'related_entities': "", 'types': "", 'catch_all': ""}
    checked_values = ""

    for predicate in properties:
        for dic in properties[predicate]:
            # Resolve the uri in case
            if dic['type'] == 'uri':
                value = resolve_uri(dic['value']) + ' '
            else:
                value = str(dic['value']) + ' '

            if predicate in NAME_PREDICATES:
                doc_entity['names'] += value
            elif predicate in TYPE_PREDICATES:
                doc_entity['types'] += value
            elif predicate in COMMENT_PREDICATE:
                doc_entity['description'] += value
            elif dic['type'] == 'literal':
                doc_entity['attributes'] += value
            elif dic['type'] == 'uri':
                doc_entity['related_entities'] += value
            doc_entity['catch_all'] += value

    return doc_entity


def bulk_index(es: Elasticsearch, pages: List[str]) -> None:
    """Iterate over pages, finding entities, and index those that are of the
    right type.

    NB! This may take some time.
    Hint: Since there will be many requests made against
        http://dbpedia.org you can speed up the process by using the same
        session (requests.Session()).
        This is expected to index a total of 1965 unique entities.

    Args:
        es: Elasticsearch instance.
        pages: A string of text.
    """
    num_entities = 0
    entities_counter = 0
    artists = set()
    for page in pages:
        sub_list_page_url = page.replace(" ", "_").replace("&", "and")
        sub_list_page = WIKI_WIKI.page(sub_list_page_url)
        artists.update(sub_list_page.links.keys())
        if len(artists) > 6000:
            break

    print("{} artists found.".format(len(artists)))
    session = requests.Session()
    for artist in sorted(artists):

        # Get the properties from dbpedia
        properties = get_dbpedia_entity(artist, session)
        
        # Index the entity data
        if properties != None:
            # Check if the entity is of type MusicalArtist
            if has_type(properties, 'http://dbpedia.org/ontology/MusicalArtist'):

                es.index(index=INDEX_NAME, id=artist, body=fielded_doc_entity(artist, session=session))
                num_entities += 1

                # Print percentage of entities indexed
                if num_entities % (2185 // 100) == 0:
                    print(f"{round(100*(num_entities/2185))}% indexed.")

    print("{} entities indexed.".format(num_entities))


def baseline_retrieval(
    es: Elasticsearch, index_name: str, query: str, k: int = 100
) -> List[str]:
    """Performs baseline retrival on index.

    Args:
        es: Elasticsearch instance.
        index_name: A string of text.
        query: A string of text, space separated terms.
        k: An integer.

    Returns:
        A list of entity IDs as strings, up to k of them, in descending order of
            scores.
    """
    res_list = None   # Variable for keeping the results
    query_terms = query.split(" ")  # Terms of the query splitted in an array
    matches = [{'match': {'catch_all': term}} for term in query_terms]  # List of matches in DSL

    # Build the elastic search query
    es_query = {
        "bool": {
            "must": matches
        }
    }

    # Search using the compound query
    res = es.search(index=INDEX_NAME, body={"query": es_query}, size=k)

    # Create a list with the entity ids
    # NOTICE: We don't need to sort cause the results are already returned sorted by score by elasticsearch
    if res['_shards']['successful'] > 0:    # We have found a result
        res_list = [entity['_id'] for entity in res['hits']['hits']]

    return res_list


def analyze_query(es: Elasticsearch, query: str) -> List[str]:
    """Analyzes query and returns a list of tokens"""
    tokens = es.indices.analyze(index=INDEX_NAME, body={"text": query})[
        "tokens"
    ]
    query_terms = []
    for t in sorted(tokens, key=lambda x: x["position"]):
        query_terms.append(t["token"])
    return query_terms


class CollectionLM:
    def __init__(
        self, es: Elasticsearch, qterms: List[str], fields: List[str] = None
    ) -> None:
        """This class is used for obtaining collection language modeling
        probabilities $P(t|C_i)$."""
        self._es = es
        self._probs = {}
        self._fields = fields or [
            "names",
            "description",
            "attributes",
            "related_entities",
            "types",
            "catch_all",
        ]
        # computing P(t|C_i) for each field and for each query term
        for field in self._fields:
            self._probs[field] = {}
            for t in qterms:
                self._probs[field][t] = self._get_prob(field, t)

    @property
    def fields(self) -> List[str]:
        return self._fields

    def _get_prob(self, field: str, term: str) -> float:
        """computes the collection Language Model probability of a term for a
        given field.
        """
        # use a boolean query to find a document that contains the term
        hits = (
            self._es.search(
                index=INDEX_NAME,
                query={"match": {field: term}},
                _source=False,
                size=1,
            )
            .get("hits", {})
            .get("hits", {})
        )
        doc_id = hits[0]["_id"] if len(hits) > 0 else None

        clm_prob = 0
        # Get the term statistics
        if doc_id != None:
            tv = self._es.termvectors(index=INDEX_NAME, doc_type='_doc', id=doc_id, fields=field, term_statistics=True)

            # Check if the term is in the term_vectors, 0 otherwise
            f_t_di_SUM = tv['term_vectors'][field]['terms'][term]['ttf'] if term in tv['term_vectors'][field]['terms'] else 0
            d_i_SUM = tv['term_vectors'][field]['field_statistics']['sum_ttf']

            clm_prob = f_t_di_SUM / d_i_SUM
            # ask for global term statistics when requesting the term vector of
            # that doc (`term_statistics=True`)

        return clm_prob

    def prob(self, field: str, term: str) -> float:
        """Return probability for a given field and term"""
        return self._probs.get(field, {}).get(term, 0)


def get_term_mapping_probs(clm: CollectionLM, term: str) -> Dict[str, float]:
    """PRMS: For a single term, find their mapping probabilities for all fields.

    Args:
        clm: Collection language model instance.
        term: A single-term string.

    Returns:
        Dictionary of mapping probabilities for the fields.
    """
    mapp_probs = {}

    f_backgroundSUM = 0
    for field in clm.fields:
        f_backgroundSUM += clm.prob(field, term) * (1/len(clm.fields))

    for field in clm.fields:
        mapp_prob = clm.prob(field, term)*(1/len(clm.fields)) / f_backgroundSUM
        mapp_probs.update({field: mapp_prob})

    return mapp_probs


def score_prms(
    es, clm: CollectionLM, qterms: List[str], doc_id: str, mu: int = 100
) -> float:
    # Getting term frequency statistics for the given document field from
    # Elasticsearch
    # Note that global term statistics are not needed (`term_statistics=False`)
    tv = es.termvectors(
        index=INDEX_NAME, id=doc_id, fields=clm.fields, term_statistics=False
    ).get("term_vectors", {})

    # compute field lengths $|d_i|$
    len_d_i = []  # document field length
    for i, field in enumerate(clm.fields):
        if field in tv:
            len_d_i.append(
                sum([s["term_freq"] for _, s in tv[field]["terms"].items()])
            )
        else:  # that document field may be empty
            len_d_i.append(0)

    # scoring the query
    score = 0  # log P(q|d)
    for t in qterms:
        Pt_theta_d = 0  # P(t|\theta_d)
        # Get field mapping probs.
        Pf_t = get_term_mapping_probs(clm, t)
        for i, field in enumerate(clm.fields):
            if field in tv:
                ft_di = (
                    tv[field]["terms"].get(t, {}).get("term_freq", 0)
                )  # $f_{t,d_i}$
            else:  # that document field is empty
                ft_di = 0
            Pt_Ci = clm.prob(field, t)  # $P(t|C_i)$
            Pt_theta_di = (ft_di + mu * Pt_Ci) / (
                mu + len_d_i[i]
            )  # $P(t|\theta_{d_i})$ with Dirichlet smoothing
            Pt_theta_d += Pf_t[field] * Pt_theta_di
        score += math.log(Pt_theta_d)

    return score


def prms_retrieval(es: Elasticsearch, query: str) -> List[str]:
    # Analyze query
    query_terms = analyze_query(es, query)

    # Perform initial retrieval using ES
    res = es.search(
        index=INDEX_NAME, q=query, df="catch_all", _source=False, size=200
    ).get("hits", {})

    # Instantiate collectionLM class
    clm = CollectionLM(es, query_terms)

    # Rerank results using PRMS
    scores = {}
    for doc in res.get("hits", {}):
        doc_id = doc.get("_id")
        scores[doc_id] = score_prms(es, clm, query_terms, doc_id)

    return [
        x[0] for x in sorted(scores.items(), key=itemgetter(1, 0), reverse=True)
    ]


def reset_index(es: Elasticsearch) -> None:
    """Clears index"""
    if es.indices.exists(INDEX_NAME):
        es.indices.delete(index=INDEX_NAME)

    es.indices.create(index=INDEX_NAME, body=INDEX_SETTINGS)


if __name__ == "__main__":
    es = Elasticsearch()
    es.info()

    pages = get_lists_of_musicians()

    example_pages = "\n".join(pages[:10])
    print(f"Example pages:\n\n{example_pages}\n\n")

    reset_index(es)
    bulk_index(es, pages)
