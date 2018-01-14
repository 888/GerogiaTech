import numpy as np
import scipy.stats as stats

class RTLearner(object):
    def __init__(self,leaf_size = 1, verbose = False):
        self.verbose = verbose
        self.leaf_size = leaf_size
        self.tree = {}

    def author(self):
        return 'nlerner3'

    def addEvidence(self, dataX, dataY):
        self.tree = self.build_tree(dataX, dataY)
        print (self.tree)
    def query(self, points):
        result = []
        for point in points:
            result.append(traverse_tree(self.tree, point))
        return result
    def build_tree(self, dataX, dataY):
        if dataX.shape[0] <= self.leaf_size or np.all(dataY == dataY[0]):
            return [
                np.mean(dataY), None, None
            ]
        correlation = 0
        split_index= -1
        split_index= np.random.randint(0,dataX.shape[1])
        split_val = np.median(dataX[:,split_index])
        left_ind = np.where(dataX[:,split_index] <= split_val)
        right_ind = np.where(dataX[:,split_index] > split_val)
        if left_ind[0].shape[0] == 0 or right_ind[0].shape[0] == 0:
            return [
                np.mean(dataY), None, None
            ]
        left_tree = self.build_tree(dataX[left_ind], dataY[left_ind])
        right_tree = self.build_tree(dataX[right_ind], dataY[right_ind])
        root = [split_index, split_val]

        return [root, left_tree, right_tree]
ROOT = 0
ROOT_SPLIT_INDEX = 0
ROOT_SPLIT_VALUE = 1
LEFT = 1
RIGHT = 2
def traverse_tree(tree, point):
    if tree[LEFT] == None and tree[RIGHT] == None :
        return tree[ROOT]
    split_index = tree[ROOT][ROOT_SPLIT_INDEX]
    split_value = tree[ROOT][ROOT_SPLIT_VALUE]
    if point[split_index] <= split_value:
        return traverse_tree(tree[LEFT], point)
    else:
        return traverse_tree(tree[RIGHT], point)

