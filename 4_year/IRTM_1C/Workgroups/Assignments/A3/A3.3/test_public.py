import pytest
import A3_3 as module


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


@pytest.fixture
def single_field_scorer(collection, index):
    return module.SDMScorer(
        collection.get_field_documents("body"), index["body"]
    )


@pytest.fixture
def scorer(collection_2, index_2):
    return module.FSDMScorer(
        collection_2, index_2, fields=["body", "title", "anchors"]
    )


def test_sdm_unigram_matches(single_field_scorer):
    """This test is 0.5 points"""
    assert single_field_scorer.unigram_matches(
        ["t7", "t3", "t3"], "d1"
    ) == pytest.approx(-1.9622, abs=1e-4)
    assert single_field_scorer.unigram_matches(
        ["t7", "t3", "t3"], "d2"
    ) == pytest.approx(-2.0137, abs=1e-4)


def test_sdm_ordered_bigram_matches(single_field_scorer):
    """This test is 0.5 points"""
    assert single_field_scorer.ordered_bigram_matches(
        ["t7", "t3", "t3"], "d1"
    ) == pytest.approx(-1.6492, abs=1e-4)
    assert single_field_scorer.ordered_bigram_matches(["t6", "t2"], "d4") == 0


def test_sdm_unordered_bigram_matches(single_field_scorer):
    """This test is 0.5 points"""
    assert single_field_scorer.unordered_bigram_matches(
        ["t7", "t3", "t3"], "d1"
    ) == pytest.approx(-1.4064, abs=1e-4)
    assert single_field_scorer.unordered_bigram_matches(["t5", "t1"], "d3") == 0


def test_sdm_score_collection(single_field_scorer):
    """This test is 1 points"""
    assert single_field_scorer.score_collection(["t7", "t3", "t3"])[
        "d1"
    ] == pytest.approx(-1.9031, abs=1e-4)
    assert single_field_scorer.score_collection(["t3", "t5", "t2"])[
        "d4"
    ] == pytest.approx(-5.2229, abs=1e-4)


def test_fsdm_unigram_matches(scorer):
    """This test is 0.5 points"""
    assert scorer.unigram_matches(["t4", "t1", "t3"], "d1") == pytest.approx(
        -4.6721, abs=1e-4
    )
    assert scorer.unigram_matches(["t4", "t3"], "d2") == pytest.approx(
        -3.4821, abs=1e-4
    )


def test_fsdm_ordered_bigram_matches(scorer):
    """This test is 0.5 points"""
    assert scorer.ordered_bigram_matches(
        ["t2", "t1", "t3"], "d1"
    ) == pytest.approx(-1.97630, abs=1e-4)
    assert scorer.ordered_bigram_matches(["t6", "t2"], "d2") == 0


def test_fsdm_unordered_bigram_matches(scorer):
    """This test is 0.5 points"""
    assert scorer.unordered_bigram_matches(
        ["t1", "t3", "t3"], "d1"
    ) == pytest.approx(-5.1396, abs=1e-4)
    assert scorer.unordered_bigram_matches(["t5", "t1"], "d3") == pytest.approx(
        -1.74725, abs=1e-4 )


def test_fsdm_score_collection(scorer):
    """This test is 1 points"""
    assert scorer.score_collection(["t2", "t1", "t3"])["d1"] == pytest.approx(
        -5.4979, abs=1e-4
    )
    assert scorer.score_collection(["t3", "t5", "t2"])["d2"] == pytest.approx(
        -5.4874, abs=1e-4
    )
