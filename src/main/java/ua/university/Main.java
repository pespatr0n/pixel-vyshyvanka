package ua.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Піксельна вишивка. Редактор орнаменту | Заяць Андрій");

        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
        primaryStage.getIcons().add(icon);

        setRoot("/menu.fxml");
        primaryStage.show();
    }

    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            Parent root = loader.load();
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root, 800, 600));
            } else {
                stage.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}