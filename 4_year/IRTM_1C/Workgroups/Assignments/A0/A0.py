from typing import List, Union

def get_unique_elements(
    lst: List[Union[str, int]], n: int = 1
) -> List[Union[str, int]]:
    """Given a list of elements returns those that repeat at least n times. The
    output list should contain all unique elements and they should be returned
    in the same order as they first appear in the input list.

    Args:
        lst: Input list
        n (optional): Minimum number of times an element should be repeated to 
            be returned. Defaults to 1.

    Returns:
        List of unique items 
    """

    r_lst = []

    for term in lst:
        if term not in r_lst and lst.count(term) >= n:
            r_lst.append(term)

    return r_lst