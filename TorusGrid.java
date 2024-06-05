import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * A class that represents a grid of cells.
 * This class is used to create a grid of cells.
 * Each cell is a rectangle with a random color.
 */
public class TorusGrid extends GridPane {
    private final int row;
    private final int col;
    private Cell[][] cells; // The cells in the grid

    /**
     * The constructor of the TorusGrid class.
     * It sets the number of rows and columns in the grid.
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     */
    public TorusGrid(int rows, int cols) {
        this.row = rows;
        this.col = cols;
    }

    /**
     * Initialize the grid of cells.
     * It creates a grid of cells with a given probability and value of K.
     * @param primaryStage The primary stage of the application.
     * @param p The probability of changing the color of the cell.
     * @param k The value of K.
     */
    public void initializeGrid(Stage primaryStage, double p, int k) {
        cells = new Cell[row][col]; // Create a grid of cells
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j] = new Cell(i, j, this, p, k); // Create a cell in the grid
                add(cells[i][j].getRectangle(), j, i); // Add the cell to the grid
            }
        }

        Scene gridScene = new Scene(this); // Create a scene with the grid
        Stage gridStage = new Stage(); // Create a stage with the scene
        gridStage.setTitle("Color Changing Grid"); 
        gridStage.setScene(gridScene); // Set the scene to the stage
        gridStage.show();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Thread thread = new Thread(cells[i][j]); // Create a thread for each cell in the grid
                thread.setDaemon(true);
                thread.start();
            }
        }

        
        
        primaryStage.close(); // Close the primary stage
    }


    /**
     * Get the neighbors of a cell.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The neighbors of the cell.
     */
    public Cell[] getNeighbors(int x, int y) {
        int[][] neighbors = { // The neighbors of the cell
            {(x - 1 + cells.length) % cells.length, y}, // Left neighbor
            {(x + 1) % cells.length, y}, // Right neighbor
            {x, (y - 1 + cells[0].length) % cells[0].length}, // Top neighbor
            {x, (y + 1) % cells[0].length} // Bottom neighbor
        };
        Cell[] neighborCells = new Cell[4];
        for(int i = 0; i < 4; i++) {
            neighborCells[i] = cells[neighbors[i][0]][neighbors[i][1]];
        }
        return neighborCells;
    }

}