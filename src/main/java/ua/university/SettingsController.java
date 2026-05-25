package ua.university;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

public class SettingsController {

    @FXML private Slider volumeSlider;
    @FXML private CheckBox muteCheckBox;

    @FXML
    public void initialize() {
        if (Main.mediaPlayer != null) {

            volumeSlider.setValue(Main.mediaPlayer.getVolume());
            muteCheckBox.setSelected(Main.mediaPlayer.isMute());

            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                Main.mediaPlayer.setVolume(newValue.doubleValue());
                if (newValue.doubleValue() > 0 && muteCheckBox.isSelected()) {
                    muteCheckBox.setSelected(false);
                }
            });

            muteCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                Main.mediaPlayer.setMute(newValue);
            });
        }
    }

    @FXML
    private void goBack() {
        Main.setRoot("/menu.fxml");
    }
}