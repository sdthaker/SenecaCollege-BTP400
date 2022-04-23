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
 * This class is responsible for creating a login page for the student. It takes the user input and
 * forwards it to Student module which then validates the input. If the input values are valid it
 * creates a Student Options instance and sets the root of the primary stage's scene to that instance.
 * Otherwise, informs the user about the errors. It also contains the back button to go back to the
 * previous root.
 * @author Soham Thaker
 * @version 1.0.0
 * @see Student
 * @see StudentOptions
 * @see Stage
 * @see GridPane
 * @see Text
 * @see String
 * @see Button
 * @see Label
 * @see Font
 * @see TextField
 * @see HBox
 */
public class StudentLogin {

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
    public StudentLogin(GridPane root, Stage stage) {
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
     * This method is responsible to setup a form, a submit and a back button and
     * event handlers on respective buttons. The event handler for submit button calls
     * for validating the user input by making a call to the Student module, and checks
     * for a signal, if successful then generates a StudentOptions view by setting the
     * primary stage's current scene's root to the StudentOptions root. Otherwise, it
     * lets user know the type of error message regarding the input fields. The event
     * handler for back button sets the primary stage's current scene's current root
     * to the previous root.
     */
    private void setupForm(){
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.add(scenetitle, 0, 0, 2, 1);
        Button signIn = new Button("Sign in");
        
        Label sid = new Label("SID:");
        root.add(sid, 0, 1);

        TextField sidField = new TextField();
        root.add(sidField, 1, 1);

        final Text actiontarget = new Text();
        actiontarget.setFont(Font.font("verdana", FontPosture.REGULAR, 17));
        root.add(actiontarget, 1, 6);

        signIn.setOnAction(e -> {
            if(sidField.getCharacters().isEmpty()) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Please provide a value for SID field");
            }
            else{
                Student s = new Student();
                s.setFile("resources/student/students.json");
                s.readDataFromFile();

                String info = s.login(sidField.getText());
                if(info.equals("Success")) {
                    StudentOptions studentChoice = new StudentOptions(s, root, stage);
                    stage.getScene().setRoot(studentChoice.getRootPane());
                }
                else{
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
        hbBtn.getChildren().add(signIn);

        root.add(hbBtn, 1, 4);
    }
}
