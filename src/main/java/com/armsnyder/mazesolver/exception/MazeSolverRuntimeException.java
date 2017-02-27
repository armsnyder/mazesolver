package com.armsnyder.mazesolver.exception;

/**
 * Maze Solver - specific version of Java's RuntimeException
 */
public class MazeSolverRuntimeException extends RuntimeException {
    public MazeSolverRuntimeException() {
        super();
    }

    public MazeSolverRuntimeException(final String message) {
        super(message);
    }

    public MazeSolverRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MazeSolverRuntimeException(final Throwable cause) {
        super(cause);
    }

    protected MazeSolverRuntimeException(final String message, final Throwable cause,
                                         final boolean enableSuppression,
                                         final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
