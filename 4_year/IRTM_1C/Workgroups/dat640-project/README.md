# dat640-project

DAT640 Project 2021 - Text Mining and Information Retrieval

## Pre-requisites

Python 3.9.7
## Installation

### Using virtualenv

1. Install virtualenv to preserve the Python global environment on your computer of the dependencies used. 

```shell
python3 -m pip install virtualenv
```

2. Create a virtualenv 

```shell
virtualenv -p $(which python3) venv
```

3. Activate the virtualenv to run python file

```shell
source venv/bin/activate
```

4. Verify the virtualenv

```shell
python --version
```

5. Install the depencies required for the assignment

```shell
python -m pip install -r requirements.txt
```

6. To deactivate the virtualenv after testing the project source, please do :

```shell
deactivate
```

## Links

- [API MSMarco](https://ir-datasets.com/msmarco-document.html)
- [Gitexplorer](https://gitexplorer.com/) : useful command for git
- [Text Classification with SVM - GUIDE](https://medium.com/@bedigunjit/simple-guide-to-text-classification-nlp-using-svm-and-naive-bayes-with-python-421db3a72d34)
- [PyTerrier API To Rank](https://pyterrier.readthedocs.io/en/latest/datasets.html)
- [RankLib Library of learning to rank algorithms](https://sourceforge.net/p/lemur/wiki/RankLib/)
