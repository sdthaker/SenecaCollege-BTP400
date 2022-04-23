package btp400.assignment1.frontend;

import btp400.assignment1.backend.Student;
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
 * This class is responsible for showing three options to the logged in librarian.
 * One being add a book, view books and delete a book. It also contains the
 * back button to go back to the previous root.
 * @author Zia Syed
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

public class LibrarianOptions {
    /**
     * Current root of the current scene of type VBox
     */
    VBox LibrarianOptions;
    /**
     * Previous root of the current scene of type GridPane
     */
    private final GridPane prevRoot;
    /**
     * Primary stage of the application
     */
    private final Stage stage;
    /**
     * ID of the current logged in Librarian
     */
    private final String employeeID;


    /**
     * Instantiates all three respective attributes to the value of arguments.
     * @param prevRoot A reference to the previous root of current stage's scene.
     * @param stage A reference to the primary stage.
     * @param ID ID that the user used to login into the system.
     */
    LibrarianOptions(String ID, GridPane prevRoot, Stage stage) {
        this.prevRoot = prevRoot;
        this.stage = stage;
        this.employeeID = ID;

        this.LibrarianOptions = new VBox();
        LibrarianOptions.setAlignment(Pos.CENTER);
        LibrarianOptions.setSpacing(20);
    }

    /**
     * This method calls the setupButtons method to set up all the nodes and adds
     * them to the current root and returns the current root to the caller.
     * @return A root of type GridPane which contains all the nodes added to itself.
     */
    public VBox getRootPane() {
        setupButtons();
        return LibrarianOptions;
    }

    /**
     * This method is responsible for creating three radio buttons, one which allows user
     * to add a book, view all the books and delete a book. Also, it contains implementation
     * for submit and go back button where submit button creates a new instance of LibrarianAddBook,
     * LibrarianViewBooks or LibrarianDeleteBook, depending on which radio button is clicked
     * and instantiates the respective class' instance and sets the primary stage's scene's
     * current root by calling the respective instances getRootPane method.
     * The event handler for logout button sets the primary stage's current scene's current
     * root to the previous root.
     */
    private void setupButtons() {
        Text scenetitle = new Text("Welcome, " + "\n ID: "+ employeeID);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

        ToggleGroup group = new ToggleGroup();

        RadioButton vbItem = new RadioButton("Add Book");
        vbItem.setToggleGroup(group);
        vbItem.setSelected(true);
        vbItem.setLayoutX(265.0);
        vbItem.setLayoutY(155.0);
        vbItem.setMaxSize(100,300);
        vbItem.setWrapText(true);

        RadioButton vbrItem = new RadioButton("View Books");
        vbrItem.setToggleGroup(group);
        vbrItem.setLayoutX(265.0);
        vbrItem.setLayoutY(155.0);
        vbrItem.setMaxSize(100,300);
        vbrItem.setWrapText(true);

        RadioButton vbrrItem = new RadioButton("Delete Books");
        vbrrItem.setToggleGroup(group);
        vbrrItem.setLayoutX(265.0);
        vbrrItem.setLayoutY(155.0);
        vbrrItem.setMaxSize(100,300);
        vbrrItem.setWrapText(true);

        Button submit = new Button("Submit");
        submit.setLayoutX(265.0);
        submit.setLayoutY(155.0);
        submit.setMaxSize(200,200);
        submit.setOnAction(e -> {
            if (Objects.equals(((RadioButton) group.getSelectedToggle()).getText(), "Add Book")) {
                LibrarianAddBook book = new LibrarianAddBook(LibrarianOptions, stage);
                stage.getScene().setRoot(book.getRootPane());
            } else if (Objects.equals(((RadioButton) group.getSelectedToggle()).getText(), "View Books")) {
                LibrarianViewBooks books = new LibrarianViewBooks(LibrarianOptions, stage);
                stage.getScene().setRoot(books.getRootPane());
            } else {
                LibrarianDeleteBook book = new LibrarianDeleteBook(LibrarianOptions, stage);
                stage.getScene().setRoot(book.getRootPane());
            }
        });

        Button back = new Button("LogOut");
        back.setLayoutX(265.0);
        back.setLayoutY(155.0);
        back.setMaxSize(100,200);
        back.setOnAction(e -> stage.getScene().setRoot(prevRoot));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(back);
        hbBtn.getChildren().add(submit);

        LibrarianOptions.getChildren().add(scenetitle);
        LibrarianOptions.getChildren().add(vbItem);
        LibrarianOptions.getChildren().add(vbrItem);
        LibrarianOptions.getChildren().add(vbrrItem);
        LibrarianOptions.getChildren().add(hbBtn);
    }
}
