import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the entry point of the application.
 * It creates a new instance of MainUI and passes the stage to it.
 */
public class MainApp extends Application {

    /**
     * The main method of the application.
     * It launches the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method of the application.
     * It creates a new instance of MainUI and passes the stage to it.
     * @param stage The stage of the application.
     */
    public void start(Stage stage) {
        new MainUI(stage);
    }
}