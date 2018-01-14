import indicators
import util
import pandas as pd
class BestPossibleStrategy(object):
    def __init__(self):
        pass

    def testPolicy(self, symbol, sd, ed, sv):
        dates = pd.date_range(sd, ed)
        data = util.get_data([symbol], dates)
        data.fillna(method="ffill", inplace=True)
        data.fillna(method="bfill", inplace=True)
        prices = indicators.normalized_prices(data[symbol])

        trades = pd.DataFrame(index=prices.index, columns=[symbol])
        trades[:] = 0
        momentum = indicators.rolling_momentum(prices, 2)
        buy_days = []
        sell_days = []
        # when momentum is positive, buy on the last day its positive and vice versas
        for i in range(1, len(momentum) -1):
            if momentum[i] >=0 and momentum[i+1] < 0:
                sell_days.append(i)
            elif momentum[i] <=0 and momentum[i+1] > 0:
                buy_days.append(i)
        if buy_days[0] < sell_days[0]:
            trades.ix[0] = -1000
        else:
            trades.ix[0] = 1000

        for i in buy_days:
            trades.ix[i] = 2000
        for i in sell_days:
            trades.ix[i] = -2000
        return trades

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