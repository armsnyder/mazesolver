package com.armsnyder.mazesolver.interfaces;

import java.util.Set;

/**
 * A problem that needs solving
 */
public interface Maze {
    Set<Cell> getCells();
    Cell getStart();
    Cell getFinish();
    Dimensions getDimensions();
}
