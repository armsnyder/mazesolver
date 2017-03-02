package com.armsnyder.mazesolver;

import com.armsnyder.mazesolver.io.MazeImageIO;
import com.armsnyder.mazesolver.io.SolutionImageIO;
import com.armsnyder.mazesolver.solver.BFSSolverStrategy;
import com.armsnyder.mazesolver.solver.SolverStrategy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

/**
 * Solves a maze drawing
 */
@Slf4j
public class SolveMaze {
    private static final String USAGE = "SolveMaze [inputFile] [outputFile]";

    private SolveMaze() {
        // private constructor hides implicit public one
    }

    public static void main(String[] args) {
        final CommandLineParser parser = new DefaultParser();
        final Options options = new Options();
        final HelpFormatter helpFormatter = new HelpFormatter();
        options.addOption(Option.builder("h").longOpt("help").build());
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            helpFormatter.printHelp(USAGE, options);
            System.exit(1);
        }
        if (commandLine.hasOption("h")) {
            helpFormatter.printHelp(USAGE, options);
            System.exit(0);
        }
        URL inputFile = null;
        URL outputFile = null;
        try {
            inputFile = new URL("file:" + commandLine.getArgs()[0]);
            outputFile = new URL("file:" + commandLine.getArgs()[1]);
        } catch (Exception e) {
            helpFormatter.printHelp(USAGE, options);
            System.exit(1);
        }
        final SolverStrategy solverStrategy = new BFSSolverStrategy();
        try {
            new SolutionImageIO(outputFile).write(solverStrategy
                    .solve(new MazeImageIO(inputFile).read()));
        } catch (IOException e) {
            helpFormatter.printHelp(USAGE, options);
            System.exit(1);
        }
    }
}
