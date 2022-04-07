module com.example.repin_course {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.repin_course to javafx.fxml;
    exports com.example.repin_course;
}