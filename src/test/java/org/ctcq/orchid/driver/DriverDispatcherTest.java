package org.ctcq.orchid.driver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.ctcq.orchid.driver.standard.in.StaticStringInputDriver;
import org.ctcq.orchid.driver.standard.out.LastRecievedOutputDriver;
import org.ctcq.orchid.model.graph.InNode;
import org.ctcq.orchid.model.graph.OutNode;
import org.junit.jupiter.api.Test;

public class DriverDispatcherTest {

    @Test void testDispatch() {
        final StaticStringInputDriver inDriver = new StaticStringInputDriver(null, null, null);
        final LastRecievedOutputDriver outDriver = new LastRecievedOutputDriver(null, null, null);

        // Create nodes and connect them
        final InNode inNode = new InNode("in", inDriver);
        final OutNode outNode = new OutNode("in", outDriver);
        inNode.setNodesOut(Set.of(outNode));

        final DriverDispatcher dispatcher = new DriverDispatcher(0);

        // Last received is null
        assertNull(outDriver.getLastReceived());

        // Dispatch message from inDriver
        dispatcher.register(inNode);
        dispatcher.dispatch();

        // Check if driver received message
        assertNotNull(outDriver.getLastReceived());
        assertEquals(outDriver.getLastReceived().getContent(), inDriver.getResponseText());
    
        // Unregister driver and try to send different message
        final String oldMessage = inDriver.getResponseText();
        final String newMessage = "Hello new World!";
        inDriver.setResponseText(newMessage);
        dispatcher.unregister(inNode);
        dispatcher.dispatch();
        assertEquals(outDriver.getLastReceived().getContent(), oldMessage);
    }
}