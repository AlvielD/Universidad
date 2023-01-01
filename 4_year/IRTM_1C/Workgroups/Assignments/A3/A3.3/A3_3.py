import abc
from collections import UserDict as DictClass
from typing import Dict, List

import math
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
        feature_weights=[0.85, 0.1, 0.05],
        mu: float = 100,
        window: int = 3,
    ):
        """Interface for the scorer class.

        Args:
            collection: Collection of documents. Needed to calculate document
                statistical information.
            index: Index to use for calculating scores.
            feature_weights: Weights associated with each feature function
            mu: Smoothing parameter
            window: Window for unordered feature function.
        """
        self.collection = collection
        self.index = index

        if not sum(feature_weights) == 1:
            raise ValueError("Feature weights should sum to 1.")

        self.feature_weights = feature_weights
        self.mu = mu
        self.window = window

    def score_collection(self, query_terms: List[str]):
        """Scores all documents in the collection using document-at-a-time query
        processing.

        Params:
            query_term: Sequence (list) of query terms.

        Returns:
            Dict with doc_ids as keys and retrieval scores as values.
            (It may be assumed that documents that are not present in this dict
            have a retrival score of 0.)
        """
        lT, lO, lU = self.feature_weights
        return {
            doc_id: (
                lT * self.unigram_matches(query_terms, doc_id)
                + lO * self.ordered_bigram_matches(query_terms, doc_id)
                + lU * self.unordered_bigram_matches(query_terms, doc_id)
            )
            for doc_id in self.collection
        }

    @abc.abstractmethod
    def unigram_matches(self, query_terms: List[str], doc_id: str) -> float:
        """Returns unigram matches based on smoothed entity language model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for unigram matches for document with doc ID.
        """
        raise NotImplementedError

    @abc.abstractmethod
    def ordered_bigram_matches(self, query_terms: List[str], doc_id):
        """Returns ordered bigram matches based on smoothed entity language
        model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for ordered bigram matches for document with doc ID.
        """
        raise NotImplementedError

    @abc.abstractmethod
    def unordered_bigram_matches(self, query_terms: List[str], doc_id):
        """Returns unordered bigram matches based on smoothed entity language
        model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for unordered bigram matches for document with doc ID.
        """
        raise NotImplementedError


