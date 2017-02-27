package com.armsnyder.mazesolver.solver;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Maze;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import lombok.Value;

/**
 * Breadth-first search solver
 */
public class BFSSolverStrategy implements SolverStrategy {
    @Override
    public Solution solve(final Maze maze) {
        SolverUtilities.validateMaze(maze);
        final Cell finish = maze.getFinish();
        final Queue<Element> queue = new ArrayDeque<>();
        final Set<Cell> visited = new HashSet<>();
        queue.add(new Element(maze.getStart(), Collections.singleton(maze.getStart())));
        while(!queue.isEmpty()) {
            final Element current = queue.poll();
            final Cell currentCell = current.getCell();
            final Collection<Cell> currentWorkingSolution = current.getWorkingSolution();
            if (visited.contains(currentCell)) {
                continue;
            }
            visited.add(currentCell);
            if (currentCell.equals(finish)) {
                return new Solution() {
                    @Override
                    public Maze getMaze() {
                        return maze;
                    }
                    @Override
                    public Collection<Cell> getSolution() {
                        return currentWorkingSolution;
                    }
                };
            }
            maze.getNeighbors(currentCell).forEach(nextCell -> {
                final Collection<Cell> nextWorkingSolution =
                        new HashSet<>(currentWorkingSolution);
                nextWorkingSolution.add(nextCell);
                queue.add(new Element(nextCell, nextWorkingSolution));
            });
        }
        return new Solution() {
            @Override
            public Maze getMaze() {
                return maze;
            }
            @Override
            public Collection<Cell> getSolution() {
                return Collections.emptyList();
            }
        };
    }

    @Value
    private static class Element {
        Cell cell;
        Collection<Cell> workingSolution;
    }
}
