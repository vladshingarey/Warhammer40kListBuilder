package lbgui.listbuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

public class MainApplication extends Application {

    public static void main(String[] args) {

        launch(args);
    }
    @Override
    public void start(Stage stage) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainApplication.fxml")));
            Scene scene = new Scene(root, 900, 700);

            Image icon = new Image("warhammerLogo.png");
            stage.getIcons().add(icon);
            stage.setTitle("List Builder Application");
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}