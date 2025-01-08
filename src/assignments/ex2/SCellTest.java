package assignments.ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SCellTest {

    @Test
    public void testGetData() {
        SCell cell = new SCell("Test");
        assertEquals("Test", cell.getData());  // לוודא שהנתונים מתקבלים כמו שצריך
    }

    @Test
    public void testSetData() {
        SCell cell = new SCell("Initial Data");
        cell.setData("New Data");
        assertEquals("New Data", cell.getData());  // לוודא שהנתונים עודכנו
    }

    @Test
    public void testGetType() {
        SCell cell = new SCell("Test");
        cell.setType(Ex2Utils.NUMBER);  // לדוגמה, סוג מספר
        assertEquals(Ex2Utils.NUMBER, cell.getType());  // לוודא שהסוג נכון
    }

    @Test
    public void testSetType() {
        SCell cell = new SCell("Test");
        cell.setType(Ex2Utils.TEXT);  // לדוגמה, סוג טקסט
        assertEquals(Ex2Utils.TEXT, cell.getType());  // לוודא שהסוג עודכן
    }

    @Test
    public void testGetOrder() {
        SCell cell = new SCell("Test");
        cell.setOrder(3);
        assertEquals(3, cell.getOrder());  // לוודא שסדר החישוב נכון
    }

    @Test
    public void testSetOrder() {
        SCell cell = new SCell("Test");
        cell.setOrder(5);
        assertEquals(5, cell.getOrder());  // לוודא שסדר החישוב עודכן
    }

    @Test
    public void testToString() {
        SCell cell = new SCell("Test");
        assertEquals("Test", cell.toString());  // לוודא שהטקסט מוצג כראוי
    }
}