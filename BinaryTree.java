package twosemestr.aisd;

/**
 * Created by User on 23.04.2017.
 */
public class BinaryTree {
    private BinaryTreeNode head;

    // private BinaryTreeNode parentOfNode;

    private boolean isLeftChild;

    public void add(int value) {
        if (head == null) {
            head = new BinaryTreeNode(value, null);
        } else {
            add(head, value);
        }
    }

    public void add(BinaryTreeNode node, int value) {
        if (value < node.value) {
            if (node.left == null) {
                node.left = new BinaryTreeNode(value, node);
            } else {
                add(node.left, value);
            }
        } else {
            if (node.right == null) {
                node.right = new BinaryTreeNode(value, node);
            } else {
                add(node.right, value);
            }
        }
    }

    public boolean remove(int value) {
        BinaryTreeNode current = find(value);
        if (current == null) {
            return false;
        }
        if (current.left == null && current.right == null) {   // Узел не имеет потомков
            if (current == head) {
                head = null;
            } else if (isLeftChild) {
                //parentOfNode.left = null;
                current.parent.left = null;
            } else {
                //parentOfNode.right = null;
                current.parent.right = null;
            }
        } else if (current.right == null) {    //  Узел имеет одно потомка(левого)
            if (current == head) {
                head = current.left;
            } else if (isLeftChild) {
                //parentOfNode.left = current.left;
                current.parent.left = current.left;
                current.left.parent = current.parent;
            } else {
                //parentOfNode.right = current.left;
                current.parent.right = current.left;
                current.left.parent = current.parent;
            }
        } else if (current.left == null) {   //  Узел имеет одного потомка(правого)
            if (current == head) {
                head = current.right;
            } else if (isLeftChild) {
                //parentOfNode.left = current.right;
                current.parent.left = current.right;
                current.right.parent = current.parent;
            } else {
                //parentOfNode.right = current.right;
                current.parent.right = current.right;
                current.right.parent = current.parent;
            }
        } else {                                                  // Удаляемый узел имеет двух потомков
            BinaryTreeNode successor = getSuccessor(current);
            if (current == head) {
                head = successor;
            } else if (isLeftChild) {
                //parentOfNode.left = successor;
                current.parent.left = successor;
                successor.parent = current.parent;   // 3
            } else {
                //parentOfNode.right = successor;
                current.parent.right = successor;
                successor.parent = current.parent;   // 3
            }
            successor.left = current.left;
            current.left.parent = successor; // 4
        }

        return true;
    }

    public BinaryTreeNode find(int value) {
        BinaryTreeNode current = head;
        while (current.value != value) {
            // parentOfNode = current;
            if (current.value > value) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    public BinaryTreeNode getSuccessor(BinaryTreeNode node) {       //    Поиск преемника
        //BinaryTreeNode successorParent = node;
        BinaryTreeNode successor = node;
        BinaryTreeNode current = node.right;
        while (current != null) {
            //successorParent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != node.right) {              // преемник не равен правому потомку узла
            //successorParent.left = successor.right;
            successor.parent.left = successor.right;
            successor.right.parent = successor.parent; // 1
            successor.right = node.right;
            node.right.parent = successor; // 2
        }
        return successor;
    }

    public void splay(BinaryTreeNode node) {
        BinaryTreeNode parent = node.parent.parent;
        BinaryTreeNode child = node.parent;
        if (child.parent == null) head = node;
        if (parent == null)
            zig(node);
        if (child.left == node && parent.left == child
                || child.right == node && parent.right == node) {
            zigZig(node);
        }
        zigZag(node);
    }

    public void zig(BinaryTreeNode node) {
        if (node == head.right) {
            head.parent = node;
            node.parent = null;
            head.right = node.left;
            node.left = head;
            node.left.right.parent = head;
            head = node;
        } else {
            head.parent = node;
            node.parent = null;
            head.left = node.right;
            node.right = head;
            node.right.left.parent = head;
            head = node;
        }
    }

    public void zigZig(BinaryTreeNode node) {
        BinaryTreeNode z = node.parent.parent;
        BinaryTreeNode z1 = node.parent.parent;
        BinaryTreeNode parent1 = z1.parent;

        BinaryTreeNode y = node.parent;
        BinaryTreeNode g = node.left, g1 = y.right;
        BinaryTreeNode b = y.left, b1 = node.right;
        if (node == z.right.right) {
            node.left = y;
            y.right = g;
            y.left = z;
            y.parent = node;
            g.parent = y;
            z.right = b;
            z.parent = y;
            b.parent = z;
            node.parent = parent1;
            if (isLeft(z1)) {
                parent1.left = node;
            } else {
                parent1.right = node;
            }
            splay(node);
        } else {
            node.right = y;
            y.left = b1;
            y.right = z;
            y.parent = node;
            b1.parent = y;
            z.left = g1;
            z.parent = y;
            g1.parent = z;
            /*if (isLeft(z1)) {   ???
                parent1.left = node;
            } else {
                parent1.right = node;
            }*/
            splay(node);
        }
    }

    public void zigZag(BinaryTreeNode node) {
        BinaryTreeNode z = node.parent.parent;
        BinaryTreeNode z1 = node.parent.parent;
        BinaryTreeNode parent1 = z1.parent;

        BinaryTreeNode y = node.parent;
        BinaryTreeNode g = node.right, g1 = node.left;
        BinaryTreeNode b = node.left, b1 = node.right;
        if (node == y.left) {
            node.left = z;
            node.right = y;
            z.parent = node;
            y.parent = node;
            z.right = b;
            b.parent = z;
            y.left = g;
            g.parent = y;
            if (isLeft(z1)) {
                parent1.left = node;
            } else {
                parent1.right = node;
            }
            splay(node);
        } else {
            node.right = z;
            node.left = y;
            z.parent = node;
            y.parent = node;
            z.left = b1;
            b1.parent = z;
            y.right = g1;
            g1.parent = y;
            if (isLeft(z1)) {
                parent1.left = node;
            } else {
                parent1.right = node;
            }
            splay(node);
        }
    }

    public boolean isLeft(BinaryTreeNode node) {
        if (node == node.parent.left) {
            return true;
        } else {
            return false;
        }
    }
}
