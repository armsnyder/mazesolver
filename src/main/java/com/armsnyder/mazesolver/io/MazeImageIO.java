package com.armsnyder.mazesolver.io;

import com.armsnyder.mazesolver.exception.MazeSolverRuntimeException;
import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import lombok.RequiredArgsConstructor;

/**
 * Encodes and decodes an image file into a maze, where white pixels represent pathways and
 * black pixels represent walls
 */
@RequiredArgsConstructor
public class MazeImageIO implements GenericIO<Maze> {

    private final URL url;

    @Override
    public Maze read() throws IOException {
        final BufferedImage buffer = ImageIO.read(url);
        final Dimensions dimensions = new SimpleDimensions(buffer.getWidth(), buffer.getHeight());
        final Set<Cell> cells = new HashSet<>();
        final List<Cell> entrances = new ArrayList<>();
        IntStream.range(0, dimensions.getWidth().intValue()).forEach(x ->
                IntStream.range(0, dimensions.getHeight().intValue()).forEach(y -> {
                    if (buffer.getData().getSample(x, y, 0) >> 7 == 1) {
                        final Cell cell = new SimpleCell(x, y);
                        cells.add(cell);
                        if (isEdgeCell(cell, dimensions)) {
                            entrances.add(cell);
                        }
                    }
                }));
        if (entrances.size() < 2) {
            throw new MazeSolverRuntimeException("Maze has fewer than 2 entrances");
        }
        if (entrances.size() > 2) {
            throw new MazeSolverRuntimeException("Maze has greater than 2 entrances");
        }
        return new SimpleMaze(cells, entrances.get(0), entrances.get(1), dimensions);
    }

    @Override
    public void write(final Maze o) {

    }

    private boolean isEdgeCell(final Cell cell, final Dimensions dimensions) {
        final int x = cell.getX().intValue();
        final int y = cell.getY().intValue();
        return x == 0 || y == 0 || x == dimensions.getWidth().intValue() - 1 ||
                y == dimensions.getHeight().intValue() - 1;
    }
}
