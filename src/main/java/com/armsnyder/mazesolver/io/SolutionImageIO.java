package com.armsnyder.mazesolver.io;

import com.armsnyder.mazesolver.exception.MazeSolverRuntimeException;
import com.armsnyder.mazesolver.maze.Cell;
import com.armsnyder.mazesolver.maze.Dimensions;
import com.armsnyder.mazesolver.maze.Maze;
import com.armsnyder.mazesolver.maze.SimpleCell;
import com.armsnyder.mazesolver.maze.SimpleDimensions;
import com.armsnyder.mazesolver.maze.SimpleMaze;
import com.armsnyder.mazesolver.solver.SimpleSolution;
import com.armsnyder.mazesolver.solver.Solution;

import java.awt.image.BufferedImage;
import java.io.File;
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
public class SolutionImageIO implements GenericIO<Solution> {

    private final URL url;

    @Override
    public Solution read() throws IOException {
        final BufferedImage buffer = ImageIO.read(url);
        final Dimensions dimensions = new SimpleDimensions(buffer.getWidth(), buffer.getHeight());
        final Set<Cell> cells = new HashSet<>();
        final List<Cell> entrances = new ArrayList<>();
        final Set<Cell> solutionCells = new HashSet<>();
        final int[] pixelBuffer = new int[4];
        IntStream.range(0, dimensions.getWidth().intValue()).forEach(x ->
                IntStream.range(0, dimensions.getHeight().intValue()).forEach(y -> {
                    buffer.getData().getPixel(x, y, pixelBuffer);
                    final PixelColor pixelColor = PixelColor.classifyPixel(pixelBuffer);
                    if (pixelColor != PixelColor.BLACK) {
                        final Cell cell = new SimpleCell(x, y);
                        cells.add(cell);
                        if (isEdgeCell(cell, dimensions)) {
                            entrances.add(cell);
                        }
                        if (pixelColor == PixelColor.RED) {
                            solutionCells.add(cell);
                        }
                    }
                }));
        if (entrances.size() < 2) {
            throw new MazeSolverRuntimeException("Maze has fewer than 2 entrances");
        }
        if (entrances.size() > 2) {
            throw new MazeSolverRuntimeException("Maze has greater than 2 entrances");
        }
        return new SimpleSolution(
                new SimpleMaze(cells, entrances.get(0), entrances.get(1), dimensions),
                solutionCells);
    }

    @Override
    public void write(final Solution solution) throws IOException {
        final Maze maze = solution.getMaze();
        final int width = maze.getDimensions().getWidth().intValue();
        final int height = maze.getDimensions().getHeight().intValue();
        final BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        maze.getCells().forEach(cell ->
                buffer.setRGB(cell.getX().intValue(), cell.getY().intValue(), 0xFFFFFF));
        solution.getSolution().forEach(cell ->
                buffer.setRGB(cell.getX().intValue(), cell.getY().intValue(), 0xFF0000));
        ImageIO.write(buffer, "png", new File(url.getFile()));
    }

    private boolean isEdgeCell(final Cell cell, final Dimensions dimensions) {
        final int x = cell.getX().intValue();
        final int y = cell.getY().intValue();
        return x == 0 || y == 0 || x == dimensions.getWidth().intValue() - 1 ||
                y == dimensions.getHeight().intValue() - 1;
    }

    private enum PixelColor {
        BLACK, WHITE, RED;

        private static PixelColor classifyPixel(final int[] pixel) {
            final boolean isRedOn = isPixelOn(pixel[0]);
            final boolean isGreenOn = isPixelOn(pixel[1]);
            final boolean isBlueOn = isPixelOn(pixel[2]);
            if (isRedOn && !isGreenOn && !isBlueOn) {
                return RED;
            }
            if (isRedOn && isGreenOn && isBlueOn) {
                return WHITE;
            }
            return BLACK;
        }

        private static boolean isPixelOn(final int pixel) {
            return pixel >> 7 == 1;
        }
    }
}
