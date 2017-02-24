package ru.spbau.mit;

/**
 * Created by lara on 18.02.17.
 */
public class TrieImpl implements Trie {
    public static final int ALPHABET_SIZE = 26;
    private final Node root = new Node();

    public boolean add(String element) {
        Node currentNode = root;
        if (contains(element)) {
            return false;
        }
        for (int i = 0; i < element.length(); i++) {
            currentNode.increaseNumberOfSubtreeTerminals();
            int index =  charToIndex(element.charAt(i));
            if (!currentNode.hasEdge(index)) {
                currentNode.createEdge(index);
            }
            currentNode = currentNode.nodeAt(index);
        }
        currentNode.increaseNumberOfSubtreeTerminals();
        currentNode.setTerminality(true);
        return true;
    }

    public boolean contains(String element) {
        Node currentNode = root;
        for (int i = 0; i < element.length(); i++) {
            int index = charToIndex(element.charAt(i));
            if (!currentNode.hasEdge(index)) {
                return false;
            } else {
                currentNode = currentNode.nodeAt(index);
            }
        }
        return currentNode.getTerminality();
    }


    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }
        Node previousNode = root;
        int i = 0;
        Node currentNode = root.nodeAt(charToIndex(element.charAt(i)));
        int index = charToIndex(element.charAt(i));
        do {
            i++;
            previousNode.decreaseNumberOfSubtreeTerminals();
            if (previousNode.getNumberOfSubtreeT() == 0) {
                previousNode.removeEdge(index);
                return true;
            }
            index = charToIndex(element.charAt(i));
            previousNode = currentNode;
            currentNode = currentNode.nodeAt(index);
        } while (i < element.length() - 1);
        currentNode.decreaseNumberOfSubtreeTerminals();
        currentNode.setTerminality(false);
        return true;
    }

    public int size() {
        return root.getNumberOfSubtreeT();
    }
    public int howManyStartsWithPrefix(String prefix) {
        Node currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = charToIndex(prefix.charAt(i));
            if (!currentNode.hasEdge(index)) {
                return 0;
            }
            currentNode = currentNode.nodeAt(index);
        }
        return currentNode.getNumberOfSubtreeT();
    }

    private static int charToIndex(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a';
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + ALPHABET_SIZE;
        } else {
            return -1;
        }
    }


}
