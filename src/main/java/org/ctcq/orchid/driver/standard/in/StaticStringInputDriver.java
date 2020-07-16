package org.ctcq.orchid.driver.standard.in;

import java.util.Arrays;
import java.util.List;
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
public class StaticStringInputDriver extends EndpointDriver implements InputDriver {
    
    private String responseText;

    public StaticStringInputDriver(String address, Iterable<HtmlTag> validHtmlTags, Set<AuthMethod> validAuthMethods) {
        super(null, null, null);
        responseText = "Hello World!";
    }

    @Override
    public List<MediaData> get()
    throws ConnectionFailureException, AuthenticationException, EndpointStatusException {
        return Arrays.asList(new MediaData(this, getResponseText()));
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}