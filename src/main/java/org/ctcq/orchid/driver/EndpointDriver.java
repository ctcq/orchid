package org.ctcq.orchid.driver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.ctcq.orchid.model.media.HtmlTag;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.util.Trie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all enpoint drivers. Subclasses are used 
 * to accept or forward {@link MediaData} from / to the rest
 * of the graph. 
 */
public abstract class EndpointDriver {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * String pointing to the API endpoint.
     * This can be an HTTP endpoint or a local file. 
     */
    protected String address;

    /**
     * Valid authorization methods for this endpoint.
     */
    private Set<AuthMethod> validAuthMethods;

    /**
     * Valid tags as a subset of valid HTML tags valid globally. 
     */
    private Trie validHtmlTags;

    /**
     * Creates a new EndpintDriver instance and assigns the given {@link HtmlTag} and {@link AuthMethod}
     * lists to their resprective attributes.
     * @param address The address this endpoint is connected to. This should usually be a some form of URI.
     * @param validHtmlTags An iterable of valid HTML tags this driver is supposed to handle. Can be null for empty {@link Iterable}.
     * @param validAuthMethods An iterable of authorization methods this driver is supposed to handle. Can be null for empty {@link Set}.
     */
    public EndpointDriver(String address, Iterable<HtmlTag> validHtmlTags, Set<AuthMethod> validAuthMethods) {
        // Initiate empty Collections if null was given
        if (validHtmlTags == null)
            validHtmlTags = new ArrayList<HtmlTag>();

        if (validAuthMethods == null) {
            this.validAuthMethods = new HashSet<AuthMethod>();
        }

        // Construct the html tags Trie
        this.validHtmlTags = new Trie();
        for (HtmlTag tag : validHtmlTags) {
            this.validHtmlTags.insert(tag.label);
        }

        // Construct the valid auth methods
        this.validAuthMethods = validAuthMethods;
    }

    public String getAddress() {
        return address;
    }

    public Set<AuthMethod> getAuthMethods() {
        return validAuthMethods;
    }

    public Trie getValidHtmlTags() {
        return validHtmlTags;
    }
}