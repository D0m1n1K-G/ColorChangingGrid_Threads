import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A class that represents the main user interface of the application.
 * This class is used to create the main user interface of the application.
 * The main user interface consists of a menu bar, a horizontal box for the buttons, and a drawing area.
 */
public class MainUI {

    /**
     * The constructor of the MainUI class.
     * It creates the main user interface of the application.
     * @param stage The stage of the application.
     */
    public MainUI(Stage stage) {
        VBox vbox = new VBox(10); // Main layout for the UI
        vbox.setPadding(new Insets(10));

        Label sizeLabel = new Label("Rozmiar planszy:"); // Labels for the parameters
        TextField RowField = new TextField(); // Text fields for the parameters
        RowField.setPromptText("Set Rows:");
        TextField ColsField = new TextField();
        ColsField.setPromptText("Set Columns:");

        Label probabilityLabel = new Label("Prawdopodobieństwo:");
        TextField probabilityField = new TextField();

        Label kLabel = new Label("Wartość K:");
        TextField kField = new TextField();

        StartButton btn = new StartButton("Set", stage, RowField, ColsField, probabilityField, kField); // Start button

        vbox.getChildren().addAll(sizeLabel, RowField, ColsField, probabilityLabel, probabilityField, kLabel, kField, btn); // Add all the elements to the layout

        Scene scene = new Scene(vbox); // Create the scene
        stage.setScene(scene);
        stage.show();
    }
    
}
