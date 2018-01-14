"""
Template for implementing QLearner  (c) 2015 Tucker Balch
"""

import numpy as np
import random as rand
class QLearner(object):

    def __init__(self, \
        num_states=100, \
        num_actions = 4, \
        alpha = 0.2, \
        gamma = 0.9, \
        rar = 0.5, \
        radr = 0.99, \
        dyna = 0, \
        verbose = False):

        self.verbose = verbose
        self.num_actions = num_actions
        self.num_states = num_states
        self.alpha = alpha
        self.gamma = gamma
        self.s = 0
        self.a = 0
        self.rar = rar
        self.radr = radr
        self.dyna = dyna
        self.Q = np.zeros((num_states, num_actions))
        self.Model = np.zeros((num_states, num_actions, 2))

    def querysetstate(self, s):
        """
        @summary: Update the state without updating the Q-table
        @param s: The new state
        @returns: The selected action
        """
        self.s = s
        if self._shouldTakeRandomAction():
            self.a = rand.randint(0, self.num_actions - 1)
        else:
            self.a = self._bestAction(actions=self.Q[s])
        if self.verbose: print "s =", s,"a =", self.a
        return self.a

    def _shouldTakeRandomAction(self):
        return np.random.choice(2,p=[1-self.rar, self.rar])

    def _bestAction(self, actions):
        takeAction = 0
        for i in range(len(actions)):
            if actions[i] > actions[takeAction]:
                takeAction = i
        return takeAction

    def query(self,s_prime,r):
        """
        @summary: Update the Q table and return an action
        @param s_prime: The new state
        @param r: The ne state
        @returns: The selected action
        """
        self.Model[self.s, self.a] = np.array([s_prime, r])
        self.Q[self.s,self.a] = self._newQValue(self.s, self.a, r, s_prime)
        self._hallucinate()
        self.querysetstate(s_prime)
        self.rar *= self.radr
        if self.verbose: print "s =", s_prime,"a =",self.a,"r =",r
        return self.a

    def _newQValue(self, s, a, r, s_prime):
        old_value = (1 - self.alpha) * self.Q[s,a]
        next_best_action = self._bestAction(self.Q[s_prime])
        new_value = self.alpha * (r + self.gamma * self.Q[s_prime, next_best_action])
        return old_value + new_value

    def _hallucinate(self):
        if self.dyna == 0:
            return
        states_seen = self.Q.nonzero()[0]
        for i in range(self.dyna):
            s = np.random.choice(states_seen)
            actions_taken = self.Q[s].nonzero()[0]
            a = np.random.choice(actions_taken)
            hallucination = self.Model[s,a]
            self.Q[s,a] = self._newQValue(s,a, int(hallucination[1]), int(hallucination[0]))

    def author(self):
        return "nlerner3"

if __name__=="__main__":
    print "Remember Q from Star Trek? Well, this isn't him"
