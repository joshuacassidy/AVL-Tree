public class Avl<T extends Comparable<T>> implements IAvl {

    private Node<T> root;


    @Override
    public void insert(Comparable data) {
        root = insert(root,data);
    }

    public Node<T> insert(Node node, Comparable data) {
        if(node == null) return new Node(data);



        if(data.compareTo(node.getData()) < 0){
            node.setLeft(insert(node.getLeft(),data));
        } else {
            node.setRight(insert(node.getRight(),data));
        }
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) +1);
        node = settleViolation(data,node);

        return node;
    }

    // This is where we check if the tree is legal meaning if the operation we have preformed has destabilised the tree or not.
    private Node settleViolation(Comparable data, Node node){
        // Will calculate the height of the left and right subtrees. if the tree is balence this value will not be more than 1. When it is in the range of +1,0 or -1 we know that the tree is balanced. if balance is greater than one (height of left - height of right) we know that it is a right heavy tree and if the balance is less than -1 we know that it is a right heavy tree (to many nodes on the right).
        int balance = getBalance(node);

        // node passed in is the grand parent of the newly inserted nodes grand parent the data is greater than the data that we have inserted means that this node that we have inserted is a left child of a left child. Its going to be left heavy make single left then right or one right. We know that the node is the grand parent because the insert function uses recursion which allows us to check every case of a tree or sb tree being unbalanced. The node and data that are passed in are linked with a grandparent and grandchild relationship. So when we check if the data < is less than the node.left.data we are comparing the node to its parent node.

        // Doubly left heavy tree.
        if(balance > 1 && data.compareTo(node.getLeft().getData()) < 0){
            return  rightRotation(node);
        }

        // Doubly right heavy tree.
        if(balance < -1 && data.compareTo(node.getRight().getData()) > 0){
            return  leftRotation(node);
        }

        // Left right heavy tree *note it doesn't matter if nodes have left or right children we don't modify pointers for them so its pretty irrelevant.
        if(balance > 1 && data.compareTo(node.getLeft().getData()) < 0) {
            // Rotating the the left child to the left so this will be the nodes child we rotate
            node.setLeft(leftRotation(node.getLeft()));
            // then we finally rotate the parent to the left to balence the tree
            return rightRotation(node);
        }

        // Right left heavy tree
        if(balance < -1 && data.compareTo(node.getRight().getData()) > 0) {
            // Rotating the the right child to the right so this will be the nodes child we rotate
            node.setRight(rightRotation(node.getRight()));
            // then we finally rotate the parent to the left to balence the tree
            return leftRotation(node);
        }

        return node;
    }

    @Override
    public void traverse() {
        if(root == null ) throw new TreeIsEmptyException("Can't traverse an empty tree");
        inOrderTraversal(root);
        System.out.println();
    }

    @Override
    public void inOrderTraversal(Node node) {
        if(node != null) {
            inOrderTraversal(node.getLeft());
            System.out.print(node + " ");
            inOrderTraversal(node.getRight());
        }
    }

    @Override
    public void postOrderTraversal(Node node) {
        if (node != null) {
            postOrderTraversal(node.getLeft());
            postOrderTraversal(node.getRight());
            System.out.print(node + " ");
        }
    }

    @Override
    public void preOrderTraversal(Node node) {
        if (node != null) {
            System.out.print(node + " ");
            preOrderTraversal(node.getLeft());
            preOrderTraversal(node.getRight());
        }
    }

    @Override
    public int height(Node node){
        if(node == null) return -1;

        return node.getHeight();
    }

    public int getBalance(Node node){
        if(node == null) return 0;

        return height(node.getLeft()) - height(node.getRight());
    }

    private Node rightRotation(Node node){
        // The node passed in is going to be the right child of its left node.
        Node newParent = node.getLeft();
        Node newChild = newParent.getRight();

        newParent.setRight(node);
        node.setLeft(newChild);

        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight()) )+1);

        return newParent;

    }

    private Node leftRotation(Node node){
        // The node passed in is going to be the right child of its left node.
        Node newParent = node.getRight();
        Node newChild = newParent.getLeft();

        newParent.setLeft(node);
        node.setRight(newChild);

        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight()) )+1);

        return newParent;

    }

    @Override
    public void delete(Comparable data) {
        if(root != null){
            root = delete(root,data);
        } else {
            throw new TreeIsEmptyException("Can't delete from an empty tree");
        }

    }

    public Node delete(Node node, Comparable data) {
        if(node == null) return node;

        // The data is smaller than the node that we want go left (recursively)
        if(data.compareTo(node.getData()) < 0 ){
            node.setLeft(delete(node.getLeft(),data));
        } else if(data.compareTo(node.getData()) > 0){
            // The data is bigger than the node that we want go right (recursively)
            node.setRight(delete(node.getRight(),data));
        } else {
            // we have now found the node we want to remove

            // When removing a leaf node just return null.
            if(node.getLeft() == null && node.getRight() == null){
                return null;
            }

            // Removing a node with a single right child
            if(node.getLeft() == null){
                Node tempNode = node.getRight();
                node = null;
                // assigning the node for the parent to point to as the temp node
                return tempNode;
            } else if(node.getRight() == null){
                // Removing a node with a single left child
                Node tempNode = node.getLeft();
                node = null;
                // assigning the node for the parent to point to as the temp node
                return tempNode;
            }

            // Removing a node that has two children

            // Setting the temporary node to the predecessor which is the biggest node in the left subtree relative to the node that will be deleted.
            Node tempNode = getPredecessor(node.getLeft());

            // setting node to be deleted to the the predecessor data
            node.setData(tempNode.getData());
            // setting the nodes (left child) predecessor to be deleted by putting the nodes data to be deleted where that was then calling this function recusively so its just like removing a leaf node
            node.setLeft(delete(node.getLeft(),tempNode.getData()));

        }

        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) +1);

        return settleDeletion(node);
    }

    private Node settleDeletion(Node node) {

        int balance = getBalance(node);

        // When it is a left heavy situation this can either mean left-right or doubly left heavy
        if(balance > 1){
            // it will be a left-right heavy situation when the balance on the left sub tree is smaller than 0
            if(getBalance(node.getLeft()) < 0){
                node.setLeft(leftRotation(node.getLeft()));
            }
            // Otherwise it will be a doubly left heavy situation
            return rightRotation(node);
        }

        // When it is a right heavy situation this can either mean right-left or doubly right heavy
        if(balance < -1){
            // it will be a right-left heavy situation when the balance on the right sub tree is greater than 0
            if(getBalance(node.getRight()) > 0){
                node.setRight(rightRotation(node.getRight()));
            }
            // Otherwise it will be a doubly right heavy situation
            return leftRotation(node);
        }

        return node;
    }

    @Override
    public Node getPredecessor(Node node) {
        if(node.getRight() != null)
            return getPredecessor(node.getRight());

        return node;
    }

    @Override
    public boolean search(Comparable key) {
        if (isEmpty()) {
            throw new TreeIsEmptyException("Error items cannot be searched in an empty tree.");
        } else {
            return search(root, key);
        }
    }
    @Override
    public boolean search(Node node, Comparable key) {
        if(node == null) {
            return false;
        } else {
            if (node.getData().compareTo(key) > 0) {
                return search(node.getLeft(),key);
            } else if (node.getData().compareTo(key) < 0) {
                return search(node.getRight(),key);
            } else {
                return true;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
