package ir.ac.kntu.model.datastructures;

public class BitSet {
    private final boolean[] data;

    public BitSet(int bits) {
        data = new boolean[bits];
        for (int i = 0; i < bits; i++) {
            data[i] = false;
        }
    }

    public int length() {
        return data.length;
    }

    public boolean get(int index){
        if (index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    public void set(int index, boolean value) {
        if (index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = value;
    }
}
