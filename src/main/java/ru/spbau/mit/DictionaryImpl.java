package ru.spbau.mit;
import java.util.Random;
/**
 * Created by lara on 03.03.17.
 */
public class DictionaryImpl implements Dictionary {
    private static final int DEFAULT_EXPONENT = 9;

    private StringPairListNode[] hashTable;
    private int a;
    private int b;
    private int size;


    public DictionaryImpl(int exponent) {
        int tableLength = 1 << exponent;
        hashTable = new StringPairListNode[tableLength];
        generateCoeffs();
        size = 0;
    }

    public DictionaryImpl() {
        this(DEFAULT_EXPONENT);
    }


    public int size() {
        return size;
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
            return null;
        }
        String oldValue = node.getValue();
        node.setValue(value);
        if (size() > hashTable.length) {
            rehash();
        }
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

    private void rehash() {
        int tableLength = hashTable.length * 2;
        StringPairListNode[] oldHashTable = hashTable;
        hashTable = new StringPairListNode[tableLength];
        generateCoeffs();
        for (StringPairListNode node : oldHashTable) {
            while (node != null) {
                put(node.getKey(), node.getValue());
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

