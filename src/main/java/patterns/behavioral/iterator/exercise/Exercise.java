package patterns.behavioral.iterator.exercise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

class Node<T>
{
    public T value;
    public Node<T> left, right, parent;

    private Node<T> current;
    Stack<Node<T>> stack = new Stack<>();
    ArrayList<Node<T>> list = new ArrayList<>();

    private void transverse() {
        stack.push(current);
        while (!stack.isEmpty()) {
            current = stack.pop();
            list.add(current);
            if (current.right != null)
                stack.push(current.right);
            if (current.left != null)
                stack.push(current.left);
        }
    }

    public Node(T value)
    {
        this.value = value;
    }

    public Node(T value, Node<T> left, Node<T> right)
    {
        this.value = value;
        this.left = left;
        this.right = right;

        left.parent = right.parent = this;
    }

    public Iterator<Node<T>> preOrder()
    {
        current = this;
        transverse();
        return list.iterator();
    }
}

