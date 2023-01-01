# Assignment A3.1: Entity retrieval

## Scenario

You have become obsessed with music and stopped showing up to work because you want to build an entity retrieval system to look up trivia about your favorite musicians and their productions, to help you check that you have all the information memorized. That way you can always show off your knowledge by saying "Did you know Leonard Cohen was born in 1934?" This can only enhance your social standing with the in-crowd! How can you make the best possible entity retrieval system? 

## Task

You will obtain information about entities (musicians) from DBpedia, then turn it into fielded document-based representations. Next, you will index these using the Elasticsearch API and set up a baseline entity retrieval model. Finally, you will implement a more advanced entity retrieval model, PRMS. 

## Assignment scoring

Complete each coding section according to instructions. There is a test for each function/method you are expected to complete. Do not change any code in any test function. Make sure that your code passes all the tests. Passing the tests that you can see will mean you should get points for the assignment. Each test grants points in an all-or-nothing manner. The assignment is worth 8 points total. 


## Submission deadline

The deadline for submitting the completed notebook is **18/10 08:00**. Your submission is whatever is uploaded at that time.

## Environment

Your environment is expected to be an Anaconda base environment of Python 3.7+, with the following additional libraries installed:
  * elasticsearch-py version 7.15
  * wikipediaapi
  
**DO NOT** install additional libraries. You may import packages that are part of the Anaconda base environment.


