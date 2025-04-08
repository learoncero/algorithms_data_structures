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

    public void displayHeap() {
        int size = heap.size();
        int height = (int) Math.floor(Math.log(size) / Math.log(2)) + 1;

        int index = 0;
        for (int level = 0; level < height; level++) {
            int levelNodes = (int) Math.pow(2, level);
            int spacing = (int) Math.pow(2, height - level);

            // Print node values with spacing
            for (int i = 0; i < levelNodes && index < size; i++, index++) {
                if (i == 0) System.out.print(" ".repeat(spacing / 2));
                System.out.print(heap.get(index));
                System.out.print(" ".repeat(spacing));
            }
            System.out.println();

            // Print connecting lines
            if (level < height - 1) {
                int lineCount = (int) Math.pow(2, level);
                for (int i = 0; i < lineCount; i++) {
                    if (i == 0) System.out.print(" ".repeat(spacing / 2 - 1));
                    System.out.print("/" + " ".repeat(spacing - 2) + "\\");
                    System.out.print(" ".repeat(spacing));
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(10);
        minHeap.insert(20);
        minHeap.insert(15);
        minHeap.insert(30);
        minHeap.insert(40);
        minHeap.insert(5);
        minHeap.displayHeap();
        System.out.println("Extracted Min: " + minHeap.extractMin());
        minHeap.displayHeap();
    }
}
