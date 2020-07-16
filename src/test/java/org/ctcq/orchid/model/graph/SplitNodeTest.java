package org.ctcq.orchid.model.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;

import org.ctcq.orchid.driver.exceptions.DriverException;
import org.ctcq.orchid.driver.standard.in.HelloWorldInputDriver;
import org.ctcq.orchid.driver.standard.out.LastRecievedOutputDriver;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.model.media.exceptions.MediaException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Check SplitNode
 */
public class SplitNodeTest extends AbstractInternalNodeTest {

    @Test@Disabled void receiveHelloWorld() {
        // Init second outDriver
        LastRecievedOutputDriver lastReceivedDriver_2 = new LastRecievedOutputDriver(null, null, null);
        // Init the graph
        InNode inNode = new InNode(null, helloWorldDriver);
        OutNode outNode_1 = new OutNode(null, lastReceivedDriver);
        OutNode outNode_2 = new OutNode(null, lastReceivedDriver_2);
        inNode.setNodesOut(Set.of(outNode_1, outNode_2));

        try {
            Iterable<MediaData> media = helloWorldDriver.get();
            for (MediaData m : media) {
                inNode.forward(m);
            }
        } catch (DriverException | MediaException e) {
            e.printStackTrace();
            fail();
        }
        // Check if the driver received the Hello World message
        assertEquals(
            lastReceivedDriver.getLastReceived().getContent(),
            HelloWorldInputDriver.RESPONSE_TEXT
        );  
        
        // Check if the second driver received the Hello World message
        assertEquals(
            lastReceivedDriver_2.getLastReceived().getContent(),
            HelloWorldInputDriver.RESPONSE_TEXT
        );
    }

}