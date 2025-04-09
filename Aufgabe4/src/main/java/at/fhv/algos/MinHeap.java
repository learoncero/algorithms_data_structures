package at.fhv.algos;

import java.util.ArrayList;

public class MinHeap {
    private ArrayList<Integer> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    public void insert(int value) {
        heap.add(value);
        int index = heap.size() - 1;

        while (index > 0 && heap.get(parent(index)) > heap.get(index)) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    public int extractMin() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = heap.get(0);
        int lastIndex = heap.size() - 1;
        swap(0, lastIndex);
        heap.remove(lastIndex);

        minHeapify(0);

        return min;
    }

    private void minHeapify(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int smallest = i;
        if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
            smallest = left;
        }
        if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void printHeap() {
        printRecursive(0, "", true);
    }

    private void printRecursive(int index, String prefix, boolean isRight) {
        if (index < heap.size()) {
            System.out.println(prefix + (isRight ? "└── " : "├── ") + heap.get(index));
            int left = leftChild(index);
            int right = rightChild(index);
            printRecursive(left, prefix + (isRight ? "    " : "│   "), false);
            printRecursive(right, prefix + (isRight ? "    " : "│   "), true);
        }
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(3);
        minHeap.insert(5);
        minHeap.insert(12);
        minHeap.insert(7);
        minHeap.insert(8);
        minHeap.insert(45);
        minHeap.insert(13);
        minHeap.printHeap();
        System.out.println("Extracted Min: " + minHeap.extractMin());
        minHeap.printHeap();
    }
}
