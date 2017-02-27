package com.armsnyder.mazesolver.io;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;
import com.armsnyder.mazesolver.solver.SimpleSolution;
import com.armsnyder.mazesolver.solver.Solution;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by asnyder on 2/27/17.
 */
class SolutionImageIOTest {

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
    void read_5x5MazeSolution_matchesMaze() throws IOException {
        final Maze maze = new MazeImageIO(getClass().getClassLoader()
                .getResource("images/5x5Maze.png")).read();
        final Solution solution = new SolutionImageIO(getClass().getClassLoader()
                .getResource("images/5x5MazeSolution.png")).read();
        assertTrue(Maze.equalsIgnoreDirection(maze, solution.getMaze()));
    }

    @Test
    void read_5x5MazeSolutionBlurry_matchesMaze() throws IOException {
        final Maze maze = new MazeImageIO(getClass().getClassLoader()
                .getResource("images/5x5Maze.png")).read();
        final Solution solution = new SolutionImageIO(getClass().getClassLoader()
                .getResource("images/5x5MazeSolutionBlurry.png")).read();
        assertTrue(Maze.equalsIgnoreDirection(maze, solution.getMaze()));
    }

    @Test
    void read_5x5MazeSolution_hasCorrectSolution() throws IOException {
        final Solution solution = new SolutionImageIO(getClass().getClassLoader()
                .getResource("images/5x5MazeSolution.png")).read();
        final Collection<Cell> expectedSolution = Arrays.asList(new SimpleCell(0, 1),
                new SimpleCell(1, 1), new SimpleCell(1, 2), new SimpleCell(1, 3),
                new SimpleCell(2, 3), new SimpleCell(3, 3), new SimpleCell(3, 4));
        assertTrue(expectedSolution.containsAll(solution.getSolution()));
        assertTrue(solution.getSolution().containsAll(expectedSolution));
    }

    @Test
    void read_5x5MazeSolutionBlurry_hasCorrectSolution() throws IOException {
        final Solution solution = new SolutionImageIO(getClass().getClassLoader()
                .getResource("images/5x5MazeSolutionBlurry.png")).read();
        final Collection<Cell> expectedSolution = Arrays.asList(new SimpleCell(0, 1),
                new SimpleCell(1, 1), new SimpleCell(1, 2), new SimpleCell(1, 3),
                new SimpleCell(2, 3), new SimpleCell(3, 3), new SimpleCell(3, 4));
        assertTrue(expectedSolution.containsAll(solution.getSolution()));
        assertTrue(solution.getSolution().containsAll(expectedSolution));
    }

    @Test
    void write_5x5MazeSolution_matchesRead() throws IOException {
        final Collection<Cell> solutionCells = Arrays.asList(new SimpleCell(0, 1),
                new SimpleCell(1, 1), new SimpleCell(1, 2), new SimpleCell(1, 3),
                new SimpleCell(2, 3), new SimpleCell(3, 3), new SimpleCell(3, 4));
        final Collection<Cell> mazeCells = new ArrayList<>(solutionCells);
        mazeCells.addAll(Arrays.asList(new SimpleCell(2, 1), new SimpleCell(3, 2)));
        final Maze maze = new SimpleMaze(
                mazeCells,
                new SimpleCell(0, 1), new SimpleCell(3, 4),
                new SimpleDimensions(5, 5));
        final SolutionImageIO solutionImage = new SolutionImageIO(new URL("file:temp/temp.png"));
        solutionImage.write(new SimpleSolution(maze, solutionCells));
        final Solution expectedSolution = new SolutionImageIO(getClass().getClassLoader()
                .getResource("images/5x5MazeSolution.png")).read();
        final Solution actualSolution = solutionImage.read();
        assertTrue(Maze.equalsIgnoreDirection(expectedSolution.getMaze(),
                actualSolution.getMaze()));
        assertTrue(expectedSolution.getSolution().containsAll(actualSolution.getSolution()));
        assertTrue(actualSolution.getSolution().containsAll(expectedSolution.getSolution()));
    }
}
