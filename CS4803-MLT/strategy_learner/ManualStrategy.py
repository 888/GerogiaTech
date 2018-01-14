import util
import indicators as indicators
import pandas as pd
from datetime import timedelta
import marketismcode
class ManualStrategy(object):
    def __init__(self, bol_weight = 2, rsi_weight = 4, mom_weight = 2, bol_window = 25, rsi_window=5, mom_window=20):
        self.bol_window = bol_window
        self.rsi_window = rsi_window
        self.mom_window = mom_window
        self.bol_weight = bol_weight
        self.mom_wight = mom_weight
        self.rsi_weight = rsi_weight
        self.thresh = 0.3

    def testPolicy(self, symbol, sd, ed, sv):
        start_date = sd - timedelta(days=max(self.bol_window, self.mom_window, self.rsi_window)*2)
        dates = pd.date_range(start_date, ed)
        data = util.get_data([symbol], dates)
        data.fillna(method="ffill", inplace=True)
        data.fillna(method="bfill", inplace=True)
        prices = indicators.normalized_prices(data[symbol])

        trades = pd.DataFrame(index=prices[sd:].index, columns=[symbol])
        trades[:] = 0
        current_shares = 0
        for i in range(len(trades)):
            available_prices = prices[:trades.index[i]]
            decision = self.decide(available_prices)
            if decision > 0:
                trades.ix[i] = 1000 - current_shares
                current_shares = 1000
            if decision < 0:
                trades.ix[i] = -1000 - current_shares
                current_shares = -1000
        return trades
    def author(self):
        return 'nlerner3'
    def decide(self, prices_to_date):
        bol_vote = self.bol_weight * self.bollinger_vote(prices_to_date)
        mom_vote = self.mom_wight * self.momentum_vote(prices_to_date)
        rsi_vote = self.rsi_weight * self.rsi_vote(prices_to_date)
        abs_max_possible = self.bol_weight + self.mom_wight + self.rsi_weight
        vote = bol_vote + mom_vote + rsi_vote
        if vote > 0:
            return 1
        if vote < 0:
            return  -1
        return  0

    def bollinger_vote(self, prices_to_date):
        roling_band = indicators.bollinger_bands(prices_to_date[-(self.bol_window + 2):], self.bol_window)
        current_price = prices_to_date[-1]
        last_price = prices_to_date[-2]
        current_band = roling_band.ix[-1]
        last_band = roling_band.ix[-2]
        if current_price <= current_band['UPPER_BAND'] and last_price > last_band['UPPER_BAND']:
            return -1
        if current_price >= current_band['LOWER_BAND'] and last_price < last_band['LOWER_BAND']:
            return 1
        return 0

    def momentum_vote(self, prices_to_date):
        rolling_momentum = indicators.rolling_momentum(prices_to_date[-(self.mom_window + 2):], self.mom_window).ix[-1]
        if rolling_momentum > 0.32:
            return 1
        if rolling_momentum < 0.32:
            return -1
        return 0
    def rsi_vote(self, prices_to_date):
        rsi = indicators.rolling_rsi(prices_to_date[-(self.rsi_window + 2):], self.rsi_window)
        current_rsi = rsi.ix[-1]
        last_rsi = rsi.ix[-2]
        if current_rsi < 30 and last_rsi <= 30:
            return 1
        if current_rsi < 70 and last_rsi >= 70:
            return -1
        return 0

    def benchmark(self, symbol, sd, ed, sv):
        dates = pd.date_range(sd, ed)
        data = util.get_data([symbol], dates)
        data.fillna(method="ffill", inplace=True)
        data.fillna(method="bfill", inplace=True)
        prices = indicators.normalized_prices(data[symbol])

        trades = pd.DataFrame(index=prices.index, columns=[symbol])
        trades[:] = 0
        trades.ix[0] = 1000
        return trades

    def find_best_params(self, symbol, start_date, end_date):
        best_weights = [(0, 0, 0, 0,0,0)]
        highest_value = None
        i = 0
        for mom_weight in [2,3,4,8]:
            for rsi_weight in [2,3,4,8]:
                for bol_weight in [2,3,4,8]:
                    if mom_weight + rsi_weight + bol_weight == 0:
                        continue
                    print "on iter " + str(i)
                    self.__init__(bol_weight,mom_weight,rsi_weight)
                    orders = self.testPolicy(symbol, start_date, end_date, 100000)
                    value = marketismcode.compute_portvals(orders)
                    val = value.ix[-1][0]
                    if highest_value == val:
                        print "equivalent high value found!"
                        best_weights.append((bol_weight, rsi_weight, mom_weight))
                        print (bol_weight, rsi_weight, mom_weight)
                    if highest_value is None or val > highest_value:
                        print "highest value found!"
                        best_weights = [(bol_weight, rsi_weight, mom_weight)]
                        highest_value = val
                        print highest_value
                        print best_weights
                        print ('------')
                    i+=1

        print('----------')
        print highest_value
        print best_weights

