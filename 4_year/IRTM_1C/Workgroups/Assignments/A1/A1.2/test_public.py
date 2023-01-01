from typing import List

import pytest

import A1_2 as module


@pytest.fixture
def actual():
    return [1, 1, 0, 1, 1, 1, 0, 0, 1, 1]


@pytest.fixture
def predicted():
    return [0, 1, 0, 1, 0, 1, 0, 1, 0, 0]


def test_get_confusion_matrix(actual: List[int], predicted: List[int]):
    """This test is 0.3 points."""
    assert module.get_confusion_matrix(actual, predicted) == [
        [2, 1],
        [4, 3],
    ]


def test_accuracy(actual: List[int], predicted: List[int]):
    """This test is 0.2 points."""
    assert module.accuracy(actual, predicted) == 1 / 2


def test_precision(actual: List[int], predicted: List[int]):
    """This test is 0.2 points."""
    assert module.precision(actual, predicted) == 3 / 4


def test_recall(actual: List[int], predicted: List[int]):
    """This test is 0.2 points."""
    assert module.recall(actual, predicted) == 3 / 7


def test_f1(actual: List[int], predicted: List[int]):
    """This test is 0.2 points."""
    assert module.f1(actual, predicted) == pytest.approx(6 / 11)


def test_false_positive_rate(actual: List[int], predicted: List[int]):
    """This test is 0.2 points."""
    assert module.false_positive_rate(actual, predicted) == 1 / 3


def test_false_negative_rate(actual: List[int], predicted: List[int]):
    """This test is 0.2 points."""
    assert module.false_negative_rate(actual, predicted) == 4 / 7
