"""MC2-P1: Market simulator."""

import pandas as pd
import numpy as np
import datetime as dt
import os
from util import get_data, plot_data

def execute_order(prices, date, commission, portfolio, order,impact):
    symbol = order[0]
    order_type = order[1]
    shares = order[2]
    price_per_share = prices[symbol][date]
    total_price = price_per_share * shares
    if order_type == 'BUY':
        portfolio.ix[date]['cash'] -= total_price
        portfolio.ix[date][symbol] += shares
    else:
        portfolio.ix[date]['cash'] += total_price
        portfolio.ix[date][symbol] -= shares
    portfolio.ix[date]['cash'] -= commission + total_price *impact

def author():
    return "nlerner3"

def compute_portvals(orders_file = "./orders/orders.csv", start_val = 1000000, commission=9.95, impact=0.005):
    # this is the function the autograder will call to test your code
    # NOTE: orders_file may be a string, or it may be a file object. Your
    # code should work correctly with either input
    # TODO: Your code here
    if type(orders_file) is str:
        orders_file = open(orders_file)
    orders_df = pd.read_csv(orders_file, index_col='Date', parse_dates=True, na_values=['nan'])
    start_date = orders_df[:1].index[0]
    end_date = orders_df[-1:].index[0]
    symbols = list(set([i for i in orders_df['Symbol']]))
    dates = pd.date_range(start_date, end_date)
    prices = get_data(symbols, dates)
    portfolio = pd.DataFrame(index=prices.index,columns=['cash']+symbols)
    raw_value = pd.DataFrame(index=prices.index,columns=['value'])
    portfolio.set_value(start_date,'cash',start_val)
    portfolio.ix[start_date].values[1:] = 0

    prev_date = start_date
    for date in prices.index:
        portfolio.ix[date].values[:] = portfolio.ix[prev_date].values[:]
        if date in orders_df.index:
            orders = orders_df.ix[date]
            if type(orders) is not pd.DataFrame:
                execute_order(prices,date,commission,portfolio,orders, impact)
            else:
                for order in orders.iterrows():
                    execute_order(prices,date,commission,portfolio,order[1],impact)
        raw_value.ix[date] = portfolio.ix[date]['cash'] + np.sum([portfolio.ix[date][i] * prices[i][date] for i in symbols])
        prev_date = date
    return raw_value

def test_code():
    # this is a helper function you can use to test your code
    # note that during autograding his function will not be called.
    # Define input parameters

    of = "./orders/orders-03.csv"
    sv = 1000000

    # Process orders
    portvals = compute_portvals(orders_file = of, start_val = sv)
    if isinstance(portvals, pd.DataFrame):
        portvals = portvals[portvals.columns[0]] # just get the first column
    else:
        "warning, code did not return a DataFrame"
    
    # Get portfolio stats
    # Here we just fake the data. you should use your code from previous assignments.
    start_date = dt.datetime(2008,1,1)
    end_date = dt.datetime(2008,6,1)
    cum_ret, avg_daily_ret, std_daily_ret, sharpe_ratio = [0.2,0.01,0.02,1.5]
    cum_ret_SPY, avg_daily_ret_SPY, std_daily_ret_SPY, sharpe_ratio_SPY = [0.2,0.01,0.02,1.5]

    # Compare portfolio against $SPX
    print "Date Range: {} to {}".format(start_date, end_date)
    print
    print "Sharpe Ratio of Fund: {}".format(sharpe_ratio)
    print "Sharpe Ratio of SPY : {}".format(sharpe_ratio_SPY)
    print
    print "Cumulative Return of Fund: {}".format(cum_ret)
    print "Cumulative Return of SPY : {}".format(cum_ret_SPY)
    print
    print "Standard Deviation of Fund: {}".format(std_daily_ret)
    print "Standard Deviation of SPY : {}".format(std_daily_ret_SPY)
    print
    print "Average Daily Return of Fund: {}".format(avg_daily_ret)
    print "Average Daily Return of SPY : {}".format(avg_daily_ret_SPY)
    print
    print "Final Portfolio Value: {}".format(portvals[-1])

if __name__ == "__main__":
    test_code()
