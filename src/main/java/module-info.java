module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.Controller to javafx.fxml;  // add this line

    exports com.example.demo1;
    exports com.example.demo1.Controller;
}