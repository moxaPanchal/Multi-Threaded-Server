module workshop.ClientA {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens myProjects.ClientA to javafx.fxml;
    exports myProjects.ClientA;
    exports myProjects.Server;
    exports myProjects.ClientB;
}
