import numpy as np
import BagLearner as bl
import LinRegLearner as lrl

class InsaneLearner(object):
    def __init__(self, verbose = False):
        self.learners = [bl.BagLearner(lrl.LinRegLearner,{},20) for i in range(20)]

    def author(self):
        return 'nlerner3'

    def addEvidence(self, dataX, dataY):
        for i in range(20):
            self.learners[i].addEvidence(dataX, dataY)

    def query(self, points):
        results = [learner.query(points) for learner in self.learners]
        return np.mean(results, axis=0)