### Resources

  * If needed, review [the Elasticsearch exercise](https://github.com/kbalog/ir-course/blob/main/exercises/E6/Elasticsearch.md).
  * You may also refer to the [Official Python client documentation](https://elasticsearch-py.readthedocs.io/en/master/).

## Getting data from DBpedia

We get relevant data from the knowledge base DBpedia.

### Wikipedia

First, we identify the musicians we wish to map, using the Wikipedia API.

We provide the function listing the pages linked from `https://en.wikipedia.org/wiki/Lists_of_musicians`. We are interested in the pages which are explicitly a "List of" musical artists or bands. 

### DBpedia

We can get information about entities and relationships between them from a knowledge base like DBpedia. A knowledge base consists of triples of Subject-Predicate-Object, also known as SPO-triples. Each such triple represents a fact where some subject entity $S$ has a relationship of type $P$ with some object $O$. $O$ could either be another entity or else could be some literal value. 

The entities and predicates are represented by universal resource identifiers (URIs), which also function as URLs. We can use this fact when identifying the strings that are URIs. 

A specific entity can be downloaded from DBpedia with the function `get_dbpedia_entity()`:

### Dictionary-based entity representation

We can obtain a lot of data with `get_dbpedia_entity()` function, but we wish to represent the entity more simply and clearly. You must implement the function `dict_based_entity()` that calls the `get_dbpedia_entity()` function as provided and builds a dictionary-based representation where:
  * Each key is a single URI representing a predicate.
  * If the predicate corresponds only to a single value, then the dictionary-based entity representation should return only that value when using the predicate key.
  * If the predicate joins the subject entity to multiple object values, then the dictionary-based entity representation should return a list of such values when using the predicate key.
  * Complete URIs should be kept as the keys and values where possible.

## Create fielded document representation of entities

You will implement a function for fielded document-based entity representation. In the previous dictionary-based entity representation, each predicate URI was used as a key, and each object value was kept as a separate element. In the fielded document representation, predicates are grouped (i.e., *folded*) together, and the object values are concatenated into a single string for each of the grouped predicates, corresponding to the fields. In the case of URI-valued objects, the URI is resolved and the resulting string is added to the field. 
In addition, a catch-all field is used to include the character strings from all the folded predicate fields. 

Thus, the fielded document representation should include the fields `names`, `description`, `attributes`, `related_entities`, `types`, and `catch_all`. They should contain the following:
  * `names`: the objects of `NAME_PREDICATES`,
  * `description`: the object(s) of `COMMENT_PREDICATE`, 
  * `attributes`: objects that are literal values, 
  * `related_entities`: objects that are entities, 
  * `types`: the objects of `TYPE_PREDICATES`, and
  * `catch_all`: all of the above. 

## Index entities

Next, we build an Elasticsearch index from fielded document representations of selected entities.

We want to only get musicians from pages that themselves list musicians or musical groups. We will index their fielded document-based representations. 

### Index configuration 

For each of the fields, we store the term vectors. These are stored in the index (to avoid them being computed on-
the-fly). See [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/term-vector.html). 

The `INDEX_SETTINGS` variable is defined as a mappings dictionary, which is used to configure the index on Elasticsearch, defining which fields are expected for each document representation to be indexed. Accordingly, in the `fielded_doc_entity()` function each of the same fields (`names`, `description`, `attributes`, `related_entities`, `types`, and `catch_all`) must be populated as a single string.  

In the function, `bulk_index()`, implement a function to loop over the pages, and consider only those that themselves are "lists of" some kind, presumably musicians. Only index entities of the type `'http://dbpedia.org/ontology/MusicalArtist'`. Skip those cases where downloading the data results in a `JSONDecodeError`.

## Baseline entity retrieval model


You will implement the function `baseline_retrieval()`, which takes a query in the form of a string with space-separated terms, and first building an Elasticsearch query from these, to apply to the catch-all field, and then retrieving the highest-ranked entities based on that query from the index, and finally returning the names of the top $k$ candidates as a list in descending order according to the score awarded by Elasticsearch's internal BM25 implementation. 

The ordering of the results list should be deterministic, and if tied to the numerical score, the ordering should be based on the entity name, also in descending order.

## Advanced entity retrieval: PRMS

The baseline retrieval model helped find musical artists related to some query. We have indexed the document representations of the entities with multiple fields and wish to take advantage of this. How will the results differ if we use the PRMS retrieval model?

### Retrieval method

Implement the mixture of language models for ranking musicians. Effectively, we will be re-ranking the initial ranking produced by the baseline retrieval model implemented in Elasticsearch. 

*Hint:* Here it will be important to use the `es.termvectors` function to get term statistics for specific fields. 

Documents should be scored according to **query (log)likelihood**: 

$\log P(q|d) = \sum_{t \in q} f_{t,q} \log P(t|\theta_d)$, 

where
  * $f_{t,q}$ is the frequency of term $t$ in the query
  * $P(t|\theta_d)$ is the (smoothed) document language model.
  
Using multiple document fields, the **document language model** is taken to be a linear combination of the (smoothed) field language models:

$P(t|\theta_d) = \sum_i w_i P(t|\theta_{d_i})$ ,

where $w_i$ is the field weight for field $i$ (and $\sum_i w_i = 1$).

We compute the **field language models** $P(t|\theta_{d_i})$ using **Dirichlet smoothing** as follows:

$p(t|\theta_{d_i}) = \frac{f_{t,d_i} + \mu_i P(t|C_i)}{|d_i| + \mu_i}$,

where 

  * $\mu_i$ is the field-specific smoothing parameter (set to 100)
  * $f_{t,d_i}$ is the raw frequency of $t$ in field $i$ of $d$. $|d_i|$ is the length (number of terms) in field $i$ of $d$.
  * $P(t|C_i) = \frac{\sum_{d'}f_{t,d'_i}}{\sum_{d'}|d'_i|}$ is the collecting field language model (term's relative frequency in that field across the entire collection)
  
  
**PRMS** extends the MLM retrieval by changing static field weights into dynamic field weights.

Replace the static weight $w_i$ for the $i$'th field ($f$) with the *mapping probability* $P(f | t)$ such that

$P(t | \theta_d) = \sum_f P(f | t) P(t|\theta_{d_f}).$

Using Bayes' theorem the law of total probability, we estimate the mapping probability as

$P(f | t) = \frac{P(t|f) P(f)}{P(t)} = \frac{P(t|f) P(f)}{\sum_{f' \in \mathcal{F}} P(t|f')P(f')},$

and we estimate $P(t|f) = P(t|C_f)$, the probability of a term given a field's background language model. 
The prior $P(f)$ is taken to be uniform across the fields. 

The skeleton of the solution is provided in the code. In the remainder of the assignment, the only coding problems that need to be completed are the collection language model and PRMS probability mapping. These tasks correspond to the estimation of $P(t|C_i)$ and $P(f|t)$ as defined above.

### Query analyzer

We will use the `analyze_query()` function to apply the default Elasticssearch analyzer on a query and return a list of query terms. See [indices.analyze](https://elasticsearch-py.readthedocs.io/en/master/api.html#elasticsearch.client.IndicesClient.analyze). It is used in both testing `CollectionLM()` and `prms_retrieval()`.  

### Collection Language Model class

This class is used for obtaining collection language modeling probabilities $P(t|C_i)$.

The reason this class is needed is that `es.termvectors` does not return term statistics for terms that do not appear in the given document. This would cause problems in scoring documents that are partial matches (do not contain all query terms in all fields). 

The idea is that for each query term, we need to find a document that contains that term. Then the collection term statistics are available from that document's term vector. To make sure we find a matching document, we issue a [boolean (match)](https://www.elastic.co/guide/en/elasticsearch/reference/5.5/query-dsl-match-query.html) query.

You must implement the `_get_prob()` method, which computes the collection Language Model probability of a term for a given field. 

### Field mapping probabilities (PRMS)

Implement the function `get_term_mapping_probs()` to return $P(t|f)$ of all fields $f$ for a given term $t$ according to the description above of how PRMS extends the MLM retrieval model. 

### PRMS Document Scorer

Finally, we put the choices above together. In the code, no changes are needed. The final code should run and pass the tests if you have correctly completed the parts above. 