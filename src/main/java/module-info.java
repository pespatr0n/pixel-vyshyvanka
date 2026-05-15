module ua.university.vyshyvanka {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    opens ua.university to javafx.fxml;
    exports ua.university;
}