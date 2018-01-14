import socket
import sys
import select

HOST = sys.argv[1]
PORT = int(sys.argv[2])
data = open(sys.argv[3],'r').read().replace("\n", " ")

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.setblocking(0)
i = 0
res = None
while i < 3 and res is None:
    sock.sendto(bytes(data,"UTF-8"), (HOST, PORT))
    ready = select.select([sock], [], [], 2)
    if ready[0]:
        res = str(sock.recv(1024), "utf-8")
        print("Response From Server:\n ", res)
    else:
        print("The server has not answered in the last two seconds.\nRetrying...\n")
        i+=1
if not res:
    print("Error! Server isn't responding")
    exit(1)