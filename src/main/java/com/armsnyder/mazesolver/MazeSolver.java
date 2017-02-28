package com.armsnyder.mazesolver;

import com.armsnyder.mazesolver.io.MazeImageIO;
import com.armsnyder.mazesolver.io.SolutionImageIO;
import com.armsnyder.mazesolver.solver.BFSSolverStrategy;
import com.armsnyder.mazesolver.solver.SolverStrategy;

import java.io.IOException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

/**
 * Solves a maze drawing
 */
@Slf4j
public class MazeSolver {
    private MazeSolver() {
        // private constructor hides implicit public one
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Two arguments required (file in, file out)");
        }
        final SolverStrategy solverStrategy = new BFSSolverStrategy();
        try {
            new SolutionImageIO(new URL("file:" + args[1])).write(solverStrategy
                    .solve(new MazeImageIO(new URL("file:" + args[0])).read()));
        } catch (IOException e) {
            log.error("Failed due to a file I/O error", e);
        }
    }
}
