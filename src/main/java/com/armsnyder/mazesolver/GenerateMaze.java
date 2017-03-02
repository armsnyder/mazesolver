package com.armsnyder.mazesolver;

import com.armsnyder.mazesolver.generator.BacktrackGenerator;
import com.armsnyder.mazesolver.io.MazeImageIO;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleDimensions;

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
 * Created by asnyder on 3/1/17.
 */
@Slf4j
public class GenerateMaze {
    private static final String USAGE = "GenerateMaze [width] [height] [outputFile]";

    private GenerateMaze() {
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
        int width = 0;
        int height = 0;
        URL outputFile = null;
        try {
            width = Integer.parseInt(commandLine.getArgs()[0]);
            height = Integer.parseInt(commandLine.getArgs()[1]);
            outputFile = new URL("file:" + commandLine.getArgs()[2]);
        } catch (Exception e) {
            helpFormatter.printHelp(USAGE, options);
            System.exit(1);
        }
        final Maze maze = new BacktrackGenerator().generate(new SimpleDimensions(width, height));
        try {
            new MazeImageIO(outputFile).write(maze);
        } catch (IOException e) {
            helpFormatter.printHelp(USAGE, options);
            System.exit(1);
        }
    }
}
