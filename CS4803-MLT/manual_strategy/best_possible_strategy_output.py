import datetime as dt
import matplotlib.pyplot as plt
import BestPossibleStrategy
import marketismcode

start_date = dt.datetime(2010,1,1)
end_date = dt.datetime(2011,12,31)

bps = BestPossibleStrategy.BestPossibleStrategy()

best_orders = bps.testPolicy('JPM', start_date, end_date, 100000)
best_values = marketismcode.compute_portvals(best_orders, commission=0,impact=0)

bench_orders = bps.benchmark('JPM',start_date, end_date,1000)
bench_values = marketismcode.compute_portvals(bench_orders, commission=0, impact=0)

best_daily_returns = (best_values['value'][1:] / best_values['value'][:-1].values) - 1
bench_daily_returns = (bench_values['value'][1:] / bench_values['value'][:-1].values) - 1

best_dr_mean = best_daily_returns.mean()
bench_dr_mean = bench_daily_returns.mean()

best_dr_std = best_daily_returns.std()
bench_dr_std = bench_daily_returns.std()

best_cr = (best_values['value'][-1] / best_values['value'][0]) - 1
bench_cr = (bench_values['value'][-1] / bench_values['value'][0]) - 1

print "Best Possible Strategy\n-----------------------"
print "\tCR: " + str(best_cr)
print "\tMean of daily returns: " + str(best_dr_mean)
print "\tSTD of daily returns: " + str(best_dr_std) + '\n'
print "Benchmark\n-----------------------"
print "\tCR: " + str(bench_cr)
print "\tMean of daily returns: " + str(bench_dr_mean)
print "\tSTD of daily returns: " + str(bench_dr_std) + '\n'
best_values.columns = ['Best Possible Strategy']
ax = best_values.plot(title="Best Possible Strategy vs Benchmark", label="Best Possible Strategy", color="black")
bench_values.columns = ['Benchmark']
bench_values.plot(color="blue", ax=ax)
ax.set_ylabel("Value")
ax.set_xlabel("Date")
plt.show()
