module com.example.breakout {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.breakout to javafx.fxml;
    exports com.example.breakout;
}