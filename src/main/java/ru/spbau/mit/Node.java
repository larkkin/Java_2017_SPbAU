package ru.spbau.mit;

/**
 * Created by lara on 18.02.17.
 */
public class Node {
    private Node[] symbols;
    private boolean isTerminal;
    private int numberOfSubtreeTerminals;
    public Node() {
        symbols = new Node[TrieImpl.ALPHABET_SIZE * 2];
        isTerminal = false;
        numberOfSubtreeTerminals = 0;
    }
    public boolean hasEdge(int index) {
        return symbols[index] != null;
    }
    public Node nodeAt(int index) {
        return symbols[index];
    }
    public void createEdge(int index) {
        symbols[index] = new Node();
    }
    public void removeEdge(int index) {
        symbols[index] = null;
    }
    public void setTerminality(boolean c) {
        isTerminal = c;
    }
    public boolean getTerminality() {
        return isTerminal;
    }
    public int getNumberOfSubtreeT() {
        return numberOfSubtreeTerminals;
    }
    public void increaseNumberOfSubtreeTerminals() {
        numberOfSubtreeTerminals++;
    }
    public void decreaseNumberOfSubtreeTerminals() {
        numberOfSubtreeTerminals--;
    }

}
