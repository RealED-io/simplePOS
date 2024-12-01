module tesda.tcsdi.simplepos {

    requires java.sql;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml.crypto;

    opens tesda.tcsdi.simplepos to javafx.fxml;
    opens tesda.tcsdi.simplepos.model to javafx.fxml;

    exports tesda.tcsdi.simplepos;
    exports tesda.tcsdi.simplepos.model;
}