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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Maze)) {
            return false;
        } else {
            Maze other = (Maze) o;
            return getCells().equals(other.getCells()) &&
                    getStart().equals(other.getStart()) &&
                    getFinish().equals(other.getFinish()) &&
                    getDimensions().equals(other.getDimensions());
        }
    }
}
