import pytest
from A0 import get_unique_elements

@pytest.fixture
def lst():
    return [1, 1, 1, 1, 2, 2, 2, 3, 3, 3, "test1", "test2", "test2"]


def test_get_unique_elements_empty():
    assert get_unique_elements([]) == []


def test_get_unique_elements_default(lst):
    assert get_unique_elements(lst) == [1, 2, 3, "test1", "test2"]


def test_get_unique_elements_n_2(lst):
    assert get_unique_elements(lst, 2) == [1, 2, 3, "test2"]


def test_get_unique_elements_n_3(lst):
    assert get_unique_elements(lst, 3) == [1, 2, 3]


def test_get_unique_elements_n_10(lst):
    assert get_unique_elements(lst, 10) == []