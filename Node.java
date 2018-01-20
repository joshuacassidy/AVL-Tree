public class Node<T extends Comparable<T>> {
    public T data;
    public Node left;
    public Node right;
    public int height;

    public Node(T data){
        this.data = data;
    }

    @Override
    public String toString() {
        return ""+this.data;
    }
}
