package assignments.ex2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellEntryTest {

    // טסט למתודה isValid
    @Test
    public void testIsValid() {
        // טסטים לאינדקסים חוקיים
        assertTrue(new CellEntry("A1").isValid());  // אות + מספר
        assertTrue(new CellEntry("B99").isValid());  // אות + מספר דו ספרתי
        assertTrue(new CellEntry("C01").isValid());  // אות + מספר עם אפס בראש
        assertTrue(new CellEntry("Z99").isValid());  // אות אחר + מספר דו ספרתי

        // טסטים לאינדקסים לא חוקיים
        assertFalse(new CellEntry("A").isValid());  // מחרוזת קצרה מדי
        assertFalse(new CellEntry("A100").isValid());  // מספר מחוץ לטווח
        assertFalse(new CellEntry("1A").isValid());  // התחלה במספר ולא אות
        assertFalse(new CellEntry("AB").isValid());  // שני תווים לא נכונים
        assertFalse(new CellEntry("A-1").isValid());  // תו לא חוקי (מינוס)
    }

    // טסט למתודה getX
    @Test
    public void testGetX() {
        // טסטים למיקומים חוקיים
        assertEquals(0, new CellEntry("A1").getX());  // A=0
        assertEquals(1, new CellEntry("B1").getX());  // B=1
        assertEquals(25, new CellEntry("Z1").getX()); // Z=25

        // טסטים לאינדקסים לא חוקיים
        assertEquals(Ex2Utils.ERR, new CellEntry("1A").getX());  // התחלה במספר

    }

    // טסט למתודה getY
    @Test
    public void testGetY() {
        // טסטים למיקומים חוקיים
        assertEquals(1, new CellEntry("A1").getY());  // A1 -> Y=1
        assertEquals(99, new CellEntry("B99").getY());  // B99 -> Y=99
        assertEquals(1, new CellEntry("C01").getY());  // C01 -> Y=1

        // טסטים לאינדקסים לא חוקיים
        assertEquals(Ex2Utils.ERR, new CellEntry("A").getY());  // לא מספר
        assertEquals(Ex2Utils.ERR, new CellEntry("AA").getY());  // לא מספר

    }

    // טסט למתודה toString
    @Test
    public void testToString() {
        assertEquals("A1", new CellEntry("A1").toString());  // פשוט מחזיר את המחרוזת כפי שהיא
        assertEquals("B99", new CellEntry("B99").toString());
        assertEquals("C01", new CellEntry("C01").toString());
    }
}