package com.armsnyder.mazesolver.io;

import com.armsnyder.mazesolver.exception.MazeSolverRuntimeException;
import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by asnyder on 2/27/17.
 */
class MazeImageIOTest {

    @BeforeEach
    void setUp() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        new File(new URL("file:temp").getFile()).mkdir();
    }

    @AfterEach
    void tearDown() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        new File(new URL("file:temp").getFile()).delete();
    }

    @Test
    void read_badURL_throws() {
        assertThrows(IOException.class,
                () -> new MazeImageIO(URI.create("file:/a/b/not.png").toURL()).read());
    }

    @Test
    void read_3x3AllBlack_throws() {
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3AllBlack.png")).read());
    }

    @Test
    void read_3x3AllWhite_throws() {
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3AllWhite.png")).read());
    }

    @Test
    void read_3x3CurvedPath_reads() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3CurvedPath.png")).read();
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(0, 1),
                new SimpleCell(1, 1), new SimpleCell(1, 0));
        final Cell expectedStart = new SimpleCell(0, 1);
        final Cell expectedFinish = new SimpleCell(1, 0);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final SimpleMaze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertTrue(Maze.equalsIgnoreDirection(expectedMaze, maze));
    }

    @Test
    void read_3x3HorizontalPath_reads() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3HorizontalPath.png")).read();
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(0, 1),
                new SimpleCell(1, 1), new SimpleCell(2, 1));
        final Cell expectedStart = new SimpleCell(0, 1);
        final Cell expectedFinish = new SimpleCell(2, 1);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final SimpleMaze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertTrue(Maze.equalsIgnoreDirection(expectedMaze, maze));
    }

    @Test
    void read_3x3TooFewEntrances_throws() {
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3TooFewEntrances.png")).read());
    }

    @Test
    void read_3x3TooManyEntrances_throws() {
        assertThrows(MazeSolverRuntimeException.class,
                () -> new MazeImageIO(getResource("images/3x3TooManyEntrances.png")).read());
    }

    @Test
    void read_3x3VerticalPath_reads() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3VerticalPath.png")).read();
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(1, 0),
                new SimpleCell(1, 1), new SimpleCell(1, 2));
        final Cell expectedStart = new SimpleCell(1, 0);
        final Cell expectedFinish = new SimpleCell(1, 2);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final SimpleMaze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertTrue(Maze.equalsIgnoreDirection(expectedMaze, maze));
    }

    @Test
    void read_3x3VerticalPathGrays_reads() throws IOException {
        final Maze maze = new MazeImageIO(getResource("images/3x3VerticalPathGrays.png")).read();
        final Collection<Cell> expectedCells = Arrays.asList(new SimpleCell(1, 0),
                new SimpleCell(1, 1), new SimpleCell(1, 2));
        final Cell expectedStart = new SimpleCell(1, 0);
        final Cell expectedFinish = new SimpleCell(1, 2);
        final Dimensions expectedDimensions = new SimpleDimensions(3, 3);
        final SimpleMaze expectedMaze = new SimpleMaze(expectedCells, expectedStart, expectedFinish,
                expectedDimensions);
        assertTrue(Maze.equalsIgnoreDirection(expectedMaze, maze));
    }

    @Test
    void write_3x3CurvedPath_matchesRead() throws IOException {
        final Maze maze = new SimpleMaze(
                Arrays.asList(new SimpleCell(1, 0), new SimpleCell(1, 1),
                        new SimpleCell(0, 1)),
                new SimpleCell(1, 0), new SimpleCell(0, 1),
                new SimpleDimensions(3, 3));
        final MazeImageIO mazeImage = new MazeImageIO(new URL("file:temp/temp.png"));
        mazeImage.write(maze);
        assertTrue(Maze.equalsIgnoreDirection(
                new MazeImageIO(getResource("images/3x3CurvedPath.png")).read(),
                mazeImage.read()));
    }

    @Test
    void write_3x3HorizontalPath_matchesRead() throws IOException {
        final Maze maze = new SimpleMaze(
                Arrays.asList(new SimpleCell(0, 1), new SimpleCell(1, 1),
                        new SimpleCell(2, 1)),
                new SimpleCell(0, 1), new SimpleCell(2, 1),
                new SimpleDimensions(3, 3));
        final MazeImageIO mazeImage = new MazeImageIO(new URL("file:temp/temp.png"));
        mazeImage.write(maze);
        assertTrue(Maze.equalsIgnoreDirection(
                new MazeImageIO(getResource("images/3x3HorizontalPath.png")).read(),
                mazeImage.read()));
    }

    @Test
    void write_3x3VerticalPath_matchesRead() throws IOException {
        final Maze maze = new SimpleMaze(
                Arrays.asList(new SimpleCell(1, 0), new SimpleCell(1, 1),
                        new SimpleCell(1, 2)),
                new SimpleCell(1, 0), new SimpleCell(1, 2),
                new SimpleDimensions(3, 3));
        final MazeImageIO mazeImage = new MazeImageIO(new URL("file:temp/temp.png"));
        mazeImage.write(maze);
        assertTrue(Maze.equalsIgnoreDirection(
                new MazeImageIO(getResource("images/3x3VerticalPath.png")).read(),
                mazeImage.read()));
    }

    private URL getResource(final String name) {
        return getClass().getClassLoader().getResource(name);
    }

}