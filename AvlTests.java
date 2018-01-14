import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AvlTests {

    private Tree testTree;

    @Before
    public void setUp() throws Exception {
        testTree = new Avl();
    }

    @Test
    public void treeIsEmptyWhenCreated() throws Exception {
        assertEquals(true, testTree.isEmpty());
    }

    @Test
    public void add() throws Exception {
        testTree.insert(2);
        assertEquals(2,testTree.getRoot().data);
        testTree.insert(3);
        testTree.insert(4);
        assertEquals(3,testTree.getRoot().data);
        assertEquals(false,testTree.isEmpty());
    }

    @Test
    public void delete() throws Exception {
        testTree.insert(2);
        testTree.insert(3);
        testTree.insert(4);
        testTree.delete(3);
        assertEquals(2,testTree.getRoot().data);
        testTree.delete(2);
        assertEquals(4,testTree.getRoot().data);

    }

}