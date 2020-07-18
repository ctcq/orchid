package org.ctcq.orchid.model.graph.exceptions;

import org.ctcq.orchid.model.graph.NodeGraph;

/**
 * Gets thrown when trying to add a {@link Node} to the {@link NodeGraph}
 * which it already contains (by name or by object identity). 
 */
public class NodeAlreadyExistsException extends NodeException {

    /**
     *  Generated serial
     */
    private static final long serialVersionUID = 4685177210890197741L;

}