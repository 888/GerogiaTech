"""
Test a learner.  (c) 2015 Tucker Balch
"""

import numpy as np
import math
import RTLearner as rtl
import DTLearner as dtl
import LinRegLearner as lrl
import BagLearner as bl
import sys
import matplotlib.pyplot as plt

def train(data,leafsize):
    # compute how much of the data is training and testing
    train_rows = int(0.6* data.shape[0])
    test_rows = data.shape[0] - train_rows

    # separate out training and testing data
    trainX = data[:train_rows,0:-1]
    trainY = data[:train_rows,-1]
    testX = data[train_rows:,0:-1]
    testY = data[train_rows:,-1]

    print testX.shape
    print testY.shape

    # create a learner and train it
    learner = bl.BagLearner(rtl.RTLearner,{"leaf_size":leafsize})
    learner.addEvidence(trainX, trainY) # train it
    print learner.author()

    # evaluate in sample
    predY = learner.query(trainX) # get the predictions
    rmse1 = math.sqrt(((trainY - predY) ** 2).sum()/trainY.shape[0])
    print
    print "In sample results"
    print "RMSE: ", rmse1
    c = np.corrcoef(predY, y=trainY)
    print "corr: ", c[0,1]

    # evaluate out of sample
    predY = learner.query(testX) # get the predictions
    rmse = math.sqrt(((testY - predY) ** 2).sum()/testY.shape[0])
    print
    print "Out of sample results"
    print "RMSE: ", rmse
    c = np.corrcoef(predY, y=testY)
    print "corr: ", c[0,1]
    return rmse

if __name__=="__main__":
    if len(sys.argv) != 2:
        print "Usage: python testlearner.py <filename>"
        sys.exit(1)
    inf = open(sys.argv[1])
    data = np.array([map(float,s.strip().split(',')) for s in inf.readlines()])
    data_to_plot = [train(data, i) for i in range(1,350)]
    plt.plot([i for i in range(1, 350)], data_to_plot)
    plt.ylabel("Root Mean Squared error")
    plt.xlabel("Leaf_Size")
    plt.title("RTL BagLearner RMSE vs. Leaf_Size Outset")
    plt.show()
