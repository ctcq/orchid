package org.ctcq.orchid.driver;

import java.util.ArrayList;
import java.util.List;

import org.ctcq.orchid.driver.exceptions.DriverException;
import org.ctcq.orchid.model.graph.InNode;
import org.ctcq.orchid.model.graph.Node;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.model.media.exceptions.MediaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton class used for registering {@link InputDriver} instances and
 * dispatching {@link org.ctcq.orchid.model.media.MediaData} to connected
 * {@link Node} instances.
 */
public class DriverDispatcher extends Thread {

    private final List<InNode> inNodes;

    private int updateMs;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param updateMs The time before probing input nodes again.
     */
    public DriverDispatcher(final int updateMs) {
        this.inNodes = new ArrayList<>();
        this.updateMs = updateMs;
    }

    /**
     * Add a new {@link InNode} to be probed.
     * 
     * @param inNode The Node to be added.
     */
    public void register(final InNode inNode) {
        logger.info(String.format("Registering inNode %s.", inNode));
        inNodes.add(inNode);
    }

    /**
     * Remove an {@link InNode} from being probed.
     * 
     * @param inNode The Node to be removed.
     */
    public void unregister(final InNode inNode) {
        logger.info(String.format("Unregistering inNode %s.", inNode));
        inNodes.remove(inNode);
    }

    /**
     * Search for new messages from all registered {@link InNode} instances.
     */
    public void dispatch() {
        // Probe all input drivers
        logger.info("Probing Input Nodes");
        for (final InNode inNode : inNodes) {
            logger.debug("Probing node " + inNode.getName());
            final InputDriver driver = (InputDriver) inNode.getEndpointDriver();
            try {
                final List<MediaData> media = driver.get();
                logger.info(String.format("Retreived %d media data from %s.", media.size(), driver));

                // Forward messages to in nodes
                for (final MediaData m : media) {
                    inNode.accept(m);
                }
            } catch (final DriverException e) {
                logger.error(String.format("Unable to retreive media from endpoint for %s.", driver));
            } catch (final MediaException e) {
                logger.error(String.format("Invalid MediaData received from %s.", driver));
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            // Read new messages and send them to the grid
            dispatch();

            // Wait for specified time
            try {
                Thread.sleep(updateMs);
            } catch (final InterruptedException e) {
                logger.error("Interruption exception from DriverDispatcher. Exiting...");
                return;
            }
        }
    }

    public int getUpdateMs() {
        return updateMs;
    }

    public void setUpdateMs(final int updateMs) {
        this.updateMs = updateMs;
    }
}