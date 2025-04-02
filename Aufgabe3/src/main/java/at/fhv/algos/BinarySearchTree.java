package at.fhv.algos;

import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> {
    protected static class Node<T> {
        protected T value;
        protected Node left;
        protected Node right;
        protected Node parent;
        protected int height;

        Node(T value, Node parent) {
            this.value = value;
            this.parent = parent;
            this.height = 1; // height of a new node is 1
        }
    }

    protected Node<T> root;

    public void insert(T value) {
        root = insertRecursive(root, value, null);
    }

    protected Node<T> insertRecursive(Node<T> node, T value, Node<T> parent) {
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

    protected Node<T> searchRecursive(Node<T> node, T value) {
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

    protected Node<T> findMinimumRecursive(Node node) {
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

    protected Node<T> findMaximumRecursive(Node node) {
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

    protected Node<T> removeNodeRecursive(Node<T> node, T value) {
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

    public boolean updateValue(T oldValue, T newValue) {
        Node<T> node = search(oldValue);

        if (node == null) {
            return false;
        }

        T predecessor = findPredecessor(oldValue);
        T successor = findSuccessor(oldValue);

        if ((predecessor == null || newValue.compareTo(predecessor) > 0) && (successor == null || newValue.compareTo(successor) < 0)) {
            node.value = newValue;
            return true;
        }

        return false;
    }

    public List<T> inOrderTraversal() {
        List<T> inOrderList = new ArrayList<>();
        inOrderTraversalRecursive(root, inOrderList);
        return inOrderList;
    }

    protected void inOrderTraversalRecursive(Node<T> node, List<T> inOrderList) {
        if (node != null) {
            inOrderTraversalRecursive(node.left, inOrderList);
            inOrderList.add(node.value);
            inOrderTraversalRecursive(node.right, inOrderList);
        }
    }

    public void printTree() {
//        displayTree();
        printRecursive(root, " ", true);
    }

    protected void printRecursive(Node<T> node, String prefix, boolean isRight) {
        if (node != null) {
            System.out.println(prefix + (isRight ? "└── " : "├── ") + node.value);
            printRecursive(node.left, prefix + (isRight ? "    " : "│   "), false);
            printRecursive(node.right, prefix + (isRight ? "    " : "│   "), true);
        }
    }

    public void displayTree()
    {
        Stack<Node<T>> globalStack = new Stack();
        globalStack.push(root);
        int emptyLeaf = 32;
        boolean isRowEmpty = false;
        System.out.println("****......................................................****");
        while(isRowEmpty==false)
        {

            Stack localStack = new Stack();
            isRowEmpty = true;
            for(int j=0; j<emptyLeaf; j++)
                System.out.print(' ');
            while(globalStack.isEmpty()==false)
            {
                Node temp = globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.value);
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if(temp.left != null ||temp.right != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<emptyLeaf*2-2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            emptyLeaf /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push((Node<T>) localStack.pop());
        }
        System.out.println("****......................................................****");
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(15);
        bst.insert(40);

        System.out.println("Binary Search Tree:");
        bst.printTree();

        System.out.println("Minimum: " + bst.findMinimum());
        System.out.println("Maximum: " + bst.findMaximum());
        System.out.println("Predecessor of 50: " + bst.findPredecessor(50));
        System.out.println("Successor of 50: " + bst.findSuccessor(50));

        bst.removeNode(70);
        System.out.println("\nTree after removing 50:");
        bst.printTree();

        List<Integer> inOrderList = bst.inOrderTraversal();
        System.out.println("\nIn-order traversal:");
        for (Integer value : inOrderList) {
            System.out.print(value + " ");
        }
//
//        bst.insert(55);
//        bst.insert(40);
//        bst.insert(60);
//        bst.insert(28);
//        bst.insert(95);
//        bst.insert(10);
//        bst.insert(30);
//        bst.insert(88);
//        bst.insert(99);
//        bst.insert(68);
//        bst.insert(90);
//        bst.insert(98);
//        bst.insert(63);
//        bst.insert(84);
//
//        System.out.println("Binary Search Tree:");
//        bst.printTree();
//
//        boolean isUpdateValid = bst.updateValue(60, 52);
//        System.out.println("\nUpdate valid: " + isUpdateValid);
    }
}
