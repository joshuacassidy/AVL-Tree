public class Node<T extends Comparable<T>> {
    private T data;
    private Node left, right;
    private int height;

    public Node(T data){
        this.data = data;
    }

    @Override
    public String toString() {
        return ""+this.data;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public T getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
