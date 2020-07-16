package org.ctcq.orchid.model.media;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.ctcq.orchid.driver.EndpointDriver;

/**
 * Represents input data throughout the whole graph.
 */
@Entity
public class MediaData {

    /**
     * Unique message id
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * The driver object that originally spawned this message.
     */
    private EndpointDriver sourceDriver;

    /**
     * The message content as text. Optionally formatted in HTML.
     */
    private String content;

    private static final Pattern tagRegexPattern = Pattern.compile("<[a-zA-Z]+>");

    public MediaData(EndpointDriver sourceDriver, String content) {
        this.sourceDriver = sourceDriver;
        this.content = content;
    }

    public MediaData () {
        // Stays empty
    }

    /**
     * @return A list of unescaped, unique HTML opening tags contained in the message.
     */
    public Iterable<String> getTags() {
        HashSet<String> tags = new HashSet<String>(); 
        Matcher matcher = tagRegexPattern.matcher(this.content);
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

    @Override
    public String toString() {
        return "MediaData [content=" + content + ", id=" + id + ", sourceDriver=" + sourceDriver + "]";
    }
}