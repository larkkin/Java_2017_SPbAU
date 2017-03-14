package ru.spbau.mit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by lara on 18.02.17.
 */
public class Node{
    private Node[] symbols;
    private boolean isTerminal;
    private int numberOfSubtreeTerminals;
    public Node() {
        symbols = new Node[TrieImpl.ALPHABET_SIZE * 2];
        isTerminal = false;
        numberOfSubtreeTerminals = 0;
    }
    public void serializeNode(DataOutputStream dos) throws SerializationException {
        try {
            dos.writeBoolean(getTerminality());
            dos.writeInt(getNumberOfSubtreeT());
            for (int i = 0; i < symbols.length; i++) {
                if (symbols[i] == null) {
                    dos.writeBoolean(false);
                } else {
                    dos.writeBoolean(true);
                    symbols[i].serializeNode(dos);
                }
            }
        } catch (IOException e) {
            throw new SerializationException();
        }
    }
    public void deserializeNode(DataInputStream dis) throws SerializationException {
        try {
            setTerminality(dis.readBoolean());
            setNumberOfSubtreeTerminals(dis.readInt());
            for (int i = 0; i < symbols.length; i++) {
                if (dis.readBoolean()) {
                    symbols[i] = new Node();
                    symbols[i].deserializeNode(dis);
                }
            }
        } catch (IOException e) {
            throw new SerializationException();
        }
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
    public void setNumberOfSubtreeTerminals(int number) {
        numberOfSubtreeTerminals = number;
    }

}
