import pytest

import A2_1 as module

is_indexed = False

@pytest.fixture
def index():
    global is_indexed
    if not is_indexed:
        module.index_collection(
            filename="inverted_index.sqlite", num_documents=50000
        )
        is_indexed = True

    with module.InvertedIndex("inverted_index.sqlite") as invindex:
        yield invindex


def test_num_terms(index):
    """This test is 0.6 points."""
    assert len(index.get_terms("title")) == 29620


def test_num_postings(index):
    """This test is 0.7 points."""
    postings = index.get_postings("title", "technologists")
    assert len(postings) == 1


def test_posting(index):
    """This test is 0.7 points."""
    freq = index.get_term_frequency(
        "title", "categorization", "d561bb48-eaec-11e1-866f-60a00f604425"
    )
    assert freq == 1
