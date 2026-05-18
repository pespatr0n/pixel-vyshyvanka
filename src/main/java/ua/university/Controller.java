package ua.university;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Controller {
    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;

    private static final int GRID_SIZE = 50; // 50x50 клітинок
    private static final int CELL_SIZE = 10; // розмір клітинки 10px
    private final Color[][] grid = new Color[GRID_SIZE][GRID_SIZE];

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
            grid[row][col] = colorPicker.getValue();
            redraw();
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
                int scale = 20;
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