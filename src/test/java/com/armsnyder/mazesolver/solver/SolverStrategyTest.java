package com.armsnyder.mazesolver.solver;

import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by asnyder on 2/26/17.
 */
abstract class SolverStrategyTest {

    abstract SolverStrategy strategy();

    @Test
    void solve_nullCells_throws() {
        assertThrows(IllegalArgumentException.class, () -> strategy().solve(new SimpleMaze(
                        null,
                        new SimpleCell(1, 1),
                        new SimpleCell(2, 1),
                        new SimpleDimensions(3, 3))));
    }

    @Test
    void solve_nullStartOrFinish_throws() {
        assertThrows(IllegalArgumentException.class, () -> strategy().solve(new SimpleMaze(
                Arrays.asList(new SimpleCell(1, 1), new SimpleCell(2, 1)),
                null,
                new SimpleCell(2, 1),
                new SimpleDimensions(3, 3))));
        assertThrows(IllegalArgumentException.class, () -> strategy().solve(new SimpleMaze(
                Arrays.asList(new SimpleCell(1, 1), new SimpleCell(2, 1)),
                new SimpleCell(1, 1),
                null,
                new SimpleDimensions(3, 3))));
    }

    @Test
    void solve_emptyCells_throws() {
        assertThrows(IllegalArgumentException.class, () -> strategy().solve(new SimpleMaze(
                Collections.emptyList(),
                new SimpleCell(1, 1),
                new SimpleCell(2, 1),
                new SimpleDimensions(3, 3))));
    }

    @Test
    void solve_startOrFinishNotInCells_throws() {
        assertThrows(IllegalArgumentException.class, () -> strategy().solve(new SimpleMaze(
                Arrays.asList(new SimpleCell(2, 1), new SimpleCell(2, 2)),
                new SimpleCell(1, 1),
                new SimpleCell(2, 1),
                new SimpleDimensions(3, 3))));
    }

    @Test
    void solve_startIsFinish_isAlreadySolved() {
        final Maze maze = new SimpleMaze(
                Arrays.asList(new SimpleCell(1, 1), new SimpleCell(2, 1)),
                new SimpleCell(1, 1),
                new SimpleCell(1, 1),
                new SimpleDimensions(3, 3));
        final Collection<Cell> expectedSolutionCells =
                Collections.singleton(new SimpleCell(1, 1));
        final Solution solution = strategy().solve(maze);
        assertEquals(maze, solution.getMaze());
        assertTrue(solution.getSolution().containsAll(expectedSolutionCells));
        assertTrue(expectedSolutionCells.containsAll(solution.getSolution()));
    }

    @Test
    void solve_simpleMaze_solutionIsCorrect() {
        final Collection<Cell> cells = Arrays.asList(
                new SimpleCell(1, 1), new SimpleCell(2, 1));
        final Maze maze = new SimpleMaze(
                cells,
                new SimpleCell(1, 1),
                new SimpleCell(2, 1),
                new SimpleDimensions(3, 3));
        final Solution solution = strategy().solve(maze);
        assertEquals(maze, solution.getMaze());
        assertTrue(solution.getSolution().containsAll(cells));
        assertTrue(cells.containsAll(solution.getSolution()));
    }

    @Test
    void solve_smallMazeWithDeadEnds_solutionIsCorrect() {
        final Maze maze = new SimpleMaze(
                Arrays.asList(
                        new SimpleCell(0, 0), new SimpleCell(0, 1),
                        new SimpleCell(1, 0), new SimpleCell(2, 0),
                        new SimpleCell(3, 0)),
                new SimpleCell(0, 0),
                new SimpleCell(2, 0),
                new SimpleDimensions(3, 3));
        final Collection<Cell> expectedSolutionCells = Arrays.asList(
                new SimpleCell(0, 0), new SimpleCell(1, 0), new SimpleCell(2, 0)
        );
        final Solution solution = strategy().solve(maze);
        assertEquals(maze, solution.getMaze());
        assertTrue(solution.getSolution().containsAll(expectedSolutionCells));
        assertTrue(expectedSolutionCells.containsAll(solution.getSolution()));
    }

    @Test
    void solve_unsolvable_solutionIsEmpty() {
        final Maze maze = new SimpleMaze(
                Arrays.asList(
                        new SimpleCell(0, 0), new SimpleCell(0, 1),
                        new SimpleCell(1, 0), new SimpleCell(2, 0),
                        new SimpleCell(3, 0), new SimpleCell(3, 2)),
                new SimpleCell(0, 0),
                new SimpleCell(3, 2),
                new SimpleDimensions(3, 3));
        final Solution solution = strategy().solve(maze);
        assertEquals(maze, solution.getMaze());
        assertEquals(0, solution.getSolution().size());
    }
}
