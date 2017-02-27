package com.armsnyder.mazesolver.generator;

import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;

/**
 * Can generate a maze
 */
public interface Generator {
    Maze generate();
    Maze generate(Dimensions dimensions);
}
