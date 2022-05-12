package btp400.assignment1.frontend;

import btp400.assignment1.backend.Student;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class is responsible for showing two options to the logged in student.
 * One being view library catalogue and borrow a book, and other being view
 * borrowed books and return the borrowed book. It also contains the back button
 * to go back to the previous root.
 * @author Soham Thaker
 * @version 1.0.0
 * @see Student
 * @see StudentViewBooksAndBorrowBook
 * @see StudentViewBorrowedBooksAndReturnBooks
 * @see Stage
 * @see GridPane
 * @see HBox
 * @see Button
 * @see VBox
 * @see ToggleGroup
 * @see RadioButton
 */
public class StudentOptions {

    /**
     * Current root of the current scene of type VBox
     */
    VBox studentOptions;

    /**
     * Previous root of the current scene of type GridPane
     */
    private final GridPane prevRoot;

    /**
     * Primary stage of the application
     */
    private final Stage stage;

    /**
     * Student reference for the logged in student
     */
    private final Student loggedInStudent;

    /**
     * Instantiates all three respective attributes to the value of arguments.
     * @param s A reference to currently logged in Student object
     * @param prevRoot A reference to the previous root of current stage's scene.
     * @param stage A reference to the primary stage.
     */
    StudentOptions(Student s, GridPane prevRoot, Stage stage) {
        this.prevRoot = prevRoot;
        this.stage = stage;
        loggedInStudent = s;

        this.studentOptions = new VBox();
        studentOptions.setAlignment(Pos.CENTER);
        studentOptions.setSpacing(20);
    }

    /**
     * This method calls the setupForm method to set up all the nodes and adds
     * them to the current root and returns the current root to the caller.
     * @return A root of type GridPane which contains all the nodes added to itself.
     */
    public VBox getRootPane() {
        setupForm();
        return studentOptions;
    }

    /**
     * This method is responsible for creating two radio buttons, one which allows user
     * to view and borrow books and other being view borrowed books and return a book.
     * Also, it contains implementation for submit and go back button where submit
     * button creates a new instance of StudentViewBooksAndBorrowBook or
     * StudentViewBorrowedBooksAndReturnBooks depending on which radio button is clicked
     * and instantiates the respective class' instance and sets the primary stage's scene's
     * current root by calling the respective instances getRootPane method.
     * The event handler for logout button sets the primary stage's current scene's current
     * root to the previous root.
     */
    private void setupForm() {

        Text scenetitle = new Text("Welcome, " + loggedInStudent.getName() + "\n SID: "
                + loggedInStudent.getSid());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

        ToggleGroup group = new ToggleGroup();

        RadioButton vbItem = new RadioButton("View Books / Borrow Books");
        vbItem.setToggleGroup(group);
        vbItem.setSelected(true);
        vbItem.setLayoutX(265.0);
        vbItem.setLayoutY(155.0);
        vbItem.setMaxSize(100,300);
        vbItem.setWrapText(true);

        RadioButton vbrItem = new RadioButton("View Borrowed Books / Return Books");
        vbrItem.setToggleGroup(group);
        vbrItem.setLayoutX(265.0);
        vbrItem.setLayoutY(155.0);
        vbrItem.setMaxSize(100,300);
        vbrItem.setWrapText(true);

        Button submit = new Button("Submit");
        submit.setLayoutX(265.0);
        submit.setLayoutY(155.0);
        submit.setMaxSize(200,200);
        submit.setOnAction(e -> {
            if(Objects.equals(((RadioButton) group
                    .getSelectedToggle()).getText(), "View Books / Borrow Books")) {
                StudentViewBooksAndBorrowBook vbr =
                        new StudentViewBooksAndBorrowBook(loggedInStudent, studentOptions, stage);
                stage.getScene().setRoot(vbr.getRootPane());
            }
            else{
                StudentViewBorrowedBooksAndReturnBooks vbr =
                        new StudentViewBorrowedBooksAndReturnBooks(loggedInStudent, studentOptions, stage);
                stage.getScene().setRoot(vbr.getRootPane());
            }
        });

        Button logout = new Button("Logout");
        logout.setLayoutX(265.0);
        logout.setLayoutY(155.0);
        logout.setMaxSize(100,200);
        logout.setOnAction(e -> stage.getScene().setRoot(prevRoot));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(logout);
        hbBtn.getChildren().add(submit);

        studentOptions.getChildren().add(scenetitle);
        studentOptions.getChildren().add(vbItem);
        studentOptions.getChildren().add(vbrItem);
        studentOptions.getChildren().add(hbBtn);
    }
}
