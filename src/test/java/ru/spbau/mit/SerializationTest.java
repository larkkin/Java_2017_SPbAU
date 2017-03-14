package ru.spbau.mit;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SerializationTest {

    @Test
    public void testSimpleSerialization() {
        Trie trieInstance = TrieTest.instance();

        assertTrue(trieInstance.add("abc"));
        assertTrue(trieInstance.add("cde"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ((StreamSerializable) trieInstance).serialize(outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Trie newTrie = TrieTest.instance();
        ((StreamSerializable) newTrie).deserialize(inputStream);

        assertTrue(newTrie.contains("abc"));
        assertTrue(newTrie.contains("cde"));
        int numberOfNewSubtreeTerminals = newTrie.size();
        int numberOfSubtreeTerminals = trieInstance.size();
        assertEquals(numberOfSubtreeTerminals, numberOfNewSubtreeTerminals);
    }

    @Test
    public void testManyRequestsSerialization() {
        Trie trieInstance = TrieTest.instance();

        assertTrue(trieInstance.add("AA"));
        assertTrue(trieInstance.add("BB"));
        assertTrue(trieInstance.add(""));
        assertTrue(trieInstance.add("ACCAbadoRA"));
        assertTrue(trieInstance.add("pumpum"));
        assertTrue(trieInstance.add("hufflepuff"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ((StreamSerializable) trieInstance).serialize(outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Trie newTrie = TrieTest.instance();
        ((StreamSerializable) newTrie).deserialize(inputStream);

        assertTrue(newTrie.contains("AA"));
        assertFalse(newTrie.contains("CC"));
        assertTrue(newTrie.contains(""));
        assertTrue(newTrie.contains("pumpum"));
        assertTrue(newTrie.contains("hufflepuff"));
        assertFalse(newTrie.contains("gghuo"));
        assertTrue(newTrie.contains("ACCAbadoRA"));
        int numberOfNewSubtreeTerminals = newTrie.size();
        int numberOfSubtreeTerminals = trieInstance.size();
        assertEquals(numberOfSubtreeTerminals, numberOfNewSubtreeTerminals);
    }

    @Test
    public void serializeEmpty() {
        Trie trieInstance = TrieTest.instance();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ((StreamSerializable) trieInstance).serialize(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Trie newTrie = TrieTest.instance();
        ((StreamSerializable) newTrie).deserialize(inputStream);
        assertFalse(newTrie.contains(""));
        assertEquals(newTrie.size(), 0);

    }


    @Test(expected = SerializationException.class)
    public void testSimpleSerializationFails() {
        Trie trieInstance = TrieTest.instance();

        assertTrue(trieInstance.add("abc"));
        assertTrue(trieInstance.add("cde"));

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Fail");
            }
        };

        ((StreamSerializable) trieInstance).serialize(outputStream);
    }


}
