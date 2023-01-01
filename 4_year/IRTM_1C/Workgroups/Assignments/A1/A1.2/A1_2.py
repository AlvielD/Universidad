from typing import List


def get_confusion_matrix(
    actual: List[int], predicted: List[int]
) -> List[List[int]]:
    """Computes confusion matrix from lists of actual or predicted labels.

    Args:
        actual: List of integers (0 or 1) representing the actual classes of
            some instances.
        predicted: List of integers (0 or 1) representing the predicted classes
            of the corresponding instances.

    Returns:
        List of two lists of length 2 each, representing the confusion matrix.
    """
    
    confusion_matrix = [[0, 0], [0 ,0]]

    # Decide which value increment of the matrix
    for pred_label, act_label in zip(predicted, actual):
        if (pred_label == 0) and (act_label == 0):
            confusion_matrix[0][0] += 1
        elif (pred_label == 1) and (act_label == 0):
            confusion_matrix[0][1] += 1
        elif (pred_label == 0) and (act_label == 1):
            confusion_matrix[1][0] += 1
        elif (pred_label == 1) and (act_label == 1):
            confusion_matrix[1][1] += 1

    return confusion_matrix


def accuracy(actual: List[int], predicted: List[int]) -> float:
    """Computes the accuracy from lists of actual or predicted labels.

    Args:
        actual: List of integers (0 or 1) representing the actual classes of
            some instances.
        predicted: List of integers (0 or 1) representing the predicted classes
            of the corresponding instances.

    Returns:
        Accuracy as a float.
    """
    
    # Gets the confusion matrix
    confusion_matrix = get_confusion_matrix(actual, predicted)

    # Sum up all the true values --> [0][0] & [1][1]
    true_values = confusion_matrix[0][0] + confusion_matrix[1][1]

    values = 0

    # Sum up all the values of the matrix
    for row in confusion_matrix:
        values += sum(row)

    return (true_values / values)


def precision(actual: List[int], predicted: List[int]) -> float:
    """Computes the precision from lists of actual or predicted labels.

    Args:
        actual: List of integers (0 or 1) representing the actual classes of
            some instances.
        predicted: List of integers (0 or 1) representing the predicted classes
            of the corresponding instances.

    Returns:
        Precision as a float.
    """
    
    # Gets confusion matrix
    confusion_matrix = get_confusion_matrix(actual, predicted)

    return (confusion_matrix[1][1] / (confusion_matrix[1][1] + confusion_matrix[0][1]))


def recall(actual: List[int], predicted: List[int]) -> float:
    """Computes the recall from lists of actual or predicted labels.

    Args:
        actual: List of integers (0 or 1) representing the actual classes of
            some instances.
        predicted: List of integers (0 or 1) representing the predicted classes
            of the corresponding instances.

    Returns:
        Recall as a float.
    """
    
    # Gets confusion matrix
    confusion_matrix = get_confusion_matrix(actual, predicted)

    return (confusion_matrix[1][1] / (confusion_matrix[1][1] + confusion_matrix[1][0]))


def f1(actual: List[int], predicted: List[int]) -> float:
    """Computes the F1-score from lists of actual or predicted labels.

    Args:
        actual: List of integers (0 or 1) representing the actual classes of
            some instances.
        predicted: List of integers (0 or 1) representing the predicted classes
            of the corresponding instances.

    Returns:
        float of harmonic mean of precision and recall.
    """
    
    # Gets precision
    prec = precision(actual, predicted)

    # Gets recall
    rec = recall(actual, predicted)

    # Computes the F1-score
    return ((2*prec*rec) / (prec + rec))



def false_positive_rate(actual: List[int], predicted: List[int]) -> float:
    """Computes the false positive rate from lists of actual or predicted
        labels.

    Args:
        actual: List of integers (0 or 1) representing the actual classes of
            some instances.
        predicted: List of integers (0 or 1) representing the predicted classes
            of the corresponding instances.

    Returns:
        float of number of instances incorrectly classified as positive divided
            by number of actually negative instances.
    """
    
    # Gets confusion matrix
    confusion_matrix = get_confusion_matrix(actual, predicted)

    return (confusion_matrix[0][1] / (confusion_matrix[0][1] + confusion_matrix[0][0]))



def false_negative_rate(actual: List[int], predicted: List[int]) -> float:
    """Computes the false negative rate from lists of actual or predicted
        labels.

    Args:
        actual: List of integers (0 or 1) representing the actual classes of
            some instances.
        predicted: List of integers (0 or 1) representing the predicted classes
            of the corresponding instances.

    Returns:
        float of number of instances incorrectly classified as negative divided
            by number of actually positive instances.
    """
    
    # Gets confusion matrix
    confusion_matrix = get_confusion_matrix(actual, predicted)

    return (confusion_matrix[1][0] / (confusion_matrix[1][0] + confusion_matrix[1][1]))
