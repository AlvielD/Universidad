import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import axes3d
import sys
import pdb


def norm1D(my,Sgm,x):

    [n,d]=np.shape(x)
    p = np.zeros(np.shape(x))
    for i in np.arange(0, n):
        p[i] = 1 / (np.sqrt(2 * np.pi) * Sgm) * \
            np.exp(-1 / 2 * np.square((x[i] - my)) / (np.square(Sgm)))

    return p


def norm2D(my, covmat, X):
    
    [n,d,_] = np.shape(X)
    p = np.zeros((np.shape(X)[0], np.shape(X)[1]))
    
    # Get the inverse of the covariance matrix
    inv_cm = np.linalg.inv(covmat)
    det_cm = np.linalg.det(covmat)
    
    for i in range(n):
        for j in range(d):
        
            x = np.array(X[i,j,:]).reshape(-1, 1)
            
            norm_const = 1.0/(2*np.pi*np.sqrt(det_cm))
            exp = np.matmul(np.transpose((x - my)), inv_cm)
            exp = -(1/2)*np.matmul(exp, (x - my))

            p[i, j] = norm_const * np.exp(exp)
        
    return p
