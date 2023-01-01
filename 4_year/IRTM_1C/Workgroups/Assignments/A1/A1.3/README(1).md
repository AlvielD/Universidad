# Assignment A1.3: Training and evaluating a spam classifier

## Scenario

You have been hired by a company as a data scientist, and you need to help build a spam classifier.  That is, create a model that is able to correctly classify the incoming emails to company email accounts as either spam or genuine non-spam ("ham") emails.

## Task

Having worked out simple feature extraction for text and evaluation of classifiers, you will finally put it all together with a larger dataset and machine learning. You will need to implement
 - Data loading, 
 - Text preprocessing, 
 - Feature extraction, 
 - Training a classifier, and
 - Evaluating predictions made by a trained classifier.

 ## Assignment scoring

Complete each function and method according to instructions. There is a test for each coding section. Make sure that your code passes all the tests. Passing the tests that you can see will mean you should get points for the assignment.  

## Submission deadline

The submission deadline is **20/09 8:00**. Your submission is the last uploaded file at that time.

## Specific steps

Implement evaluation functions which will be used to calculate performance metrics when comparing some predicted classes with the corresponding actual classes. 

**For this assignment (A1.3), you are expected to import appropriate packages and call on these.**

### Import packages

The required packages to import are listed in `requirements.txt`. You may install and import whatever packages help you solve the assignment. You are not expected to implement any particular solution from scratch. 

**Hint:** `scikit-learn` is a popular Python library for machine learning. 

### Data

For your convenience, we have prepared a ham and spam dataset that you can train and evaluate your classifier on. It's important to note that this is the same dataset and partitioning (i.e. the dataset has been partitioned into `train` and `test` splits). 

Example structure of both datasets:
```
Id      Label   Email
train/000/000   ham     "Received: from NAHOU-MSMBX01V ([192.168.110.39]) by ...
train/000/002   ham     "Received: from NAHOU-MSMBX01V ([192.168.110.39]) by ...
```
For the splits, the labels (`ham` or `spam`) are provided to you. The Id, Lablel, and Email in the files are separated by *tab* (`\t` in Python).

Your submission will be graded primarily on tests in `test_public.py`, but there will also be a hidden test that will be applied during grading, where your classifier gets applied to the `hidden` split and the performance that your model achieves will be graded. 



### Load data

In order to train or evaluate the spam classifier, you must load the data from file. 
Implement the function `load_file` which takes a file path as input and returns a tuple of two lists
 - list of file's contents as a string and
 - list of labels converted to integers; For `spam`, use 1, and for `ham` use a 0. 


### Text preprocessing

The text data should be preprocessed in order to improve the quality of the feature extraction. Implement the function that does this for you. You have a wide discretion in selecting which text preprocessing gives the best result when training and evaluating your classifier later. 

You need to implement the function `preprocess` which takes a string---such as that returned by `load_file`---as input and returns another string comprising preprocessed text. 

At a minimum, will need to implement punctuation removal and stopword removal.

### Extract features

The preprocessed text can then be turned into feature vectors in some way, the final step before applying a machine learning algorithm to the training data. 

You must implement the function `extract_features`, which takes two list of strings, a train split and a validation/test split, and returns two numerical array-like objects representing the two data splits. 

### Train classifier

Implement the function that takes data produced by your feature extraction to train a classifier. 

You must implement the function `train`, which takes two numerical array-like objects (representing the instances and their labels) as input and returns a trained model object. The model object must have a `predict` method that takes as input a numerical array-like object (representing instances) and returns a numerical array-like object (representing predicted labels).

### Evaluate trained classifier

Implement the function `evaluate`, which takes as input a trained model, as well as a labeled dataset, and returns the following evaluated performance metrics: recall, precision, F1, and accuracy.

## NB

The threshold for getting the points on the evaluation is set at 85% accuracy. For the hidden test, however, points are awarded proportionally to performance with maximum score achieved for 99% accuracy. This should be achievable with simple preprocessing and good choice of feature extraction and of classifier.
