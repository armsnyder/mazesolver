package com.armsnyder.mazesolver.maze;

/**
 * Reads and writes a maze to and from an encoded type
 *
 * @param <T> encoded type
 */
@FunctionalInterface
public interface MazeDecoder<T> {
    Maze decodeMaze(T encodedMaze);
}
