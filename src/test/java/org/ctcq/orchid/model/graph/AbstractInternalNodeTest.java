package org.ctcq.orchid.model.graph;

import org.ctcq.orchid.driver.InputDriver;
import org.ctcq.orchid.driver.standard.in.StaticStringInputDriver;
import org.ctcq.orchid.driver.standard.out.LastRecievedOutputDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Superclass for all test classes using the {@link StaticStringInputDriver} as an {@link InputDriver}
 * and the {@link LastRecievedOutputDriver} as output.
 * 
 * Does only instantiate the aforementioned drivers.
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractInternalNodeTest {

    protected StaticStringInputDriver staticInputDriver;
    protected LastRecievedOutputDriver lastReceivedDriver;

    @BeforeAll void initDrivers() {
        staticInputDriver = new StaticStringInputDriver(null, null, null);
        lastReceivedDriver = new LastRecievedOutputDriver(null, null, null);
    }
}