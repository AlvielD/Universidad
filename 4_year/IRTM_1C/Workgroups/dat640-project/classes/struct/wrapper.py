import ir_datasets
import re
from typing import *
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from nltk.tokenize import word_tokenize

class MsMarcoWrapper:

    FIELDS = ["title","body"]
    STEMMER = PorterStemmer()
    STOPWORDS = set(stopwords.words("english"))

    def __init__(self, doc: ir_datasets.datasets.msmarco_document) -> None:
        self.__doc = doc
        self.__terms = {field: [] for field in self.FIELDS}
        self.__frequencies = self.__terms.copy()

    @property
    def doc_id(self): return self.__doc.doc_id

    @property
    def body(self): return self.__doc.body

    @property
    def title(self): return self.__doc.title

    @property
    def url(self): return self.__doc.url

    @property
    def frequencies(self): return self.__frequencies

    def get_terms(self, field: str, **kwargs) -> List[str]:
        """Returns preprocesses terms for a given field

        Args:
            field (str): field to preprocess

        Returns:
            List[str]: terms preprocessed
        """
        if not self.__terms.get(field):
            self.__terms[field] = self.preprocess(field, **kwargs)
        return self.__terms[field]

    def preprocess(self, field: str, stem: bool=False) -> List[str]:
        """Preprocesses the document by the given field

        Args:
            field (str): field to preprocess

        Returns:
            List[str]: List of terms
        """
        print("> Preprocessing: ", self.doc_id)
        terms = word_tokenize(getattr(self.__doc,field))
        filtered = []
        for term in terms:
            # apply filter
            term = term.lower()
            if term not in self.STOPWORDS and not re.match(r"\W",term):
                filtered.append(term)
        terms = filtered
        # stemmize terms
        if stem: terms = [self.STEMMER.stem(term) for term in terms]
        self.__frequencies[field] = Counter(terms)
        return terms

if __name__ == "__main__":
    DATASET = ir_datasets.load("msmarco-document/train")
    for i,doc in enumerate(DATASET.docs_iter()):
        if i == 2: break
        ms = MsMarcoWrapper(doc)
        terms = ms.get_terms("body")
        print(terms, len(terms))
        
        