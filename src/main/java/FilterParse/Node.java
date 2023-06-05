package FilterParse;

public class Node {
    private final String value;
    private final Node left;
    private final Node right;

    public Node(String value) {
        this(value, null, null);
    }

    public Node(String value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}