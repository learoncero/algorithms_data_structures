package at.fhv.algos;

public class FibonacciHeap {
    private class FibonacciHeapNode {
        private int key;
        private FibonacciHeapNode next;
        private FibonacciHeapNode prev;
        private FibonacciHeapNode child;
        private int degree;
        private boolean marked;

        public FibonacciHeapNode(int key) {
            this.key = key;
            this.next = null;
            this.prev = null;
            this.child = null;
            this.degree = 0;
            this.marked = false;
        }
    }

    private FibonacciHeapNode minNode;
    private int nodeCount;

    public FibonacciHeap() {
        this.minNode = null;
        this.nodeCount = 0;
    }

    public void insert(int key) {
        FibonacciHeapNode newNode = new FibonacciHeapNode(key);
        if (minNode == null) {
            minNode = newNode;
            minNode.next = minNode;
            minNode.prev = minNode;
        } else {
            addToRootList(newNode);
            if (key < minNode.key) {
                minNode = newNode;
            }
        }
        nodeCount += 1;
    }

    private void addToRootList(FibonacciHeapNode node) {
        node.prev = minNode;
        node.next = minNode.next;
        minNode.next.prev = node;
        minNode.next = node;
    }

    public FibonacciHeapNode findMind() {
        if (minNode == null) {
            throw new IllegalStateException("Heap is empty");
        }
        return minNode;
    }

    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(24);
        heap.insert(17);
        heap.insert(39);

        System.out.println("Minimum: " + heap.findMind().key);
    }
}
