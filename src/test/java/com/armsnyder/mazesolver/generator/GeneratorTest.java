package com.armsnyder.mazesolver.generator;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleDimensions;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by asnyder on 2/27/17.
 */
abstract class GeneratorTest {
    abstract Generator generator();

    @Test
    void generate_withDimensions_mazeHasCorrectDimensions() {
        Dimensions dimensions = new SimpleDimensions(10, 10);
        Maze maze = generator().generate(dimensions);
        assertEquals(dimensions, maze.getDimensions());
    }

    @Test
    void generate_withDimensions_mazeContainsStartAndFinish() {
        Dimensions dimensions = new SimpleDimensions(10, 10);
        Maze maze = generator().generate(dimensions);
        assertTrue(maze.getCells().contains(maze.getStart()));
        assertTrue(maze.getCells().contains(maze.getFinish()));
    }

    @Test
    void generate_withDimensions_mazeHasSomeWalls() {
        Dimensions dimensions = new SimpleDimensions(10, 10);
        Maze maze = generator().generate(dimensions);
        assertTrue(maze.getCells().size() <
                dimensions.getHeight().intValue() * dimensions.getWidth().intValue());
    }

    @Test
    void generate_withDimensions_mazeBorderedByStartAndFinishOnly() {
        Dimensions dimensions = new SimpleDimensions(10, 10);
        Maze maze = generator().generate(dimensions);
        final Collection<Cell> discoveredEntrances = maze.getCells().stream().filter(cell ->
                cell.getX().intValue() == 0 || cell.getY().intValue() == 0 ||
                        cell.getX().intValue() == maze.getDimensions().getWidth().intValue() - 1 ||
                        cell.getY().intValue() == maze.getDimensions().getHeight().intValue() - 1)
                .collect(Collectors.toList());
        assertEquals(2, discoveredEntrances.size());
        assertTrue(discoveredEntrances.contains(maze.getStart()));
        assertTrue(discoveredEntrances.contains(maze.getFinish()));
    }
}
