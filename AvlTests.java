import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AvlTests {

    private IAvl testIAvl;

    @Before
    public void setUp() throws Exception {
        testIAvl = new Avl();
    }

    @Test
    public void treeIsEmptyWhenCreated() throws Exception {
        assertEquals(true, testIAvl.isEmpty());
    }

    @Test
    public void add() throws Exception {
        testIAvl.insert(2);
        assertEquals(2, testIAvl.getRoot().getData());
        testIAvl.insert(3);
        testIAvl.insert(4);
        assertEquals(3, testIAvl.getRoot().getData());
        assertEquals(false, testIAvl.isEmpty());
    }

    @Test
    public void delete() throws Exception {
        testIAvl.insert(2);
        testIAvl.insert(3);
        testIAvl.insert(4);
        testIAvl.delete(3);
        assertEquals(2, testIAvl.getRoot().getData());
        testIAvl.delete(2);
        assertEquals(4, testIAvl.getRoot().getData());

    }

}