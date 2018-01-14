import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from util import get_data
def bollinger_bands(prices, window=20):
    sma = pd.rolling_mean(prices, window=window)
    std = pd.rolling_std(prices,window=window)
    bands_df = pd.DataFrame(index=sma.index, columns=['LOWER_BAND', 'UPPER_BAND'])
    bands_df['UPPER_BAND'] = sma + 2 * std
    bands_df['LOWER_BAND'] = sma - 2 * std
    bands_df.dropna()
    return bands_df

def normalized_prices(prices):
    return prices/prices[0]

def _rsi_for_window(window):
    gains = []
    losses = []
    for i in range(1, len(window)):
        delta = window[i] - window[i - 1]
        if delta > 0:
            gains.append(delta)
        else:
            losses.append(abs(delta))
    RS = (np.sum(gains)/len(window)) / (np.sum(losses)/len(window))

    return 100 - 100 / (1 +RS)
def author():
    return 'nlerner3'
def rolling_rsi(prices, window=10):
    return pd.rolling_apply(prices, window, _rsi_for_window)

def rolling_momentum(prices, window=20):
    rm = pd.rolling_apply(prices, window, lambda arr: arr[-1]/arr[0] - 1)
    return rm

def plot_bands(symbol, prices, bands):
    ax = prices.plot(title = symbol + " Normalized To 1 And Bollinger Bands", label = symbol)
    bands.plot(ax=ax)
    ax.set_xlabel("Date")
    ax.set_ylabel("Price")
    ax.legend(loc='lower left')

def plot_momentum(symbol, prices, momentum):
    ax = (prices- 1).plot(title = symbol + " Normalized To 0 and Momentum", label = symbol)
    momentum.plot(ax=ax, label="Momentum")
    ax.set_xlabel("Date")
    ax.set_ylabel("Price")
    ax.legend(loc='upper left')

def plot_rsi(symbol, prices, rsi):
    ax = (prices * 100).plot(title = symbol + " Normalized to 1 * 100 and RSI", label = symbol)
    rsi.plot(ax=ax, label="RSI")
    lines = pd.DataFrame(index=rsi.index, columns=['70', '30'])
    lines['70'][:] = 70
    lines['30'][:] = 30
    lines.plot(ax=ax)
    ax.set_xlabel("Date")
    ax.set_ylabel("Price")
    ax.legend(loc='lower left')


def plot_all_charts_for_symbol(symbol, start_date, end_date):
    dates = pd.date_range(start_date, end_date)

    data = get_data([symbol],dates)
    data.fillna(method="ffill", inplace=True)
    data.fillna(method="bfill", inplace=True)
    prices = normalized_prices(data[symbol])

    bands = bollinger_bands(prices)
    plot_bands(symbol,prices,bands)
    plt.show()

    momentum = rolling_momentum(prices)
    plot_momentum(symbol, prices, momentum)
    plt.show()

    rsi = rolling_rsi(prices)
    plot_rsi(symbol, prices, rsi)
    plt.show()

