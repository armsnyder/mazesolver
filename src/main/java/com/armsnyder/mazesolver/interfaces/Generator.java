package com.armsnyder.mazesolver.interfaces;

/**
 * Can generate a maze
 */
public interface Generator {
    Maze generate();
    Maze generate(Dimensions dimensions);
}
