package btp400.assignment1.frontend;

import btp400.assignment1.backend.Librarian;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is responsible for creating the primary stage and a scene
 * for that stage which will render multiple roots as the user progresses
 * through the application.
 * @author Soham Thaker
 * @version 1.0.0
 * @see Application
 * @see IOException
 * @see LoginAndRegister
 * @see VBox
 * @see Scene
 * @see ToggleGroup
 * @see RadioButton
 * @see Stage
 */
public class Library extends Application {

    /**
     * This method is responsible for showing the primary stage which has a scene
     * associated to it along with a root which has radio buttons. Depending on
     * selected radio button it will render a Login and Register root.
     * @param stage A primary stage that is provided by JavaFX
     * @throws IOException Exception is thrown if an exception occurs during IO process.
     */
    @Override
    public void start(Stage stage) throws IOException {

        Librarian.setPath("resources/librarian/library.json");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.setTitle("Library");
        stage.setResizable(false);

        ToggleGroup group = new ToggleGroup();

        RadioButton adminButton = new RadioButton("Admin");
        adminButton.setToggleGroup(group);
        adminButton.setSelected(true);
        adminButton.setLayoutX(265.0);
        adminButton.setLayoutY(155.0);
        adminButton.setMaxSize(100, 200);

        RadioButton librarianButton = new RadioButton("Librarian");
        librarianButton.setToggleGroup(group);
        librarianButton.setLayoutX(265.0);
        librarianButton.setLayoutY(155.0);
        librarianButton.setMaxSize(100, 200);

        RadioButton studentButton = new RadioButton("Student");
        studentButton.setToggleGroup(group);
        studentButton.setLayoutX(265.0);
        studentButton.setLayoutY(155.0);
        studentButton.setMaxSize(100, 200);

        Button submitButton = new Button("Submit");
        submitButton.setLayoutX(265.0);
        submitButton.setLayoutY(155.0);
        submitButton.setMaxSize(100, 200);
        submitButton.setOnAction(e -> {
            LoginAndRegister login = new LoginAndRegister(group, root, stage);
            stage.getScene().setRoot(login.getRootPane());
        });

        root.getChildren().add(adminButton);
        root.getChildren().add(librarianButton);
        root.getChildren().add(studentButton);
        root.getChildren().add(submitButton);

        scene.setRoot(root);
        stage.show();
    }

    /**
     * This method is responsible to run the whole JavaFX application.
     * @param args An array of arguments passed to the application from terminal.
     */
    public static void main(String[] args) {
        launch(args);
    }
}