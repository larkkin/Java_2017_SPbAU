package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TrieTest {

    @Test
    public void testSimple() {
        Trie stringSet = instance();

        assertTrue(stringSet.add("abc"));
        assertTrue(stringSet.contains("abc"));
        assertEquals(1, stringSet.size());
        assertEquals(1, stringSet.howManyStartsWithPrefix("abc"));
    }

    @Test
    public void testPrefix() {
        Trie trie = instance();
        assertTrue(trie.add("abc"));
        assertFalse(trie.contains("abcONE"));
        assertTrue(trie.add("abcONE"));
        assertTrue(trie.add("abcTWO"));
        assertTrue(trie.add("abcTHREE"));
        assertTrue(trie.contains("abcONE"));
        final int three = 4;
        assertEquals(three, trie.size());
        assertEquals(three, trie.howManyStartsWithPrefix("abc"));
        assertEquals(1, trie.howManyStartsWithPrefix("abcONE"));
        assertEquals(2, trie.howManyStartsWithPrefix("abcT"));
        assertEquals(0, trie.howManyStartsWithPrefix("abcE"));
        assertFalse(trie.contains("abcONEE"));
    }

    @Test
    public void testRemove() {
        Trie trie = instance();
        assertTrue(trie.add("abc"));
        assertFalse(trie.contains("abcONE"));
        assertTrue(trie.add("abcONE"));
        assertTrue(trie.add("abcTWO"));
        assertTrue(trie.add("abcTHREE"));
        final int four = 4;
        assertEquals(four, trie.size());
        assertFalse(trie.remove("aBcONE"));
        assertTrue(trie.remove("abcONE"));
        assertEquals(four - 1, trie.size());
        assertTrue(trie.remove("abc"));
        assertEquals(2, trie.size());
    }

    @Test
    public void routineTest() {
        Trie trie = instance();
        assertTrue(trie.add("apELsIN"));
        assertTrue(trie.contains("apELsIN"));
        assertFalse(trie.contains("APELSIN"));
        assertFalse(trie.contains("apEL"));
        assertTrue(trie.add("HMM"));
        assertTrue(trie.add("university"));
        assertFalse(trie.add("university"));
        final int three = 3;
        assertEquals(three, trie.size());
        assertTrue(trie.remove("HMM"));
        assertEquals(2, trie.size());
        assertFalse(trie.contains("HMM"));
        assertTrue(trie.contains("university"));
        assertTrue(trie.add("apELs"));
        assertTrue(trie.remove("apELsIN"));
        assertTrue(trie.contains("apELs"));
    }

    @Test
    public void testAbc() {
        final Trie instance = instance();

        assertTrue(instance.add("a"));
        assertEquals(1, instance.howManyStartsWithPrefix(""));
        assertTrue(instance.remove("a"));
        assertEquals(0, instance.howManyStartsWithPrefix(""));
    }

    public static Trie instance() {
        try {
            return (Trie) Class.forName("ru.spbau.mit.TrieImpl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Error while class loading");
    }
}
