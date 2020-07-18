package org.ctcq.orchid.model.graph;

import org.ctcq.orchid.driver.EndpointDriver;
import org.ctcq.orchid.driver.exceptions.DriverException;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.model.media.exceptions.InvalidMediaFormatException;
import org.ctcq.orchid.model.media.exceptions.MediaException;
import org.ctcq.orchid.util.Trie;

/**
 * Abstract superclass for all ingoing and outgoing nodes.
 * Permits authorization handler {@link #auth} and endpoint driver {@link #endpointDriver}.
 */
public abstract class EdgeNode extends Node {
    /**
     *  Generated serial
     */
    private static final long serialVersionUID = 7981469153257760022L;
    protected EndpointDriver endpointDriver;

    public EdgeNode(final String name, final EndpointDriver endpointDriver) {
        super(name);
        this.endpointDriver = endpointDriver;
    }

    public EndpointDriver getEndpointDriver() {
        return endpointDriver;
    }

    @Override
    public void accept(final MediaData media) throws MediaException, DriverException {
        // Check if the given message contains the correct tags
        if (!validateHtmlContent(media.getTags())) {
            throw new InvalidMediaFormatException();
        }

        // Forward to next node
        this.forward(media);
    }

    @Override
    protected void forward(final MediaData media) throws MediaException, DriverException {
        for (final Node node : getNodesOut()) {
            node.accept(media);
        }
    }

    /**
     * Check whether the given HTML tags are specified as valid for this driver.
     * 
     * @param tags The HTML tags without braces e.g. 'span'
     * @return Whether the given tags are valid.
     */
    protected boolean validateHtmlContent(final Iterable<String> tags) {
        final Trie validTags = endpointDriver.getValidHtmlTags();
        for (final String tag : tags) {
            if (!validTags.seek(tag))
                return false;
        }
        return true;
    }
}