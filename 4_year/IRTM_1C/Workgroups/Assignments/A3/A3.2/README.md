# Assignment A3.2: Learning to Rank

## Scenario

Phew! You managed to keep your job with the company despite being out of contact while indulging in your passion for music trivia (See Assignment A3.1). You sold management on the story that you were merely out of touch while conducting a work-relevant research project on your own initiative. Very sly. However, you need to prove that you have learned something useful during your absence. You will extend your work on retrieval models and investigate machine learning approaches to creating retrieval models,  known as learning-to-rank (LTR) models.  

## Task

The task is to implement a learning-to-rank approach for web search and evaluate it using a standard test collection.

You are provided with a set of documents, queries, and relevance judgments. You will use an initial ranking of top-100 results for each query (based on BM25). You will re-rank these documents using a learning-to-rank approach. Specifically, you need to use a pointwise learning-to-rank approach, i.e., any standard regression algorithm that is available in scikit-learn.

## Assignment Scoring

Complete each coding section according to instructions. There is a test for each coding section. Make sure that your code passes all the tests. Passing the tests that you can see will mean you should get points for the assignment. Each test cell grants points in an all-or-nothing manner.


## Submission Deadline

The deadline for submitting the completed notebook is **25/10 08:00**. Your submission is whatever uploaded to Canvas at that time.

## Environment
  
You must have a running local Elasticsearch instance (assuming version 7.15) on your machine.


## Test Collections

For developing your solutions quickly, you are provided with a toy dataset `toy_index` and the code which indexes this. For the final and hidden tests, you are provided with a larger dataset (`documents.jsonl`).  

For both toy dataset used for development and the larger real-world dataset used for the final testing, only two fields will be used: `'title'` and `'body'`. 

The same `INDEX_SETTINGS` will be used to index both collections.

## Feature extraction

Machine learning algorithms typically rely on numerical vectors representing the data points that the model trains and tests against. The choices made regarding how to represent text data as numerical vectors have a critical impact on the effectiveness of machine learning for text processing tasks. The process of representing text data as numerical vectors is called *feature extraction*. 

You will implement specific feature-extraction functions, specifically for query features, document features, and query-document features, for use in the learning-to-rank approach to retrieval models.

### Extract Query Features

Implement the function `extract_query_features`, which for a given query and index will consider only those query terms found among the `'body'` fields of the indexed documents. In other words, queries are analyzed with respect to the vocabulary present in the `'body'` field only.

The function should return a dictionary with the keys `'query_length'`, `'query_sum_idf'`, `'query_max_idf'`, and `'query_avg_idf'`. These features, respectively, represent the number of tokens in the analyzed query, as well as the sum, maximum value, and average value of IDF for the same tokens. You will calculate IDF based on the `'body'` field only. 

Note: we assume that the query has been analyzed and that query_terms only contain terms that appear in at least one document's body in the collection. It is also assumed that the index always contains a `'body'` field.

### Extract Document Features

Implement the function `extract_doc_features`, which for a given doc ID and index will quantify features based on the `'title'` and `'body'` fields of the document. 

The function should return a dictionary with the keys `'doc_length_title'` and `'doc_length_body'`. These features represent the number of tokens in the title and body, respectively, of the document.

### Extract Query-Document Features

Implement the function `extract_query_doc_features`, which for a given query, doc ID and index will quantify query-document features based on the `'title'` and `'body'` fields of the document. 

The function should return a dictionary with the keys `'unique_query_terms_in_title'`, `'unique_query_terms_in_body'`,`'sum_TF_title'`, `'sum_TF_body'`, `'max_TF_title'`, `'max_TF_body'`, `'avg_TF_title'`, `'avg_TF_body'`. 

It should be clear from the structure of how these keys are named which field each should be derived from. Besides the two fields, each query-document feature will be either a count of unique query terms present one of the documents' fields, or else an aggregation function (sum, maximum, or average) over the term frequencies of each query term. 

## The TREC'09 Collection

After developing your feature extraction functions, you will now index the `TREC'09` collection, after which you will work with the feature extraction functions to do learning-to-rank and train a retrieval model on this collection. For indexing the documents collection, the required function is provided. You will, however, implement functions to parse the queries and relevance judgements from the associated files. 

### Load TREC-9-train queries and relevance judgments

Yu will implement the functions to read queries and relevance judgments from the corresponding files. More detail about the file structure is in the docstrings.

## Training Learning-to-Rank Retrieval Model

You have built the code that can extract feature vectors for query and document pairs. You will now use these functions in training a learning-to-rank model to re-rank the initially retrieved top-k documents (retrieved using Elasticsearch's built-in BM25 implementation) and evaluate against ground truth relevance judgments. 

During training, for each query we will need feature vectors for the union of all (ground truth) labeled documents and the top-k documents from initial retrieval. We will assume retrieved documents without labels are actually non-relevant. 

### Prepare training data

In the test the query IDs are partitioned into train and test splits. Implement the function `prepare_ltr_training_data` which takes a list of query IDs, performs initial retrieval, and compute feature vectors for all first-pass results as well as all relevant docs in the loaded QRELS for the given query ID. A given query-document pair should be used to extract a feature vector only once. 

At the same time, for each query and document pair, build the corresponding list of labels. The label should be 1 if a document is relevant to a query, i.e. the document ID is in the QRELS for that query ID. Otherwise, the label should be 0. 

For each query ID, perform first-pass retrieval using Elasticsearch taking the top 100 documents retrieved.

The function should also return lists of the query and document IDs corresponding to the feature vectors and labels extracted. Here, query IDs and document IDs may repeat, reflecting different pairings resulting in different feature vectors and labels, but any given pair of query and document IDs should not repeat. 

#### A class for pointwise-based learning to rank model

You are provided the class for training a learning-to-rank model and ranking documents given feature vectors extracted with respect to a single query. You only need to instantiate a scikit-learn regressor. You have the freedom to choose which one to use. 

### Generate the rankings of documents

Implement the function `get_rankings` to take a trained `PointWiseLTRModel` and the prepared testing data to generate document rankings for each of the queries in a list of test query IDs. 

### Predictions and Evaluation

In last section, we see if the learning-to-rank work yields the benefit we are looking for. We will use mean reciprocal rank as the evaluation metric, and we will compare the first-pass retrieval with the re-ranked retrieval across the test split of the TREC'09 query IDs. 

#### Evaluation metrics

Use `get_reciprocal_rank` and `get_mean_eval_measure` to prepare an evaluation of the learning-to-rank model's performance. (These implementations are given from a previous assignment.)

With the implementation in place, you can calculate the mean reciprocal rank of both the first-pass retrieval and the re-ranking obtained with the learning-to-rank model.  

You should have clear evidence that the learning-to-rank approach can outperform and improve upon the BM25 first-pass retrieval (even when using only a fraction of the available training data). Hopefully, this convinces management at your company to let you continue with doing projects on your own initiative!