import socket
import sys
from spam_scorer import SpamScorer
PORT = int(sys.argv[1])
spamScorer = SpamScorer(sys.argv[2])

HOST = "127.0.0.1"
print("server running at 127.0.0.1:",PORT)
print("Ctrl+c to close server\n")

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((HOST,PORT))
server.listen(5)

while True:
    req, address = server.accept()
    rfile = req.makefile('rb', -1)
    data = rfile.readline().strip()
    print("Connection From: ", address,"\n")
    print("Recieved: ",data,"\n")
    numSusWords, score, susWords = spamScorer.spamScore(data.decode("utf-8"))
    res = "%s %s %s\n" % (numSusWords, score, " ".join(susWords))
    print("Responding: ", res)
    wfile = req.makefile('wb', 0)
    wfile.write(bytes(res,'utf-8'))
    print('Connection Closed\n-----------------------------------')


