public interface IAvl<T extends Comparable<T>> {
    public void insert(T data);
    public void traverse();
    public void preOrderTraversal(Node<T> node);
    public void inOrderTraversal(Node<T> node);
    public void postOrderTraversal(Node<T> node);
    public void delete(T data);
    public Node<T> delete(Node<T> node, T data);
    public boolean search(T key);
    public boolean search(Node<T> node, T key);
    public Node<T> getPredecessor(Node<T> node);
    public boolean isEmpty();
    public int height(Node<T> node);
    public Node<T> getRoot();
}
