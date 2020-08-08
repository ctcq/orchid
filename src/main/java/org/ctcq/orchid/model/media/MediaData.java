package org.ctcq.orchid.model.media;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ctcq.orchid.driver.EndpointDriver;
import org.jsoup.Jsoup;

/**
 * Represents input data throughout the whole graph.
 */
public class MediaData {

    public static final String DEFAULT_LOCALE = "EN";

    /**
     * Unique message id
     */
    private Long id;
    /**
     * The driver object that originally spawned this message.
     */
    private EndpointDriver sourceDriver;

    /**
     * The message content as text. Optionally formatted in HTML.
     */
    private String content;

    /**
     * Locale of this message.
     */
    private String locale;

    private static final Pattern tagRegexPattern = Pattern.compile("<[a-zA-Z]+>");

    /**
     * Create a new MediaData instance that can be handled by {@link Node} instances
     * and passed to and {@link org.ctcq.orchid.model.graph.OutNode}.
     * 
     * Input is clean with {@link Jsoup#clean(String, org.jsoup.safety.Whitelist)} whitelist function.
     * @param sourceDriver The {@link org.ctcq.orchid.driver.InputDriver} the message originated from.
     * @param content The HTML String as the message content.
     */
    public MediaData(final EndpointDriver sourceDriver, final String content) {
        this.sourceDriver = sourceDriver;
        this.locale = DEFAULT_LOCALE;
        
        // Filter content for unsafe HTML
        this.content = content;   
    }

    public MediaData() {
        // Stays empty
    }

    /**
     * @return A list of unescaped, unique HTML opening tags contained in the
     *         message.
     */
    public Iterable<String> getTags() {
        final HashSet<String> tags = new HashSet<String>();
        final Matcher matcher = tagRegexPattern.matcher(this.content);
        while (matcher.find()) {
            tags.add(matcher.group());
        }
        return tags;
    }

    public Long getId() {
        return id;
    }

    public EndpointDriver getSourceDriver() {
        return sourceDriver;
    }

    public String getContent() {
        return content;
    }

    public String getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return "MediaData [content=" + content + ", id=" + id + ", sourceDriver=" + sourceDriver + "]";
    }
}