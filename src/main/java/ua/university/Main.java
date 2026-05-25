package ua.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static MediaPlayer mediaPlayer;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Піксельна вишивка. Редактор орнаменту | Заяць Андрій");

        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
        primaryStage.getIcons().add(icon);

        setRoot("/menu.fxml");
        initMusic();
        primaryStage.show();
    }

    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            Parent root = loader.load();
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root, 900, 650));
            } else {
                stage.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMusic() {
        try {
            URL resource = getClass().getResource("/music.mp3"); // Назва твого файлу
            if (resource != null) {
                Media sound = new Media(resource.toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Зациклити музику
                mediaPlayer.setVolume(0.3); // Гучність за замовчуванням (30%)
                mediaPlayer.play();
            } else {
                System.out.println("Файл music.mp3 не знайдено в resources!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}