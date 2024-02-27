import socket

# Listener method 
def start_listener():
    # Creates a listener socket at 0.0.0.0:8080 
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('0.0.0.0', 8080))
    server_socket.listen(5)

    print("Listening on 0.0.0.0:8080")

    # Handle incoming connections + decode packet and print contents 
    client_socket, client_address = server_socket.accept()
    print(f"Accepted connection from {client_address}") 
    while True:
        data = client_socket.recv(1024) # Buffer size - I 100% guessed and 100000000% going to need to be much bigger 
        if not data:
            break
        print(f"Received data: {data.decode()}")
    client_socket.close()

# Main loop 
if __name__ == '__main__':
    start_listener()