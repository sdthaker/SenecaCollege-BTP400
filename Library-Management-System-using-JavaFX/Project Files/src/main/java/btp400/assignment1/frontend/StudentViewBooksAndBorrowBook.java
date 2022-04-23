package btp400.assignment1.frontend;

import btp400.assignment1.backend.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class is responsible for showing the current catalogue for the library.
 * Each catalogue item has a borrow button right next to it where the user can borrow
 * an item. It also contains the back button to go back to the previous root.
 * @author Soham Thaker
 * @version 1.0.0
 * @see Student
 * @see JSONObject
 * @see JSONArray
 * @see Font
 * @see Stage
 * @see GridPane
 * @see HBox
 * @see Button
 * @see ScrollPane
 * @see String
 * @see VBox
 * @see Label
 * @see Text
 * @see Insets
 * @see Object
 */
public class StudentViewBooksAndBorrowBook {

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
    StudentViewBooksAndBorrowBook(Student s, VBox prevRoot, Stage stage) {
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
     * available in the library catalogue. At the same time, it adds a borrow button
     * to each item. If a borrow button is clicked for a specific item, it will display
     * a message based on the return value of the Student module's borrowABook method.
     * The event handler for back button sets the primary stage's current scene's
     * current root to the previous root.
     */
    private void setupView() {

        JSONArray books = loggedInStudent.searchBooks();

        if (books.isEmpty()) {
            Label label = new Label("Library catalogue is empty!");
            view.getChildren().add(label);
        }
        else {
            for (Object b : books) {
                HBox hb = new HBox(20);
                hb.setAlignment(Pos.CENTER);

                String aBook = "ISBN: " + ((JSONObject) b).get("ID") +
                        ", Book name: " + ((JSONObject) b).get("name")
                        + ", Book author: " + ((JSONObject) b).get("author")
                        + ", Pages: " + ((JSONObject) b).get("pages");

                JSONObject book = new JSONObject();
                book.put("ID", ((JSONObject) b).get("ID"));
                book.put("Author", ((JSONObject) b).get("author"));
                book.put("Title", ((JSONObject) b).get("name"));
                book.put("Pages", ((JSONObject) b).get("pages"));

                Label label = new Label(aBook);
                label.setWrapText(true);

                final Text actiontarget = new Text();
                actiontarget.setFont(Font.font("verdana", FontPosture.REGULAR, 13));

                Button burrowBtn = new Button("Borrow item");
                burrowBtn.setLayoutX(265.0);
                burrowBtn.setLayoutY(155.0);
                burrowBtn.setMaxSize(200, 200);

                burrowBtn.setOnAction(e -> {
                    String signal = loggedInStudent.borrowABook(book);

                    if (signal.equals("Book already borrowed!") ||
                            signal.equals("Book successfully borrowed!")) {
                        loggedInStudent.writeDataToFile();
                        actiontarget.setFill(Color.GREEN);
                        actiontarget.setText(signal);
                    }
                    else if(signal.equals("Book still in waiting list!") ||
                            signal.equals("Book put on waiting list!")) {
                        loggedInStudent.writeDataToFile();
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText(signal);
                    }
                });

                hb.getChildren().add(label);
                hb.getChildren().add(burrowBtn);
                view.getChildren().add(hb);
                view.getChildren().add(actiontarget);
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
