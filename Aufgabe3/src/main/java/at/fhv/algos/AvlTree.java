package at.fhv.algos;

public class AvlTree<T extends Comparable<T>> extends BinarySearchTree {

    @Override
    protected Node<T> insertRecursive(Node node, Comparable value, Node parent) {
        node = super.insertRecursive(node, value, parent);

        updateHeight((Node<T>) node);
        return balance((Node<T>) node);
    }

    @Override
    protected Node<T> removeNodeRecursive(Node node, Comparable value) {
        node = super.removeNodeRecursive(node, value);

        if (node != null) {
            updateHeight((Node<T>) node);
            return balance((Node<T>) node);
        }

        return null;
    }


    private void updateHeight(Node<T> node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getHeight(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalanceFactor(Node<T> node) {
        return (node == null) ? 0 : getHeight(node.right) - getHeight(node.left);
    }

    private Node<T> balance(Node<T> node) {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && getBalanceFactor(node.right) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        if (balanceFactor > 1 && getBalanceFactor(node.right) >= 0) {
            return rotateLeft(node);
        }

        if (balanceFactor < -1 && getBalanceFactor(node.left) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balanceFactor < -1 && getBalanceFactor(node.left) <= 0) {
            return rotateRight(node);
        }

        return node;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new AvlTree<>();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(15);
        bst.insert(40);

        System.out.println("Binary Search Tree:");
        bst.printTree();
    }
}
