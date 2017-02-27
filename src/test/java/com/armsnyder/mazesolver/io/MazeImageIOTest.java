package com.armsnyder.mazesolver.io;

import com.armsnyder.mazesolver.exception.MazeSolverRuntimeException;
import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by asnyder on 2/27/17.
 */
class MazeImageIOTest {

    @Test
    void decodeMaze_badURL_throws() {
        assertThrows(IOException.class,
                () -> new MazeImageIO(URI.create("file:/a/b/not.png").toURL()).read());
    }

    @Test
    void decodeMaze_3x3AllBlack_throws() {
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3AllBlack.png")).read());
    }

    @Test
    void decodeMaze_3x3AllWhite_throws() {
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3AllWhite.png")).read());
    }

    @Test
    void decodeMaze_3x3CurvedPath_decodes() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3CurvedPath.png")).read();
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
    void decodeMaze_3x3HorizontalPath_decodes() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3HorizontalPath.png")).read();
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
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3TooFewEntrances.png")).read());
    }

    @Test
    void decodeMaze_3x3TooManyEntrances_throws() {
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3TooManyEntrances.png")).read());
    }

    @Test
    void decodeMaze_3x3VerticalPath_decodes() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3VerticalPath.png")).read();
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
    void decodeMaze_3x3VerticalPathGrays_decodes() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3VerticalPathGrays.png")).read();
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