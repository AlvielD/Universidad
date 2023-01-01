# Required packages:
from typing import Any, List, Tuple, Union
from numpy import ndarray

# For preprocessing text
import string
import re
from nltk.corpus import stopwords

# For training the model
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline

# For evaluating the model
from sklearn import metrics


def load_data(path: str) -> Tuple[List[str], List[str]]:
    """Loads data from file. Each except first (header) is a datapoint
    containing ID, Label, Email (content) separated by "\t".

    Args:
        path: Path to file from which to load data

    Returns:
        List of email contents and a list of labels coresponding to each email.
    """

    emails = []
    labels = []

    with open (path, "r", encoding="mbcs") as file:
        data = file.readlines()
        for line in data[1:]:
            line = line.split("\t")     # Split the line by withespaces
            if line[2][0] == '"':
                line[2] = line[2][1:-2] 
            emails.append(line[2])      # Append the content of the email

            # If the label is "spam" --> append 1, else append 0
            if line[1] == "spam":
                labels.append(1)
            else:
                labels.append(0)

    return emails, labels


def preprocess(doc: str) -> str:
    """Preprocesses text to prepare it for feature extraction.

    Args:
        doc: String comprising the unprocessed contents of some email file.

    Returns:
        String comprising the corresponding preprocessed text.
    """
    
    ## TOKENIZATION ##

    # Replace punctuation marks with spaces using regular expresion
    # First, turn to lowercase so we can replace with the same regex
    # ^ --> not
    # a-z all letter from a to z
    # 0-9 all numbers from 0 to 9
    doc = doc.lower()
    doc = re.sub("[^a-z0-9]", " ", doc)
    
    # Split by whitespaces.
    doc = doc.split()


    ## STOPWORD REMOVAL ##

    stop_words = stopwords.words("english")
    doc = [word for word in doc if word not in stop_words]
    
    return " ".join(doc)




def preprocess_multiple(docs: List[str]) -> List[str]:
    """Preprocesses multiple texts to prepare them for feature extraction.

    Args:
        docs: List of strings, each consisting of the unprocessed contents
            of some email file.

    Returns:
        List of strings, each comprising the corresponding preprocessed
            text.
    """
    
    preprocessed_docs = []

    for doc in docs:
        preprocessed_docs.append(preprocess(doc))

    return preprocessed_docs


def extract_features(
    train_dataset: List[str], test_dataset: List[str]
) -> Tuple[ndarray, ndarray]:
    """Extracts feature vectors from a preprocessed train and test datasets.

    Args:
        train_dataset: List of strings, each consisting of the preprocessed
            email content.
        test_dataset: List of strings, each consisting of the preprocessed
            email content.

    Returns:

    """

    # Try to use the CountVectorizer class of the sklearn library to get the doc term matrix
    count_vect = CountVectorizer()

    ## CREATION OF THE DOCUMENT-TERM MATRIX ##
    train_doc_term_matrix = count_vect.fit_transform(train_dataset)
    test_doc_term_matrix = count_vect.transform(test_dataset)

    return tuple((train_doc_term_matrix, test_doc_term_matrix))



def train(X: ndarray, y: List[int]) -> object:
    """Trains a classifier on extracted feature vectors.

    Args:
        X: Numerical array-like object (2D) representing the instances.
        y: Numerical array-like object (1D) representing the labels.

    Returns:
        A trained model object capable of predicting over unseen sets of
            instances.
    """

    # Model pipeline
    model = Pipeline([
    ('tfidf', TfidfTransformer()),
    ('clf', MultinomialNB()),
    ])

    # Train the model
    model.fit(X, y)

    return model


def evaluate(
    y: List[int], y_pred: List[int]
) -> Tuple[float, float, float, float]:
    """Evaluates a model's predictive performance with respect to a labeled
    dataset.

    Args:
        y: Numerical array-like object (1D) representing the true labels.
        y_pred: Numerical array-like object (1D) representing the predicted
            labels.

    Returns:
        A tuple of four values: recall, precision, F_1, and accuracy.
    """

    rec = metrics.recall_score(y, y_pred)
    prec = metrics.precision_score(y, y_pred)
    f1 = metrics.f1_score(y, y_pred)
    acc = metrics.accuracy_score(y, y_pred)

    return rec, prec, f1, acc


if __name__ == "__main__":
    print("Loading data...")
    train_data_raw, train_labels = load_data("data/train.tsv")
    test_data_raw, test_labels = load_data("data/test.tsv")

    print("Processing data...")
    train_data = preprocess_multiple(train_data_raw)
    test_data = preprocess_multiple(test_data_raw)

    print("Extracting features...")
    train_feature_vectors, test_feature_vectors = extract_features(
        train_data, test_data
    )

    print("Training...")
    classifier = train(train_feature_vectors, train_labels)

    print("Applying model on test data...")
    predicted_labels = classifier.predict(test_feature_vectors)

    print("Evaluating")
    recall, precision, f1, accuracy = evaluate(test_labels, predicted_labels)

    print(f"Recall:\t{recall}")
    print(f"Precision:\t{precision}")
    print(f"F1:\t{f1}")
    print(f"Accuracy:\t{accuracy}")