/**********************************************
 Workshop 4
 Course:BTP400 - Semester 4
 Last Name: Thaker
 First Name: Soham
 ID: 011-748-159
 Section: NBB
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature Date: April 10, 2022
 **********************************************/

package com.example.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Responsible for creating a server socket, accepting new client
 * connections, removing a client connection, and
 * broadcasting those messages to all connected clients.
 * @author Soham
 * @version 1.0
 * @see Runnable
 * @see ServerSocket
 * @see ServerController
 * @see Hashtable
 * @see PrintWriter
 * @see Socket
 * @see DateTimeFormatter
 * @see LocalDateTime
 * @see Thread
 * @see String
 * @see Set
 * @see Map
 * @see MultiThreadedServer
 * @see IOException
 */
public class Server implements Runnable {

    /**
     * ServerSocket instance that is initialized to port 6000.
     */
    private final ServerSocket ss = new ServerSocket(6000);

    /**
     * Hashtable that holds Map of Socket and PrintWriter
     * instances; used to broadcast messages to all connected sockets.
     */
    private final Hashtable<Socket, PrintWriter> connectedSockets
            = new Hashtable<>();

    /**
     * ServerController instance used to set the UI root nodes.
     */
    ServerController sc;

    /**
     * Initializes the current instance's attributes.
     * Calls listen to start listening for any client connection.
     * @param sc ServerController instance used to set the UI root nodes.
     * @throws IOException Throws IOException if an exception occurs during IO operation.
     */
    Server(ServerController sc) throws IOException {
        this.sc = sc;
        listen();
    }

    /**
     * Displays messages on UI, starts a new thread to detach itself
     * from the main thread to avoid blocking the execution of main thread.
     */
    private void listen() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String message = "Multithreaded server started at " + dtf.format(now);
        sc.setTfMessageArea(message);

        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Broadcasts message received from one client
     * to all connected clients except to the one that sent the message.
     * Locks the connectedSockets instance so that no other thread can
     * access it when the current thread is executing the function to avoid
     * half-changed situation for variable connectedSockets.
     * @param message The message sent from a client that needs
     *                to be broadcast to all connected clients.
     * @param s The client socket that sent the message.
     */
    public /*synchronized*/ void sendMessageToClients(String message, Socket s){
        synchronized (connectedSockets) {
            for (Map.Entry<Socket, PrintWriter> e : connectedSockets.entrySet()) {
                if (!s.equals(e.getKey())) {
                    PrintWriter pw = e.getValue();
                    pw.println(message);
                }
            }
        }
    }

    /**
     * Removes the key Socket and its PrintWriter value from connectedSockets and
     * close the socket.
     * @param s The client socket that needs to be removed.
     * @param clientName Name of the client who needs to be removed.
     */
    public /*synchronized*/ void removeClientSocket(Socket s, String clientName){
        synchronized (connectedSockets) {
            sc.setTfMessageArea("Removing " + clientName + " -- Socket info: " +  s);
            connectedSockets.remove(s);
            try{
                s.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Listens for new client socket connections, updates UI for the same,
     * adds the Socket and its PrintWriter instances to the connectedSockets instance,
     * creates new MultiThreadedServer instance to start receiving messages
     * from client and broadcasting messages to all clients.
     */
    @Override
    public void run() {

        DateTimeFormatter dtf;
        LocalDateTime now;

        try {
            while (true) {
                Socket socket = ss.accept();

                dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                now = LocalDateTime.now();
                sc.setTfMessageArea("Connection from Socket[addr=" + socket.getInetAddress()
                        + ", port=" + socket.getLocalPort() + ", localport=" + socket.getPort()
                        + "] at " + dtf.format(now));

                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                connectedSockets.put(socket, output);

                new MultiThreadedServer(this, socket, sc);
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
