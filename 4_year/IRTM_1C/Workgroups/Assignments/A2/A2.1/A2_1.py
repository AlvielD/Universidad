import os
import re
from dataclasses import dataclass
from typing import Any, List, Set, Union

import ir_datasets
import requests
from nltk.corpus import stopwords
from sqlitedict import SqliteDict


STOPWORDS = set(stopwords.words("english"))

def download_dataset(filename: str, force: bool = False) -> None:
    """Download a dataset to be used with ir_datasets.

    Args:
        filename: Name of the file to download.
        force (optional): Downloads a file and overwrites if already exists.
            Defaults to False.
    """
    filepath = os.path.expanduser(f"~/.ir_datasets/wapo/{filename}")
    if not force and os.path.isfile(filepath):
        return

    response = requests.get(f"https://gustav1.ux.uis.no/dat640/{filename}")
    if response.ok:
        print("File downloaded; saving to file...")
    with open(filepath, "wb") as f:
        f.write(response.content)

    print("First document:\n")
    print(next(ir_datasets.load("wapo/v2/trec-core-2018").docs_iter()))


def preprocess(doc: str) -> List[str]:
    """Preprocesses a string of text.

    Arguments:
        doc: A string of text.

    Returns:
        List of strings.
    """
    return [
        term
        for term in re.sub(r"[^\w]|_", " ", doc).lower().split()
        if term not in STOPWORDS
    ]


@dataclass
class Posting:
    doc_id: Union[str, int]
    payload: Any = None


class InvertedIndex(SqliteDict):
    # Class constructor
    def __init__(
        self,
        filename: str = "inverted_index.sqlite",
        fields: List[str] = ["title", "body"],
        new: bool = False,
    ) -> None:
        super().__init__(filename, flag="n" if new else "c")
        self.fields = fields
        self.index = {} if new else self

    def build_key(self, term: str, field: str):
        return f"{field}_{term}"

    # TODO Method to populate index
    def populate_index(self, doc: ir_datasets):
        for field in self.fields:
            # This method returns the atribute of the class with a given string
            if getattr(doc, field):
                docfield_terms = preprocess(getattr(doc, field))
                for term in docfield_terms:
                    posting = Posting(doc.doc_id, docfield_terms.count(term))
                    self.index.setdefault(self.build_key(term, field), []).append(posting)

        return

    def get_postings(self, field: str, term: str) -> List[str]:
        """Fetches the posting list for a given field and term.

        Args:
            field: Field for which to get postings.
            term: Term for which to get postings.

        Returns:
            List of postings for the given term in the given field.
        """
        return self.index.get(self.build_key(term, field))

    def get_term_frequency(self, field: str, term: str, doc_id: str) -> int:
        """Return the frequency of a given term in a document.

        Args:
            field: Index field.
            term: Term for which to find the count.
            doc_id: Document ID

        Returns:
            Term count in a document.
        """
        # Go through the posting list of the term and field requested
        for posting in self.index.get(self.build_key(term, field)):
            if posting.doc_id == doc_id:
                # We have found the document
                return posting.payload


    def get_terms(self, field: str) -> Set[str]:
        """Returns all unique terms in the index.

        Args:
            field: Field for which to return the terms.

        Returns:
            Set of all terms in a given field.
        """
        # Unique terms of the dictionary
        terms = set()

        # For each key, split by "_" and add the term to the set. This is because the keys of the dictionary
        # are of the type: field_term
        for key in self.index.keys():
            _field, _term = key.split("_")
            if _field == field:
                terms.add(_term)
                
        # Return the resulting set
        return terms

    def __exit__(self, *exc_info):
        if self.flag == "n":
            self.update(self.index)
            self.commit()
            print("Index updated.")
        super().__exit__(*exc_info)


def index_collection(
    collection: str = "wapo/v2/trec-core-2018",
    filename: str = "inverted_index.sqlite",
    num_documents: int = 595037,
) -> None:
    """Builds an inverted index from a document collection.

    Note: WashingtonPost collection has 595037 documents. This might take a very
        long time to index on an average computer.


    Args:
        collection: Collection from ir_datasets.
        filename: Sqlite filename to save index to.
        num_documents: Number of documents to index.
    """
    dataset = ir_datasets.load(collection)
    with InvertedIndex(filename, new=True) as index:
        for i, doc in enumerate(dataset.docs_iter()):
            if i % (num_documents // 100) == 0:
                print(f"{round(100*(i/num_documents))}% indexed.")
            if i == num_documents:
                break

            # Call the class method for populating the index
            index.populate_index(doc)
            

if __name__ == "__main__":
    print("Donwloading Dataset")
    download_dataset("WashingtonPost.v2.tar.gz")
    print("Indexing")
    index_collection()  # total 595037 docs
