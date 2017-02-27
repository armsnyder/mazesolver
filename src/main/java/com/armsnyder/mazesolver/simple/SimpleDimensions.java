package com.armsnyder.mazesolver.simple;

import com.armsnyder.mazesolver.interfaces.Dimensions;

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
