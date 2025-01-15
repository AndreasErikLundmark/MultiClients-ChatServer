# Java Multi-Client Chat Server with JavaFX GUI

This project consists of a **Java chat server** capable of handling multiple clients, built using **Java threads** and **sockets**. The server features a simple **JavaFX GUI** for easy interaction and management, while the client is run in the console, more for demonstration purpose.

## Server Setup

### 1. Run the Server

To launch the chat server simply:

- **Default Port (2000):**
    ```bash
    java -jar Server.jar
    ```

- **Custom Port:**
    To launch the server on a custom port, specify the port number:
    ```bash
    java -jar Server.jar <customport>
    ```

### 2. Server Features
- Handles multiple clients using Java threads and sockets.
- **JavaFX GUI** for viewing connected clients, messages, and server status.
- Customizable communication port (default is 2000).

## Client Setup

### 1. Run the Client

To start the client, navigate to the `src/` directory and run:

- **Default Port (2000):**
    ```bash
    java Client
    ```

- **Custom Port:**
    To connect the client to a server running on a custom port, provide the custom port number:
    ```bash
    java Client <customport>
    ```

### 2. Client Features
- Connects to the chat server and sends/receives messages.
- The client can be launched with a default or custom port depending on the server configuration.

