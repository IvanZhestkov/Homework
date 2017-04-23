package twosemestr.aisd;

/**
 * Created by User on 23.04.2017.
 */
public class BinaryTreeNode {
    public int value;
    public BinaryTreeNode left, right;
    public BinaryTreeNode parent;

    public BinaryTreeNode(int value, BinaryTreeNode parent) {
        this.value = value;
        this.parent = parent;
    }
}
