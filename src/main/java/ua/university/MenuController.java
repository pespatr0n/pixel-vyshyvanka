package ua.university;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void openEditor() {
        Main.setRoot("/main.fxml"); // Запускає твій поточний редактор
    }

    @FXML
    private void openInstructions() {
        InfoController.viewType = "instructions";
        Main.setRoot("/info.fxml");
    }

    @FXML
    private void openTraditions() {
        InfoController.viewType = "traditions";
        Main.setRoot("/info.fxml");
    }

    @FXML
    private void exitApp() {
        Platform.exit(); // Закриває програму
    }
}