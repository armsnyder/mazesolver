package com.armsnyder.mazesolver.generator;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Maze generator that uses a stack-based backtracking algorithm
 */
public class BacktrackGenerator implements Generator {
    private static final Dimensions DEFAULT_DIMENSIONS = new SimpleDimensions(20, 20);
    private static final Random RANDOM = new Random();

    @Override
    public Maze generate() {
        return generate(DEFAULT_DIMENSIONS);
    }

    @Override
    public Maze generate(final Dimensions dimensions) {
        final Edge startEdge = Edge.values()[RANDOM.nextInt(Edge.values().length)];
        final Cell start = pickStart(dimensions, startEdge);
        final Deque<Cell> stack = new ArrayDeque<>();
        final Set<Cell> visited = new HashSet<>();
        visit(start, visited, stack);
        filterInsideMaze(getAxisNeighbors(start), dimensions).findAny()
                .ifPresent(cell -> visit(cell, visited, stack));
        while (!stack.isEmpty()) {
            final Cell head = stack.peek();
            final List<Cell> candidates =
                    filterInsideMaze(getAxisNeighbors(head), dimensions)
                            .filter(candidate -> !visited.contains(candidate) && Stream.concat(
                                    getAxisNeighbors(candidate), getDiagonalNeighbors(candidate))
                                    .filter(subCandidate ->
                                            !subCandidate.equals(head) && getAxisNeighbors(head)
                                                    .noneMatch(subCandidate::equals))
                                    .noneMatch(visited::contains))
                            .collect(Collectors.toList());
            if (!candidates.isEmpty()) {
                Collections.shuffle(candidates, RANDOM);
                final Cell nextHead = candidates.get(0);
                visit(nextHead, visited, stack);
            } else {
                stack.pop();
            }
        }
        final Cell finish = pickFinish(visited, dimensions, startEdge.opposite());
        visited.add(finish);
        return new SimpleMaze(visited, start, finish, dimensions);
    }

    private static void visit(final Cell cell, final Set<Cell> visited, final Deque<Cell> stack) {
        stack.push(cell);
        visited.add(cell);
    }

    private static Cell pickStart(final Dimensions dimensions, final Edge edge) {
        Cell start;
        switch (edge) {
            case LEFT:
                start = new SimpleCell(0,
                        1 + RANDOM.nextInt(dimensions.getHeight().intValue() - 2));
                break;
            case RIGHT:
                start = new SimpleCell(dimensions.getWidth().intValue() - 1,
                        1 + RANDOM.nextInt(dimensions.getHeight().intValue() - 2));
                break;
            case UP:
                start = new SimpleCell(1 + RANDOM.nextInt(dimensions.getWidth().intValue() - 2),
                        0);
                break;
            case DOWN:
                start = new SimpleCell(1 + RANDOM.nextInt(dimensions.getWidth().intValue() - 2),
                        dimensions.getHeight().intValue() - 1);
                break;
            default:
                throw new IllegalStateException();
        }
        return start;
    }

    private static Cell pickFinish(final Collection<Cell> path, final Dimensions dimensions,
                                   final Edge edge) {
        Predicate<Cell> predicate;
        UnaryOperator<Cell> transformer;
        switch (edge) {
            case LEFT:
                predicate = cell -> cell.getX().intValue() == 1;
                transformer = cell -> new SimpleCell(0, cell.getY());
                break;
            case RIGHT:
                predicate = cell -> cell.getX().intValue() == dimensions.getWidth().intValue() - 2;
                transformer = cell ->
                        new SimpleCell(dimensions.getWidth().intValue() - 1, cell.getY());
                break;
            case UP:
                predicate = cell -> cell.getY().intValue() == 1;
                transformer = cell -> new SimpleCell(cell.getX(), 0);
                break;
            case DOWN:
                predicate = cell -> cell.getY().intValue() == dimensions.getHeight().intValue() - 2;
                transformer = cell ->
                        new SimpleCell(cell.getX(), dimensions.getHeight().intValue() - 1);
                break;
            default:
                throw new IllegalStateException();
        }
        Cell[] finish = new Cell[1];
        path.stream().filter(predicate).findAny().map(transformer)
                .ifPresent(cell -> finish[0] = cell);
        return finish[0];
    }

    private static Stream<Cell> filterInsideMaze(final Stream<Cell> cells,
                                                 final Dimensions dimensions) {
        final int width = dimensions.getWidth().intValue();
        final int height = dimensions.getHeight().intValue();
        return cells.filter(candidate -> {
            final int candidateX = candidate.getX().intValue();
            final int candidateY = candidate.getY().intValue();
            return candidateX > 0 && candidateX < width - 1 &&
                    candidateY > 0 && candidateY < height - 1;
        });
    }

    private static Stream<Cell> getAxisNeighbors(final Cell cell) {
        final int cellX = cell.getX().intValue();
        final int cellY = cell.getY().intValue();
        return Stream.of(
                new SimpleCell(cellX - 1, cellY), new SimpleCell(cellX + 1, cellY),
                new SimpleCell(cellX, cellY - 1), new SimpleCell(cellX, cellY + 1));
    }

    private static Stream<Cell> getDiagonalNeighbors(final Cell cell) {
        final int cellX = cell.getX().intValue();
        final int cellY = cell.getY().intValue();
        return Stream.of(
                new SimpleCell(cellX - 1, cellY - 1), new SimpleCell(cellX - 1, cellY + 1),
                new SimpleCell(cellX + 1, cellY - 1), new SimpleCell(cellX + 1, cellY - 1));
    }

    private enum Edge {
        UP, DOWN, LEFT, RIGHT;

        private Edge opposite() {
            switch (this) {
                case DOWN:
                    return UP;
                case UP:
                    return DOWN;
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
