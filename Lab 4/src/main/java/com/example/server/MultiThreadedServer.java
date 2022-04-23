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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Responsible for receiving messages from connected clients.
 * @author Soham
 * @version 1.0
 * @see Server
 * @see Socket
 * @see ServerController
 * @see String
 * @see BufferedReader
 * @see InputStreamReader
 * @see IOException
 */
public class MultiThreadedServer extends Thread {

    /**
     * Instance of Server that is currently running.
     */
    private final Server server;

    /**
     * Instance of a client socket that is currently connected.
     */
    private final Socket socket;

    /**
     * ServerController instance used to set the UI root nodes.
     */
    private final ServerController sc;

    /**
     * Initializes current instance's attributes according to the params received
     * and starts thread execution.
     * @param server Instance of Server that is currently running.
     * @param socket Instance of a client socket that is currently connected.
     * @param sc ServerController instance used to set the UI root nodes.
     */
    MultiThreadedServer(Server server, Socket socket, ServerController sc) {
        this.server = server;
        this.socket = socket;
        this.sc = sc;

        start();
    }

    /**
     * Receives messages from the client, updates UI, calls the function on the Server
     * which is responsible to broadcast messages to all the clients, calls
     * the function on the Server that is responsible to remove the client
     * if they send "quit" string.
     */
    @Override
    public void run(){

        String clientName = "";

        try {
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true) {
                String clientMessage = input.readLine();
                sc.setTfMessageArea(clientMessage);

                String[] split = clientMessage.split(":");

                split[0] = split[0].replaceAll(" ", "");
                split[1] = split[1].replaceAll(" ", "");

                clientName = split[0];

                if(split[1].equals("quit")){
                    server.sendMessageToClients(split[0] + " is quitting!", socket);
                    break;
                }
                else{
                    server.sendMessageToClients(clientMessage, socket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            server.removeClientSocket(socket, clientName);
        }
    }
}
