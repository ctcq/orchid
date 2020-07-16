package org.ctcq.orchid.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Check {@link Trie}.
 */
public class TrieTest {

    @Test void insert_one_then_seekTest() {
        Trie trie = new Trie();
        trie.insert("A");
        
        assertTrue(trie.seek("A"));
        
        assertFalse(trie.seek("A" + Trie.ENDING_CHARACTER));
        assertFalse(trie.seek("" + Trie.ENDING_CHARACTER));
        assertFalse(trie.seek("B"));
    }

    @Test void insert_multiple_then_seekTest() {
        Trie trie = new Trie();
        trie.insert("AAAA");
        
        assertTrue(trie.seek("AAAA"));
        
        assertFalse(trie.seek("AAAAA"));
        assertFalse(trie.seek("AAA"));
    }

    @Test void insert_one_twice_then_seekTest() {
        Trie trie = new Trie();
        trie.insert("A");
        trie.insert("B");

        assertTrue(trie.seek("A"));
        assertTrue(trie.seek("B"));

        assertFalse(trie.seek("AB"));
        assertFalse(trie.seek("BA"));
    }

    @Test void insert_similar_then_seekTest() {
        Trie trie = new Trie();
        trie.insert("ABC");
        trie.insert("ABD");


        assertTrue(trie.seek("ABC"));
        assertTrue(trie.seek("ABD"));
    }
}