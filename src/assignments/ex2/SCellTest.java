package assignments.ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SCellTest {

    private SCell cell;


    @BeforeEach
    public void setUp() {
        // creating new object for each test
        cell = new SCell("A99");
    }

    //test for order
    @Test
    void getOrder() {

        cell.setOrder(5);
        assertEquals(5, cell.getOrder());
    }

    @Test
    void testToString() {
        assertEquals("A99", cell.toString());
    }

    @Test
    void setData() {

        cell.setData("B45");
        assertEquals("B45", cell.getData());
    }

    @Test
    void getData() {

        assertEquals("A99", cell.getData());
    }

    @Test
    void getType() {

        assertEquals(0, cell.getType());
    }

    @Test
    void setType() {

        cell.setType(3);
        assertEquals(3, cell.getType());
    }

    @Test
    void setOrder() {

        cell.setOrder(7);
        assertEquals(7, cell.getOrder());
    }
}