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
 * Simple driver that prints the message toString to SysOut.
 */
public class SystemOutOutputDriver extends EndpointDriver implements OutputDriver {
    
    public SystemOutOutputDriver() {
        super(null, null, null);
    }

    public SystemOutOutputDriver(String address, Iterable<HtmlTag> validHtmlTags, Set<AuthMethod> validAuthMethods) {
        super(null, null, null);
    }

    @Override
    public void post(MediaData media)
            throws ConnectionFailureException, AuthenticationException, EndpointStatusException, NodeForwardException {
                System.out.println(media.toString());
    }
}