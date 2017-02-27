package com.armsnyder.mazesolver.solver;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Maze;

import java.util.Collection;

/**
 * A solution to a Maze
 */
public interface Solution {
    Maze getMaze();
    Collection<Cell> getSolution();
}
