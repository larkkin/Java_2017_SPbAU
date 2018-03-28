package ru.spbau.mit;
import java.util.Random;
/**
 * Created by lara on 03.03.17.
 */
public class DictionaryImpl implements Dictionary {
    static final int DEFAULT_EXPONENT = 4;
    private static final int MIN_LENGTH_TO_REHASH = 8;
    private static final double DEFAULT_MAX_LOADFACTOR = 0.75;
    private static final double DEFAULT_MIN_LOADFACTOR = 0.1;

    private StringPairListNode[] hashTable;
    private final double maxLoadfactor;
    private final double minLoadfactor;
    private int a;
    private int b;
    private int size;


    public DictionaryImpl(int exponent, double maxLoadfactor) {
        int tableLength = 1 << exponent;
        hashTable = new StringPairListNode[tableLength];
        generateCoeffs();
        this.maxLoadfactor = maxLoadfactor;
        minLoadfactor = Math.min(this.maxLoadfactor / 2, DEFAULT_MIN_LOADFACTOR);
        size = 0;
    }

    public DictionaryImpl() {
        this(DEFAULT_EXPONENT, DEFAULT_MAX_LOADFACTOR);
    }

    public int size() {
        return size;
    }

    public int tableLength() {
        return hashTable.length;
    }

    public double loadFactor() {
        return ((double) size()) / tableLength();
    }

    public boolean contains(String key) {
        return find(key) != null;
    }

    public String get(String key) {
        StringPairListNode node = find(key);
        if (node != null) {
            return node.getValue();
        }
        return null;
    }

    public String put(String key, String value) {
        int hash = getHash(key);
        StringPairListNode node = find(key);
        if (node == null) {
            StringPairListNode newNode = new StringPairListNode(key, value);
            StringPairListNode oldListHead = hashTable[hash];
            hashTable[hash] = newNode;
            if (oldListHead != null) {
                newNode.setNext(oldListHead);
                oldListHead.setPrev(newNode);
            }
            size++;
            if (loadFactor() > maxLoadfactor) {
                rehashUp();
            }
            return null;
        }
        String oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    public String remove(String key) {
        StringPairListNode node = find(key);
        if (node == null) {
            return null;
        }
        StringPairListNode next = node.getNext();
        StringPairListNode prev = node.getPrev();
        if (prev != null) {
            prev.setNext(next);
        } else {
            hashTable[getHash(key)] = node.getNext();
        }
        if (next != null) {
            next.setPrev(prev);
        }
        size--;

        if (loadFactor() < minLoadfactor) {
            rehashDown();
        }
        return node.getValue();
    }

    public void clear() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        size = 0;
    }

    private int getHash(String str) {
        int p = hashTable.length;
        return ((a * str.hashCode() + b) % p + p) % p;
    }

    private StringPairListNode find(String key) {
        StringPairListNode node = hashTable[getHash(key)];
        while (node != null && !node.getKey().equals(key)) {
            node = node.getNext();
        }
        return node;
    }

    private void generateCoeffs() {
        int tableLength = hashTable.length;
        Random randomGenerator = new Random();
        a = randomGenerator.nextInt(tableLength / 2) * 2 + 1;
        b = randomGenerator.nextInt(tableLength / 2) * 2 + 1;
    }

    private void rehashUp() {
        rehash(hashTable.length * 2);
    }
    private void rehashDown() {
        if (hashTable.length < MIN_LENGTH_TO_REHASH) {
            return;
        }
        rehash(hashTable.length / 2);
    }
    private void rehash(int tableLength) {
        size = 0;
        StringPairListNode[] oldHashTable = hashTable;
        hashTable = new StringPairListNode[tableLength];
        generateCoeffs();
        for (StringPairListNode node : oldHashTable) {
            StringPairListNode currentNode = node;
            while (currentNode != null) {
                put(currentNode.getKey(), currentNode.getValue());
                currentNode = currentNode.getNext();
            }
        }
    }


    private final class StringPairListNode {
        private final String key;
        private String value;
        private StringPairListNode next;
        private StringPairListNode prev;


        private StringPairListNode(String key, String value) {
            this.key = key;
            this.value = value;
        }


        private StringPairListNode getNext() {
            return next;
        }

        private StringPairListNode getPrev() {
            return prev;
        }

        private void setNext(StringPairListNode node) {
            next = node;
        }

        private void setPrev(StringPairListNode node) {
            prev = node;
        }

        private String getKey() {
            return key;
        }

        private String getValue() {
            return value;
        }

        private void setValue(String inputValue) {
            value = inputValue;
        }
    }
}

