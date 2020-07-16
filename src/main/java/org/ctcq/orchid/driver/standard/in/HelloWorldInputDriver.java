package org.ctcq.orchid.driver.standard.in;

import java.util.Set;

import org.ctcq.orchid.driver.AuthMethod;
import org.ctcq.orchid.driver.EndpointDriver;
import org.ctcq.orchid.driver.InputDriver;
import org.ctcq.orchid.driver.exceptions.AuthenticationException;
import org.ctcq.orchid.driver.exceptions.ConnectionFailureException;
import org.ctcq.orchid.driver.exceptions.EndpointStatusException;
import org.ctcq.orchid.model.media.HtmlTag;
import org.ctcq.orchid.model.media.MediaData;

/**
 * Simple input driver that fetches an unformmatted 'Hello World!' text.
 * Only use this form testing purposes.
 */
public class HelloWorldInputDriver extends EndpointDriver implements InputDriver {
    
    public static final String RESPONSE_TEXT = "Hello World!";

    public HelloWorldInputDriver(String address, Iterable<HtmlTag> validHtmlTags, Set<AuthMethod> validAuthMethods) {
        super(null, null, null);
    }

    @Override
    public MediaData get()
    throws ConnectionFailureException, AuthenticationException, EndpointStatusException {
        return new MediaData(this, RESPONSE_TEXT);
    }
}