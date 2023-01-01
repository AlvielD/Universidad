import pytest
import math
import A2_2 as module


@pytest.fixture
def collection():
    return module.DocumentCollection(
        {
            "d1": {"body": ["t3", "t3", "t3", "t6", "t6"]},
            "d2": {"body": ["t1", "t2", "t3", "t3", "t6"]},
            "d3": {"body": ["t3", "t3", "t4", "t5"]},
            "d4": {"body": ["t4", "t5", "t6", "t6"]},
            "d5": {"body": ["t1", "t2", "t3", "t5"]},
        }
    )


@pytest.fixture
def index():
    return {
        "body": {
            "t1": [("d2", 1), ("d5", 1)],
            "t2": [("d2", 1), ("d5", 1)],
            "t3": [("d1", 3), ("d2", 2), ("d3", 2), ("d5", 1)],
            "t4": [("d3", 1), ("d4", 1)],
            "t5": [("d3", 1), ("d4", 1), ("d5", 1)],
            "t6": [("d1", 2), ("d2", 1), ("d4", 2)],
        }
    }


@pytest.fixture
def collection_2():
    return module.DocumentCollection(
        {
            "d1": {
                "title": ["t1"],
                "body": ["t1", "t2", "t3", "t1", "t3"],
                "anchors": ["t2", "t2"],
            },
            "d2": {
                "title": ["t4", "t5"],
                "body": ["t1", "t3", "t4", "t4", "t4", "t5"],
                "anchors": ["t5", "t3"],
            },
            "d3": {
                "title": ["t1", "t3", "t5"],
                "body": ["t1", "t1", "t5", "t3", "t5", "t3", "t3"],
                "anchors": ["t1", "t1", "t5"],
            },
        }
    )


@pytest.fixture
def index_2():
    return {
        "title": {
            "t1": [("d1", 1), ("d3", 1)],
            "t3": [("d3", 1)],
            "t4": [("d2", 1)],
            "t5": [("d2", 1), ("d3", 1)],
        },
        "body": {
            "t1": [("d1", 2), ("d2", 1), ("d3", 2)],
            "t2": [("d1", 1)],
            "t3": [("d1", 2), ("d2", 1), ("d3", 3)],
            "t4": [("d2", 3)],
            "t5": [("d2", 1), ("d3", 2)],
        },
        "anchors": {
            "t1": [("d3", 2)],
            "t2": [("d1", 2)],
            "t3": [("d2", 1)],
            "t5": [("d2", 1), ("d3", 1)],
        },
    }


def test_simple(collection, index):
    """This test is 0.3 points"""
    scorer = module.SimpleScorer(collection, index, field="body")
    assert scorer.score_collection(["t7", "t3", "t3"])["d1"] == 6
    assert scorer.score_collection(["t7", "t3", "t3"])["d2"] == 4


def test_bm25(collection, index):
    """This test is 0.7 points"""
    scorer_bm25 = module.ScorerBM25(collection, index, field="body")

    scores_bm25_1 = scorer_bm25.score_collection(["t3"])
    assert scores_bm25_1["d1"] == pytest.approx(0.3406, rel=1e-3)

    scores_bm25_2 = scorer_bm25.score_collection(["t2", "t1"])
    assert scores_bm25_2["d2"] == pytest.approx(1.7357, rel=1e-3)

    scores_bm25_3 = scorer_bm25.score_collection(["t6"])
    assert max(scores_bm25_3.items(), key=lambda p: p[1])[0] == "d4"

    scores_bm25_4 = scorer_bm25.score_collection(["t3", "t1", "t3", "t2"])
    assert max(scores_bm25_4.items(), key=lambda p: p[1])[0] == "d5"


def test_lm(collection, index):
    """This test is 0.7 points"""
    scorer_lm = module.ScorerLM(collection, index, field="body")

    scores_lm_1 = scorer_lm.score_collection(["t3"])
    assert scores_lm_1["d1"] == pytest.approx(math.log(0.576), rel=1e-2)

    scores_lm_2 = scorer_lm.score_collection(["t2", "t1"])
    assert scores_lm_2["d2"] == pytest.approx(math.log(0.036), rel=1e-2)

    scores_lm_3 = scorer_lm.score_collection(["t6"])
    assert max(scores_lm_3.items(), key=lambda p: p[1])[0] == "d4"

    scores_lm_4 = scorer_lm.score_collection(["t3", "t1", "t3", "t2"])
    assert max(scores_lm_4.items(), key=lambda p: p[1])[0] == "d2"


def test_bm25f(collection_2, index_2):
    """This test is 0.9 points"""
    scorer_bm25f = module.ScorerBM25F(
        collection_2,
        index_2,
        fields=["title", "body", "anchors"],
        field_weights=[0.1, 0.7, 0.2],
        bi=[0.75, 0.75, 0.75],
    )

    scores_bm25f_1 = scorer_bm25f.score_collection(["t3"])
    assert scores_bm25f_1["d1"] == pytest.approx(0, rel=1e-3)

    scores_bm25f_2 = scorer_bm25f.score_collection(["t2", "t4"])
    assert scores_bm25f_2["d1"] == pytest.approx(0.560, abs=1e-3)
    assert scores_bm25f_2["d2"] == pytest.approx(0.7108, abs=1e-3)

    scores_bm25f_3 = scorer_bm25f.score_collection(["t2", "t5", "t4", "t5"])
    assert max(scores_bm25f_3.items(), key=lambda p: p[1])[0] == "d2"


def test_mlm(collection_2, index_2):
    """This test is 0.9 points"""
    scorer_mlm = module.ScorerMLM(
        collection_2,
        index_2,
        fields=["title", "body", "anchors"],
        field_weights=[0.1, 0.7, 0.2],
    )

    scores_mlm_1 = scorer_mlm.score_collection(["t3"])
    assert scores_mlm_1["d1"] == pytest.approx(math.log(0.2798), rel=1e-3)

    scores_mlm_2 = scorer_mlm.score_collection(["t2", "t1"])
    assert scores_mlm_2["d2"] == pytest.approx(math.log(0.00128), abs=1e-2)

    scores_mlm_3 = scorer_mlm.score_collection(["t4"])
    assert max(scores_mlm_3.items(), key=lambda p: p[1])[0] == "d2"

    scores_mlm_4 = scorer_mlm.score_collection(["t1", "t2", "t4"])
    assert max(scores_mlm_4.items(), key=lambda p: p[1])[0] == "d1"
