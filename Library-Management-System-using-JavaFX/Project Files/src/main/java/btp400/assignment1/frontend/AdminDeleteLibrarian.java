package btp400.assignment1.frontend;

import btp400.assignment1.backend.Admin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class displays the form that requires the admin to enter the librarian's employee "id" key
 * to search for the librarian in the librarians.json file and delete the record that matches the key.
 *
 * @author Nathaniel Ling
 * @version 1.0.0
 * @see GridPane
 * @see VBox
 * @see Stage
 * @see Admin
 * @see Text
 * @see Button
 * @see Label
 * @see TextField
 * @see String
 * @see Integer
 * @see HBox
 */
public class AdminDeleteLibrarian {

    /**
     * Root of the current scene
     */
    private final GridPane root;

    /**
     * Previous root of the current scene
     */
    private final VBox prevRoot;

    /**
     * Primary stage of application
     */
    private final Stage stage;

    /**
     * Current admin user
     */
    Admin admin;

    /**
     * Constructor that sets the values of the attributes to the values of the arguments.
     * The root is set as a new GridPane.
     * @param a Admin sets admin to current admin user
     * @param prevRoot Previous root
     * @param stage Current stage
     */
    public AdminDeleteLibrarian(Admin a, VBox prevRoot, Stage stage) {
        this.root = new GridPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setHgap(10);
        this.root.setVgap(10);
        this.root.setPadding(new Insets(25, 25, 25, 25));
        this.prevRoot = prevRoot;
        this.stage = stage;
        admin = a;
    }

    /**
     * This method uses the setupForm method to create a form and add it to the root.
     * @return Gridpane root with form set up.
     */
    public GridPane getRootPane() {
        setupForm();
        return root;
    }

    /**
     * This method creates a form which has one TextField where the user must input
     * the librarian's "id". The admin is set to point to the librarians.json file and
     * the librarians JSONArray in admin is instantiated to contain the contents of the .json file.
     * The singular TextField must not be left empty. When the delete button is pressed,
     * the deleteLibrarian admin method is called which will return a success or error message.
     */
    private void setupForm() {
        Text scenetitle = new Text("Delete Librarian");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.add(scenetitle, 0, 0, 2, 1);
        Button delete = new Button("Delete");
        Admin admin = new Admin();
        admin.setFile("resources/admin/librarians.json");
        admin.readDataFromFile(false);

        Label id = new Label("Employee Id:");
        root.add(id, 0, 1);

        TextField idField = new TextField();
        root.add(idField, 1, 1);

        final Text msgRecv = new Text();
        msgRecv.setFont(Font.font("verdana", FontPosture.REGULAR, 17));
        root.add(msgRecv, 1, 3);

        delete.setOnAction(e->{
            if(idField.getText().isEmpty()) {
                msgRecv.setFill(Color.FIREBRICK);
                msgRecv.setText("");
            }
            else {
                String msg = admin.deleteLibrarian(Integer.parseInt(idField.getText()));
                if (msg.equals("Success")){
                    admin.writeDataToFile();
                    msgRecv.setFill(Color.GREEN);
                    msgRecv.setText("Librarian deleted successfully!");
                    idField.clear();
                } else {
                    msgRecv.setFill(Color.FIREBRICK);
                    msgRecv.setText(msg);
                }
            }
        });
        Button back = new Button("Go Back");
        back.setOnAction(e -> stage.getScene().setRoot(prevRoot));

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(back);
        hbBtn.getChildren().add(delete);

        root.add(hbBtn, 1, 2);
    }
}
