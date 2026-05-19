package ua.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);

        primaryStage.setTitle("Піксельна вишивка. Редактор орнаменту | Заяць Андрій");
        primaryStage.setScene(scene);
        primaryStage.show();
        Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/icon.png"));
        primaryStage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
