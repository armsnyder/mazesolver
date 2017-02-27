package com.armsnyder.mazesolver.solver;

import com.armsnyder.mazesolver.maze.Maze;

import java.util.Arrays;

/**
 * Created by asnyder on 2/26/17.
 */
public class SolverUtilities {
    public static void validateMaze(final Maze maze) {
        if (maze.getStart() == null) {
            throw new IllegalArgumentException("maze start required");
        }
        if (maze.getFinish() == null) {
            throw new IllegalArgumentException("maze finish required");
        }
        if (maze.getCells() == null || maze.getCells().isEmpty()) {
            throw new IllegalArgumentException("maze cells required");
        }
        if (!maze.getCells().containsAll(Arrays.asList(maze.getStart(), maze.getFinish()))) {
            throw new IllegalArgumentException("maze start and finish must be contained in maze");
        }
    }
}
