module tesda.tcsdi.simplepos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens tesda.tcsdi.simplepos to javafx.fxml;
    exports tesda.tcsdi.simplepos;
}