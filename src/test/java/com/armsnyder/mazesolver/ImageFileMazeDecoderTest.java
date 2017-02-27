package com.armsnyder.mazesolver;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.ImageFileMazeDecoder;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ImageFileMazeDecoder
 */
class ImageFileMazeDecoderTest {

    private ImageFileMazeDecoder imageFileParser;

    @BeforeEach
    void setUp() {
        imageFileParser = new ImageFileMazeDecoder();
    }

    @Test
    void decodeMaze_badURL_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> imageFileParser.decodeMaze(URI.create("not.real.png").toURL()));
    }

    @Test
    void decodeMaze_3x3AllBlack_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> imageFileParser.decodeMaze(getResource("images/3x3AllBlack.png")));
    }

    @Test
    void decodeMaze_3x3AllWhite_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> imageFileParser.decodeMaze(getResource("images/3x3AllWhite.png")));
    }

    @Test
    void decodeMaze_3x3CurvedPath_decodes() {
        final Maze maze = imageFileParser.decodeMaze(getResource("images/3x3CurvedPath.png"));
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(0, 1),
                new SimpleCell(1, 1), new SimpleCell(1, 0));
        final Cell expectedStart = new SimpleCell(0, 1);
        final Cell expectedFinish = new SimpleCell(1, 0);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final Maze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertMazesAreEqualWithSwappableEntrances(expectedMaze, maze);
    }

    @Test
    void decodeMaze_3x3HorizontalPath_decodes() {
        final Maze maze = imageFileParser.decodeMaze(getResource("images/3x3HorizontalPath.png"));
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(0, 1),
                new SimpleCell(1, 1), new SimpleCell(2, 1));
        final Cell expectedStart = new SimpleCell(0, 1);
        final Cell expectedFinish = new SimpleCell(2, 1);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final Maze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertMazesAreEqualWithSwappableEntrances(expectedMaze, maze);
    }

    @Test
    void decodeMaze_3x3TooFewEntrances_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> imageFileParser.decodeMaze(getResource("images/3x3TooFewEntrances.png")));
    }

    @Test
    void decodeMaze_3x3TooManyEntrances_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> imageFileParser.decodeMaze(getResource("images/3x3TooManyEntrances.png")));
    }

    @Test
    void decodeMaze_3x3VerticalPath_decodes() {
        final Maze maze = imageFileParser.decodeMaze(getResource("images/3x3VerticalPath.png"));
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(1, 0),
                new SimpleCell(1, 1), new SimpleCell(1, 2));
        final Cell expectedStart = new SimpleCell(1, 0);
        final Cell expectedFinish = new SimpleCell(1, 2);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final Maze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertMazesAreEqualWithSwappableEntrances(expectedMaze, maze);
    }

    @Test
    void decodeMaze_3x3VerticalPathGrays_decodes() {
        final Maze maze = imageFileParser.decodeMaze(
                getResource("images/3x3VerticalPathGrays.png"));
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(1, 0),
                new SimpleCell(1, 1), new SimpleCell(1, 2));
        final Cell expectedStart = new SimpleCell(1, 0);
        final Cell expectedFinish = new SimpleCell(1, 2);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final Maze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertMazesAreEqualWithSwappableEntrances(expectedMaze, maze);
    }

    private URL getResource(final String name) {
        return getClass().getClassLoader().getResource(name);
    }

    private void assertMazesAreEqualWithSwappableEntrances(Maze a, Maze b) {
        assertTrue(a.getCells().containsAll(b.getCells()));
        assertTrue(b.getCells().containsAll(a.getCells()));
        assertEquals(a.getDimensions(), b.getDimensions());
        assertTrue(Arrays.asList(a.getStart(), a.getFinish()).contains(b.getStart()));
        assertTrue(Arrays.asList(a.getStart(), a.getFinish()).contains(b.getFinish()));
        final List<Cell> entrances = Arrays.asList(a.getStart(), a.getFinish());
        assertTrue(entrances.contains(b.getStart()));
        assertTrue(entrances.contains(b.getFinish()));
        assertNotEquals(entrances.get(0), entrances.get(1));
        assertNotEquals(b.getStart(), b.getFinish());
    }
}