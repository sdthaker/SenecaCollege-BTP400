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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/**
 * Frontend application responsible to set up the stage to display the messages for client.
 * @author Soham
 * @version 1.0
 * @see Application
 * @see IOException
 * @see Stage
 * @see FXMLLoader
 * @see ClientController
 * @see String
 */
public class ClientApplication extends Application {

    /**
     * Sets up the stage for the UI, displays the UI
     * and sets values for some root nodes.
     * @param stage Reference to current Stage.
     * @throws IOException Throws IOException if an exception occurs during IO operation.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();

        ClientController cc = fxmlLoader.getController();
        cc.setTfMessageArea("Enter your name: ");
    }

    /**
     * Launches the entire javafx application.
     * @param args Command line arguments passed from the terminal.
     */
    public static void main(String[] args) {
        launch();
    }
}