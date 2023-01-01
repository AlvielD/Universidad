import re
import ir_datasets
from typing import *
from classes.struct import MsMarcoWrapper, InvertedIndex

__DEBUG__ = True
DATASET = ir_datasets.load("msmarco-document/train")

def log(*args):
    if __DEBUG__:
        print(*args)

def run():
    with InvertedIndex(new=True) as inv_index:
        for i,doc in enumerate(DATASET.docs_iter()):
            if i == 2: break
            ms = MsMarcoWrapper(doc)
            for field in ms.FIELDS:
                terms = ms.get_terms(field)
                log(terms, len(terms))
                inv_index.process_doc(ms)

if __name__ == "__main__":
   run()