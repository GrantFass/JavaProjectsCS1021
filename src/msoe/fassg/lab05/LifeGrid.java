/*
 * Course:     CS 1021 - 021
 * Winter 2018-2019
 * Lab Game Of Life
 * Name: Derek Riley (edits by Chris Taylor)
 * Created 11/25/2016
 */

package msoe.fassg.lab05;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the grid of cells used to model Conway's Game of Life.
 */
public class LifeGrid {

    /**
     * This instance variable stores the grid of Cells
     */
    private List<List<Cell>> cells;

    /**
     * This instance variable stores the width of the cell grid
     */
    private final int numberOfCellsWide;

    /**
     * This instance variable stores the height of the cell grid
     */
    private final int numberOfCellsHigh;

    /**
     * This constructor builds a LifeGrid using the size of the
     * Pane passed and the scale of the cells
     * @param gamePane viewing pane
     */
    public LifeGrid(Pane gamePane) {
        this(gamePane, (int) gamePane.getPrefWidth(), (int) gamePane.getPrefHeight());
    }

    /**
     * This constructor builds a LifeGrid using the size of the
     * Pane passed and the scale of the cells
     * @param gamePane viewing pane
     * @param width the preferred width of the viewing pane
     * @param height the preferred width of the viewing pane
     */
    public LifeGrid(Pane gamePane, int width, int height) {
        this.numberOfCellsWide = width/Cell.SCALE;
        this.numberOfCellsHigh = height/Cell.SCALE;
        cells = new ArrayList<>();

        //initialize the two dimensional ArrayList
        for(int i = 0; i < numberOfCellsHigh; i++) {
            cells.add(new ArrayList<>());
        }

        //create cells
        for(int i = 0; i<numberOfCellsHigh; i++) {     // yPosition
            for(int j = 0; j<numberOfCellsWide; j++) { // xPosition
                cells.get(i).add(new Cell(j, i));
            }
        }

        //set neighbors for all cells
        for(int i = 0; i<numberOfCellsHigh; i++) {     // yPosition
            for(int j = 0; j<numberOfCellsWide; j++) { // xPosition
                if(i>0) {
                    if(j>0) {
                        cells.get(i).get(j).setNeighborAboveLeft(cells.get(i-1).get(j-1));
                    }
                    cells.get(i).get(j).setNeighborAboveCenter(cells.get(i-1).get(j));
                    if(j<numberOfCellsWide-1) {
                        cells.get(i).get(j).setNeighborAboveRight(cells.get(i-1).get(j+1));
                    }
                }
                if(j>0) {
                    cells.get(i).get(j).setNeighborMiddleLeft(cells.get(i).get(j-1));
                }
                if(j<numberOfCellsWide-1) {
                    cells.get(i).get(j).setNeighborMiddleRight(cells.get(i).get(j+1));
                }
                if(i<numberOfCellsHigh-1) { // bottom boarder elements
                    if(j>0) {
                        cells.get(i).get(j).setNeighborBelowLeft(cells.get(i+1).get(j-1));
                    }
                    cells.get(i).get(j).setNeighborBelowCenter(cells.get(i+1).get(j));
                    if(j<numberOfCellsWide-1) {
                        cells.get(i).get(j).setNeighborBelowRight(cells.get(i+1).get(j+1));
                    }
                }
            }
        }
        initialize(gamePane);
    }

    /**
     * method to count and return the number of alive cells and dead cells
     * @return an integer array where the 0 index is the number of alive cells,
     *  and the 1 index is the number of dead cells.
     */
    public int[] cellsAliveAndDead(){
        int[] aliveAndDeadCells = new int[2];
        aliveAndDeadCells[0] = 0;
        aliveAndDeadCells[1] = 0;
        //create cells
        for(int i = 0; i < numberOfCellsHigh; i++) {     // yPosition
            for(int j = 0; j < numberOfCellsWide; j++) { // xPosition
                if(cells.get(i).get(j).isAlive()){
                    aliveAndDeadCells[0]++;
                } else {
                    aliveAndDeadCells[1]++;
                }
            }
        }
        return aliveAndDeadCells;
    }

    /**
     * method to toggle a cell in the lifeGrid that is clicked by the user
     * @param mouseX the x coordinate of the mouse when the mouse clicks in the grid
     * @param mouseY the y coordinate of the mouse when the mouse clicks in the grid
     * @return 1 if toggled cell, -1 if did not toggle cell
     */
    public int click(double mouseX, double mouseY) {
        double cellY;
        double cellX;
        double cellSize = Cell.SCALE;
        for(int i = 0; i < numberOfCellsHigh; i++) {     // yPosition
            for(int j = 0; j < numberOfCellsWide; j++) { // xPosition
                cellX = cells.get(i).get(j).getLayoutX();
                cellY = cells.get(i).get(j).getLayoutY();
                if (mouseX >= cellX && mouseX <= cellX + cellSize) {
                    if (mouseY >= cellY && mouseY <= cellY + cellSize) {
                        if (cells.get(i).get(j).isAlive()) {
                            cells.get(i).get(j).setAlive(false);
                            cells.get(i).get(j).updateColors();
                            return 1;
                        } else {
                            cells.get(i).get(j).setAlive(true);
                            cells.get(i).get(j).updateColors();
                            return 1;
                        }
                    }
                }
            }
        }
        return -1;
    }



    /**
     * This method randomizes the life and death attributes of all cells in the cells.
     * Cells have a 50% chance of being alive or dead.
     */
    public void randomize() {
        final double roundingModifier = 0.5;
        for(List<Cell> row : cells) {
            for(Cell cell : row) {
                cell.setAlive(Math.random() < roundingModifier);
                cell.updateColors();
            }
        }
    }

    /**
     * This method triggers one iteration (tick) of the game of life rules for the entire grid.
     */
    public void iterate() {
        for(List<Cell> row : cells) {
            for(Cell cell : row) {
                cell.determineNextTick();
            }
        }
        for(List<Cell> row : cells) {
            for(Cell cell : row) {
                cell.updateTick();
            }
        }
    }

    /**
     * This method adds all the cell rectangles to the Pane
     * @param gamePane the gamePane to be initialized
     */
    private void initialize(Pane gamePane) {
        for(List<Cell> row : cells) {
            for(Cell cell : row) {
                gamePane.getChildren().add(cell);
            }
        }
    }
}
