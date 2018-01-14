public class Node {
    public int data;
    public Node left;
    public Node right;
    public int height;

    public Node(int data){
        this.data = data;
    }

    @Override
    public String toString() {
        return ""+this.data;
    }
}
