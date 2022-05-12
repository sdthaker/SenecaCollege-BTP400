package btp400.assignment1.frontend;

import btp400.assignment1.backend.Admin;
import btp400.assignment1.backend.Librarian;
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

/**
 * This class is responsible for adding a new book in the library. It takes the
 * user input, validates it and forwards it to Librarian module. If the input
 * values are valid it adds the book otherwise informs the user of the errors.
 * It also contains the back button to go back to the previous root.
 * @author Zia Syed
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

public class LibrarianAddBook {
    /**
     * Scroll pane of the current view.
     */
    ScrollPane sp = new ScrollPane();
    /**
     * Current root of the current scene of type VBox
     */
    VBox view;
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
     * @param prevRoot A reference to the previous root of current stage's scene.
     * @param stage A reference to the primary stage.
     */
    LibrarianAddBook(VBox prevRoot, Stage stage) {
        this.prevRoot = prevRoot;
        this.stage = stage;

        this.view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * This method calls the setupForm method to set up all the nodes and adds
     * them to the current root and returns the current scroll pane to the caller.
     * @return A root of type ScrollPane which contains all the nodes added to itself.
     */
    public ScrollPane getRootPane() {
        setupForm();
        sp.setFitToWidth(true);
        sp.setContent(view);
        return sp;
    }

    /**
     * This method is responsible to set up a form, submit and a back button and
     * event handlers on respective buttons. The event handler for submit button calls
     * for validating the user input by making a call to the Librarian module, and if
     * successful then adds the book. Otherwise, it lets user know the type of error
     * message regarding the input fields. The event handler for back button sets the
     * primary stage's current scene's current root to the previous root.
     */
    private void setupForm() {
        JSONArray books = Librarian.booksCatalog();

        Label isbn = new Label("ISBN:");
        view.getChildren().add(isbn);

        TextField isbnField = new TextField();
        view.getChildren().add(isbnField);

        Label name = new Label("Book Name:");
        view.getChildren().add(name);

        TextField nameField = new TextField();
        view.getChildren().add(nameField);

        Label author = new Label("Author Name:");
        view.getChildren().add(author);

        TextField authorField = new TextField();
        view.getChildren().add(authorField);

        Label pages = new Label("Total Pages:");
        view.getChildren().add(pages);

        TextField pagesField = new TextField();
        view.getChildren().add(pagesField);

        Label stock = new Label("Total Stock:");
        view.getChildren().add(stock);

        TextField stockField = new TextField();
        view.getChildren().add(stockField);

        final Text actiontarget = new Text();
        actiontarget.setFont(Font.font("verdana", FontPosture.REGULAR, 17));
        view.getChildren().add(actiontarget);

        Button addBook = new Button("Add Book");
        addBook.setLayoutX(265.0);
        addBook.setLayoutY(155.0);
        addBook.setMaxSize(100, 200);
        addBook.setOnAction(e -> {
            if(isbnField.getText().isEmpty() ||
                    nameField.getText().isEmpty() ||
                    authorField.getText().isEmpty() ||
                    pagesField.getText().isEmpty() ||
                    stockField.getText().isEmpty()) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("All fields are required");
            } else {
                String msg = Librarian.addBook(
                        isbnField.getText(),
                        nameField.getText(),
                        authorField.getText(),
                        pagesField.getText(),
                        stockField.getText());
                if (msg.equals("Success")) {
                    actiontarget.setFill(Color.GREEN);
                    actiontarget.setText("Book added successfully!");

                    isbnField.setText("");
                    nameField.setText("");
                    authorField.setText("");
                    pagesField.setText("");
                    stockField.setText("");
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText(msg);
                }
            }
        });

        Button back = new Button("Go Back");
        back.setLayoutX(265.0);
        back.setLayoutY(155.0);
        back.setMaxSize(100, 200);
        back.setOnAction(e -> {
            stage.getScene().setRoot(prevRoot);
        });

        view.getChildren().add(addBook);
        view.getChildren().add(back);
    }
}
