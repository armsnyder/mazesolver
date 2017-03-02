package com.armsnyder.mazesolver.io;

import com.armsnyder.mazesolver.maze.Maze;

import java.io.IOException;
import java.io.PrintStream;

import lombok.RequiredArgsConstructor;


/**
 * Prints a maze to a console
 */
@RequiredArgsConstructor
public class MazePrintStreamOut implements GenericIO<Maze> {

    private final PrintStream printStream;

    @Override
    public Maze read() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(final Maze maze) throws IOException {
        for (final boolean[] row : maze.mapify()) {
            StringBuilder sb = new StringBuilder();
            for (final boolean cell : row) {
                sb.append(cell ? '•' : '▮');
            }
            printStream.println(sb.toString());
        }
    }
}
