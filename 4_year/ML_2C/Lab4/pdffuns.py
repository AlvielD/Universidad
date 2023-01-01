import numpy as np
import math
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

def norm2D_samples(my, covmat, X):
    """Compute the probability density function values of a vector of samples"""

    # Get the inverse of the covariance matrix
    inv_cm = np.linalg.inv(covmat)
    det_cm = np.linalg.det(covmat)
    
    # Get the number of samples
    [l,n] = np.shape(X) 
    # X is an array of l rows and n columns, where l is the number of feature dimensions
    # and n the number of samples

    # Initialize the vector of values
    p = np.zeros(n)

    for i in range(n):

        # Get the point of computation
        x = np.array(X[:,i]).reshape(-1, 1)

        norm_const = 1.0/(2*np.pi*np.sqrt(det_cm))
        exp = np.matmul(np.transpose((x - my)), inv_cm)
        exp = -(1/2)*np.matmul(exp, (x - my))

        p[i] = norm_const * np.exp(exp)
        
    return p

def knn2D(dataset, X, kn = 1):
    #Get sizes of the dataset
    N = len(dataset[0])

    [d,n] = np.shape(X)
    p = np.zeros(n)

    if N < kn:
        print("kn can't be bigger than the size of the dataset")
        return p
    
    # Go through all computation points
    for i in range(n):
        x = np.array(X[:,i])

        # List of distances
        L = []

        # Compare the distance of the computaiton point with all points in the dataset
        for k in range(N):
            y = []
            for j in range(d):
                y.append(dataset[j][k])
            L.append(np.linalg.norm(x-y))

        # Order distances
        L.sort()
        
        # Find our desired distance
        l = L[kn - 1]

        # Calculate V
        v = math.pi * l**2

        #Calculate p
        p[i] = (kn/N)/v
    
    return p
    
    
def classplot(g, x1, x2, gnan=0, discr='', gsv={'gsv':0, 'figstr':'fig_'}):

    if discr == 'pxw':
        zlb = '$p(\mathbf{x}|\omega_i)$'
    elif discr == 'Pwpxw':
        zlb = '$P(\omega_i)p(\mathbf{x}|\omega_i)$'
    elif discr == 'Pwx':
        zlb = '$P(\omega_i | \mathbf{x})$'
    else:
        zlb = ''

    eps = sys.float_info.epsilon
    X1, X2 = np.meshgrid(x1, x2)
    M = len(g)
    col = ['r', 'b', 'g', 'y', 'k']
    fig = plt.figure(figsize=(10,10))
    fig.set_facecolor('gray')
    ax = fig.gca(projection='3d')
    obj = []
    mx = []
    for i in range(0, M):
        if gnan == 0:
            G = g[i]
        else:
            G = np.copy(g[i]) + 0 * 1e100 * eps
            NN = (G < np.amax(g, axis=0)) * 1
            NN = NN.astype(float)
            G = np.copy(g[i])
            np.putmask(G, NN == 1, np.nan)
        obj = ax.plot_surface(X1, X2, G, facecolor=col[i])
        mx.append(g[i].max())
    zm = np.around(1.2 * max(mx), decimals=2)
    xt = (np.linspace(x1[0, 0], x1[-1, 0], 5))
    yt = (np.linspace(x2[0, 0], x2[-1, 0], 5))
    zt = (np.linspace(0, zm, 4))
    zt = np.around(zt, decimals=3)
    ax.set(xticks=xt, yticks=yt, zticks=zt)
    ax.set(xlim=(x1[0, 0], x1[-1, 0]), ylim=(x2[0, 0], x2[-1, 0]))
    ax.set(xlabel='$x_1$', ylabel='$x_2$', zlabel=zlb)
    if gsv['gsv']:
        plt.savefig(gsv['figstr'] + discr + '.png')

    plt.show()
    
    return