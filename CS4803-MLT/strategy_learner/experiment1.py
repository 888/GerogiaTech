import indicators as indicators
from util import get_data
import pandas as pd
import datetime as dt
import matplotlib.pyplot as plt
import ManualStrategy
import marketismcode
import StrategyLearner
'manual'
ms = ManualStrategy.ManualStrategy()
symbol = "JPM"
impact = 0
def author():
    return 'nlerner3'
def run(sd, ed, strategy_learner):
    strategy_orders = strategy_learner.testPolicy(symbol, sd,ed,1000000)
    manual_orders = ms.testPolicy(symbol,sd,ed,1000000)
    bench_orders = ms.benchmark(symbol, sd, ed, 1000000)

    manual_sim = marketismcode.compute_portvals(manual_orders, commission=0,impact=impact)
    bench_sim = marketismcode.compute_portvals(bench_orders, commission=0,impact=impact)
    strategy_sim = marketismcode.compute_portvals(strategy_orders, commission=0,impact=impact)

    manual_sim['value']= manual_sim['value']/manual_sim['value'][0]
    bench_sim['value']= bench_sim['value']/bench_sim['value'][0]
    strategy_sim['value']= strategy_sim['value']/strategy_sim['value'][0]

    daily_returns = (manual_sim['value'][1:] / manual_sim['value'][:-1].values) - 1

    dr_mean = daily_returns.mean()

    dr_std = daily_returns.std()

    cr = (manual_sim['value'][-1] / manual_sim['value'][0]) - 1
    print str(sd) + ' - ' + str(ed)
    print "Manual-----------------------"
    print "\tCR: " + str(cr)
    print "\tMean of daily returns: " + str(dr_mean)
    print "\tSTD of daily returns: " + str(dr_std) + '\n'

    bench_daily_returns = (bench_sim['value'][1:] / bench_sim['value'][:-1].values) - 1

    bench_dr_mean = bench_daily_returns.mean()

    bench_dr_std = bench_daily_returns.std()

    bench_cr = (bench_sim['value'][-1] / bench_sim['value'][0]) - 1
    print "Bench-----------------------"
    print "\tCR: " + str(bench_cr)
    print "\tMean of daily returns: " + str(bench_dr_mean)
    print "\tSTD of daily returns: " + str(bench_dr_std) + '\n'

    strategy_daily_returns = (strategy_sim['value'][1:] / strategy_sim['value'][:-1].values) - 1

    strategy_dr_mean =strategy_daily_returns.mean()

    strategy_dr_std = strategy_daily_returns.std()

    strategy_cr = (strategy_sim['value'][-1] / strategy_sim['value'][0]) - 1
    print "Strategy-----------------------"
    print "\tCR: " + str(strategy_cr)
    print "\tMean of daily returns: " + str(strategy_dr_mean)
    print "\tSTD of daily returns: " + str(strategy_dr_std) + '\n'

    manual_sim.columns = ['Manual Trader']
    bench_sim.columns = ['Benchmark']
    strategy_sim.columns = ['Strategy Learner']
    ax = manual_sim.plot(title=symbol + " Trader Performances\n" + str(sd) + ' - ' + str(ed), color="black")
    bench_sim.plot(ax=ax, color="blue")
    strategy_sim.plot(ax=ax, color="red")
    ax.set_ylabel('Portfolio Value')
    ax.set_xlabel('Date')



slearner = StrategyLearner.StrategyLearner(impact=impact)
slearner.addEvidence(symbol,dt.datetime(2008,1,1),dt.datetime(2009,12,31),100000)
run(dt.datetime(2008,1,1),dt.datetime(2009,12,31), slearner)
plt.show()
