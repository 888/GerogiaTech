import indicators as indicators
from util import get_data
import pandas as pd
import datetime as dt
import matplotlib.pyplot as plt
import ManualStrategy
import marketismcode
import StrategyLearner
'manual'
symbol = "JPM"
ms = ManualStrategy.ManualStrategy()
def author():
    return 'nlerner3'
def run(sd, ed, impact_learner, strategy_learner):
    impact_orders = impact_learner.testPolicy(symbol, sd,ed,1000000)
    strategy_orders = strategy_learner.testPolicy(symbol, sd,ed,1000000)
    # manual_orders = ms.testPolicy(symbol,sd,ed,1000000)
    # bench_orders = ms.benchmark(symbol, sd, ed, 1000000)
    #
    # manual_sim = marketismcode.compute_portvals(manual_orders,impact=impact, commission=0)
    # bench_sim = marketismcode.compute_portvals(bench_orders,impact=impact, commission=0)
    strategy_sim = marketismcode.compute_portvals(strategy_orders,impact=impact, commission=0)
    impact_sim = marketismcode.compute_portvals(impact_orders,impact=impact, commission=0)

    long_positions = strategy_sim['value'].where(strategy_orders['JPM']>0).dropna(how="all")
    short_positions= strategy_sim['value'].where(strategy_orders['JPM']<0).dropna(how="all")
    impact_long_positions = impact_sim['value'].where(impact_orders['JPM']>0).dropna(how="all")
    impact_short_positions= impact_sim['value'].where(impact_orders['JPM']<0).dropna(how="all")

    # manual_sim['value']= manual_sim['value']/manual_sim['value'][0]
    # bench_sim['value']= bench_sim['value']/bench_sim['value'][0]
    strategy_sim['value']= strategy_sim['value']/strategy_sim['value'][0]
    impact_sim['value']=impact_sim['value']/impact_sim['value'][0]

    # daily_returns = (manual_sim['value'][1:] / manual_sim['value'][:-1].values) - 1
    #
    # dr_mean = daily_returns.mean()
    #
    # dr_std = daily_returns.std()
    #
    # cr = (manual_sim['value'][-1] / manual_sim['value'][0]) - 1
    # print str(sd) + ' - ' + str(ed)
    # print "Manual-----------------------"
    # print "\tCR: " + str(cr)
    # print "\tMean of daily returns: " + str(dr_mean)
    # print "\tSTD of daily returns: " + str(dr_std) + '\n'
    #
    # bench_daily_returns = (bench_sim['value'][1:] / bench_sim['value'][:-1].values) - 1
    #
    # bench_dr_mean = bench_daily_returns.mean()
    #
    # bench_dr_std = bench_daily_returns.std()
    #
    # bench_cr = (bench_sim['value'][-1] / bench_sim['value'][0]) - 1
    # print "Bench-----------------------"
    # print "\tCR: " + str(bench_cr)
    # print "\tMean of daily returns: " + str(bench_dr_mean)
    # print "\tSTD of daily returns: " + str(bench_dr_std) + '\n'
    print "Impact: " + str(impact)
    strategy_daily_returns = (strategy_sim['value'][1:] / strategy_sim['value'][:-1].values) - 1
    strategy_dr_mean =strategy_daily_returns.mean()
    strategy_dr_std = strategy_daily_returns.std()
    strategy_cr = (strategy_sim['value'][-1] / strategy_sim['value'][0]) - 1
    print "Strategy-----------------------"
    print "\tCR: " + str(strategy_cr)
    print "\tMean of daily returns: " + str(strategy_dr_mean)
    print "\tSTD of daily returns: " + str(strategy_dr_std) + '\n'
    print "\tNum Trades: " + str(len(long_positions) + len(short_positions))

    impact_daily_returns = (impact_sim['value'][1:] / impact_sim['value'][:-1].values) - 1
    impact_dr_mean =impact_daily_returns.mean()
    impact_dr_std = impact_daily_returns.std()
    impact_cr = (impact_sim['value'][-1] / impact_sim['value'][0]) - 1
    print "Impact Strategy-----------------------"
    print "\tCR: " + str(impact_cr)
    print "\tMean of daily returns: " + str(impact_dr_mean)
    print "\tSTD of daily returns: " + str(impact_dr_std) + '\n'
    print "\tNum Trades: " + str(len(impact_long_positions) + len(impact_short_positions))
    # manual_sim.columns = ['Manual Trader']
    # bench_sim.columns = ['Benchmark']
    strategy_sim.columns = ['Strategy Learner Trained Without Impact']
    impact_sim.columns = ['Strategy Learner Trained With Impact']
    ax = impact_sim.plot(title=symbol + " Trader Performances\n" + str(sd) + ' - ' + str(ed) +'\n Impact: ' + str(impact), color="black")
    # bench_sim.plot(ax=ax, color="blue")
    strategy_sim.plot(ax=ax, color="red")
    # manual_sim.plot(ax=ax, color="green")
    ax.set_ylabel('Portfolio Value')
    ax.set_xlabel('Date')

impact = 0.05
impact_learner = StrategyLearner.StrategyLearner(impact=impact)
strategy_learner = StrategyLearner.StrategyLearner()

impact_learner.addEvidence(symbol,dt.datetime(2008,1,1),dt.datetime(2009,12,31),100000)
strategy_learner.addEvidence(symbol,dt.datetime(2008,1,1),dt.datetime(2009,12,31),100000)

run(dt.datetime(2008,1,1),dt.datetime(2009,12,31), impact_learner, strategy_learner)
impact = 0
run(dt.datetime(2008,1,1),dt.datetime(2009,12,31), impact_learner, strategy_learner)

plt.show()
