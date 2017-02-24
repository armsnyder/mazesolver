package com.armsnyder.mazesolver.interfaces;

/**
 * Algorithm to solve a maze
 */
@FunctionalInterface
public interface SolverStrategy {
    Solution solve(Maze maze);
}
