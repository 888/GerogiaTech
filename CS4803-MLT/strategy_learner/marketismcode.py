"""MC2-P1: Market simulator."""

import pandas as pd
import numpy as np
import datetime as dt
import os
from util import get_data, plot_data


def execute_order(prices, date, commission, portfolio, symbol, shares, impact):
    price_per_share = prices[symbol][date]
    total_price = price_per_share * shares
    portfolio.ix[date]['cash'] -= total_price
    portfolio.ix[date][symbol] += shares
    portfolio.ix[date]['cash'] -= abs(commission + total_price * impact)

def author():
    return "nlerner3"


def compute_portvals(orders_df, start_val=1000000, commission=9.95, impact=0.005):
    start_date = orders_df[:1].index[0]
    end_date = orders_df[-1:].index[0]
    dates = pd.date_range(start_date, end_date)
    symbol =orders_df.columns[0]
    prices = get_data([symbol], dates)
    portfolio = pd.DataFrame(index=prices.index, columns=['cash', symbol])
    raw_value = pd.DataFrame(index=prices.index, columns=['value'])
    portfolio.set_value(start_date, 'cash', start_val)
    portfolio.ix[start_date].values[1:] = 0

    prev_date = start_date
    for date in prices.index:
        portfolio.ix[date].values[:] = portfolio.ix[prev_date].values[:]
        if date in orders_df.index:
            delta_shares = orders_df.ix[date][0]
            execute_order(prices, date, commission, portfolio, symbol, delta_shares, impact)
        raw_value.ix[date] = portfolio.ix[date]['cash'] + portfolio.ix[date][symbol] * prices[symbol][date]
        prev_date = date
    return raw_value