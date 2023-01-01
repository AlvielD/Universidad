import zlib, pickle, sqlite3, json
from sqlitedict import SqliteDict
from typing import *
from .wrapper import MsMarcoWrapper

dict_encode = lambda obj: sqlite3.Binary(zlib.compress(pickle.dumps(obj, pickle.HIGHEST_PROTOCOL)))

dict_decode = lambda obj: pickle.loads(zlib.decompress(bytes(obj)))

class Posting:

    INDEX_FREQUENCY = 0
    INDEX_POSITION = 1
    INDEX_REL_FREQUENCY = 2

    def __init__(
        self,
        doc_id: Union[str, int],
        freq: int = None,
        position: int = None,
        rel_freq: float = None,
    ) -> None:
        self.doc_id = doc_id
        self.data = {self.doc_id: (freq, position, rel_freq)}

    @property
    def payload(self):
        return self.data[self.doc_id]

    @property
    def frequency(self):
        return self.payload[self.INDEX_FREQUENCY]

    @property
    def position(self):
        return self.payload[self.INDEX_POSITION]

    @property
    def rel_frequency(self):
        return self.payload[self.INDEX_REL_FREQUENCY]

    def to_tuple(self):
        return (self.doc_id, *self.payload)

    def to_list(self):
        return [self.doc_id, *self.payload]

    def __dict__(self):
        return self.data


class InvertedIndex(SqliteDict):

    # Class constructor
    def __init__(
        self,
        filename: str = "inverted_index.sqlite",
        fields: List[str] = ["title", "body"],
        new: bool = False,
    ) -> None:
        super().__init__(
            filename,
            flag="n" if new else "c",
            encode=dict_encode,
            decode=dict_decode,
        )
        self.fields = fields
        self.index = {field: {} for field in self.fields} if new else self

    def process_doc(self, doc: MsMarcoWrapper):
        """Process document to index.

        Add all the terms in the document to the index.

        Args:
            doc (MsMarcoWrapper): document to process
        """
        for field in self.fields:
            for term in doc.get_terms(field):
                posting = Posting(doc.doc_id, freq=doc.frequencies[field].get(term, 0)).to_tuple()
                self.index.setdefault(field, {}).setdefault(term, []).append(posting)

    def get_postings(self, field: str, term: str) -> List[str]:
        """Fetches the posting list for a given field and term.

        Args:
            field: Field for which to get postings.
            term: Term for which to get postings.

        Returns:
            List of postings for the given term in the given field.
        """
        return self.index.get(field, {}).get(term, [])

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
        return sum(
            filter(lambda post: post.doc_id == doc_id, self.get_postings(field, term)),
            0,
        )

    def get_terms(self, field: str) -> Set[str]:
        """Returns all unique terms in the index.

        Args:
            field: Field for which to return the terms.

        Returns:
            Set of all terms in a given field.
        """
        # Unique terms of the dictionary
        return set(self.index.get(field, {}).keys())

    def __exit__(self, *exc_info):
        if self.flag == "n":
            self.update(self.index)
            self.commit()
            print("Index updated.")
        super().__exit__(*exc_info)
