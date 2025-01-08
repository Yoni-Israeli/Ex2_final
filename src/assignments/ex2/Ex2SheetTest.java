package assignments.ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

class Ex2SheetTest {

    private Sheet sheet;

    @BeforeEach
    void setUp() {
        // אתחול גיליון בגודל 3x3
        sheet = new Ex2Sheet(3, 3);
    }

    // טסט לבדיקת אם הקואורדינטות בתנאים נכונים
    @Test
    void testIsIn() {
        assertTrue(sheet.isIn(0, 0));  // תא A1
        assertTrue(sheet.isIn(2, 2));  // תא C3
        assertFalse(sheet.isIn(3, 3)); // מחוץ לגיליון
    }

    // טסט להחזרת רוחב הגיליון
    @Test
    void testWidth() {
        assertEquals(3, sheet.width());  // 3 עמודות
    }

    // טסט להחזרת גובה הגיליון
    @Test
    void testHeight() {
        assertEquals(3, sheet.height());  // 3 שורות
    }

    // טסט לקביעת ערך בתא
    @Test
    void testSet() {
        sheet.set(0, 0, "5");
        assertEquals("5", sheet.get(0, 0).getData());
    }

    // טסט לשליפה של תא לפי קואורדינטות
    @Test
    void testGetByCoordinates() {
        sheet.set(1, 1, "=A1+2");
        assertNotNull(sheet.get(1, 1));
    }

    // טסט לשליפה של תא לפי המפתח (לדוג' "A1")
    @Test
    void testGetByEntry() {
        sheet.set(2, 2, "=A1+2");
        assertNotNull(sheet.get("C3"));
        assertEquals("=A1+2", sheet.get("C3").getData());
    }

    // טסט לפונקציה eval (מחשבת את הערך הסופי בתא)
    @Test
    public void testEval() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A1+2");
        sheet.eval();  // נוודא שהכל מחושב

        // תא (1,1) תלוי בתא (0,0), אז ערך שלו יהיה 7
        assertEquals("7.0", sheet.eval(1, 1));

        // תא (0,0) לא תלוי בשום דבר, אז ערך שלו יהיה 5
        assertEquals("5.0", sheet.eval(0, 0));
    }

    @Test
    public void testEvalCircularDependency() {
        Ex2Sheet sheet = new Ex2Sheet(2, 2);
        sheet.set(0, 0, "=B1+1");
        sheet.set(1, 1, "=A1+1");

        // במקרה של תלות מעגלית, יש להחזיר שגיאה או ערך מוגדר מראש
        try {
            sheet.eval();
            fail("Expected circular dependency error");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("ERROR"));
        }
    }

//     טסט לעומק של תא (depth)
    @Test

    public void testDepth() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A1+2");
        sheet.eval();  // נוודא שהכל מחושב

        // תא (1,1) תלוי בתא (0,0), אז העומק שלו צריך להיות 1
        int[][] depths = sheet.depth();
        assertEquals(1, depths[1][1]);

        // תא ללא נוסחה צריך להיות עומק 0
        assertEquals(0, depths[0][0]);
    }

    @Test
    public void testCircularDependency() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=B1+1");
        sheet.set(1, 1, "=A1+1");

        // במקרה של תלות מעגלית, נצפה לעומק -1
        int[][] depths = sheet.depth();
        assertEquals(-1, depths[0][0]);
    }


    // טסט לשמירה וטעינה של הגיליון
    @Test
    void testSaveAndLoad() throws IOException {
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A1+10");

        // שמירה לקובץ
        sheet.save("testSheet.txt");

        // יצירת גיליון חדש וטעינתו
        Sheet newSheet = new Ex2Sheet(3, 3);
        newSheet.load("testSheet.txt");

        assertEquals("5", newSheet.get(0, 0).getData());
        assertEquals("15.0", newSheet.get(1, 1).getData());  // חישוב eval עבור A1+10
    }

    // טסט למקרים כאשר ישנם תאים ריקים
    @Test
    void testEmptyCells() {
        sheet.set(0, 0, "=A1+5");
        sheet.set(1, 1, "=B1-1");
        assertEquals("5.0", sheet.eval(0, 0));
        assertEquals("-1.0", sheet.eval(1, 1));
    }
    @Test
    void testCircularDependency1() {
        sheet.set(0, 0, "=A1");
        sheet.set(1, 0, "=A0");
        assertEquals("ERR", sheet.eval(0, 0));  // במקרה של תלות מעגלית
    }
}
