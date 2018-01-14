import indicators
from util import get_data
import pandas as pd
import datetime as dt
import matplotlib.pyplot as plt
import ManualStrategy
import marketismcode

'manual'
ms = ManualStrategy.ManualStrategy()
def run(sd, ed, vert_lines):
    manual_orders = ms.testPolicy('JPM',sd,ed,1000000)
    bench_orders = ms.benchmark('JPM', sd, ed, 1000000)

    manual_sim = marketismcode.compute_portvals(manual_orders)
    bench_sim = marketismcode.compute_portvals(bench_orders)

    manual_sim['value']= manual_sim['value']/manual_sim['value'][0]
    bench_sim['value']= bench_sim['value']/bench_sim['value'][0]

    long_positions = manual_sim['value'].where(manual_orders['JPM']>0).dropna(how="all")
    short_positions= manual_sim['value'].where(manual_orders['JPM']<0).dropna(how="all")

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

    long_positions.columns = ['Long Positions']
    short_positions.columns = ['Short Positions']
    manual_sim.columns = ['Manual Strategy']
    bench_sim.columns = ['Benchmark']
    ax = manual_sim.plot(title="Manual Strategy vs Benchmark\n" + str(sd) + ' - ' + str(ed), color="black")
    bench_sim.plot(ax=ax, color="blue")
    if vert_lines:
        for i in short_positions.index:
            ax.axvline(x=i, c='r')
        for i in long_positions.index:
            ax.axvline(x=i, c='g')
    ax.set_ylabel('Portfolio Value')
    ax.set_xlabel('Date')



run(dt.datetime(2008,1,1),dt.datetime(2009,12,31),True)
run(dt.datetime(2010,1,1),dt.datetime(2011,12,31), False)
plt.show()
# ms.find_best_params('JPM',dt.datetime(2008,1,1),dt.datetime(2009,12,31))