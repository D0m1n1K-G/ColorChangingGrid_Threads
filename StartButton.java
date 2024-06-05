import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * A class that is used to start the simulation.
 * This class is used to create a button that it is setting the parameters of the simulation and initializes the grid.
 * 
 */
public class StartButton extends Button{

    /**
     * The constructor of the StartButton class.
     * It creates a button that sets the parameters of the simulation and initializes the grid.
     * @param text The text of the button.
     * @param primaryStage The stage of the application.
     * @param rows The text field for the number of rows.
     * @param cols The text field for the number of columns.
     * @param prob The text field for the probability.
     * @param K The text field for the value of K.
     */
    public StartButton(String text,Stage primaryStage, TextField rows, TextField cols, TextField prob, TextField K) {
        setText(text);
        setOnAction(event -> {
            try {
                int row = Integer.parseInt(rows.getText()); // Parse the given parameters and check if they are valid
                int col = Integer.parseInt(cols.getText()); 
                double p = Double.parseDouble(prob.getText());
                int k = Integer.parseInt(K.getText());

                if (row <= 0 || col <= 0 || p < 0 || p > 1 || k <= 0) {
                    throw new IllegalArgumentException();
                }

                TorusGrid torus = new TorusGrid(row, col); // Create the instance of the grid with the given parameters
                torus.initializeGrid(primaryStage, p, k); // Initialize the grid
            } catch (IllegalArgumentException e) {
                // Display an error message to the user
                rows.setText("");
                cols.setText("");
                prob.setText("");
                K.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR); // Create an alert dialog to inform the user about the error
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error occurs");
                alert.setContentText("Invalid arguments");
                alert.showAndWait();
            }
        });
    }
}
