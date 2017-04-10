package Informatics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 01.04.2017.
 */
public class BinaryTree {

    private BinaryTreeNode head;

    private BinaryTreeNode parentOfNode;

    private boolean isLeftChild;

    public void add(int value) {
        if (head == null) {
            head = new BinaryTreeNode(value);
        } else {
            add(head, value);
        }
    }

    public void add(BinaryTreeNode node, int value) {
        if (value < node.value) {
            if (node.left == null) {
                node.left = new BinaryTreeNode(value);
            } else {
                add(node.left, value);
            }
        } else {
            if (node.right == null) {
                node.right = new BinaryTreeNode(value);
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
                parentOfNode.left = null;
            } else {
                parentOfNode.right = null;
            }
        } else if (current.right == null) {    //  Узел имеет одно потомка(левого)
            if (current == head) {
                head = current.left;
            } else if (isLeftChild) {
                parentOfNode.left = current.left;
            } else {
                parentOfNode.right = current.left;
            }
        } else if (current.left == null) {   //  Узел имеет одного потомка(правого)
            if (current == head) {
                head = current.right;
            } else if (isLeftChild) {
                parentOfNode.left = current.right;
            } else {
                parentOfNode.right = current.right;
            }
        } else {                                                  // Удаляемый узел имеет двух потомков
            BinaryTreeNode successor = getSuccessor(current);
            if (current == head) {
                head = successor;
            } else if (isLeftChild) {
                parentOfNode.left = successor;
            } else {
                parentOfNode.right = successor;
            }
            successor.left = current.left;
        }

        return true;
    }

    public BinaryTreeNode find(int value) {
        BinaryTreeNode current = head;
        while (current.value != value) {
            parentOfNode = current;
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
        BinaryTreeNode successorParent = node;
        BinaryTreeNode successor = node;
        BinaryTreeNode current = node.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != node.right) {              // преемник не равен правому потомку узла
            successorParent.left = successor.right;
            successor.right = node.right;
        }
        return successor;
    }
}
