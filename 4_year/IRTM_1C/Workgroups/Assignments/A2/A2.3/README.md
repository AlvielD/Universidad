# Assignment A2.3: Evaluation

## Scenario

In this assignment we build the final component of our retrieval system: evaluation. This is a crucial part to ensure that a retrieval system performs as expected.

## Task

You need to implement three binary evaluation measures, Precision@k, Average Precision, and Reciprocal Rank. Additionally, you also need to implement a method that computes the mean evaluation measure (for any of the above) over a set of queries (that is, a list of system rankings and corresponding ground truth).

## Assignment scoring

Complete each function and method according to instructions. There is a test for each coding section. Make sure that your code passes all the tests. Passing the tests that you can see will mean you should get points for the assignment.  

## Submission deadline

The submission deadline is **11/10 8:00**. Your submission is the last uploaded file at that time.

## Specific steps

### Import packages

Once again we make use of the library `ir_datasets` to load the ground truth documents. You can use any library you want for loading system rankings from file. For evaluation functions in this task, you are only allowed to import standard Python libraries.

### Data loading

Data loading functions need to be implemented for both system ranking and ground truth. For system ranking, implement `load_rankings()`, which takes a path to rankings file and returns a dictionary with query IDs as keys and a list of documents for each query as values. For ground truth, implement `load_ground_truth()` to return a dictionary with query IDs as keys and a set of documents for each query as values.

NB! The docstrings in the functions contain more information about the data loading structure.

### Evaluation

Given system rankings as a list and ground truth as a set of documents implement three binary evaluation measures, Precision@k, Average Precision, and Reciprocal Rank. Additionally, implement a method that computes the mean evaluation measure (for any of the above) over a set of queries. This method should take dictionaries for system rankings and ground truth where keys are query IDs and values are a list (for system rankings) and a set (for ground truth) of documents.
