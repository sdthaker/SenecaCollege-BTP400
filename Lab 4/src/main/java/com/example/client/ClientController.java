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

package com.example.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

/**
 * Controller that is responsible to obtain root nodes of current window, updates
 * UI, creates a client socket to connect to the server,
 * sends messages to and receives messages from the server.
 * @author Soham
 * @version 1.0
 * @see Runnable
 * @see TextArea
 * @see String
 * @see Socket
 * @see BufferedReader
 * @see PrintWriter
 * @see ActionEvent
 * @see Stage
 * @see IOException
 */
public class ClientController implements Runnable {

    /**
     * TextArea node on the current window used to
     * allow users to type messages inside a text area on
     * client window.
     */
    @FXML
    private TextArea tfClientInput = new TextArea();

    /**
     * TextArea node on the current window used to show
     * server replied messages to user on server window.
     */
    @FXML
    private TextArea tfMessageArea = new TextArea();

    /**
     * Name of the user that has logged in.
     */
    private String clientName = "";

    /**
     * Instance of client socket.
     */
    Socket socket;

    /**
     * Used to read data sent from the server.
     */
    BufferedReader input;

    /**
     * Used to send data to the server.
     */
    PrintWriter output;

    /**
     * String that holds the data that needs to be sent
     * to the sever.
     */
    private String sendData = "";

    /**
     * Setter for the message text area which displays all the
     * messages coming from server.
     * @param tfMessageArea Message that needs to displayed to the user.
     */
    public void setTfMessageArea(String tfMessageArea) {
        String message = this.tfMessageArea.getText() + tfMessageArea;
        this.tfMessageArea.setText(message);
    }

    /**
     * Button even handler that is fired whenever a button is pressed.
     * @param event Action event, triggered when send button is clicked.
     */
    @FXML
    void sendHandler(ActionEvent event) {
        displayMessages();
    }

    /**
     * Displays messages to the UI window, sends messages to the server and
     * spawns a new thread which performs the task
     * of receiving messages from the server.
     */
    public void displayMessages(){
        Stage mainWindow = (Stage) tfClientInput.getScene().getWindow();
        String clientName = tfClientInput.getText();

        if(Objects.equals(this.clientName, "")) {
            mainWindow.setTitle(clientName);
            tfMessageArea.setText(tfMessageArea.getText() + clientName + "\nEnter \"quit\" to quit the chat!");
            tfClientInput.setText("");
            this.clientName = clientName;

            try{
                socket = new Socket("localhost",6000);

                input = new BufferedReader((new InputStreamReader(socket.getInputStream())));
                output = new PrintWriter(socket.getOutputStream(), true);

                new Thread(this).start();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            tfMessageArea.setText(tfMessageArea.getText() +
                    "\n\n" + this.clientName + ": "
                    + tfClientInput.getText());

            sendData = tfClientInput.getText();
            output.println(this.clientName + ": " + sendData);
            tfClientInput.setText("");
        }
    }

    /**
     * Receives messages from the server and displays on the window.
     */
    @Override
    public void run() {
        try {
            String responseData;

            while((responseData = input.readLine()) != null) {
                tfMessageArea.setText(tfMessageArea.getText()
                        + "\n\n" + responseData);
            }

            tfMessageArea.setText(tfMessageArea.getText()
                    + "\n\n" + "You are now disconnected from the server!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