class SDMScorer(Scorer):
    def __init__(
        self,
        collection: DocumentCollection,
        index: CollectionType,
        feature_weights=[0.85, 0.1, 0.05],
        mu: float = 100,
        window: int = 3,
    ):
        """SDM scorer. This scorer can be applied on a single field only.

        Args:
            collection: Collection of documents. Needed to calculate document
                statistical information.
            index: Index to use for calculating scores.
            field (optional): Single field to use in scoring.. Defaults to None.
            fields (optional): List of fields to use in scoring. Defaults to
                None.
        """
        super().__init__(collection, index, feature_weights, mu, window)

    def unigram_matches(self, query_terms: List[str], doc_id: str) -> float:
        """Returns unigram matches based on smoothed entity language model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for unigram matches for document with doc ID.
        """
        f_T_SUM = 0

        # Compute f_T(q_i, e) for each query term
        for q_i in query_terms:

            # Get the entity description
            entity_desc = self.collection[doc_id]

            # Get the length of the entity's description
            l_e = len(entity_desc)

            # Search for the raw term frequency on the index --> c_qi_e
            c_qi_e = 0
            for j in range(l_e):
                if q_i == entity_desc[j]:
                    c_qi_e += 1

            #--------------- Compute the P(q_i | E) ---------------#
            c_qi_E = 0
            l_E = 0
            for entity_id, entity_desc in self.collection.items():
                c_qi_E += entity_desc.count(q_i)
                l_E += len(entity_desc)
            P_qi_E = c_qi_E / l_E
            #------------------------------------------------------#

            if (c_qi_e + self.mu*P_qi_E) / (l_e + self.mu) == 0:
                f_T_SUM += 0
            else:
                f_T_SUM += math.log((c_qi_e + self.mu*P_qi_E) / (l_e + self.mu))

        return f_T_SUM

    def ordered_bigram_matches(self, query_terms: List[str], doc_id):
        """Returns ordered bigram matches based on smoothed entity language
        model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for ordered bigram matches for document with doc ID.
        """
        f_O_SUM = 0
        entity_desc = self.collection[doc_id]   # Get the entity's description
        l_e = len(entity_desc)  # Get the length of the entity's description

        for i in range(len(query_terms)-1):

            # If the term or the adjacent term is not in the index, jump to the next term
            if query_terms[i] not in self.index or query_terms[i+1] not in self.index:
                continue    # Jump to the next term

            #------------ Compute the count of ordered bigram ------------#
            co_qi_e = 0
            for j in range(l_e-1):
                if entity_desc[j] == query_terms[i] and entity_desc[j+1] == query_terms[i+1]:
                    co_qi_e += 1

            #--------------- Compute the Po(q_i, q_i+1 | E) ---------------#
            co_qi_E = 0
            l_E = 0
            for entity_desc_E in self.collection.values():

                for j in range(len(entity_desc_E)-1):
                    if entity_desc_E[j] == query_terms[i] and entity_desc_E[j+1] == query_terms[i+1]:
                        co_qi_E += 1

                l_E += len(entity_desc_E)

            Po_qi_E = co_qi_E / l_E
            #--------------------------------------------------------------#

            # Add the value to the accumulative variable
            if (co_qi_e + self.mu*Po_qi_E) / (l_e + self.mu) != 0:
                f_O_SUM += math.log((co_qi_e + self.mu*Po_qi_E) / (l_e + self.mu))

        return f_O_SUM

    def unordered_bigram_matches(self, query_terms: List[str], doc_id):
        """Returns unordered bigram matches based on smoothed entity language
        model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for unordered bigram matches for document with doc ID.
        """
        f_U_SUM = 0
        entity_desc = self.collection[doc_id]   # Get the entity's description
        l_e = len(entity_desc)  # Get the length of the entity's description

        for i in range(len(query_terms)-1):

            # Since order doesn't matter, define a query set of terms that will be used to compare with the window pair
            query_pair = set([query_terms[i], query_terms[i+1]])

            #----------- Compute the count of unordered bigrams -----------#
            cw_qi_e = 0
            checked_indices = []
            for j in range(l_e-self.window+1):

                for k in range(j, j+self.window):
                    for l in range(k+1, j+self.window):

                        window_pair = set([entity_desc[k], entity_desc[l]])

                        if query_pair == window_pair:

                            # Check if the indices have been added to the set
                            if (set([k, l]) not in checked_indices):

                                cw_qi_e += 1
                                checked_indices.append(set([k, l]))

            #--------------- Compute the Pw(q_i, q_i+1 | E) ---------------#
            cw_qi_E = 0
            l_E = 0

            for entity_desc_E in self.collection.values():

                checked_indices = []    # Checked indices for the entity description
                # For each window...
                for j in range(len(entity_desc_E)-self.window+1):

                    # Check each pair through the whole window
                    for k in range(j, j+self.window):
                        for l in range(k+1, j+self.window):

                            window_pair = set([entity_desc_E[k], entity_desc_E[l]]) # Define the pair of window terms

                            if query_pair == window_pair:

                                # Check if the indices pair have not been added to the set
                                if (set([k, l]) not in checked_indices):
                                    cw_qi_E += 1
                                    checked_indices.append(set([k, l]))

                l_E += len(entity_desc_E)

            Pw_qi_E = cw_qi_E / l_E
            #--------------------------------------------------------------#

            if (cw_qi_e + self.mu*Pw_qi_E) / (l_e + self.mu) != 0:
                f_U_SUM += math.log((cw_qi_e + self.mu*Pw_qi_E) / (l_e + self.mu))

        return f_U_SUM


