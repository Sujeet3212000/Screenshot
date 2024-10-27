module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires org.apache.poi.ooxml;
    requires com.github.kwhat.jnativehook;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}