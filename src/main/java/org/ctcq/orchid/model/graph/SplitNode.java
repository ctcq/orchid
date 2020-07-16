package org.ctcq.orchid.model.graph;

import org.ctcq.orchid.driver.exceptions.DriverException;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.model.media.exceptions.MediaException;

/**
 * Node which forwards incoming messages to all outgoing nodes. No additional
 * behavior.
 */
public class SplitNode extends InnerNode {

    @Override
    protected void accept(MediaData media) throws MediaException, DriverException {
        // No processing is done
        forward(media);
    }

    @Override
    protected void forward(MediaData media) throws MediaException, DriverException {
        // Send message to every outgoing node.
        for (Node node : getNodesOut()) {
            node.accept(media);
        }
    }
}