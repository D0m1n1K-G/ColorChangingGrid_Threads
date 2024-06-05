import java.util.Random;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * A class that represents a cell in the grid.
 * This class is used to create a cell in the grid.
 * Each cell is a rectangle with a random color.
 * The color of the cell can change with a given probability.
 * The color of the cell can be pdated based on the average color of its neighbors.
 * The cell can be suspended and resumed.
 */
public class Cell implements Runnable {
    private static final Random random = new Random();
    private final int x; // The position of the cell in the grid
    private final int y;
    private final TorusGrid torus;
    private final Rectangle rectangle; // The rectangle representing the cell
    private Cell[] neighbors;
    private boolean suspended = false; // A flag to indicate if the cell is suspended
    private final double probability;
    private final int k;

    /**
     * The constructor of the Cell class.
     * It creates a cell in the grid.
     * Rectangle on mouse click will suspend or resume the cell.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @param torus The grid of cells.
     * @param probability The probability of changing the color of the cell.
     * @param k The value of K.
     */
    public Cell(int x, int y, TorusGrid torus, double probability, int k) {
        this.x = x;
        this.y = y;
        this.torus = torus;
        this.rectangle = new Rectangle(50, 50); // Create a rectangle with a random color
        this.rectangle.setFill(randomColor());
        this.probability = probability;
        this.k = k;

        rectangle.setOnMouseClicked(event -> {
            if (suspended) {
                resumeThread();
            } else {
                suspendThread();
            }
        });
    }

    /**
     * Get the rectangle representing the cell.
     * @return The rectangle representing the cell.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * The run method of the cell.
     * It changes the color of the cell with a given probability.
     * It updates the color of the cell based on the average color of its neighbors.
     * It prints the start and end of the thread.
     */
    @Override
    public void run() {
        this.neighbors = torus.getNeighbors(x, y); // Get the neighbors of the cell

        while (true) {
            try {
                checkForSuspend(); // Check if the cell is suspended
                System.out.println("Start: " + Thread.currentThread().getId());
                Thread.sleep(random.nextInt(k) + k / 2); // Sleep for a given speed of action [0,5k ; 1.5k]

                if (random.nextDouble() < probability) { // Change the color of the cell with a given probability
                    changeColor(randomColor());
                } else { // Update the color of the cell based on the average color of its neighbors with a probability 1 - p
                    updateColor();
                }

                System.out.println("End: " + Thread.currentThread().getId());
                checkForSuspend(); // Check if the cell is suspended
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Change the color of the cell.
     * It locks the cell, changes the color of the cell, and unlocks the cell.
     * @param color The new color of the cell.
     */
    private void changeColor(Color color) {
        synchronized(this) {
            Platform.runLater(() -> rectangle.setFill(color)); // Change the color of the cell 
        }
    }

    /**
     * Update the color of the cell based on the average color of its neighbors.
     * It locks the neighbors of the cell, gets the colors of the neighbors, and unlocks the neighbors.
     * It calculates the average color of the neighbors and changes the color of the cell.
     */
    private void updateColor() {
        Color[] neighborColors = new Color[4]; // The colors of the neighbors of the cell

        for(int i = 0; i < 4; i++) {
            synchronized(neighbors[i]) {
                neighborColors[i] = (Color) neighbors[i].getRectangle().getFill();
            }
        }


        Color averageColor = averageColor(neighborColors); // Calculate the average color of the neighbors
        changeColor(averageColor); // Change the color of the cell
    }

    /**
     * Generate a random color.
     * @return A random color.
     */
    private Color randomColor() {
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    /**
     * Calculate the average color of an array of colors.
     * @param colors The array of colors.
     * @return The average color of the array of colors.
     */
    private Color averageColor(Color[] colors) {
        double r = 0, g = 0, b = 0;
        for (Color color : colors) {
            r += color.getRed();
            g += color.getGreen();
            b += color.getBlue();
        }
        int len = colors.length;
        return Color.color(r / len, g / len, b / len);
    }

    /**
     * Suspend the cell.
     */
    private void suspendThread() {
        suspended = true;
    }

    /**
     * Resume the cell.
     */
    private void resumeThread() {
        synchronized(this) {
            suspended = false;
            this.notify();
        }
    }

    /**
     * Check if the cell is suspended.
     * If the cell is suspended, it waits until the cell is resumed.
     * @throws InterruptedException
     */
    private void checkForSuspend() throws InterruptedException {
        synchronized(this) {
            while(suspended) {
                this.wait();
            }
        }
    }
}

