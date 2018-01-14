import numpy as np
import scipy.stats as stats

class BagLearner(object):
    def __init__(self, learner, kwargs = {"verbose":False}, bags = 20, \
                 boost = False, verbose = False):
        self.boost = boost
        self.verbose = verbose
        self.learners = []
        self.bags = bags
        for i in range(bags):
            self.learners.append(learner(**kwargs))

    def author(self):
        return 'nlerner3'

    def addEvidence(self, dataX, dataY):
        bags = createbags(dataX, dataY, self.bags)
        for i in range(self.bags):
            self.learners[i].addEvidence(bags[i][0], bags[i][1])

    def query(self, points):
        results = [learner.query(points) for learner in self.learners]
        return np.mean(results, axis=0)

def createbags(dataX, dataY, numbags):
    bags = []
    indices_list = [i for i in range(dataY.shape[0])]
    for i in range(numbags):
        chosen_indices = np.random.choice(indices_list,size=dataY.shape[0],replace=True)
        bags.append((dataX[chosen_indices], dataY[chosen_indices]))
    return bags