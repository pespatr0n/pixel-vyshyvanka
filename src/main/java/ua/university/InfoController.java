package ua.university;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class InfoController {
    // Змінна, яка визначає, який текст показувати
    public static String viewType = "instructions";

    @FXML private Label titleLabel;
    @FXML private TextArea textArea;

    @FXML
    public void initialize() {
        if ("instructions".equals(viewType)) {
            titleLabel.setText("Інструкція користувача");
            textArea.setText("test test test instruction");
        } else if ("traditions".equals(viewType)) {
            titleLabel.setText("Традиції української вишивки");
            textArea.setText("info about traditions test");
        }
    }

    @FXML
    private void goBack() {
        Main.setRoot("/menu.fxml"); // Повертаємось у меню
    }
}