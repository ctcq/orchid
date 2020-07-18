package org.ctcq.orchid.model.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.ctcq.orchid.model.graph.exceptions.NodeAlreadyExistsException;
import org.ctcq.orchid.model.graph.exceptions.NodeNotFoundException;
import org.ctcq.orchid.model.graph.exceptions.NodesNotConnectedException;
import org.junit.jupiter.api.Test;

class NodeGraphTest {

    @Test void isEmptyTest() {
        final NodeGraph graph = new NodeGraph();
        assertTrue(graph.isEmpty());

        try {
            graph.add(new InNode("node", null));
        } catch (final NodeAlreadyExistsException e) {
            fail();
        }

        assertFalse(graph.isEmpty());

    }

    @Test void addNodeTest() {
        final NodeGraph graph = new NodeGraph();
        final Node node = new InNode(null, null);
        try {
            graph.add(node);
        } catch (final NodeAlreadyExistsException e) {
            fail();
        }
        assertTrue(graph.contains(node));
    }

    @Test void addDuplicateTest() {
        final NodeGraph graph = new NodeGraph();
        final Node node1 = new InNode("Node", null);
        final Node node2 = new InNode("Node", null);
        try {
            graph.add(node1);
        } catch (final NodeAlreadyExistsException e) {
            fail();
        }

        try {
            graph.add(node2);
            fail();
        } catch (final NodeAlreadyExistsException e) {
            assertTrue(true);
        }
    }

    @Test
    void removeNodeTest() {
        final NodeGraph graph = new NodeGraph();
        final Node node = new InNode(null, null);
        try {
            graph.add(node);
            assertTrue(graph.contains(node));
            graph.remove(node);
            assertFalse(graph.contains(node));
        } catch (NodeAlreadyExistsException | NodeNotFoundException e) {
            fail();
        }
    }

    @Test
    void removeNotExistingNodeTest() {
        final NodeGraph graph = new NodeGraph();
        final Node node = new InNode(null, null);
        try {
            graph.remove(node);
            fail();
        } catch (final NodeNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void testConnectNodesTest() {
        final NodeGraph graph = new NodeGraph();
        final Node node1 = new InNode("node1", null);
        final Node node2 = new InNode("node2", null);

        try {
            graph.add(node1);
            graph.add(node2);
        } catch (final NodeAlreadyExistsException e) {
            fail();
        }

        try {
            graph.connect(node1, node2);
        } catch (final NodeNotFoundException e) {
            fail();
        }

        assertTrue(node1.getNodesOut().contains(node2));
    }

    @Test
    void testConnectNodesNotExisting() {
        final NodeGraph graph = new NodeGraph();
        final Node node1 = new InNode("node1", null);
        final Node node2 = new InNode("node2", null);
        try {
            graph.add(node1);
        } catch (final NodeAlreadyExistsException e) {
            fail();
        }

        // Try node1 -> node2
        try {
            graph.connect(node1, node2);
            fail();
        } catch (final NodeNotFoundException e) {
            // Success
        }

        // Try node2 -> node1
        try {
            graph.connect(node2, node1);
            fail();
        } catch (final NodeNotFoundException e) {
            // Success
        }
    }

    @Test void disconnectTest() {
        final NodeGraph graph = new NodeGraph();
        final Node node1 = new InNode("node1", null);
        final Node node2 = new InNode("node2", null);

        try {
            graph.add(node1);
            graph.add(node2);
        } catch (final NodeAlreadyExistsException e) {
            fail();
        }

        // Disconnect on unconnected nodes should throw exception
        try {
            graph.disconnect(node1, node2);
            fail();
        } catch (NodesNotConnectedException e) {
            // Success
        } catch (NodeNotFoundException e) {
            fail();
        }

        // Now connect
        try {
            graph.connect(node1, node2);
        } catch (NodeNotFoundException e) {
            fail();
        }

        // Now disconnect
        try {
            graph.disconnect(node1, node2);
        } catch (NodesNotConnectedException | NodeNotFoundException e) {
            fail();
        }

        // Check if nodes are really disconnected
        assertFalse(node1.getNodesOut().contains(node2));

    }

    @Test
    void getGraphNodesTest() {
        final NodeGraph graph = new NodeGraph();
        final Node node1 = new InNode("node1", null);
        final Node node2 = new InNode("node2", null);

        try {
            graph.add(node1);
            graph.add(node2);
        } catch (final NodeAlreadyExistsException e) {
            fail();
        }

        final List<String> nodeNames = graph.getNodeNames();
        assertEquals(nodeNames.size(), 2);
        assertTrue(nodeNames.contains(node1.getName()));
        assertTrue(nodeNames.contains(node2.getName()));
    }

}