package btp400.assignment1.frontend;

import btp400.assignment1.backend.Admin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class creates a list of the currently registered librarians in the system.
 * All librarian information except for password is displayed and a report is written
 * in the registeredLibrariansReport.txt
 * @author Nathaniel Ling
 * @version 1.0.0
 * @see VBox
 * @see ScrollPane
 * @see Stage
 * @see Admin
 * @see JSONArray
 * @see JSONObject
 * @see File
 * @see Logger
 * @see PrintWriter
 * @see Label
 * @see HBox
 * @see String
 * @see FileWriter
 * @see Button
 * @see FileHandler
 * @see SimpleFormatter
 */
public class AdminViewLibrarians {

    /**
     * Part of current root
     */
    VBox view;

    /**
     * Root for current scene
     */
    ScrollPane root = new ScrollPane();

    /**
     * Previous root for current scene
     */
    private final VBox prevRoot;

    /**
     * Primary stage for application
     */
    private final Stage stage;

    /**
     * Current admin user
     */
    Admin admin;

    /**
     * Constructor that sets values of class attributes to values of arguments.
     * Sets view to new VBox.
     * @param a Current admin user
     * @param prevRoot Reference to previous root
     * @param stage Reference to current stage
     */
    public AdminViewLibrarians(Admin a, VBox prevRoot, Stage stage){
        admin = a;
        this.prevRoot = prevRoot;
        this.stage = stage;
        this.view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(10,10,10,10));
    }

    /**
     * This method uses the setupView method and adds the view to the root.
     * @return ScrollPane root with the view set up to format the list of librarians properly.
     */
    public ScrollPane getRootPane(){
        setupView();
        root.setFitToWidth(true);
        root.setContent(view);
        return root;
    }

    /**
     * This method modifies the VBox view to show the list of currently registered librarians.
     * A temporary JSONArray is instantiated by using the viewLibrarians method from admin.
     * A File pointer is created to point to the registeredLibrariansReport.txt file.
     * When setupView is called, PrintWriter overwrites the data previously stored in the .txt
     * file to print a fresh report. If the temporary JSONArray is empty, an error message is added
     * to the view. Otherwise, this method iterates through the JSONArray and creates a String which
     * displays the keys of the librarian JSONObject and writes the output to the .txt file.
     * The String is used to create a Label which is added to the view. Any exceptions are
     * handled using the setupLogger method.
     */
    private void setupView(){
        admin.setFile("resources/admin/librarians.json");
        admin.readDataFromFile(false);
        JSONArray libs = admin.viewLibrarians();
        File f = new File("resources/admin/registeredLibrariansReport.txt");

        Text title = new Text("Registered Librarians");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        view.getChildren().add(title);

        try {
            PrintWriter pw = new PrintWriter(f);
            pw.print("");
            pw.close();
        } catch (FileNotFoundException e) {
            Logger logger = setupLogger();
            assert logger != null;
            logger.severe("FileNotFoundException occurred when finding \""+f.getName()+"\" file");
        }
        if (libs.isEmpty()){
            Label label = new Label("There are no librarians registered in the system");
            view.getChildren().add(label);
        } else {
            for (Object obj : libs) {
                HBox hb = new HBox(20);
                hb.setAlignment(Pos.CENTER);
                String lib =
                        "Employee Id: " + ((JSONObject) obj).get("id") + "\n"
                        + "Name: " + ((JSONObject) obj).get("name") + "\n"
                        + "Email: " + ((JSONObject) obj).get("email") + "\n\n";
                Label libLabel = new Label(lib);
                libLabel.setWrapText(true);
                hb.getChildren().add(libLabel);
                view.getChildren().add(hb);
                try {
                    FileWriter fw = new FileWriter(f, true);
                    fw.write(lib);
                    fw.close();
                } catch (IOException e) {
                    Logger logger = setupLogger();
                    assert logger != null;
                    logger.severe("IOException happened on the file while " +
                            "reading data from \"" + f.getName() + "\" file");
                }
            }
        }
        Button backBtn = new Button("Go Back");
        backBtn.setLayoutX(265.0);
        backBtn.setLayoutY(155.0);
        backBtn.setMaxSize(100, 200);
        backBtn.setOnAction(e -> {
            stage.getScene().setRoot(prevRoot);
        });

        view.getChildren().add(backBtn);
    }

    /**
     * This method is the same as the setupLogger method in the Admin class
     * @return Logger which appends the error message to the adminLogger.txt file
     * @see Admin
     */
    private Logger setupLogger(){
        try {
            Logger logger = Logger.getLogger("fileLog");
            FileHandler fh = new FileHandler("resources/admin/adminLogger.txt", true);

            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            return logger;
        }
        catch (IOException ioe){
            System.out.println("File write error while writing data to the logger file.");
        }
        return null;
    }
}
