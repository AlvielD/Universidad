# Assignment A2.2: Scoring

## Scenario

We continue the task of building a retrieval system. After we finished building an inverted index, we need to implement a scoring function to be able to retrieve relevant documents.

## Task

You will implement a term-at-a-time scoring strategy to score all documents in the collection with respect to an input query. In addition to a simple scoring function, you will implement retrieval models **BM25**, **LM**, as well as their fielded variants **BM25F** and **MLM**.

In the public tests, the implementations will be tested using "dummy" (toy-sized) collections while for the private tests we will be using a real data collection.

## Assignment scoring

Complete each function and method according to instructions. There is a test for each scoring method. Make sure that your code passes all the tests.

## Submission deadline

The submission deadline is **04/10 8:00**. Your submission is the last uploaded file at that time.

## Specific steps

### Import packages

For this assignment, **only packages that are part of the standard Anaconda distribution are allowed**. If you do not follow these restrictions, you may potentially lose all marks for the assignment.

You are not expected to implement any particular solution from scratch unless otherwise noted. 

## Retrieval models

Before proceeding to implement each of the five retrieval models, look carefull at the abstract `Scorer` class, from which each of the retrieval models will inherit. Note that all the subclasses inherit the method `score_collection` (which applies `score_term` in a term-at-a-time manner) without overloading it.


### Single-field retrieval models

Implement each of the two single-field retrieval models by expressing in code (specifically, in the `score_term` method) that model's particular scoring function. 

### Simple scoring

For scoring documents, employ the following simple retrieval function:

$$score(d,q) = \sum_{t \in q} w_{t,d} \times w_{t,q}$$

Where $w_{t,d}$ should simply be the number of occurrences of term $t$ in the document. Similarly, $w_{t,q}$ is set to number of times term $t$ appears in the query.

### BM25 scoring

$$score(d,q) = \sum_{t \in q} \frac{c_{t,d}\times (1+k_1)}{c_{t,d} + k_1(1-b+b\frac{|d|}{avgdl})} \times idf_t$$

IDF is to be computed as $idf_t=\text{log}(\frac{N}{n_t})$, where $N$ is the total number of documents in the collection and $n_t$ is the number of documents containing term $t$. Note that $\log$ is the natural logarithm (i.e., `math.log()`).

### LM Scoring

Implement the language model approach for ranking documents. Specifically, we score document based on query log likelihood: 

$$score(d,q) = \sum_{t \in q} c_{t,q} \times \log P(t|\theta_d) = \sum_{t \in q} c_{t,q} \times \log \Big ( (1-\lambda) \frac{c_{t,d}}{|d|} + \lambda P(t|C) \Big )$$

The collection (background) term probability $P(t|C)$ is a maximum likelihood estimate, which is simply the term's relative frequency across the entire collection. (Hint: mind that we operate on a single field here, so only that field's content is considered as the collection.)

## Multi-field retrieval models

### BM25F scoring

$$score(d,q) = \sum_{t \in q} \frac{\tilde{c}_{t,d}}{k_1 + \tilde{c}_{t,d}} \times idf_t$$

$$\tilde{c}_{t,d} = \sum_i w_i \times \frac{c_{t,d_i}}{B_i}$$

where

  * $i$ corresponds to the field index
  * $w_i$ is the field weight
  * $B_i$ is soft normalization for field $i$
  
$$B_i = (1-b_i+b_i\frac{|d_i|}{avgdl_i})$$

IDF values should be computed based on the body field using natural-base logarithm.

### MLM scoring

Using multiple document fields, the document language model is taken to be a linear combination of the (smoothed) field language models:

$P(t|\theta_d) = \sum_i w_i P(t|\theta_{d_i})$ ,

where $w_i$ is the field weight for field $i$ (and $\sum_i w_i = 1$).

The field language models $P(t|\theta_{d_i})$ are computed as follows (we use Jelinek-Mercer smoothing):

$P(t|\theta_{d_i}) = (1-\lambda_i) P(t|d_i) + \lambda_i P(t|C_i)$,

where 

  * $\lambda_i$ is a field-specific smoothing parameter
  * $P(t|d_i) = \frac{f_{t,d_i}}{|d_i|}$ is the empirical field language model (term's relative frequency in the document field). $f_{t,d_i}$ is the raw frequency of $t$ in field $i$ of $d$. $|d_i|$ is the length (number of terms) in field $i$ of $d$.
  * $P(t|C_i) = \frac{\sum_{d'}f_{t,d'_i}}{\sum_{d'}|d'_i|}$ is the collecting field language model (term's relative frequency in that field across the entire collection)
  
(Hint: You'll need to substitute $P(t|\theta_d)$ back into the log query likelihood formula shown in the LM cell.)