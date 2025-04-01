package at.fhv.algos;

public class BinarySearchTree<T extends Comparable<T>> {
    private static class Node<T> {
        private T value;
        private Node left;
        private Node right;
        private Node parent;

        Node(T value, Node parent) {
            this.value = value;
            this.parent = parent;
        }
    }

    private Node<T> root;

    public void insert(T value) {
        root = insertRecursive(root, value, null);
    }

    private Node<T> insertRecursive(Node<T> node, T value, Node<T> parent) {
        if (node == null) {
            return new Node<>(value, parent);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insertRecursive(node.left, value, node);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insertRecursive(node.right, value, node);
        } else {
            // value already exists
            return node;
        }

        return node;
    }

    public Node<T> search(T value) {
        return searchRecursive(root, value);
    }

    private Node<T> searchRecursive(Node<T> node, T value) {
        if (node == null || node.value.equals(value)) {
            return node;
        }

        if (value.compareTo(node.value) < 0) {
            return searchRecursive(node.left, value);
        } else {
            return searchRecursive(node.right, value);
        }
    }

    public T findMinimum() {
        if (root == null) {
            return null;
        }

        return findMinimumRecursive(root).value;
    }

    private Node<T> findMinimumRecursive(Node node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public T findMaximum() {
        if (root == null) {
            return null;
        }

        return findMaximumRecursive(root).value;
    }

    private Node<T> findMaximumRecursive(Node node) {
        while (node.right != null) {
            node = node.right;
        }

        return node;
    }

    public T findPredecessor(T value) {
        Node<T> node = search(value);

        if (node.left != null) {
            return findMaximumRecursive(node.left).value;
        } else {
            Node<T> parent = node.parent;

            while (parent != null && node == parent.left) {
                node = parent;
                parent = parent.parent;
            }

            return parent.value;
        }
    }

    public T findSuccessor(T value) {
        Node<T> node = search(value);

        if (node.right != null) {
            return findMinimumRecursive(node.right).value;
        } else {
            Node<T> parent = node.parent;

            while (parent != null && node == parent.right) {
                node = parent;
                parent = parent.parent;
            }

            return parent.value;
        }
    }

    public Node<T> removeNode(T value) {
        return removeNodeRecursive(root, value);
    }

    private Node<T> removeNodeRecursive(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.value) < 0) {
            node.left = removeNodeRecursive(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = removeNodeRecursive(node.right, value);
        } else {
            if (node.left == null) {
                if (node.right != null) {
                    node.right.parent = node.parent;
                }

                return node.right;
            } else if (node.right == null) {
                if (node.left != null) {
                    node.left.parent = node.parent;
                }

                return node.left;
            }

            node.value = findSuccessor(node.value);
            node.right = removeNodeRecursive(node.right, node.value);
        }

        return node;
    }

    public void printTree() {
        printRecursive(root, "", true);
    }

    private void printRecursive(Node<T> node, String prefix, boolean isRight) {
        if (node != null) {
            System.out.println(prefix + (isRight ? "└── " : "├── ") + node.value);
            printRecursive(node.left, prefix + (isRight ? "    " : "│   "), false);
            printRecursive(node.right, prefix + (isRight ? "    " : "│   "), true);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        System.out.println("Binary Search Tree:");
        bst.printTree();

        System.out.println("Minimum: " + bst.findMinimum());
        System.out.println("Maximum: " + bst.findMaximum());
        System.out.println("Predecessor of 50: " + bst.findPredecessor(50));
        System.out.println("Successor of 50: " + bst.findSuccessor(50));

        bst.removeNode(70);
        System.out.println("\nTree after removing 50:");
        bst.printTree();
    }
}
