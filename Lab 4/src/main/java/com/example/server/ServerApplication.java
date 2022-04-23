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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Frontend application responsible to setup the stage to display the messages for server.
 * @author Soham
 * @version 1.0
 * @see Application
 * @see FXMLLoader
 * @see Scene
 * @see String
 * @see Server
 */
public class ServerApplication extends Application {

    /**
     * Creates a new Server instance to run the server.
     * @param stage Reference to current Stage.
     * @throws IOException Throws IOException if an exception occurs during IO operation.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ServerApplication.class.getResource("server.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Multithreaded Server");
        stage.setScene(scene);
        stage.show();

        new Server(fxmlLoader.getController());
    }

    /**
     * Launches the entire javafx application.
     * @param args Command line arguments passed from the terminal.
     */
    public static void main(String[] args) {
        launch();
    }

}