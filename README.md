# Java Multi-Client Chat Server

This project consists of a **Java chat server** capable of handling multiple clients, built using **Java threads** and **sockets**. The server features a simple **JavaFX GUI** for easy interaction and management, while the client is run in the console, more for demonstration purpose.

<img width="805" alt="Chat-Server" src="https://github.com/user-attachments/assets/e825666a-82e7-4afa-b31e-53fd595d464b" />


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

## Tips for Creating a Fat Jar Including JavaFX on the Server Side 

1. **Good read**: [How to Deploy Your JavaFX Application the Easy Way](https://medium.com/@PoulLorca/how-to-deploy-your-javafx-application-the-easy-way-51ce105700a4)
2. **Use Liberica Full JDK** that includes JavaFX: [Liberica Full JDK](https://bell-sw.com/pages/downloads/#jdk-21-lts)
3. In IntelliJ, create a new artifact under "Project Structure". Choose to add a new Jar with “from modules with dependencies…”
4. To create a new jar:
    ```bash
    mvn clean package
    ```
5. The `manifest.mf` specifies the Server (launch class).
6. Maven creates new jars under the `target` directory. They should include JavaFX the way things are set up in the server build.


   

