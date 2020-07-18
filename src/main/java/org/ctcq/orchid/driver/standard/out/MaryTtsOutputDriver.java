package org.ctcq.orchid.driver.standard.out;

import java.io.IOException;
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

import marytts.client.MaryClient;
import marytts.client.http.MaryHttpClient;
import marytts.util.http.Address;

public class MaryTtsOutputDriver extends EndpointDriver implements OutputDriver {

    private MaryClient client;

    public MaryTtsOutputDriver(final String address, final Iterable<HtmlTag> validHtmlTags,
            final Set<AuthMethod> validAuthMethods) {
        super(address, null, null);
        final Address maryAddress = new Address(address);
        try {
            client = new MaryHttpClient(maryAddress);
        } catch (final IOException e) {
            logger.error("Could not instantiate Driver. IOException.");
        }
    }

    @Override
    public void post(final MediaData media)
            throws ConnectionFailureException, AuthenticationException, EndpointStatusException, NodeForwardException {
        try {
            client.process(media.getContent(), "TEXT", "AUDIO", media.getLocale(), "WAV_FILE", "", null);
        } catch (final IOException e) {
            logger.error(String.format("Could not send text %s to mary-server."));
        }
    }

}