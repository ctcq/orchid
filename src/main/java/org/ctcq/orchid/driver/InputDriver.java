package org.ctcq.orchid.driver;

import java.util.List;

import org.ctcq.orchid.driver.exceptions.AuthenticationException;
import org.ctcq.orchid.driver.exceptions.ConnectionFailureException;
import org.ctcq.orchid.driver.exceptions.EndpointStatusException;
import org.ctcq.orchid.driver.exceptions.NodeForwardException;
import org.ctcq.orchid.model.media.MediaData;

public interface InputDriver {
    /**
     * Fetch endpoint resources.
     * @throws ConnectionFailureException If no connection could be established.
     * @throws AuthenticationException If the authentication failed.
     * @throws EndpointStatusException If a non-success response was recieved from the endpoint. E.g. 400, 500 HTTP status code.  
     * @throws NodeForwardException If the message could not be forwarded to subsequent nodes.
     * @return MediaData The resource as a {@link MediaData} representation.
     */
    public List<MediaData> get() 
        throws ConnectionFailureException, AuthenticationException, EndpointStatusException, NodeForwardException;

    public String getAddress();
}