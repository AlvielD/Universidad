import abc
import math
from collections import Counter, defaultdict
from typing import Dict, List
from collections import UserDict as DictClass

CollectionType = Dict[str, Dict[str, List[str]]]


class DocumentCollection(DictClass):
    """Document dictionary class with helper functions."""

    def total_field_length(self, field: str) -> int:
        """Total number of terms in a field for all documents."""
        return sum(len(fields[field]) for fields in self.values())

    def avg_field_length(self, field: str) -> float:
        """Average number of terms in a field across all documents."""
        return self.total_field_length(field) / len(self)

    def get_field_documents(self, field: str) -> Dict[str, List[str]]:
        """Dictionary of documents for a single field."""
        return {
            doc_id: doc[field] for (doc_id, doc) in self.items() if field in doc
        }


class Scorer(abc.ABC):
    def __init__(
        self,
        collection: DocumentCollection,
        index: CollectionType,
        field: str = None,
        fields: List[str] = None,
    ):
        """Interface for the scorer class.

        Args:
            collection: Collection of documents. Needed to calculate document
                statistical information.
            index: Index to use for calculating scores.
            field (optional): Single field to use in scoring.. Defaults to None.
            fields (optional): List of fields to use in scoring. Defaults to
                None.

        Raises:
            ValueError: Either field or fields need to be specified.
        """
        self.collection = collection
        self.index = index

        if not (field or fields):
            raise ValueError("Either field or fields have to be defined.")

        self.field = field
        self.fields = fields

        # Score accumulator for the query that is currently being scored.
        self.scores = None

    def score_collection(self, query_terms: List[str]):
        """Scores all documents in the collection using term-at-a-time query
        processing.

        Params:
            query_term: Sequence (list) of query terms.

        Returns:
            Dict with doc_ids as keys and retrieval scores as values.
            (It may be assumed that documents that are not present in this dict
            have a retrival score of 0.)
        """
        self.scores = defaultdict(float)  # Reset scores.
        query_term_freqs = Counter(query_terms)

        for term, query_freq in query_term_freqs.items():
            self.score_term(term, query_freq)

        return self.scores

    @abc.abstractmethod
    def score_term(self, term: str, query_freq: int):
        """Scores one query term and updates the accumulated document retrieval
        scores (`self.scores`).

        Params:
            term: Query term
            query_freq: Frequency (count) of the term in the query.
        """
        raise NotImplementedError


class SimpleScorer(Scorer):
    def score_term(self, term: str, query_freq: int) -> None:

        # Gets the collection of documents for the field given as an attribute.
        doc_collection = self.collection.get_field_documents(self.field)

        # For each document in the collection update the score for the given term.
        for (doc_id, doc) in doc_collection.items():

            # Computes the accumulated score for the document
            self.scores[doc_id] += query_freq * doc.count(term)
            
        return


class ScorerBM25(Scorer):
    def __init__(
        self,
        collection: DocumentCollection,
        index: CollectionType,
        field: str = "body",
        b: float = 0.75,
        k1: float = 1.2,
    ) -> None:
        super(ScorerBM25, self).__init__(collection, index, field)
        self.b = b
        self.k1 = k1

    def score_term(self, term: str, query_freq: int) -> None:

        # Compute idf_t
        # Total number of documents in the collection
        N = len(self.collection.items())

        # Number of times the given term appears in the collection
        n_t = len(self.index.get(self.field).get(term))

        # Calculate the value of the idf_t = log(N/n_t)
        idf_t = math.log(N/n_t)

        # Get the average document length
        avdl = self.collection.avg_field_length(self.field)

        for (doc_id, doc) in self.collection.items():
            # Compute the normalizer of the doc 1 - b + b(|d|/avdl) | |d| = len(doc.get(self.field))
            norm = 1 - self.b + self.b*(len(doc.get(self.field))/avdl)
            c_td = doc.get(self.field).count(term)

            self.scores[doc_id] += (c_td * (1 + self.k1)) / (c_td + self.k1*norm) * idf_t 

        return


