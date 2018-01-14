Noam Lerner
noaml1295@gmail.com
CS 3251, 1/31/2017 Programming	Assignment	1:	Basics	of	Socket	Programming

Files
-------------------------------------
	smsengineUDP.py - udp server described in assignment 
	smsclientUDP.py - udp client described in assignment 
	smsengineTCP.py - tcp server described in assignment 
	smsclientTCP.py - tcp client described in assignment 
	spam_scorer.py  - Helper class to perform spam scoring functionality described in assignment

Running Program
-------------------------------------
The same steps are required to run the TCP or UDP versions of this project.

Python 3 was used for this project and so it is required to run the scripts in python 3.

To run, assuming python 3 is aliased to python3 on your machine:
 [fill in the words in caps with appropriate arguments]	
UDP server:	python3 smsengineUDP.py PORT SUSPICIOUS_WORDS.txt
UDP client: python3 smsclientUDP.py HOST PORT MSG.txt
TCP server:	python3 smsengineTCP.py PORT SUSPICIOUS_WORDS.txt
TCP client: python3 smsclientTCP.py HOST PORT MSG.txt