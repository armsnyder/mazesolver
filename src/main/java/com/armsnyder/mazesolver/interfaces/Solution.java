package com.armsnyder.mazesolver.interfaces;

import java.util.Collection;

/**
 * A solution to a Maze
 */
public interface Solution {
    Maze getMaze();
    Collection<Cell> getSolution();
}
