public interface Tree {
    public void insert(int data);
    public void traverse();
    public void preOrderTraversal(Node node);
    public void inOrderTraversal(Node node);
    public void postOrderTraversal(Node node);
    public void delete(int data);
    public Node delete(Node node, int data);
    public boolean search(int key);
    public boolean search(Node node, int key);
    public Node getPredecessor(Node node);
    public boolean isEmpty();
}
