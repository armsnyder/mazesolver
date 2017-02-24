package com.armsnyder.mazesolver.interfaces;

import java.util.Collection;

/**
 * A problem that needs solving
 */
public interface Maze {
    Collection<Cell> getCells();
    Cell getStart();
    Cell getFinish();
    Dimensions getDimensions();
}
