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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Cell)) {
            return false;
        } else {
            Cell other = (Cell) o;
            return getX().equals(other.getX()) &&
                    getY().equals(other.getY());
        }
    }

    @Override
    public int hashCode() {
        byte result = 1;
        Number myX = this.getX();
        int result1 = result * 59 + (myX == null ? 0 : myX.hashCode());
        Number myY = this.getY();
        result1 = result1 * 59 + (myY == null ? 0 : myY.hashCode());
        return result1;
    }
}
