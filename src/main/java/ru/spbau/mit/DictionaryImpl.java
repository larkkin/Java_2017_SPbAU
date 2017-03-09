package ru.spbau.mit;
import java.util.Random;
/**
 * Created by lara on 03.03.17.
 */
public class DictionaryImpl implements Dictionary {
    private static final int SOME_PRIME = 541;

    private StringPairListNode[] hashTable;
    private int a;
    private int b;
    private int dictSize;

    public DictionaryImpl(int hasToBePrimeP) {
        hashTable = new StringPairListNode[hasToBePrimeP];
        Random randomGenerator = new Random();
        a = randomGenerator.nextInt(hasToBePrimeP);
        b = randomGenerator.nextInt(hasToBePrimeP);
        dictSize = 0;
    }
    public DictionaryImpl() {
        this(SOME_PRIME);
    }

    public int size() {
        return dictSize;
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
        int i = getHash(key);
        StringPairListNode node = find(key);
        if (node == null) {
            StringPairListNode newNode = new StringPairListNode(key, value);
            StringPairListNode oldListHead = hashTable[i];
            hashTable[i] = newNode;
            if (oldListHead != null) {
                newNode.setNext(oldListHead);
                oldListHead.setPrev(newNode);
            }
            dictSize++;
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
        dictSize--;
        return node.getValue();
    }
    public void clear() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        dictSize = 0;
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
}

class StringPairListNode {
    private String key;
    private String value;
    private StringPairListNode next;
    private StringPairListNode prev;

    StringPairListNode(String inputKey, String inputValue) {
        key = inputKey;
        value = inputValue;
    }

    StringPairListNode getNext() {
        return next;
    }
    StringPairListNode getPrev() {
        return prev;
    }
    void setNext(StringPairListNode node) {
        next = node;
    }
    void setPrev(StringPairListNode node) {
        prev = node;
    }
    String getKey() {
        return key;
    }
    String getValue() {
        return value;
    }
    void setValue(String inputValue) {
        value = inputValue;
    }

} //package-private