class ScorerLM(Scorer):
    def __init__(
        self,
        collection: DocumentCollection,
        index: CollectionType,
        field: str = "body",
        smoothing_param: float = 0.1,
    ):
        super(ScorerLM, self).__init__(collection, index, field)
        self.smoothing_param = smoothing_param

    def score_term(self, term: str, query_freq: int) -> None:

        # Compute the collection term probability
        ctP = sum([d.get(self.field).count(term) for d in self.collection.values()]) / self.collection.total_field_length(self.field)

        # Score each document in the collection
        for (doc_id, doc) in self.collection.items():
            c_td = doc.get(self.field).count(term)
            # Compute query likelihood probability
            qlhP = (1-self.smoothing_param)*c_td/len(doc.get(self.field)) + self.smoothing_param*ctP
            # Update dictionary
            self.scores[doc_id] += query_freq * math.log(qlhP)

        return


class ScorerBM25F(Scorer):
    def __init__(
        self,
        collection: DocumentCollection,
        index: CollectionType,
        fields: List[str] = ["title", "body"],
        field_weights: List[float] = [0.2, 0.8],
        bi: List[float] = [0.75, 0.75],
        k1: float = 1.2,
    ) -> None:
        super(ScorerBM25F, self).__init__(collection, index, fields=fields)
        self.field_weights = field_weights
        self.bi = bi
        self.k1 = k1

    def score_term(self, term: str, query_freq: int) -> None:
        # Compute idf_t
        # Total number of documents in the collection
        N = len(self.collection.items())

        # Number of times the given term appears in the collection (Based on body field)
        n_t = len(self.index.get("body").get(term))

        # Calculate the value of the idf_t = log(N/n_t)
        idf_t = math.log(N/n_t)

        # Precompute the average document length per field
        avdl_i = [self.collection.avg_field_length(field) for field in self.fields]

        for (doc_id, doc) in self.collection.items():
            # Compute the pseudo-term frequency
            pseudo_c_td = 0
            for i in range(len(self.fields)):
                # Compute the B_i (soft_norm) of the doc 1 - b_i + b_i(|d_i|/avdl_i) | |d_i| = len(doc.get(field))
                soft_norm = 1 - self.bi[i] + self.bi[i]*(len(doc.get(self.fields[i]))/avdl_i[i])
                c_td = doc.get(self.fields[i]).count(term)
                pseudo_c_td += self.field_weights[i] * c_td / soft_norm

            self.scores[doc_id] += pseudo_c_td / (pseudo_c_td + self.k1) * idf_t 

        return


class ScorerMLM(Scorer):
    def __init__(
        self,
        collection: DocumentCollection,
        index: CollectionType,
        fields: List[str] = ["title", "body"],
        field_weights: List[float] = [0.2, 0.8],
        smoothing_param: float = 0.1,
    ):
        super(ScorerMLM, self).__init__(collection, index, fields=fields)
        self.field_weights = field_weights
        self.smoothing_param = smoothing_param

    def score_term(self, term: str, query_freq: float) -> None:
        
        # Score each document in the collection
        for (doc_id, doc) in self.collection.items():
            
            qlhP = 0
            for i in range(len(self.fields)):
                # Compute the collection term probability
                ctP = sum([d.get(self.fields[i]).count(term) for d in self.collection.values()]) / self.collection.total_field_length(self.fields[i])
                
                c_td = doc.get(self.fields[i]).count(term)
                # Compute query likelihood probability
                qlhP_i = (1-self.smoothing_param)*c_td/len(doc.get(self.fields[i])) + self.smoothing_param*ctP

                qlhP += self.field_weights[i]*qlhP_i

            # Update dictionary
            self.scores[doc_id] += query_freq * math.log(qlhP)

        return