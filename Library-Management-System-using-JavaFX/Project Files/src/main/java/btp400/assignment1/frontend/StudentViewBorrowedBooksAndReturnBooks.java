package btp400.assignment1.frontend;

import btp400.assignment1.backend.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class is responsible for showing the currently borrowed books for logged in student.
 * Each item has a return button right next to it where the user can return
 * an item. It also contains the back button to go back to the previous root.
 * @author Soham Thaker
 * @version 1.0.0
 * @see Student
 * @see JSONObject
 * @see JSONArray
 * @see Font
 * @see FontPosture
 * @see Stage
 * @see GridPane
 * @see HBox
 * @see Button
 * @see ScrollPane
 * @see String
 * @see VBox
 * @see Label
 * @see Insets
 * @see Object
 */
public class StudentViewBorrowedBooksAndReturnBooks {

    /**
     * Current root of type ScrollPane for current scene.
     */
    ScrollPane sp = new ScrollPane();

    /**
     * A view that is part of the current root
     */
    VBox view;

    /**
     * Previous root of the current scene of type VBox
     */
    private final VBox prevRoot;

    /**
     * Primary stage of the application
     */
    private final Stage stage;

    /**
     * Student reference for the logged in student
     */
    Student loggedInStudent;

    /**
     * Instantiates all three respective attributes to the value of arguments.
     * @param s A reference to currently logged in Student object
     * @param prevRoot A reference to the previous root of current stage's scene.
     * @param stage A reference to the primary stage.
     */
    StudentViewBorrowedBooksAndReturnBooks(Student s, VBox prevRoot, Stage stage) {
        this.prevRoot = prevRoot;
        this.stage = stage;
        loggedInStudent = s;

        this.view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * This method calls the setupView method to set up all the nodes and adds
     * them to the current root and returns the current root to the caller.
     * @return A root of type ScrollPane which contains all the nodes added to itself.
     */
    public ScrollPane getRootPane() {
        setupView();
        sp.setFitToWidth(true);
        sp.setContent(view);
        return sp;
    }

    /**
     * This method iterates over the books array and shows all the books that are
     * currently borrowed by the logged in student. At the same time, it adds a return button
     * to each item. If a return button is clicked for a specific item, it will remove the
     * book from borrowed list and re-render the current view which wil have updated borrowed
     * books list displayed to the user. The event handler for back button sets the primary
     * stage's current scene's current root to the previous root.
     */
    private void setupView() {

        view.getChildren().clear();

        JSONArray books = loggedInStudent.getAllBorrowedBooks();

        if (books.isEmpty()) {
            Label label = new Label("You donot have any books on hold right now!");
            view.getChildren().add(label);
        }
        else {
            for (Object book : books) {
                HBox hb = new HBox(20);
                hb.setAlignment(Pos.CENTER);

                String aBook = "ISBN: " + ((JSONObject) book).get("ID") +
                        ", Book name: " + ((JSONObject) book).get("Title")
                        + ", Book author: " + ((JSONObject) book).get("Author")
                        + ", Pages: " + ((JSONObject) book).get("Pages");

                Label label = new Label(aBook);
                label.setWrapText(true);

                Button returnBtn = new Button("Return item");
                returnBtn.setLayoutX(265.0);
                returnBtn.setLayoutY(155.0);
                returnBtn.setMaxSize(200, 200);
                returnBtn.setOnAction(e -> {
                    if(loggedInStudent.returnABook((JSONObject) book)) {
                        loggedInStudent.writeDataToFile();
                        setupView();
                    }
                });

                hb.getChildren().add(label);
                hb.getChildren().add(returnBtn);
                view.getChildren().add(hb);
            }
        }

        Button back = new Button("Go Back");
        back.setLayoutX(265.0);
        back.setLayoutY(155.0);
        back.setMaxSize(100, 200);
        back.setOnAction(e -> stage.getScene().setRoot(prevRoot));

        view.getChildren().add(back);
    }
}
