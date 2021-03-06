package org.ctcq.orchid.model.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;

import org.ctcq.orchid.driver.exceptions.DriverException;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.model.media.exceptions.MediaException;
import org.junit.jupiter.api.Test;

public class NodeForwardTest extends AbstractInternalNodeTest {

    @Test
    void forward_then_readTest() {
        // Init the graph
        InNode inNode = new InNode("in", staticInputDriver);
        OutNode outNode = new OutNode("out", lastReceivedDriver);
        inNode.setNodesOut(Set.of(outNode));

        try {
            Iterable<MediaData> media = staticInputDriver.get();
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
            staticInputDriver.getResponseText()
        );
    }

}