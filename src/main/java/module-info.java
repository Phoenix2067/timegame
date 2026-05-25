module com.example {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.rewinder to javafx.fxml;
    exports com.rewinder;
}
