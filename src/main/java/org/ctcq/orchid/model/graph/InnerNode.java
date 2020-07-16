package org.ctcq.orchid.model.graph;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Superclass for all nodes that are not an {@link EdgeNode} instance.
 */
@NodeEntity
public abstract class InnerNode extends Node {
    // Purely structural class
}