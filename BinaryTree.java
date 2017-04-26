package twosemestr.aisd;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by User on 23.04.2017.
 */
public class BinaryTree {
    private BinaryTreeNode head;

    // private BinaryTreeNode parentOfNode;

    public BinaryTreeNode getHead() {
        return head;
    }

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
                splay(node.left);
            } else {
                add(node.left, value);
            }
        } else {
            if (node.right == null) {
                node.right = new BinaryTreeNode(value, node);
                splay(node.right);
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
        /*if (current.right == null) {
            head = current.left;

        } else {
            head = current.right;
        }
        if (current.left != null && current.right != null) {
            head = current.left;
            head.right = current.right;
        }
        return true;*/
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
        splay(current);
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
        if (node.parent == null) {
            head = node;
            return;
        }
        if (node.parent.parent == null) {
            zig(node);
            return;
        }
        BinaryTreeNode parent = node.parent.parent;
        BinaryTreeNode child = node.parent;
        if ((child.left == node && parent.left == child)
                || (child.right == node && parent.right == child)) {
            zigZig(node);
        } else {
            zigZag(node);
        }
    }

    public void zig(BinaryTreeNode node) {
        if (node == head.right) {
            head.parent = node;
            node.parent = null;
            head.right = node.left;
            node.left = head;
            if (node.left.right != null) {
                node.left.right.parent = head;
            }
            head = node;
        } else {
            head.parent = node;
            node.parent = null;
            head.left = node.right;
            node.right = head;
            if (node.right.left != null) {
                node.right.left.parent = head;
            }
            head = node;
        }
    }

    public void zigZig(BinaryTreeNode node) {
        /*BinaryTreeNode z = node.parent.parent;
        BinaryTreeNode node1 = node;
        BinaryTreeNode y1 = node.parent, node2 = node.parent;
        if (proverka(z)) {
            node2 = null;
        } else {
            if (z == z.parent.left) {
                z.parent.left = node2;
                node2 = z.parent;
            } else {
                z.parent.right = node2;
                node2 = z.parent;
            }
        }
        if (node == node.parent.right) {
            node.left = z.right;
            z.right.right = node.left;
            z.right.left = z;
            z.right = y1.left;
            if (z.right != null) {
                z.right.parent = z;
            }
            z.parent = node.left;
            if (node.left.right != null) {
                node.left.right.parent = node.left;
            }
            node.left.parent = node;

            node.parent = node2;
            splay(node);
        } else {
            node.right = z.left;
            z.left.left = node.right;
            z.left.right = z;
            z.left = y1.right;
            if (z.left != null) {
                z.left.parent = z;
            }
            z.parent = node.right;
            if (node.right.left != null) {
                node.right.left.parent = node.right;
            }
            node.right.parent = node;

            node.parent = node2;
            splay(node);
        }*/
        BinaryTreeNode z = node.parent.parent;
        BinaryTreeNode y = node.parent;
        BinaryTreeNode node2 = node;
        if (proverka(z)) {
            node2 = null;
        } else {
            if (z == z.parent.left) {
                z.parent.left = node2;
                node2 = z.parent;
            } else {
                z.parent.right = node2;
                node2 = z.parent;
            }
        }
        if (node == node.parent.right) {
            y.right = node.left;
            if (node.left != null) {
                node.left.parent = y;
            }
            y.parent = node;
            z.right = y.left;
            if (y.left != null) {
                y.left.parent = z;
            }
            z.parent = y;
            y.left = z;
            node.left = y;

            node.parent = node2;
            splay(node);
        } else {
            y.left = node.right;
            if (node.right != null) {
                node.right.parent = y;
            }
            y.parent = node;
            z.left = y.right;
            if (y.right != null) {
                y.right.parent = z;
            }
            z.parent = y;
            y.right = z;
            node.right = y;

            node.parent = node2;
            splay(node);
        }
    }

    public void zigZag(BinaryTreeNode node) {
        /*BinaryTreeNode z = node.parent.parent;
        BinaryTreeNode node1 = node;
        BinaryTreeNode node2 = node.parent;
        if (proverka(z)) {
            node2 = null;
        } else {
            if (z == z.parent.left) {
                z.parent.left = node2;
                node2 = z.parent;
            } else {
                z.parent.right = node2;
                node2 = z.parent;
            }
        }

        if (node.parent.left == node) {
            z.right.left = node1.right;
            node.right = z.right;
            z.right = node1.left;
            node.left = z;
            if (z.right != null) {
                z.right.parent = z;
            }
            if (node.right.left != null) {
                node.right.left.parent = node.right;
            }
            z.parent = node;
            node.right.parent = node;

            node.parent = node2;
            splay(node);
        } else {
            z.left.right = node1.left;
            node.left = z.left;
            z.left = node1.right;
            node.right = z;
            if (z.left != null) {
                z.left.parent = z;
            }
            if (node.left.right != null) {
                node.left.right.parent = node.left;
            }
            z.parent = node;
            node.left.parent = node;

            node.parent = node2;
            splay(node);
        }*/

        BinaryTreeNode z = node.parent.parent;
        BinaryTreeNode y = node.parent;
        BinaryTreeNode node2 = node;
        if (proverka(z)) {
            node2 = null;
        } else {
            if (z == z.parent.left) {
                z.parent.left = node2;
                node2 = z.parent;
            } else {
                z.parent.right = node2;
                node2 = z.parent;
            }
        }
        if (node.parent.left == node) {
            z.right = node.left;
            if (node.left != null) {
                node.left.parent = z;
            }
            z.parent = node;
            y.left = node.right;
            if (node.right != null) {
                node.right.parent = y;
            }
            y.parent = node;
            node.left = z;
            node.right = y;
            node.parent = node2;
            splay(node);
        } else {
            z.left = node.right;
            if (node.right != null) {
                node.right.parent = z;
            }
            z.parent = node;
            y.right = node.left;
            if (node.left != null) {
                node.left.parent = y;
            }
            y.parent = node;
            node.right = z;
            node.left = y;
            node.parent = node2;
            splay(node);
        }
    }

    public boolean proverka(BinaryTreeNode z) {
        if (z.parent == null) {
            return true;
        } else {
            return false;
        }
    }

    public void bfs() {
        Queue<BinaryTreeNode> st = new ArrayDeque<>();
        st.add(head);
        while (!st.isEmpty()) {
            BinaryTreeNode n = st.remove();
            System.out.println(n.value);
            if (n.left != null) {
                st.add(n.left);
            }
            if (n.right != null) {
                st.add(n.right);
        }
    }
}
}
