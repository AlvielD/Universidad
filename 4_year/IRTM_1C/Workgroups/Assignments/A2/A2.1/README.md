# Assignment A2.1: Indexing

## Scenario

You have been hired by a company to build a news search engine. You are going to build an inverted index using term frequencies, implement a function to score documents for retrieval with respect to a given query, and finally evaluate the performance of your rankings relative to the ground truth. 

## Task

You will build the essential components of document retrieval. You will need to implement functions and class methods dealing with
 - Indexing (A2.1),
 - Scoring (A2.2), and
 - Evaluation (A2.3).
 
The data you will be working on constitutes a standard test collection, including a document collection, a set of queries, and a set of relevance assessments. In this assignment, the instructions are highly specific and you are provided with certain parts of the code to ensure every student can and should achieve exactly the same results. 

## Assignment scoring

Complete each function and method according to instructions. There is a test for each coding section. Make sure that your code passes all the tests. Passing the tests that you can see will mean you should get points for the assignment.  

## Submission deadline

The submission deadline is **27/09 8:00**. Your submission is the last uploaded file at that time.

## Specific steps

### Import packages

You may install and import whatever packages help you solve the assignment. You are not expected to implement any particular solution from scratch unless otherwise noted. For this assignment, you will need to install additional packages `requests`, `nltk`, `ir_datasets`, and `sqlitedict`. You can use use the provided `requirements.txt` file to update your environment.

### Collection

In this assignment, we will be using Washington Post collection. This collection is 1.5GB compressed and contains 595,037 documents. It might take a long time to index that many documents on an average computer. Therefore, your solution will not be tested on the index building part, but only on retrieving information from the index.

The function to download the collection is provided.

### Preprocessing

The text preprocessing code is provided to ensure that all assignment submissions are consistent in this regard and evaluation can be automated.

### Indexing

Use the two classes for representing an inverted index (these should be familiar from the earlier exercises). You'll need to complete and/or create new methods in `InvertedIndex`. The class inherits from `sqlitedict` which can conveniently store key - value pairs on the disk. This is a lightweight addition to sqlite specifically designed to deal with key - value type information. Saving and reading to and from the drive is already implemented automatically for the `self.index` dictionary you need to populate. You have the freedom to choose how you want to construct the keys and values of this dictionary.

The documents are iterated over using [ir_datasets](https://ir-datasets.com/) API. The API returns a NamedTuple containing document ID and several other field containing text. We will be building a multi-field document index, using the `title` and `body` fields.

There are three methods that you need to complete (i.e., these will be tested):
 * `get_terms(field)` should return all available terms in the field.
 * `get_postings(field, term)` should return a list of postings for the (`field`, `term`) pair.
 * `get_term_frequency(field, term, doc_id)` should return the term frequency for a particular document.

Document texts should be preprocessed using the provided `preprocess()` method.