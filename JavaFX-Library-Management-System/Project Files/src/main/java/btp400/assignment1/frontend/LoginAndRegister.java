package btp400.assignment1.frontend;

import btp400.assignment1.backend.Admin;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is responsible for creating the primary stage and a scene
 * to login. The user selection will be passed to this class, where we
 * render a login screen for that specified account type.
 * @author Zia Syed
 * @version 1.0.0
 * @see Application
 * @see IOException
 * @see VBox
 * @see Scene
 * @see ToggleGroup
 * @see RadioButton
 * @see Stage
 * @see Insets
 * @see Object
 */

public class LoginAndRegister {
    /**
     * Current root of the current scene of type GridPane
     */
    private final GridPane root ;
    /**
     * Holds the value of the currently selected button
     */
    private final ToggleGroup selectedButton;
    /**
     * Previous root of the current scene of type GridPane
     */
    private final VBox prevRoot;
    /**
     * Primary stage of the application
     */
    private final Stage stage;

    /**
     * The constructor instantiates the root attribute & sets the previous root & stage attributes to the arguments passed.
     * This will also set the currently selected button which will set the stage for the login screen.
     * @param selectedButton A reference to the selected value the user has picked to log in.
     * @param root A reference to the previous root of current stage's scene.
     * @param stage A reference to the primary stage.
     */
    public LoginAndRegister(ToggleGroup selectedButton, VBox root, Stage stage) {
        this.selectedButton = selectedButton;
        prevRoot = root;
        this.stage = stage;

        this.root = new GridPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setHgap(10);
        this.root.setVgap(10);
        this.root.setPadding(new Insets(25, 25, 25, 25));
    }

    /**
     * This method calls the setupForm method to set up all the nodes and adds
     * them to the current root and returns the current root to the caller.
     * @return A root of type GridPane which contains all the nodes added to itself.
     */
    public GridPane getRootPane() {
        setupForm();
        return root;
    }

    /**
     * This method is responsible to set up a form, submit and a back button and
     * event handlers on respective buttons. The event handler for submit button calls
     * for validating the user input by making a call to the respected module, and
     * checks for a signal, if successful then generates a view of that user's module
     * by setting the primary stage's current scene's root to the options list of that
     * module, as root. Otherwise, it lets user know the type of error message regarding
     * the input fields. The event handler for back button sets the primary stage's
     * current scene's current root to the previous root.
     */
    private void setupForm(){
        Button back = new Button("Go Back");
        back.setOnAction(e -> stage.getScene().setRoot(prevRoot));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.setSpacing(20);

        if(Objects.equals(((RadioButton) selectedButton.getSelectedToggle()).getText(), "Librarian")) {
            Button signIn = new Button("Sign in");

            Label userId = new Label("ID:");
            root.add(userId, 0, 1);

            TextField userIdField = new TextField();
            root.add(userIdField, 1, 1);

            Label password = new Label("Password:");
            root.add(password, 0, 2);

            PasswordField pwField = new PasswordField();
            root.add(pwField, 1, 2);

            final Text actiontarget = new Text();
            root.add(actiontarget, 1, 6);

            signIn.setOnAction(e -> {
                if(userIdField.getCharacters().isEmpty()) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Please enter employee Id");
                }
                else if (pwField.getCharacters().isEmpty()) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Please enter password");
                }
                else{
                    Admin admin = new Admin();
                    admin.setFile("resources/admin/librarians.json");
                    admin.readDataFromFile(false);
                    String msg = admin.libLogin(Integer.parseInt(userIdField.getText()),pwField.getText());
                    System.out.println(msg);
                    if (msg.equals("Success")) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText(msg);
                        LibrarianOptions loggedIn = new LibrarianOptions(userIdField.getText(), root, stage);
                        stage.getScene().setRoot(loggedIn.getRootPane());
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText(msg);
                    }
                }
            });

            hbBtn.getChildren().add(back);
            hbBtn.getChildren().add(signIn);

            root.add(hbBtn, 1, 4);
        }
        else if(Objects.equals(((RadioButton) selectedButton.getSelectedToggle()).getText(), "Admin")) {
            Button signIn = new Button("Sign in");

            Label userName = new Label("User Name:");
            root.add(userName, 0, 1);

            TextField userNameField = new TextField();
            root.add(userNameField, 1, 1);

            Label password = new Label("Password:");
            root.add(password, 0, 2);

            PasswordField pwField = new PasswordField();
            root.add(pwField, 1, 2);

            final Text actiontarget = new Text();
            root.add(actiontarget, 1, 6);

            signIn.setOnAction(e -> {
                if (userNameField.getCharacters().isEmpty()) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Please enter username");
                } else if (pwField.getCharacters().isEmpty()) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Please enter password");
                } else {
                    Admin admin = new Admin();
                    admin.setFile("resources/admin/admins.json");
                    admin.readDataFromFile(true);
                    String msg = admin.login(userNameField.getText(), pwField.getText());
                    if (msg.equals("Success")) {
                        AdminOptions adminChoice = new AdminOptions(admin, this.root, this.stage);
                        this.stage.getScene().setRoot(adminChoice.getRootPane());
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText(msg);
                    }
                }
            });

            hbBtn.getChildren().add(back);
            hbBtn.getChildren().add(signIn);

            root.add(hbBtn, 1, 4);
        }
        else{
            Button submit = new Button("Submit");

            VBox vbBtn = new VBox();
            vbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            vbBtn.setSpacing(20);

            ToggleGroup group = new ToggleGroup();

            RadioButton register = new RadioButton("Register");
            register.setToggleGroup(group);
            register.setSelected(true);
            register.setLayoutX(265.0);
            register.setLayoutY(155.0);
            register.setMaxSize(100,200);

            RadioButton login = new RadioButton("Login");
            login.setToggleGroup(group);
            login.setLayoutX(265.0);
            login.setLayoutY(155.0);
            login.setMaxSize(100,200);

            submit.setLayoutX(265.0);
            submit.setLayoutY(155.0);
            submit.setMaxSize(100,200);

            submit.setOnAction(e -> {
                if(Objects.equals(((RadioButton) group
                        .getSelectedToggle()).getText(), "Register")) {
                    StudentRegister sl = new StudentRegister(root, stage);
                    stage.getScene().setRoot(sl.getRootPane());
                }
                else{
                    StudentLogin sl = new StudentLogin(root, stage);
                    stage.getScene().setRoot(sl.getRootPane());
                }
            });

            vbBtn.getChildren().add(register);
            vbBtn.getChildren().add(login);
            hbBtn.getChildren().add(back);
            hbBtn.getChildren().add(submit);

            root.add(vbBtn, 1, 4);
            root.add(hbBtn, 1, 6);
        }
    }
}