package twosemestr.aisd;

/**
 * Created by User on 24.04.2017.
 */
public class TestMain {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.add(5);
        tree.add(7);
        tree.add(0);
        tree.add(-5);
        tree.add(3);
        tree.add(17);
        tree.add(6);
        tree.add(15);
        tree.add(25);
        tree.add(4);
        tree.add(-6);
        tree.add(-7);
        tree.add(-4);
        tree.splay(tree.find());
        System.out.println(tree.getHead().value);
    }
}
