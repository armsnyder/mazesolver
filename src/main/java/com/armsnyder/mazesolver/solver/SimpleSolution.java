package com.armsnyder.mazesolver.solver;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Maze;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple implementation of Solution that exposes setters
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleSolution implements Solution {
    private Maze maze;
    private Collection<Cell> solution;
}