class FSDMScorer(Scorer):
    def __init__(
        self,
        collection: DocumentCollection,
        index: CollectionType,
        feature_weights=[0.85, 0.1, 0.05],
        mu: float = 100,
        window: int = 3,
        fields: List[str] = ["title", "body", "anchors"],
        field_weights: List[float] = [0.2, 0.7, 0.1],
    ):
        """SDM scorer. This scorer can be applied on a single field only.

        Args:
            collection: Collection of documents. Needed to calculate document
                statistical information.
            index: Index to use for calculating scores.
            field (optional): Single field to use in scoring.. Defaults to None.
            fields (optional): List of fields to use in scoring. Defaults to
                None.
        """
        super().__init__(collection, index, feature_weights, mu, window)
        self.fields = fields
        self.field_weights = field_weights

    def unigram_matches(self, query_terms: List[str], doc_id: str) -> float:
        """Returns unigram matches based on smoothed entity language model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for unigram matches for document with doc ID.
        """
        f_T_SUM = 0

        # Compute f_T(q_i, e) for each query term
        for q_i in query_terms:

            f_T = 0
            for field in self.fields:

                # Get the entity description for the field
                entity_desc = self.collection[doc_id][field]

                # Get the length of the entity's description
                l_e = len(entity_desc)
                    
                # Search for the raw term frequency on the index --> c_qi_e
                c_qi_e = 0
                for j in range(l_e):
                    if q_i == entity_desc[j]:
                        c_qi_e += 1

                #--------------- Compute the P(q_i | E) ---------------#
                c_qi_E = 0
                l_E = 0
                for entity_id, entity_desc in self.collection.items():
                    c_qi_E += entity_desc[field].count(q_i)
                    l_E += len(entity_desc[field])
                P_qi_E = c_qi_E / l_E
                #------------------------------------------------------#

                f_T += self.field_weights[self.fields.index(field)]*(c_qi_e + self.mu*P_qi_E) / (l_e + self.mu)

            if f_T != 0:
                f_T_SUM += math.log(f_T)
            else:
                f_T_SUM += 0

        return f_T_SUM

    def ordered_bigram_matches(self, query_terms: List[str], doc_id):
        """Returns ordered bigram matches based on smoothed entity language
        model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for ordered bigram matches for document with doc ID.
        """
        f_O_SUM = 0
        for i in range(len(query_terms)-1):
            f_O = 0
            for field in self.fields:
                entity_desc = self.collection[doc_id][field]   # Get the entity's description
                l_e = len(entity_desc)  # Get the length of the entity's description

                # Compute the count of ordered bigram
                co_qi_e = 0
                for j in range(l_e-1):
                    if entity_desc[j] == query_terms[i] and entity_desc[j+1] == query_terms[i+1]:
                        co_qi_e += 1

                #--------------- Compute the Po(q_i, q_i+1 | E) ---------------#
                co_qi_E = 0
                l_E = 0
                for entity_desc_E in self.collection.values():

                    for j in range(len(entity_desc_E[field])-1):
                        if entity_desc_E[field][j] == query_terms[i] and entity_desc_E[field][j+1] == query_terms[i+1]:
                            co_qi_E += 1

                    l_E += len(entity_desc_E[field])

                Po_qi_E = co_qi_E / l_E
                #--------------------------------------------------------------#

                f_O += self.field_weights[self.fields.index(field)]*(co_qi_e + self.mu*Po_qi_E) / (l_e + self.mu)

            if f_O != 0:
                f_O_SUM += math.log(f_O)
            else:
                f_O_SUM += 0

        return f_O_SUM

    def unordered_bigram_matches(self, query_terms: List[str], doc_id):
        """Returns unordered bigram matches based on smoothed entity language
        model.

        Args:
            query_terms: List of query terms
            doc_id: Document ID for the document we wish to score

        Returns:
            Score for unordered bigram matches for document with doc ID.
        """
        f_U_SUM = 0
        for i in range(len(query_terms)-1):
            f_U = 0

            # Since order doesn't matter, define a query set of terms that will be used to compare with the window pair
            query_pair = set([query_terms[i], query_terms[i+1]])

            for field in self.fields:

                entity_desc = self.collection[doc_id][field]   # Get the entity's description
                l_e = len(entity_desc)  # Get the length of the entity's description

                #----------- Compute the count of unordered bigrams -----------#
                cw_qi_e = 0
                checked_indices = []
                for j in range(l_e-self.window+1):

                    for k in range(j, j+self.window):
                        for l in range(k+1, j+self.window):

                            window_pair = set([entity_desc[k], entity_desc[l]])

                            if query_pair == window_pair:

                                # Check if the indices have been added to the set
                                if (set([k, l]) not in checked_indices):

                                    cw_qi_e += 1
                                    checked_indices.append(set([k, l]))

                #--------------- Compute the Pw(q_i, q_i+1 | E) ---------------#
                cw_qi_E = 0
                l_E = 0

                for entity_desc_E in self.collection.values():

                    checked_indices = []    # Checked indices for the entity description
                    # For each window...
                    for j in range(len(entity_desc_E[field])-self.window+1):

                        # Check each pair through the whole window
                        for k in range(j, j+self.window):
                            for l in range(k+1, j+self.window):

                                window_pair = set([entity_desc_E[field][k], entity_desc_E[field][l]]) # Define the pair of window terms

                                if query_pair == window_pair:

                                    # Check if the indices pair have not been added to the set
                                    if (set([k, l]) not in checked_indices):
                                        cw_qi_E += 1
                                        checked_indices.append(set([k, l]))

                    l_E += len(entity_desc_E[field])

                Pw_qi_E = cw_qi_E / l_E
                #--------------------------------------------------------------#

                f_U += self.field_weights[self.fields.index(field)]*(cw_qi_e + self.mu*Pw_qi_E) / (l_e + self.mu)

            if f_U != 0:
                f_U_SUM += math.log(f_U)

        return f_U_SUM
