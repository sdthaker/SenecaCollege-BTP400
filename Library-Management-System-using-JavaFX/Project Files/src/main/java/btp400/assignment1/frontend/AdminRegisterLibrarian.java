package btp400.assignment1.frontend;

import btp400.assignment1.backend.Admin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class displays the form to gather the data to register a new librarian.
 * If a librarian cannot be created, an error message will appear. A successful
 * registration will save the librarian in the librarians.json file and return a
 * success message.
 * @author Nathaniel Ling
 * @version 1.0.0
 * @see Admin
 * @see VBox
 * @see GridPane
 * @see Stage
 * @see Text
 * @see Button
 * @see Label
 * @see TextField
 * @see String
 * @see HBox
 */
public class AdminRegisterLibrarian {

    /**
     * Root of current scene
     */
    private final GridPane root;

    /**
     * Previous root of current scene
     */
    private final VBox prevRoot;

    /**
     * Primary stage
     */
    private final Stage stage;

    /**
     * Current instance of admin
     */
    Admin admin;

    /**
     * Constructor sets values of class attributes to values of arguments.
     * Sets root to new Gridpane.
     * @param a Reference to Admin
     * @param prevRoot Reference to previous root
     * @param stage Reference to primary stage
     */
    public AdminRegisterLibrarian(Admin a, VBox prevRoot, Stage stage) {
        this.root = new GridPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setHgap(10);
        this.root.setVgap(10);
        this.root.setPadding(new Insets(25, 25, 25, 25));
        this.prevRoot = prevRoot;
        this.stage = stage;
        admin = a;
    }

    /**
     * This method uses setupForm to create the form used to gather data for a
     * new librarian.
     * @return Gridpane root with form
     */
    public GridPane getRootPane() {
        setupForm();
        return root;
    }

    /**
     * This method sets the librarians JSONArray in admin to the data from the setFile and
     * creates the form to gather data on librarian. Includes fname, lname, email and password.
     * All fields are required to be filled. When the register button is activated, it calls
     * the registerLibrarian admin method to register the librarian using the data gathered
     * from the form as arguments for the parameters of the method.
     */
    private void setupForm() {
        Text scenetitle = new Text("Register Librarian");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.add(scenetitle, 0, 0, 2, 1);
        Button register = new Button("Register");
        Admin admin = new Admin();
        admin.setFile("resources/admin/librarians.json");
        admin.readDataFromFile(false);

        Label fname = new Label("First Name:");
        root.add(fname, 0, 1);

        TextField fnameField = new TextField();
        root.add(fnameField, 1, 1);

        Label lname = new Label("Last Name:");
        root.add(lname, 0, 2);

        TextField lnameField = new TextField();
        root.add(lnameField, 1, 2);

        Label email = new Label("Email:");
        root.add(email, 0, 3);

        TextField emailField = new TextField();
        root.add(emailField, 1, 3);

        Label password = new Label("Password");
        root.add(password, 0, 4);

        TextField pwField = new TextField();
        root.add(pwField, 1, 4);

        final Text actiontarget = new Text();
        actiontarget.setFont(Font.font("verdana", FontPosture.REGULAR, 17));
        root.add(actiontarget, 1, 6);

        register.setOnAction(e -> {
            if(emailField.getText().isEmpty() ||
                    fnameField.getText().isEmpty() ||
                    lnameField.getText().isEmpty() ||
                    pwField.getText().isEmpty()) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("All fields are required");
            }
            else {
                String msg = admin.registerLibrarian(
                        fnameField.getText(),
                        lnameField.getText(),
                        emailField.getText(),
                        pwField.getText());
                if (msg.equals("Success")) {
                    admin.writeDataToFile();
                    actiontarget.setFill(Color.GREEN);
                    actiontarget.setText("Librarian registered successfully!");
                    fnameField.clear();
                    lnameField.clear();
                    emailField.clear();
                    pwField.clear();
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText(msg);
                }
            }
        });

        Button back = new Button("Go Back");
        back.setOnAction(e -> stage.getScene().setRoot(prevRoot));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(back);
        hbBtn.getChildren().add(register);

        root.add(hbBtn, 1, 5);
    }
}
