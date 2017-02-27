package com.armsnyder.mazesolver.maze;

import java.util.Collection;

/**
 * A problem that needs solving
 */
public interface Maze {
    Collection<Cell> getCells();
    Cell getStart();
    Cell getFinish();
    Dimensions getDimensions();
    Collection<Cell> getNeighbors(Cell cell);
}
