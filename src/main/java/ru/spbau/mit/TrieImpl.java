package ru.spbau.mit;

/**
 * Created by lara on 18.02.17.
 */
public class TrieImpl implements Trie {
    public static final int ALPHABET_SIZE = 26;
    private Node root;
    public TrieImpl() {
        root = new Node();
    }

    public boolean add(String element) {
        Node currentNode = root;
        boolean result = contains(element);
        if (result) {
            return false;
        }
        for (int i = 0; i < element.length(); i++) {
            currentNode.setNumberOfSubtreeT(currentNode.getNumberOfSubtreeT() + 1);
            int index =  charToIndex(element.charAt(i));
            if (currentNode.hasEdge(index)) {
                currentNode = currentNode.nodeAt(index);
            } else {
                currentNode.createEdge(index);
                currentNode = currentNode.nodeAt(index);
            }
        }
        currentNode.setNumberOfSubtreeT(currentNode.getNumberOfSubtreeT() + 1);
        currentNode.setTerminality(true);
        return true;
    }

    public boolean contains(String element) {
        Node currentNode = root;
        boolean result = true;
        for (int i = 0; i < element.length(); i++) {
            int index = charToIndex(element.charAt(i));
            if (!currentNode.hasEdge(index)) {
                result = false;
                break;
            } else {
                currentNode = currentNode.nodeAt(index);
            }
        }
        return result && currentNode.getTerminality();
    }


    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }
        Node previousNode = root;
        Node currentNode = root.nodeAt(charToIndex(element.charAt(0)));
        for (int i = 1; i < element.length(); i++) {
            int index = charToIndex(element.charAt(i));
            previousNode.setNumberOfSubtreeT(previousNode.getNumberOfSubtreeT() - 1);
            if (previousNode.getNumberOfSubtreeT() == 0) {
                previousNode.removeEdge(index);
                return true;
            }
            previousNode = currentNode;
            currentNode = currentNode.nodeAt(index);
        }
        currentNode.setNumberOfSubtreeT(currentNode.getNumberOfSubtreeT() - 1);
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

    public static int charToIndex(char c) {
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
