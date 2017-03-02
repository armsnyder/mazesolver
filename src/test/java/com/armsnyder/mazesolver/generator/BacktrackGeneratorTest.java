package com.armsnyder.mazesolver.generator;

import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleDimensions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by asnyder on 2/27/17.
 */
class BacktrackGeneratorTest extends GeneratorTest {

    @Override
    Generator generator() {
        return new BacktrackGenerator();
    }

    @Test
    void generate_withDimensions_mazeHasBranches() {
        final int width = 10;
        final int height = 10;
        final Dimensions dimensions = new SimpleDimensions(width, height);
        final Maze maze = generator().generate(dimensions);
        final boolean[][] image = maze.mapify();
        assertTrue(IntStream.range(0, height - 2).anyMatch(cornerY -> IntStream.range(0, width - 2)
                .filter(cornerX -> IntStream.range(cornerY, cornerY + 3).mapToLong(cellY ->
                        IntStream.range(cornerX, cornerX + 3).filter(cellX ->
                                image[cellY][cellX]).count()).sum() > 3).findAny().isPresent()));
    }

    @Test
    @Disabled
    void generate_withDimensions_mazeDoesNotHaveKittyCorners() {
        final int width = 10;
        final int height = 10;
        final Dimensions dimensions = new SimpleDimensions(width, height);
        final Maze maze = generator().generate(dimensions);
        final boolean[][] image = maze.mapify();
        IntStream.range(0, height - 1).forEach(cornerY -> IntStream.range(0, width - 1)
                .forEach(cornerX -> {
                    assertFalse(image[cornerX][cornerY] && image[cornerX + 1][cornerY + 1] &&
                            !image[cornerX + 1][cornerY] && !image[cornerX][cornerY + 1]);
                    assertFalse(!image[cornerX][cornerY] && !image[cornerX + 1][cornerY + 1] &&
                            image[cornerX + 1][cornerY] && image[cornerX][cornerY + 1]);
                }));
    }

    @Test
    void generate_withDimensions_mazeDoesNotHave2x2PathBlocks() {
        final int width = 10;
        final int height = 10;
        final Dimensions dimensions = new SimpleDimensions(width, height);
        final Maze maze = generator().generate(dimensions);
        final boolean[][] image = maze.mapify();
        IntStream.range(0, height - 1).forEach(cornerY -> IntStream.range(0, width - 1)
                .forEach(cornerX -> assertFalse(
                        image[cornerX][cornerY] && image[cornerX + 1][cornerY + 1] &&
                        image[cornerX + 1][cornerY] && image[cornerX][cornerY + 1])));
    }

    @Test
    void generate_withDimensions_mazeDoesNotHave3x3WallBlocks() {
        final int width = 10;
        final int height = 10;
        final Dimensions dimensions = new SimpleDimensions(width, height);
        final Maze maze = generator().generate(dimensions);
        final boolean[][] image = maze.mapify();
        IntStream.range(0, height - 2).forEach(cornerY -> IntStream.range(0, width - 2)
                .forEach(cornerX -> assertNotEquals(9, IntStream.range(cornerY, cornerY + 3)
                        .mapToLong(cellY -> IntStream.range(cornerX, cornerX + 3).filter(cellX ->
                                image[cellY][cellX]).count()).sum())));
    }

    @Test
    void generate_withDimensions_mazeHasEntrancesOnOpposingSides() {
        final int width = 3;
        final int height = 3;
        final Dimensions dimensions = new SimpleDimensions(width, height);
        for (int i = 0; i < 10; i++) {
            final Maze maze = generator().generate(dimensions);
            assertTrue(maze.getStart().getX().equals(maze.getFinish().getX()) ||
                    maze.getStart().getY().equals(maze.getFinish().getY()));
        }
    }
}
