from typing import Dict, List
import string

def get_word_frequencies(doc: str) -> Dict[str, int]:
    """Extracts word frequencies from a document.
    
    Args:
        doc: Document content given as a string.
    
    Returns:
        Dictionary with words as keys and their frequencies as values.
    """
    
    # Replace all punctuation marks with whitespaces
    for p_mark in string.punctuation:
        doc = doc.replace(p_mark, " ")
    
    # Split based on whitespaces --> result is an array of terms
    doc = doc.split()

    # Count each term and add to the dictionary

    result = {}     # Empty dictionary

    for term in doc:
        if term not in result:
            times = doc.count(term)
            result.update({term: times})

    return result




def get_word_feature_vector(
    word_frequencies: Dict[str, int], vocabulary: List[str]
) -> List[int]:
    """Creates a feature vector for a document, comprising word frequencies 
        over a vocabulary.
    
    Args:
        word_frequencies: Dictionary with words as keys and frequencies as 
            values.
        vocabulary: List of words.
    
    Returns:
        List of length `len(vocabulary)` with respective frequencies as values.
    """
    
    result = []     # Empty list

    # For each term in the vocabulary, repeat
    for term in vocabulary:
        # If the term is not in the word_frequencies --> Append 0 to the result list
        if term not in word_frequencies:
            result.append(0)
        # If the term is in the word_frequencies --> Append its frequency
        else:
            result.append(word_frequencies.get(term))

    return result