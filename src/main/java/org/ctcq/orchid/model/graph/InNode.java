package org.ctcq.orchid.model.graph;

import org.ctcq.orchid.driver.EndpointDriver;
import org.ctcq.orchid.driver.InputDriver;
import org.ctcq.orchid.model.graph.exceptions.InvalidDriverException;

public class InNode extends EdgeNode {

    /**
     *  Generated serial
     */
    private static final long serialVersionUID = -5681231158160058043L;
    protected InputDriver endpointDriver;

    /**
     * Instantiate the InNode with a specific authentication driver and endpointDriver.
     * @param auth The authorization driver used to access a protected endpoint.
     * @param endpointDriver The driver used to communicate with the endpoint.
     * @throws InvalidDriverException 
     */
    public <T extends EndpointDriver & InputDriver> InNode(final String name, final T endpointDriver) {
        super(name, endpointDriver);
    }
}