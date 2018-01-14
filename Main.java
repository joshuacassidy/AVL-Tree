public class Main {
    public static void main(String[] args) {
        Tree avl = new Avl();
        avl.insert(10);
        avl.insert(15);
        avl.insert(5);
        avl.insert(6);

        avl.delete(15);

        avl.traverse();



    }
}
