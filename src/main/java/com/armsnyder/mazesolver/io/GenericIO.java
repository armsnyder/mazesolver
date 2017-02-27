package com.armsnyder.mazesolver.io;

import java.io.IOException;

/**
 * Object that can read and write an object to and from a resource
 */
public interface GenericIO<T> {
    T read() throws IOException;
    void write(T o) throws IOException;
}
