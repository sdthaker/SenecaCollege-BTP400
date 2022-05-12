package btp400.assignment1.frontend;

import btp400.assignment1.backend.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is responsible for creating a register page for the student. It takes the user input and
 * forwards it to Student module which then validates the input. If the input values are valid it
 * registers the student otherwise informs the user of the errors. It also contains the back button to go back to the
 * previous root.
 * @author Soham Thaker
 * @version 1.0.0
 * @see Student
 * @see Stage
 * @see GridPane
 * @see Text
 * @see Button
 * @see Label
 * @see String
 * @see TextField
 * @see HBox
 */
public class StudentRegister {

    /**
     * Current root of the current scene of type GridPane
     */
    private final GridPane root;

    /**
     * Previous root of the current scene of type GridPane
     */
    private final GridPane prevRoot;

    /**
     * Primary stage of the application
     */
    private final Stage stage;

    /**
     * The constructor instantiates the root attribute & sets the previous root & stage attributes to the arguments passed.
     * @param root A reference to the previous root of current stage's scene.
     * @param stage A reference to the primary stage.
     */
    public StudentRegister(GridPane root, Stage stage) {
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
     * This method is responsible to setup a form, a register and a back button and
     * event handlers on respective buttons. The event handler for register button calls
     * for validating the user input by making a call to the Student module, and checks
     * for a signal, if successful then registers the student. Otherwise, it lets user
     * know the type of error message regarding the input fields. The event handler for
     * back button sets the primary stage's current scene's current root to the previous root.
     */
    private void setupForm(){
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.add(scenetitle, 0, 0, 2, 1);
        Button register = new Button("Register");
        Student s = new Student();
        s.setFile("resources/student/students.json");
        s.readDataFromFile();

        Label sid = new Label("SID:");
        root.add(sid, 0, 1);

        TextField sidField = new TextField();
        root.add(sidField, 1, 1);

        Label fname = new Label("First Name:");
        root.add(fname, 0, 2);

        TextField fnameField = new TextField();
        root.add(fnameField, 1, 2);

        Label lname = new Label("Last Name:");
        root.add(lname, 0, 3);

        TextField lnameField = new TextField();
        root.add(lnameField, 1, 3);

        final Text actiontarget = new Text();
        actiontarget.setFont(Font.font("verdana", FontPosture.REGULAR, 17));
        root.add(actiontarget, 1, 6);

        register.setOnAction(e -> {
            if(sidField.getText().isEmpty() ||
                    fnameField.getText().isEmpty() ||
                    lnameField.getText().isEmpty()) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("A value must be provided for all three fields");
            }
            else {
                String info = s.register(
                        fnameField.getText(),
                        lnameField.getText(),
                        sidField.getText());
                if (info.equals("Success")) {
                    s.writeDataToFile();
                    actiontarget.setFill(Color.GREEN);
                    actiontarget.setText("Student registered successfully!");
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText(info);
                }
            }
        });

        Button back = new Button("Go Back");
        back.setOnAction(e -> stage.getScene().setRoot(prevRoot));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(back);
        hbBtn.getChildren().add(register);

        root.add(hbBtn, 1, 4);
    }
}
