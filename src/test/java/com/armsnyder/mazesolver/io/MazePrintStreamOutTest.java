package com.armsnyder.mazesolver.io;

import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by asnyder on 3/1/17.
 */
class MazePrintStreamOutTest {

    @Test
    void write_3x3CurvedPath_printsMazeCorrectly() throws IOException {
        final Maze maze = new SimpleMaze(
                Arrays.asList(new SimpleCell(1, 0), new SimpleCell(1, 1),
                        new SimpleCell(0, 1)),
                new SimpleCell(1, 0), new SimpleCell(0, 1),
                new SimpleDimensions(3, 3));
        final OutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(outputStream);
        new MazePrintStreamOut(printStream).write(maze);
        assertEquals("▮•▮\n••▮\n▮▮▮\n", outputStream.toString());
    }

    @Test
    void read_any_throws() {
        assertThrows(UnsupportedOperationException.class, () ->
                new MazePrintStreamOut(new PrintStream(new ByteArrayOutputStream())).read());
    }
}
