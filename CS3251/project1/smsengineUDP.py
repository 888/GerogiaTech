import socket
import sys
from spam_scorer import SpamScorer
PORT = int(sys.argv[1])
spamScorer = SpamScorer(sys.argv[2])


HOST = "127.0.0.1"
print("server running at 127.0.0.1:",PORT)
print("Ctrl+c to close server\n")

server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server.bind((HOST,PORT))

while True:
    data, address = server.recvfrom(1024)
    print("Recieving Data from: ", address,"\n")
    print("Recieved: ",data,"\n")
    numSusWords, score, susWords = spamScorer.spamScore(data.decode("utf-8"))
    res = "%s %s %s\n" % (numSusWords, score, " ".join(susWords))
    print("Responding: ", res)
    server.sendto(bytes(res,'utf-8'),address)
    print('----------------------------')