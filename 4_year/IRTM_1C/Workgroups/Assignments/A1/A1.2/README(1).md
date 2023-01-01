# Assignment A1.2: Evaluation

## Scenario

You have been hired by a company as a data scientist, and you need to help build a spam classifier.  That is, create a model that is able to correctly classify the incoming emails to company email accounts as either spam or genuine non-spam ("ham") emails.

## Task

Before getting the data, as a warm-up to more challenging parts of the assignment coming later, you can begin by implementing functions for performance evaluation metrics that will be used by the classifier in later assignments.

## Assignment scoring

Complete each function according to instructions. There is a test for each function. Make sure that your code passes all the tests. Passing the tests that you can see will mean you should get points for the assignment.  

## Submission deadline

The deadline for submitting the completed notebook is **13/09 8:00**. Your submission is whatever is the last file uploaded at that time.

## Specific steps

Implement evaluation functions that will be used to calculate performance metrics when comparing some predicted classes with the corresponding actual classes. 

**For this assignment (A1.2), you are not allowed to import any packages and must implement the functions from scratch.**

### Compute the confusion matrix from actual and predicted classes

First, implement `get_confusion_matrix()`, which takes the lists `actual` and `predicted`, as input and returns a list of two lists of `len()` 2. In other words, the function returns a \(2 \times 2\) matrix as a list of lists: 
`[[tn, fp], [fn, tp]]` 

`actual` and `predicted` must be of the same length, and their elements must correspond, so that `actual[i]` and `predicted[i]` must be the actual and predicted class of the `i`th instance.

Classification is binary and the class labels are either 1 or 0. The labels are interpreted as the positive and negative classes, respectively. 

The confusion matrix is 

<center>

|||
|--|--|
| \(TN\) | \(FP\) |
| \(FN\) | \(TP\) |

</center>

where \(TN\)  \(FP\)  \(FN\)  and \(TP\) are the numbers of true negatives, false positives, false negatives, and true positives, respectively. 

### Compute specific measures

Implement the computation of Accuracy, Precision, Recall, F1-score, False positive rate, and False Negative rate.

Use the function `get_confusion_matrix()` to access the true/false positives/negatives needed.

For each measure, implement a function that takes the lists `actual` and `predicted`, and returns a decimal number according to the measure definition. 

**Accuracy**: Number of correctly classified instances out of all instances.

$$Acc = \frac{TP+TN}{TP+TN+FP+FN}$$

**Precision**: Number of instances correctly identified as positive out of the total instances identified as positive.

$$P = \frac{TP}{TP+FP}$$

**Recall**: Number of instances correctly identified as positive out of the total number of actual positives.

$$R = \frac{TP}{TP+FN}$$

**F1-score**: The harmonic mean of precision and recall.

$$F1 = \frac{2 \cdot P \cdot R}{P+R}$$

**False positive rate**: Number of instances incorrectly identified as positive out of the total number of actual negatives.

$$FPR = \frac{FP}{FP+TN}$$

**False negative rate**: Number of instances incorrectly identified as negative out of the total number of actual positives.

$$FNR = \frac{FN}{FN+TP}$$