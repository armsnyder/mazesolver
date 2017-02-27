package com.armsnyder.mazesolver.simple;

import com.armsnyder.mazesolver.interfaces.Cell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple implementation of Cell that exposes setters
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCell implements Cell {
    private Number x;
    private Number y;
}
