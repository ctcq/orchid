package org.ctcq.orchid.util;

import java.util.HashMap;
import java.util.Map;

import org.ctcq.orchid.driver.EndpointDriver;
import org.ctcq.orchid.model.media.HtmlTag;

/**
 * Implementation of the lookup-efficient prefix search tree for ASCII based
 * strings.
 * 
 * Only use this for a small number of character types e.g. ASCII. The character
 * {@link #ENDING_CHARACTER} is not supported and will lead to undefined behavior.
 */
public class Trie {

    /**
     * Contains all HTML tags as strings which are valid for use by {@link EndpointDriver} instances.
     */
    public static final Trie VALID_HTML_TRIE = getValidHtmlTrie();

    /**
     * Construct the Trie object which contains all statically defined 
     * HTML tags valid for drivers to parse. All other must be ingored by
     * the drivers.
     * 
     * @return The Trie containing the valid HTML tags as nodes.
     */
    private static Trie getValidHtmlTrie() {
        Trie trie = new Trie();
        for (int i = 0; i < HtmlTag.values().length; i++) {
            trie.insert(HtmlTag.values()[i].label);
        }
        return trie;
    }

    /**
     * Character signalizing the end of a string in a Trie.
     */
    public static final char ENDING_CHARACTER = '$';

    /**
     * Children of a particular root node.
     */
    private final Map<Character, Trie> children;

    /**
     * Instantiate empty Trie with empty children {@link Map}.
     */
    public Trie() {
        this.children = new HashMap<Character, Trie>();
    }

    /**
     * Insert a new word into the Trie.
     * This operation is not very fast and should be used sparingly
     * @param word The word to be inserted at the root.
     */
    public void insert(final String word) {
        // Empty word is inserted as ending char
        if (word.length() == 0) {
            if (!seek("")) {
                children.put(ENDING_CHARACTER, new Trie());
                return;
            }
        }

        // Insert either into new Trie or into existing one
        final char nextChar = word.charAt(0);
        Trie child = children.get(nextChar);
        final String wordTail = word.substring(1);
        if (child != null) {
            children.get(nextChar).insert(wordTail);
        } else {
            final Trie trie = new Trie();
            trie.insert(wordTail);
            children.put(nextChar, trie);
        }
    }

    /**
     * Check for word occurence. This operation is very efficient on shallow Tries.
     * @param word The word to search for
     * @return Whether the word is contained in the Trie.
     */
    public boolean seek(final String word) {
        // If emtpy word is given, look for ending character
        char nextChar;
        if (word.length() == 0) {
            return children.get(ENDING_CHARACTER) != null;
        } else {
            nextChar = word.charAt(0);
        }

        // Look for hashed char value
        final Trie child = children.get(nextChar);
        if (child == null) {
            return false;
        } else {
            return child.seek(word.substring(1));
        }
    }

    @Override
    public String toString() {
        return "Trie [children=" + children + "]";
    }
}