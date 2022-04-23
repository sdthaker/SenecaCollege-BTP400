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

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller that is responsible to obtain root nodes of current window.
 * @author Soham
 * @version 1.0
 * @see TextArea
 * @see String
 */
public class ServerController {

    /**
     * TextArea node on the current window used to show
     * messages to user on server window.
     */
    @FXML
    private TextArea tfMessageArea = new TextArea();

    /**
     * Setter for the trMessageArea node.
     * @param tfMessageArea A message that is displayed on tfMessageArea.
     */
    public void setTfMessageArea(String tfMessageArea) {
        String message = this.tfMessageArea.getText() + tfMessageArea + "\n\n";
        this.tfMessageArea.setText(message);
    }
}
