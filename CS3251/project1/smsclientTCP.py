import socket
import sys

HOST = sys.argv[1]
PORT = int(sys.argv[2])
data = open(sys.argv[3],'r').read().replace("\n", " ")

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
    sock.connect((HOST, PORT))
    sock.sendall(bytes(data + "\n", "utf-8"))
    res = str(sock.recv(1024), "utf-8")

print("Response From Server:\n ", res)
