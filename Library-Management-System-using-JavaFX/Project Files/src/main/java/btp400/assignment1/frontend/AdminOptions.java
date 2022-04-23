package btp400.assignment1.frontend;

import btp400.assignment1.backend.Admin;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * After a successful Admin login, this class creates the page which shows the admin
 * a list of options that can be chosen. The options include register a librarian,
 * delete a librarian, and view all librarians.
 * @author Nathaniel Ling
 * @version 1.0.0
 * @see Admin
 * @see LoginAndRegister
 * @see VBox
 * @see GridPane
 * @see Stage
 * @see ToggleGroup
 * @see RadioButton
 * @see Button
 * @see Object
 * @see AdminRegisterLibrarian
 * @see AdminDeleteLibrarian
 * @see AdminViewLibrarians
 * @see HBox
 * @see Text
 */
public class AdminOptions {

    /**
     * Root of the current scene
     */
    VBox adminOptions;

    /**
     * Previous root of the current scene
     */
    private final GridPane prevRoot;

    /**
     * Primary stage of the application
     */
    private final Stage stage;

    /**
     * Currently logged in admin
     */
    private final Admin adminUser;

    /**
     * Constructor sets the values of the class attributes to the value of the arguments and
     * instantiates adminOptions as a new VBox.
     * @param admin Reference to Admin user
     * @param prevRoot Reference to previous root of current scene
     * @param stage Reference to primary stage
     */
    AdminOptions(Admin admin, GridPane prevRoot, Stage stage) {
        this.prevRoot = prevRoot;
        this.stage = stage;
        this.adminUser = admin;
        this.adminOptions = new VBox();
        this.adminOptions.setAlignment(Pos.CENTER);
        this.adminOptions.setSpacing(20.0D);
    }

    /**
     * This method calls setupButtons method to add the buttons to the root.
     * @return VBox root which has been set up with the proper layout.
     */
    public VBox getRootPane() {
        this.setupButtons();
        return this.adminOptions;
    }

    /**
     * This method adds three RadioButton options to the root. A Go Back button is implemented
     * to return to the previous root. When the Submit button is activated, one of AdminRegisterLibrarian,
     * AdminDeleteLibrarian, or AdminViewLibrarians will be created and set as the primary stage.
     */
    private void setupButtons() {
        Text welcomeMsg = new Text("Welcome " + adminUser.getLoggedInAdmin());
        welcomeMsg.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        ToggleGroup group = new ToggleGroup();
        RadioButton regLib = new RadioButton("Register Librarian");
        regLib.setToggleGroup(group);
        regLib.setSelected(true);
        regLib.setLayoutX(265.0D);
        regLib.setLayoutY(155.0D);
        regLib.setMaxSize(100.0D, 300.0D);
        regLib.setWrapText(true);
        RadioButton delLib = new RadioButton("Delete Librarian");
        delLib.setToggleGroup(group);
        delLib.setLayoutX(265.0D);
        delLib.setLayoutY(155.0D);
        delLib.setMaxSize(100.0D, 300.0D);
        delLib.setWrapText(true);
        RadioButton viewLib = new RadioButton("View Librarians");
        viewLib.setToggleGroup(group);
        viewLib.setLayoutX(265.0D);
        viewLib.setLayoutY(155.0D);
        viewLib.setMaxSize(100.0D, 300.0D);
        viewLib.setWrapText(true);
        Button submit = new Button("Submit");
        submit.setLayoutX(265.0D);
        submit.setLayoutY(155.0D);
        submit.setMaxSize(200.0D, 200.0D);
        submit.setOnAction((e) -> {
            if (Objects.equals(((RadioButton)group.getSelectedToggle()).getText(), "Register Librarian")) {
                AdminRegisterLibrarian arl = new AdminRegisterLibrarian(this.adminUser, this.adminOptions, this.stage);
                this.stage.getScene().setRoot(arl.getRootPane());
            }
            else if (Objects.equals(((RadioButton)group.getSelectedToggle()).getText(), "Delete Librarian")){
                AdminDeleteLibrarian adl = new AdminDeleteLibrarian(this.adminUser, this.adminOptions, this.stage);
                this.stage.getScene().setRoot(adl.getRootPane());
            }
            else {
                AdminViewLibrarians avl = new AdminViewLibrarians(this.adminUser, this.adminOptions, this.stage);
                this.stage.getScene().setRoot(avl.getRootPane());
            }

        });
        Button back = new Button("Go Back");
        back.setLayoutX(265.0D);
        back.setLayoutY(155.0D);
        back.setMaxSize(100.0D, 200.0D);
        back.setOnAction((e) -> {
            this.stage.getScene().setRoot(this.prevRoot);
        });
        HBox hbBtn = new HBox(10.0D);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(back);
        hbBtn.getChildren().add(submit);
        this.adminOptions.getChildren().add(welcomeMsg);
        this.adminOptions.getChildren().add(regLib);
        this.adminOptions.getChildren().add(delLib);
        this.adminOptions.getChildren().add(viewLib);
        this.adminOptions.getChildren().add(hbBtn);
    }
}
