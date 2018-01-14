public class Avl implements Tree {

    private Node root;

    @Override
    public void insert(int data) {
        root = insert(root,data);
    }

    public Node insert(Node node, int data) {
        if(node == null) return new Node(data);



        if(data < node.data){
            node.left = insert(node.left,data);
        } else if(data > node.data){
            node.right = insert(node.right,data);
        } else {
            System.out.println("This node already exists in this tree!");
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) +1;
        node = settleViolation(data,node);

        return node;
    }

    // This is where we check if the tree is legal meaning if the operation we have preformed has destabilised the tree or not.
    private Node settleViolation(int data, Node node){
        // Will calculate the height of the left and right subtrees. if the tree is balence this value will not be more than 1. When it is in the range of +1,0 or -1 we know that the tree is balanced. if balance is greater than one (height of left - height of right) we know that it is a right heavy tree and if the balance is less than -1 we know that it is a right heavy tree (to many nodes on the right).
        int balance = getBalence(node);

        // node passed in is the grand parent of the newly inserted nodes grand parent the data is greater than the data that we have inserted means that this node that we have inserted is a left child of a left child. Its going to be left heavy make single left then right or one right. We know that the node is the grand parent because the insert function uses recursion which allows us to check every case of a tree or sb tree being unbalanced. The node and data that are passed in are linked with a grandparent and grandchild relationship. So when we check if the data < is less than the node.left.data we are comparing the node to its parent node.

        // Doubly left heavy tree.
        if(balance > 1 && data < node.left.data){
            return  rightRotation(node);
        }

        // Doubly right heavy tree.
        if(balance < -1 && data > node.right.data){
            return  leftRotation(node);
        }

        // Left right heavy tree *note it doesn't matter if nodes have left or right children we don't modify pointers for them so its pretty irrelevant.
        if(balance > 1 && data > node.left.data) {
            // Rotating the the left child to the left so this will be the nodes child we rotate
            node.left = leftRotation(node.left);
            // then we finally rotate the parent to the left to balence the tree
            return rightRotation(node);
        }

        // Right left heavy tree
        if(balance < -1 && data < node.right.data) {
            // Rotating the the right child to the right so this will be the nodes child we rotate
            node.right = rightRotation(node.right);
            // then we finally rotate the parent to the left to balence the tree
            return leftRotation(node);
        }

        return node;
    }

    @Override
    public void traverse() {
        if(root == null ) return;
        inOrder(root);
    }

    private void inOrder(Node node) {
        if(node.left != null) inOrder(node.left);

        System.out.println(node);

        if(node.right != null) inOrder(node.right);

    }

    public int height(Node node){
        if(node == null) return -1;

        return node.height;
    }

    public int getBalence(Node node){
        if(node == null) return 0;

        return height(node.left) - height(node.right);
    }

    private Node rightRotation(Node node){
        System.out.println("Rotating to the right on node: " + node );
        // The node passed in is going to be the right child of its left node.
        Node newParent = node.left;
        Node newChild = newParent.right;

        newParent.right = node;
        node.left = newChild;

        node.height = Math.max(height(node.left), height(node.right) )+1;

        return newParent;

    }

    private Node leftRotation(Node node){
        System.out.println("Rotating to the left on node: " + node);
        // The node passed in is going to be the right child of its left node.
        Node newParent = node.right;
        Node newChild = newParent.left;

        newParent.left = node;
        node.right = newChild;

        node.height = Math.max(height(node.left), height(node.right) )+1;

        return newParent;

    }

    @Override
    public void delete(int data) {
        if(root != null){
            root = delete(root,data);
        }
    }

    public Node delete(Node node, int data) {
        if(node == null) return node;

        // The data is smaller than the node that we want go left (recursively)
        if(data < node.data ){
            node.left = delete(node.left,data);
        } else if(data > node.data){
            // The data is bigger than the node that we want go right (recursively)
            node.right = delete(node.right,data);
        } else {
            // we have now found the node we want to remove

            // When removing a leaf node just return null.
            if(node.left == null && node.right == null){
                System.out.println("Removing leaf node");
                return null;
            }

            // Removing a node with a single right child
            if(node.left == null){
                System.out.println("Removing removing the right child");
                Node tempNode = node.right;
                node = null;
                // assigning the node for the parent to point to as the temp node
                return tempNode;
            } else if(node.right == null){
                // Removing a node with a single left child
                System.out.println("Removing removing the left child");
                Node tempNode = node.left;
                node = null;
                // assigning the node for the parent to point to as the temp node
                return tempNode;
            }

            // Removing a node that has two children
            System.out.println("Removing item with two childern");
            // Setting the temporary node to the predecessor which is the biggest node in the left subtree relative to the node that will be deleted.
            Node tempNode = getPredecessor(node.left);

            // setting node to be deleted to the the predecessor data
            node.data = tempNode.data;
            // setting the nodes (left child) predecessor to be deleted by putting the nodes data to be deleted where that was then calling this function recusively so its just like removing a leaf node
            node.left = delete(node.left,tempNode.data);

        }

        node.height = Math.max(height(node.left), height(node.right)) +1;

        return settleDeletion(node);
    }

    private Node settleDeletion(Node node) {

        int balance = getBalence(node);

        // When it is a left heavy situation this can either mean left-right or doubly left heavy
        if(balance > 1){
            // it will be a left-right heavy situation when the balance on the left sub tree is smaller than 0
            if(getBalence(node.left) < 0){
                node.left = leftRotation(node.left);
            }
            // Otherwise it will be a doubly left heavy situation
            return rightRotation(node);
        }

        // When it is a right heavy situation this can either mean right-left or doubly right heavy
        if(balance < -1){
            // it will be a right-left heavy situation when the balance on the right sub tree is greater than 0
            if(getBalence(node.right) > 0){
                node.right = rightRotation(node.right);
            }
            // Otherwise it will be a doubly right heavy situation
            return leftRotation(node);
        }

        return node;
    }

    private Node getPredecessor(Node node) {
        if(node.right != null)
            return getPredecessor(node.right);

        return node;
    }

    public boolean search(int key) {
        if (isEmpty()) {
            System.out.println("No happening");
            return false;
        } else {
            return search(root, key);
        }
    }

    public boolean search(Node node, int key) {
        if(node == null) {
            return false;
        } else {
            System.out.println(node.data);
            if (node.data > key) {
                return search(node.left,key);
            } else if (node.data < key) {
                return search(node.right,key);
            } else {
                return true;
            }
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

}
