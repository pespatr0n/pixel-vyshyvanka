package ua.university;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Controller {
    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider scaleSlider;
    @FXML private javafx.scene.control.CheckBox horizSymCheck;
    @FXML private javafx.scene.control.CheckBox vertSymCheck;
    @FXML private javafx.scene.control.TextField fragWidthField;
    @FXML private javafx.scene.control.TextField fragHeightField;

    private static final int GRID_SIZE = 50; // 50x50 клітинок
    private static final int CELL_SIZE = 10; // розмір клітинки 10px
    private Color[][] grid = new Color[GRID_SIZE][GRID_SIZE];

    @FXML
    public void initialize() {
        colorPicker.setValue(Color.RED);
        clearGrid();
        redraw();
    }

    private void clearGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = Color.WHITE;
            }
        }
    }

    @FXML
    public void handleMouse(MouseEvent event) {
        int col = (int) (event.getX() / CELL_SIZE);
        int row = (int) (event.getY() / CELL_SIZE);

        if (col >= 0 && col < GRID_SIZE && row >= 0 && row < GRID_SIZE) {
            Color selectedColor = colorPicker.getValue();

            grid[row][col] = selectedColor;

            if (horizSymCheck.isSelected()) {
                int mirrorCol = GRID_SIZE - 1 - col;
                grid[row][mirrorCol] = selectedColor;
            }

            if (vertSymCheck.isSelected()) {
                int mirrorRow = GRID_SIZE - 1 - row;
                grid[mirrorRow][col] = selectedColor;
            }

            if (horizSymCheck.isSelected() && vertSymCheck.isSelected()) {
                int mirrorCol = GRID_SIZE - 1 - col;
                int mirrorRow = GRID_SIZE - 1 - row;
                grid[mirrorRow][mirrorCol] = selectedColor;
            }
            redraw();
        }
    }

    @FXML
    public void handleDuplicate() {
        try {
            int fragW = Integer.parseInt(fragWidthField.getText());
            int fragH = Integer.parseInt(fragHeightField.getText());

            if (fragW <= 0 || fragH <= 0) return;

            for (int y = 0; y < GRID_SIZE; y++) {
                for (int x = 0; x < GRID_SIZE; x++) {
                    grid[y][x] = grid[y % fragH][x % fragW];
                }
            }
            redraw();

        } catch (NumberFormatException e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Некоректний ввід");
            alert.setHeaderText("Помилка розміру фрагмента");
            alert.setContentText("Будь ласка, введіть цілі числа у поля ширини та висоти.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Зображення PNG (*.png)", "*.png")
        );
        java.io.File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if (file != null) {
            try {
                int scale = (int) scaleSlider.getValue();
                int imgSize = GRID_SIZE * scale;

                java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
                        imgSize,
                        imgSize,
                        java.awt.image.BufferedImage.TYPE_INT_ARGB
                );

                java.awt.Graphics2D g2d = img.createGraphics();

                for (int y = 0; y < GRID_SIZE; y++) {
                    for (int x = 0; x < GRID_SIZE; x++) {
                        javafx.scene.paint.Color fxColor = grid[y][x];

                        java.awt.Color awtColor = new java.awt.Color(
                                (float) fxColor.getRed(),
                                (float) fxColor.getGreen(),
                                (float) fxColor.getBlue(),
                                (float) fxColor.getOpacity()
                        );

                        g2d.setColor(awtColor);

                        g2d.fillRect(x * scale, y * scale, scale, scale);
                    }
                }

                g2d.dispose();

                javax.imageio.ImageIO.write(img, "png", file);

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Зображення PNG (*.png)", "*.png")
        );
        java.io.File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (file != null) {
            try {
                java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(file);

                int scaleX = img.getWidth() / GRID_SIZE;
                int scaleY = img.getHeight() / GRID_SIZE;

                javafx.scene.paint.Color[][] tempGrid = new javafx.scene.paint.Color[GRID_SIZE][GRID_SIZE];

                for (int y = 0; y < GRID_SIZE; y++) {
                    for (int x = 0; x < GRID_SIZE; x++) {
                        int startX = x * scaleX;
                        int startY = y * scaleY;

                        if (startX < img.getWidth() && startY < img.getHeight()) {

                            int referenceArgb = img.getRGB(startX, startY);

                            for (int dy = 0; dy < scaleY; dy++) {
                                for (int dx = 0; dx < scaleX; dx++) {
                                    int currentX = startX + dx;
                                    int currentY = startY + dy;

                                    if (currentX < img.getWidth() && currentY < img.getHeight()) {
                                        if (img.getRGB(currentX, currentY) != referenceArgb) {
                                            throw new IllegalArgumentException("Знайдено різні кольори в межах однієї клітинки.");
                                        }
                                    }
                                }
                            }

                            int a = (referenceArgb >> 24) & 0xff;
                            int r = (referenceArgb >> 16) & 0xff;
                            int g = (referenceArgb >> 8) & 0xff;
                            int b = referenceArgb & 0xff;

                            tempGrid[y][x] = javafx.scene.paint.Color.rgb(r, g, b, a / 255.0);
                        }
                    }
                }

                grid = tempGrid;
                redraw();

            } catch (IllegalArgumentException e) {

                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Помилка формату");
                alert.setHeaderText("Неправильна картинка");
                alert.setContentText("Цей файл не підходить для редактора вишивки. " + e.getMessage());
                alert.showAndWait();

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                gc.setFill(grid[row][col]);
                gc.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                gc.setStroke(Color.LIGHTGRAY);
                gc.strokeRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

}