package com.armsnyder.mazesolver.maze;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A problem that needs solving
 */
public interface Maze {
    Collection<Cell> getCells();

    Cell getStart();

    Cell getFinish();

    Dimensions getDimensions();

    Collection<Cell> getNeighbors(Cell cell);

    static boolean equalsIgnoreDirection(final Maze a, final Maze b) {
        if (!a.getCells().containsAll(b.getCells())) {
            return false;
        }
        if (!b.getCells().containsAll(a.getCells())) {
            return false;
        }
        if (!a.getDimensions().equals(b.getDimensions())) {
            return false;
        }
        if (!Arrays.asList(a.getStart(), a.getFinish()).contains(b.getStart())) {
            return false;
        }
        if (!Arrays.asList(a.getStart(), a.getFinish()).contains(b.getFinish())) {
            return false;
        }
        final List<Cell> entrances = Arrays.asList(a.getStart(), a.getFinish());
        if (!entrances.contains(b.getStart())) {
            return false;
        }
        if (!entrances.contains(b.getFinish())) {
            return false;
        }
        if (entrances.get(0).equals(entrances.get(1))) {
            return false;
        }
        if (b.getStart().equals(b.getFinish())) {
            return false;
        }
        return true;
    }
}
