package com.armsnyder.mazesolver.solver;

import com.armsnyder.mazesolver.maze.Maze;

/**
 * Algorithm to solve a maze
 */
@FunctionalInterface
public interface SolverStrategy {
    Solution solve(Maze maze);
}
