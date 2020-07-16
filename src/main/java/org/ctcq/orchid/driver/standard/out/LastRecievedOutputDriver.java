package org.ctcq.orchid.driver.standard.out;

import java.util.Set;

import org.ctcq.orchid.driver.AuthMethod;
import org.ctcq.orchid.driver.EndpointDriver;
import org.ctcq.orchid.driver.OutputDriver;
import org.ctcq.orchid.driver.exceptions.AuthenticationException;
import org.ctcq.orchid.driver.exceptions.ConnectionFailureException;
import org.ctcq.orchid.driver.exceptions.EndpointStatusException;
import org.ctcq.orchid.driver.exceptions.NodeForwardException;
import org.ctcq.orchid.model.media.HtmlTag;
import org.ctcq.orchid.model.media.MediaData;

/**
 * Stores the message last recieved as {@link #lastReceived}. Only use this for
 * testing purposes.
 */
public class LastRecievedOutputDriver extends EndpointDriver implements OutputDriver {

    public LastRecievedOutputDriver(String address, Iterable<HtmlTag> validHtmlTags, Set<AuthMethod> validAuthMethods) {
        super(null, null, null);
    }

    private MediaData lastReceived;

    public MediaData getLastReceived() {
        return lastReceived;
    }

    @Override
    public void post(MediaData media)
            throws ConnectionFailureException, AuthenticationException, EndpointStatusException, NodeForwardException {
        this.lastReceived = media;
    }
}