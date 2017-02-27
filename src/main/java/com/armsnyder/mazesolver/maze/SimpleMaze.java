package com.armsnyder.mazesolver.maze;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.IntStream;

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
        IntStream.rangeClosed(cellX - 1, cellX + 1).forEach(x ->
                IntStream.rangeClosed(cellY - 1, cellY + 1).forEach(y -> {
                    final Cell neighbor = cellIdentityMap.get(new SimpleCell(x, y));
                    if (neighbor != null) {
                        result.add(neighbor);
                    }
                }));
        result.remove(cell);
        return result;
    }
}
