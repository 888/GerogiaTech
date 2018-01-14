import indicators
import datetime as dt
start_date = dt.datetime(2008,1,1)
end_date = dt.datetime(2009,12,31)
indicators.plot_all_charts_for_symbol('JPM', start_date, end_date)
