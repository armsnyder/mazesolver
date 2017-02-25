package com.armsnyder.mazesolver;

import com.armsnyder.mazesolver.interfaces.Cell;
import com.armsnyder.mazesolver.interfaces.Dimensions;
import com.armsnyder.mazesolver.interfaces.Maze;
import com.armsnyder.mazesolver.interfaces.Parser;
import com.armsnyder.mazesolver.interfaces.Solution;
import com.armsnyder.mazesolver.simple.SimpleCell;
import com.armsnyder.mazesolver.simple.SimpleDimensions;
import com.armsnyder.mazesolver.simple.SimpleMaze;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by asnyder on 2/24/17.
 */
@Slf4j
public class ImageFileParser implements Parser<URL> {

    private BufferedImage buffer;

    @Override
    public Maze decodeMaze(final URL encodedMaze) {
        try {
            buffer = ImageIO.read(encodedMaze);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while reading image file", e);
        }

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
            throw new IllegalArgumentException("Maze has fewer than 2 entrances");
        }
        if (entrances.size() > 2) {
            throw new IllegalArgumentException("Maze has greater than 2 entrances");
        }
        return new SimpleMaze(cells, entrances.get(0), entrances.get(1), dimensions);
    }

    @Override
    public Solution decodeSolution(final URL encodedSolution) {
        return null;
    }

    @Override
    public URL encode(final Maze maze) {
        return null;
    }

    @Override
    public URL encode(final Solution solution) {
        return null;
    }

    private boolean isEdgeCell(final Cell cell, final Dimensions dimensions) {
        final int x = cell.getX().intValue();
        final int y = cell.getY().intValue();
        return x == 0 || y == 0 || x == dimensions.getWidth().intValue() - 1 ||
                y == dimensions.getHeight().intValue() - 1;
    }
}
