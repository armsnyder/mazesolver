package com.armsnyder.mazesolver.interfaces;

/**
 * Reads and writes a maze to and from an encoded type
 *
 * @param <T> encoded type
 */
public interface Parser<T> {
    Maze decodeMaze(T encodedMaze);
    Solution decodeSolution(T encodedSolution);
    T encode(Maze maze);
    T encode(Solution solution);
}
