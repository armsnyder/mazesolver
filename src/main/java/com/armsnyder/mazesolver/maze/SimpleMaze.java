package com.armsnyder.mazesolver.maze;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple implementation of Maze that exposes setters
 */
@Data
@NoArgsConstructor
public class SimpleMaze implements Maze {
    private Collection<Cell> cells;
    private Map<Cell, Cell> cellIdentityMap;
    private Cell start;
    private Cell finish;
    private Dimensions dimensions;
    private static final Collection<Cell> neighbors = Arrays.asList(new SimpleCell(-1, 0),
            new SimpleCell(0, -1), new SimpleCell(0, 1), new SimpleCell(1, 0));

    public SimpleMaze(final Collection<Cell> cells, final Cell start, final Cell finish,
                      final Dimensions dimensions) {
        setCells(cells);
        this.start = start;
        this.finish = finish;
        this.dimensions = dimensions;
    }

    public void setCells(final Collection<Cell> cells) {
        if (cells == null) {
            throw new IllegalArgumentException("cells cannot be null");
        }
        this.cells = cells;
        cellIdentityMap = new HashMap<>();
        cells.forEach(cell -> cellIdentityMap.put(cell, cell));
    }

    @Override
    public Collection<Cell> getNeighbors(final Cell cell) {
        if (!cells.contains(cell)) {
            throw new IllegalArgumentException("cell not contained in maze");
        }
        final int cellX = cell.getX().intValue();
        final int cellY = cell.getY().intValue();
        final Collection<Cell> result = new HashSet<>();
        neighbors.forEach(relativeNeighbor -> {
            final Cell neighbor = cellIdentityMap.get(new SimpleCell(
                    relativeNeighbor.getX().intValue() + cellX,
                    relativeNeighbor.getY().intValue() + cellY));
            if (neighbor != null) {
                result.add(neighbor);
            }
        });
        return result;
    }
}
