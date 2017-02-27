package com.armsnyder.mazesolver.solver;

/**
 * Created by asnyder on 2/26/17.
 */
class BFSSolverStrategyTest extends SolverStrategyTest {
    @Override
    SolverStrategy strategy() {
        return new BFSSolverStrategy();
    }
}