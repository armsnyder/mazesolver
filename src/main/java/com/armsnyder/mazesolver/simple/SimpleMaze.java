package com.armsnyder.mazesolver.simple;

import com.armsnyder.mazesolver.interfaces.Cell;
import com.armsnyder.mazesolver.interfaces.Dimensions;
import com.armsnyder.mazesolver.interfaces.Maze;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple implementation of Maze that exposes setters
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMaze implements Maze {
    private Collection<Cell> cells;
    private Cell start;
    private Cell finish;
    private Dimensions dimensions;
}
