package btp400.assignment1.frontend;

import btp400.assignment1.backend.Librarian;
import btp400.assignment1.backend.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class is responsible for showing the current catalogue for the library.
 * It also contains the back button to go back to the previous root.
 * @author Zia Syed
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

public class LibrarianViewBooks {
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
     * Instantiates all three respective attributes to the value of arguments.
     * @param prevRoot A reference to the previous root of current stage's scene.
     * @param stage A reference to the primary stage.
     */
    LibrarianViewBooks(VBox prevRoot, Stage stage) {
        this.prevRoot = prevRoot;
        this.stage = stage;

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
     * available in the library catalogue. The event handler for back button sets
     * the primary stage's current scene's current root to the previous root.
     */
    private void setupView() {
        JSONArray books = Librarian.booksCatalog();

        if (books.isEmpty()) {
            Label label = new Label("Library catalogue is empty!");
            view.getChildren().add(label);

            label = new Label("Library catalogue is empty!");
            view.getChildren().add(label);
        }
        else {
            for (Object b : books) {
                HBox hb = new HBox(20);
                hb.setAlignment(Pos.CENTER);

                String aBook = "ISBN: " + ((JSONObject) b).get("ID") + ", Book name: "
                        + ((JSONObject) b).get("name") + ", Book author: "
                        + ((JSONObject) b).get("author") + ", Pages: "
                        + ((JSONObject) b).get("pages") + ", Stock: "
                        + ((JSONObject) b).get("stock");

                Label label = new Label(aBook);
                label.setWrapText(true);

                final Text actiontarget = new Text();
                actiontarget.setFont(Font.font("verdana", FontPosture.REGULAR, 17));

                hb.getChildren().add(label);
                hb.getChildren().add(actiontarget);
                view.getChildren().add(hb);
            }
        }

        Button back = new Button("Go Back");
        back.setLayoutX(265.0);
        back.setLayoutY(155.0);
        back.setMaxSize(100, 200);
        back.setOnAction(e -> {
            stage.getScene().setRoot(prevRoot);
            //loggedInStudent.writeDataToFile();
        });

        view.getChildren().add(back);
    }
}
