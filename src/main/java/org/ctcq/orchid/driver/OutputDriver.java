package org.ctcq.orchid.driver;

import org.ctcq.orchid.driver.exceptions.AuthenticationException;
import org.ctcq.orchid.driver.exceptions.ConnectionFailureException;
import org.ctcq.orchid.driver.exceptions.EndpointStatusException;
import org.ctcq.orchid.driver.exceptions.NodeForwardException;
import org.ctcq.orchid.model.media.MediaData;

public interface OutputDriver {

    /**
     * Post a resource to endpoint.
     * @throws ConnectionFailureException If no connection could be established.
     * @throws AuthenticationException If the authentication failed.
     * @throws EndpointStatusException If a non-success response was recieved from the endpoint. E.g. 400, 500 HTTP status code.  
     * @throws NodeForwardException If the message could not be forwarded to subsequent nodes.
     */
    public void post(MediaData media) 
        throws ConnectionFailureException, AuthenticationException, EndpointStatusException, NodeForwardException;
}