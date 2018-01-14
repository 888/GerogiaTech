import os
from time import sleep
from datetime import datetime, timedelta
hosts = ['medium.com','mailchimp.com','hazelroad.com']
while True:
    for host in hosts:
	    foldername = './%sTR/' % (host)
	    i = len(os.listdir(foldername)) - 1
	    filename = './%sTR/%s:%s.txt' %(host, host, str(i))
	    f = open(filename, 'w+')
	    f.write('time: %s\n\n' % str(datetime.now()))
	    f.close()
	    cmd = "traceroute %s >> %s" % (host, filename)
	    os.system(cmd)
	sleep(8 * 3600)