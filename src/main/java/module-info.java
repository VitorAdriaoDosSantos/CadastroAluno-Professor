module com.example.projetojavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projetojavafxjdbc to javafx.fxml;
    exports com.example.projetojavafxjdbc;
    exports com.example.projetojavafxjdbc.controller;
    opens com.example.projetojavafxjdbc.controller to javafx.fxml;
}