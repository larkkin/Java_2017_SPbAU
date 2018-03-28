package ru.spbau.mit;

import org.junit.Test;
import java.util.UUID;

import static org.junit.Assert.*;

public class DictionaryTest {
    @Test
    public void testSimplePutGet() {
        Dictionary dict = instance();
        assertNull(dict.get("abc"));
        assertNull(dict.put("abc", "cde"));
        assertEquals("cde", dict.get("abc"));
    }
    @Test
    public void testMultiplePutRemove() {
        final int numberOfPairsToPut = 3000;
        Dictionary dict = instance();
        assertEquals(0, dict.size());
        for (int i = 0; i < numberOfPairsToPut; i++) {
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            dict.put(key, value);
            dict.remove(key);
            assertEquals(0, dict.size());
        }
        dict.put("adf", "hm");
        assertEquals(1, dict.size());
        assertEquals("hm", dict.get("adf"));
        assertNull(dict.get("hm"));
        assertEquals("hm", dict.remove("adf"));
        assertEquals(0, dict.size());
    }

    @Test
    public void testMultipleGet() {
        final int numberOfPairsToPut = 3000;
        Dictionary dict = instance();
        String[] keys = new String[numberOfPairsToPut];
        String[] values = new String[numberOfPairsToPut];
        assertNull(dict.get("Spbau"));
        assertNull(dict.get(""));
        for (int i = 0; i < numberOfPairsToPut; i++) {
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            keys[i] = key;
            values[i] = value;
            assertEquals(dict.size(), i);
            assertNull(dict.put(key, value));
            assertEquals(i + 1, dict.size());
        }
        for (int i = 0; i < numberOfPairsToPut; i++) {
            assertEquals(dict.get(keys[i]), values[i]);
        }
        dict.clear();
        assertEquals(0, dict.size());
        assertNull(dict.get("abc"));

    }

    @Test
    public void viciousCase() {
        Dictionary dict = instance();
        dict.put("abc", null);
        assertTrue(dict.contains("abc"));
        dict.remove("abc");
        assertEquals(0, dict.size());
        dict.put("abc", null);
        assertTrue(dict.contains("abc"));
        dict.put("abc", "ff");
        assertTrue(dict.contains("abc"));
        dict.remove("abc");
        dict.put("abc", null);
        assertNull(dict.remove("abc"));
        assertFalse(dict.contains("abc"));
    }
    @Test
    public void testRehash() {
        Dictionary dict = instance();
        final int maxNum = 32;
        final int defautTableLength = 16;
        final double maxLoadfactor = 0.75;
        final double minLoadfactor = 0.1;

        assertEquals(1 << DictionaryImpl.DEFAULT_EXPONENT, ((DictionaryImpl) dict).tableLength());
        for (int i = 0; i < maxNum + 1; i++) {
            assertFalse(dict.contains(Integer.toString(i * i)));
            dict.put(Integer.toString(i * i), "hey");
            assertEquals(i + 1, dict.size());
            assertTrue(dict.contains(Integer.toString(i * i)));
            if (i < defautTableLength * maxLoadfactor) {
                assertEquals(defautTableLength, ((DictionaryImpl) dict).tableLength());
            } else if (i < defautTableLength * 2 * maxLoadfactor) {
                assertEquals(defautTableLength * 2, ((DictionaryImpl) dict).tableLength());
            } else {
                assertEquals(defautTableLength * 2 * 2, ((DictionaryImpl) dict).tableLength());
            }
        }
        for (int i = maxNum; i > 0; i--) {
            assertTrue(dict.contains(Integer.toString(i * i)));
            dict.remove(Integer.toString(i * i));
            assertFalse(dict.contains(Integer.toString(i * i)));
            assertEquals(i, dict.size());
            if (i < defautTableLength * 2 * 2 * minLoadfactor && i > defautTableLength * 2 * minLoadfactor) {
                assertEquals(defautTableLength * 2, ((DictionaryImpl) dict).tableLength());
            }
        }
        System.out.println(((DictionaryImpl) dict).tableLength());
    }


    private static Dictionary instance() {
        try {
            return (Dictionary) Class.forName("ru.spbau.mit.DictionaryImpl").newInstance();
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
