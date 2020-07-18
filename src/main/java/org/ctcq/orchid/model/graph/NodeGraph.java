package org.ctcq.orchid.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.ctcq.orchid.model.graph.exceptions.NodeAlreadyExistsException;
import org.ctcq.orchid.model.graph.exceptions.NodeNotFoundException;
import org.ctcq.orchid.model.graph.exceptions.NodesNotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeGraph implements Serializable {

    /**
     *  Generated serial
     */
    private static final long serialVersionUID = -2646340619728831460L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private List<Node> nodes;

    public NodeGraph() {
        nodes = new ArrayList<>();
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Check whether a node is contained in the graph. Nodes with the
     * same name are counted as identical.
     * @param node The node to check if it exists in the Graph.
     * @return Whether the node exists in the graph.
     */
    public boolean contains(final Node node) {
        return nodes.stream().map((Node n) -> n.getName())
            .collect(Collectors.toList()).contains(node.getName());
    }

    public void add(final Node node) throws NodeAlreadyExistsException {
        if (this.contains(node)) {
            throw new NodeAlreadyExistsException();
        }

        nodes.add(node);
        logger.info(String.format("Added node %s.", node));
    }

    public void remove(final Node node) throws NodeNotFoundException {
        if (!nodes.contains(node)) {
            throw new NodeNotFoundException();
        }

        nodes.remove(node);
        logger.info(String.format("Removed node %s.", node));
    }

    /**
     * Connect two nodes.
     * 
     * @param node1 The source node.
     * @param node2 The target node.
     * @throws NodeNotFoundException If one of the given nodes is not found in the
     *                               graph.
     */
    public void connect(final Node node1, final Node node2) throws NodeNotFoundException {
        if (!nodes.contains(node1) || !nodes.contains(node2)) {
            throw new NodeNotFoundException();
        }

        // Add node to nodesOut
        final Set<Node> nodesOut = node1.getNodesOut();
        nodesOut.add(node2);
        node1.setNodesOut(nodesOut);
        logger.info(String.format("Connected nodes %s and %s.", node1, node2));
    }

    /**
     * Disconnects to nodes inside the graph.
     * @param node1 The source node.
     * @param node2 The target node.
     * @throws NodesNotConnectedException If the nodes are not directly connected.
     * @throws NodeNotFoundException If one of the given nodes was not found.
     */
    public void disconnect(final Node node1, final Node node2)
            throws NodesNotConnectedException, NodeNotFoundException {
        if (!nodes.contains(node1) || !nodes.contains(node2)) {
            throw new NodeNotFoundException();
        }

        // remove node from nodesOut
        final Set<Node> nodesOut = node1.getNodesOut();

        if (!nodesOut.contains(node2)) {
            throw new NodesNotConnectedException();
        }

        nodesOut.remove(node2);
        node1.setNodesOut(nodesOut);
        logger.info(String.format("Disconnected nodes %s from %s.", node1, node2));
    }

    public List<String> getNodeNames() {
        return nodes.stream().map(
            (Node node) -> node.getName())
                .collect(Collectors.toList());
    }
}