# Program Description
In this program we got a grid which user can provide it's size in textfields, the probability of the cell's color change and the speed of changes.<br>
Every cell in the grid is a **Thread** in which every certain period of time (the delay is equal to a randomly selected <br>
number of milliseconds from the interval [0.5k, 1.5k]) performs the following actions: <br>
    - with probability p it changes its color to a random color;
    - with probability 1 - p checks the colors of its four neighbors (we treat the board as a two-dimensional track.
    we treat as a two-dimensional torus) and takes the average of them as its color.

Each cell can be suspended and resumed by clicking on it 

## Overview
- [MainApp](classMainApp.html) - Entry point of the application.
    - [MainUI](classMainUI.html) - Main user interface of the application.
        - [TorusGrid](classTorusGrid.html) - A class that represents a grid of cells.
            - [Cell](classCell.html) - A class that represents a cell in the grid, each cell is a rectangle with a random color.
        - [StartButton](classStartButton.html) - A class is used to create a button that it is setting the parameters of the simulation and initializes the grid.