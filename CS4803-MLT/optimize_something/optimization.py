"""MC1-P2: Optimize a portfolio."""

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import datetime as dt
import scipy.optimize as spo
from util import get_data, plot_data

# This is the function that will be tested by the autograder
# The student must update this code to properly implement the functionality
def optimize_portfolio(sd=dt.datetime(2008,1,1), ed=dt.datetime(2009,1,1), \
    syms=['GOOG','AAPL','GLD','XOM'], gen_plot=False):

    # Read in adjusted closing prices for given symbols, date range
    dates = pd.date_range(sd, ed)
    prices_all = get_data(syms, dates)  # automatically adds SPY
    prices = prices_all[syms]  # only portfolio symbols
    prices_SPY = prices_all['SPY']  # only SPY, for comparison later

    # find the allocations for the optimal portfolio
    # note that the values here ARE NOT meant to be correct for a test case
    allocs_guess = [1.0/len(syms) for i in syms]
    constraints = (
        {'type': 'eq', 'fun': lambda x: np.sum(x) - 1}
    )
    bounds = [(0,1) for i in syms]
    x = spo.minimize(err,
                     allocs_guess,
                     args=(sd, ed, syms),
                     method="SLSQP",
                     constraints=constraints,
                     bounds=bounds,
                     options={'disp':True})
    allocs = x.x

    cr, adr, sddr, sr, ev = assess_portfolio(sd,ed,syms,allocs)

    # Get daily portfolio value    prices.fillna(method="ffill", inplace=True)
    prices.fillna(method="ffill", inplace=True)
    prices.fillna(method="bfill", inplace=True)
    # Get daily portfolio value
    port_val = np.sum(prices * np.array(allocs), axis = 1)

    # Compare daily portfolio value with SPY using a normalized plot
    if gen_plot:
        # add code to plot here
        df_temp = pd.concat([prices_SPY, port_val], keys=['Spy', 'Portfolio'], axis=1)
        df_temp = (df_temp[1:] / df_temp[:-1].values)
        plot_data(df_temp,
                  title="Daily Portfolio Value and Spy",
                  xlabel="Normalized Price",
                  ylabel="date")
        pass

    return allocs, cr, adr, sddr, sr
def err(allocs, sd, ed, syms):
    a = assess_portfolio(sd, ed, syms, allocs)
    return a[2]

def assess_portfolio(sd = dt.datetime(2008,1,1), ed = dt.datetime(2009,1,1), \
    syms = ['GOOG','AAPL','GLD','XOM'], \
    allocs=[0.1,0.2,0.3,0.4], \
    sv=1000000, rfr=0.0, sf=252.0, \
    gen_plot=False):

    # Read in adjusted closing prices for given symbols, date range
    dates = pd.date_range(sd, ed)
    prices_all = get_data(syms, dates)  # automatically adds SPY
    prices = prices_all[syms]  # only portfolio symbols
    prices_SPY = prices_all['SPY']  # only SPY, for comparison later

    prices.fillna(method="ffill", inplace=True)
    prices.fillna(method="bfill", inplace=True)
    # Get daily portfolio value
    port_val = np.sum(prices / prices[:1].values[0] * np.array(allocs) * sv, axis = 1)
    daily_returns = (port_val[1:] / port_val[:-1].values) - 1
    # Get portfolio statistics (note: std_daily_ret = volatility)
    cr = (port_val[-1] / port_val[0]) - 1
    adr = daily_returns.mean()
    sddr = daily_returns.std()
    sr = np.sqrt(sf) * (adr - rfr) /sddr
    # Compare daily portfolio value with SPY using a normalized plot
    if gen_plot:
        # add code to plot here
        df_temp = pd.concat([port_val, prices_SPY], keys=['Portfolio', 'SPY'], axis=1)
        df_temp = (df_temp[1:] / df_temp[:-1].values)
        plot_data(df_temp,
                  title="Daily Portfolio Value and Spy",
                  xlabel="Normalized Price",
                  ylabel="date")
        pass

    # Add code here to properly compute end value
    ev = port_val[-1]

    return cr, adr, sddr, sr, ev

def test_code():
    # This function WILL NOT be called by the auto grader
    # Do not assume that any variables defined here are available to your function/code
    # It is only here to help you set up and test your code

    # Define input parameters
    # Note that ALL of these values will be set to different values by
    # the autograder!

    start_date = dt.datetime(2008,6,1)
    end_date = dt.datetime(2009,06,1)
    symbols = ['GLD', 'X', 'IBM']

    # Assess the portfolio
    allocations, cr, adr, sddr, sr = optimize_portfolio(sd = start_date, ed = end_date,\
        syms = symbols, \
        gen_plot = True)

    # Print statistics
    print "Start Date:", start_date
    print "End Date:", end_date
    print "Symbols:", symbols
    print "Allocations:", allocations
    print "Sharpe Ratio:", sr
    print "Volatility (stdev of daily returns):", sddr
    print "Average Daily Return:", adr
    print "Cumulative Return:", cr

if __name__ == "__main__":
    # This code WILL NOT be called by the auto grader
    # Do not assume that it will be called
    test_code()
