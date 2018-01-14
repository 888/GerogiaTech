"""
Template for implementing StrategyLearner  (c) 2016 Tucker Balch
"""

import datetime as dt
import pandas as pd
import util as ut
import random
import indicators as indicators
from QLearner import QLearner

class StrategyLearner(object):

    # constructor
    def __init__(self, verbose = False, impact=0.0):
        self.verbose = verbose
        self.impact = impact
        self.learner = QLearner(num_states=90000, num_actions=2, dyna=0)
        self.mom_mean =0
        self.mom_std = 0
        self.long_mom_mean = 0
        self.long_mom_std = 0
        self.bollinger_bands = None
        self.rsi = None
        self.momentum = None
        self.cr = -100000

    def author(self):
        return 'nlerner3'

    # this method should create a QLearner, and train it for trading
    def addEvidence(self, symbol = "IBM", \
        sd=dt.datetime(2008,1,1), \
        ed=dt.datetime(2009,1,1), \
        sv = 10000):
        self.cash = sv
        start = sd - dt.timedelta(days=60)
        dates = pd.date_range(start, ed)
        data = ut.get_data([symbol], dates)
        data.fillna(method="ffill", inplace=True)
        data.fillna(method="bfill", inplace=True)
        prices = indicators.normalized_prices(data[symbol])

        self.bollinger_bands = None
        self.momentum = None

        self.rsi = indicators.rolling_rsi(prices, 5)
        self.bollinger_bands = indicators.bollinger_bands(prices, 15)
        self.momentum = indicators.rolling_momentum(prices, 2)

        self.mom_mean = self.momentum.mean()
        self.mom_std = self.momentum.std()

        last_action = 2
        current_state = self.create_state(sd,prices[:sd],last_action)
        action = self.learner.querysetstate(current_state)
        trades = pd.DataFrame(index=prices[sd:].index, columns=[symbol])
        keepgoing = True
        iteration = 0
        while keepgoing:
            for i in trades.index:
                reward = self.get_reward(action, last_action, prices[:i])
                last_action = action
                current_state = self.create_state(i, prices[:i], last_action)
                action = self.learner.query(current_state, reward)
            cr = self.cash/sv - 1
            if cr == self.cr or iteration == 20:
                keepgoing = False
            self.cr = cr
            iteration+=1

    def get_reward(self, action, last_action, prices_to_date):
        delta = prices_to_date.ix[-1] - prices_to_date.ix[-2]
        gains = 0
        if action == 0:
            gains = -1000 * delta
        if action ==1:
            gains += 1000 * delta
        if action != last_action:
            gains = gains - (1000 * prices_to_date.ix[-1]*self.impact)
        self.cash = self.cash + gains
        return gains

    def create_state(self, date, prices_to_date, prev_action):
        rsi = self.rsi_state(date)
        mom = self.momentum_state(date)
        bollinger = self.bollinger_state(date, prices_to_date)
        return int("{}{}{}{}{}".format(bollinger, rsi[0],rsi[1], mom, prev_action))

    def rsi_state(self, date):
        rsi = self.rsi[:date]
        current_rsi = rsi[-1]
        last_rsi = rsi[-2]
        rsi_0 = int(current_rsi / 10)
        if rsi_0 > 9:
            rsi_0 = 9
        rsi_1 = int(last_rsi / 10)
        if rsi_1 > 9:
            rsi_1 = 9
        return (rsi_0, rsi_1)

    def momentum_state(self, date):
        rolling_momentum = self.momentum[:date][-1]
        zscore = (rolling_momentum - self.mom_mean) / self.mom_std
        state = int(round(zscore * 3 + 5))
        if state < 0:
            state = 0
        if state > 9:
            state = 9
        return state

    def bollinger_state(self, date, prices_to_date):
        roling_band = self.bollinger_bands[:date]
        current_price = prices_to_date[-1]
        last_price = prices_to_date[-2]
        current_band = roling_band.ix[-1]
        last_band = roling_band.ix[-2]
        current_price_state = 1
        last_price_state = 1
        if current_price > current_band['UPPER_BAND']:
            current_price_state += 1
        if current_price < current_band['LOWER_BAND']:
            current_price_state -= 1
        if last_price > last_band['UPPER_BAND']:
            last_price_state += 1
        if last_price < last_band['LOWER_BAND']:
            last_price_state -= 1
        # return (current_price_state, last_price_state)
        return current_price_state * 3 + last_price_state

    # this method should use the existing policy and test it against new data
    def testPolicy(self, symbol = "IBM", \
        sd=dt.datetime(2009,1,1), \
        ed=dt.datetime(2010,1,1), \
        sv = 10000):

        # here we build a fake set of trades
        # your code should return the same sort of data
        start = sd - dt.timedelta(days=60)
        dates = pd.date_range(start, ed)
        data = ut.get_data([symbol], dates)
        data.fillna(method="ffill", inplace=True)
        data.fillna(method="bfill", inplace=True)
        prices = indicators.normalized_prices(data[symbol])
        trades = pd.DataFrame(index=prices[sd:].index, columns=[symbol])

        self.rsi = indicators.rolling_rsi(prices, 5)
        self.bollinger_bands = indicators.bollinger_bands(prices, 15)
        self.momentum = indicators.rolling_momentum(prices, 2)

        action = 2
        holding = 0
        for i in range(len(trades)):
            prices_to_date = prices[:trades.index[i]]
            state = self.create_state(trades.index[i],prices_to_date, action)
            action = self.learner.querysetstate(state)
            if action is 0:
                trades.ix[i] = -1000 - holding
                holding = -1000
            if action is 1:
                trades.ix[i] = 1000 - holding
                holding = 1000
            if action is 2:
                trades.ix[i] = 0
        return trades

if __name__=="__main__":
    learner = StrategyLearner()
    # learner.addEvidence()
    # learner.testPolicy()
