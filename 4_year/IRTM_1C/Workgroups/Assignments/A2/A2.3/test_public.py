import pytest
import A2_3 as module


@pytest.fixture(scope="module")
def system_rankings():
    return module.load_rankings()


@pytest.fixture(scope="module")
def ground_truth():
    return module.load_ground_truth()


def test_load_rankings(system_rankings):
    """This test is 0 points"""
    assert len(system_rankings) == 50
    assert "823" in system_rankings
    assert len(system_rankings["426"]) == 100
    assert "2d54a5d4-430a-11e2-8061-253bccfc7532" in system_rankings["807"]


def test_ground_truth(ground_truth):
    """This test is 0 points"""
    assert len(ground_truth) == 50
    assert "804" in ground_truth
    assert len(ground_truth["442"]) == 153
    assert "23a36ac6-47bf-11e1-9ad3-2ae063ae0730" in ground_truth["375"]


def test_get_precision_100(system_rankings, ground_truth):
    """This test is 0.2 points"""
    precision = module.get_precision(
        system_rankings["646"], ground_truth["646"], k=100
    )
    assert precision == 0.14


def test_get_precision_10(system_rankings, ground_truth):
    """This test is 0.3 points"""
    precision = module.get_precision(
        system_rankings["393"], ground_truth["393"], k=10
    )
    assert precision == 0.8


def test_get_average_precision(system_rankings, ground_truth):
    """This test is 0.5 points"""
    precision = module.get_average_precision(
        system_rankings["801"], ground_truth["801"]
    )
    assert precision == pytest.approx(0.295079)


def test_get_reciprocal_rank(system_rankings, ground_truth):
    """This test is 0.5 points"""
    rr = module.get_reciprocal_rank(system_rankings["414"], ground_truth["414"])
    assert rr == 0.25


def test_get_mean_eval_measure(system_rankings, ground_truth):
    """This test is 0.5 points"""
    mean_eval = module.get_mean_eval_measure(
        system_rankings, ground_truth, module.get_reciprocal_rank
    )
    assert mean_eval == pytest.approx(0.744038)
