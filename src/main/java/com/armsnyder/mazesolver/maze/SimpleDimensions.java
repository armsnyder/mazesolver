package com.armsnyder.mazesolver.maze;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple implementation of Dimensions that exposes setters
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDimensions implements Dimensions {
    private Number width;
    private Number height;
}
