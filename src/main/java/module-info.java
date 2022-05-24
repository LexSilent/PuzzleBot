module com.example.puzzlebot {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.puzzlebot to javafx.fxml;
    exports com.example.puzzlebot;
}