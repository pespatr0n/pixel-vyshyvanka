module ua.university.vyshyvanka {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    opens ua.university to javafx.fxml;
    exports ua.university;
}