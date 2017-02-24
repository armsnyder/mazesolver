package com.armsnyder.mazesolver.interfaces;

import java.util.Set;

/**
 * A solution to a Maze
 */
public interface Solution {
    Maze getMaze();
    Set<Cell> getSolution();
}
