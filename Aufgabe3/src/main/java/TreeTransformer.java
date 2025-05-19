import java.util.*;

class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;

    Node(T value) {
        this.value = value;
    }
}

public class TreeTransformer {

    public static <T> Node<T> transform(Node<T> root, Comparator<T> comp) {
        List<T> values = new ArrayList<>();
        collectValues(root, values);
        values.sort(comp);
        Iterator<T> iterator = values.iterator();
        overwriteValuesInOrder(root, iterator);
        return root;
    }

    private static <T> void collectValues(Node<T> node, List<T> values) {
        if (node == null) return;
        collectValues(node.left, values);
        values.add(node.value);
        collectValues(node.right, values);
    }

    private static <T> void overwriteValuesInOrder(Node<T> node, Iterator<T> iterator) {
        if (node == null) return;
        overwriteValuesInOrder(node.left, iterator);
        node.value = iterator.next();
        overwriteValuesInOrder(node.right, iterator);
    }

    // Hilfsmethode zur Ausgabe in Inorder-Reihenfolge
    public static <T> void printInOrder(Node<T> node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.value + " ");
        printInOrder(node.right);
    }

    public static void main(String[] args) {
        // Beispielbaum:
        //
        //       5
        //      / \
        //     3   8
        //    /   / \
        //   9   1   4

        Node<Integer> root = new Node<>(5);
        root.left = new Node<>(3);
        root.left.left = new Node<>(9);
        root.right = new Node<>(8);
        root.right.left = new Node<>(1);
        root.right.right = new Node<>(4);

        System.out.println("Vor der Umwandlung (Inorder):");
        printInOrder(root);
        System.out.println();

        transform(root, Integer::compareTo);

        System.out.println("Nach der Umwandlung (Inorder):");
        printInOrder(root);
        System.out.println();
    }
}
