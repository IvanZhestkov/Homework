package twosemestr.aisd;

/**
 * Created by User on 24.04.2017.
 */
public class TestMain {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.add(34);
        tree.add(123);
        tree.add(15);
        tree.add(60);
        tree.add(20);
        tree.add(100);
        tree.bfs();
    }
}
