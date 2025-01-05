package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellEntryTest {

    @org.junit.jupiter.api.Test
    void isValid() {

    }

    /**
     * this lists of tests check if a string is valid index
     */
    @Test
    public void testValidIndex() {
        CellEntry entry = new CellEntry("A5");
        assertTrue(entry.isValid(), "A5 should be a valid index");

        entry = new CellEntry("B99");
        assertTrue(entry.isValid(), "B99 should be a valid index");

        entry = new CellEntry("z10");
        assertTrue(entry.isValid(), "z10 should be a valid index");

        entry = new CellEntry("A0");
        assertTrue(entry.isValid(), "A0 should be a valid index");

        entry = new CellEntry("A99");
        assertTrue(entry.isValid(), "A99 should be a valid index");
    }

    /**
     * this test check the String length
     */
    @Test
    public void testInvalidLength() {
        CellEntry entry = new CellEntry("A");
        assertFalse(entry.isValid(), "A should not be valid (less than 2 characters)");

        entry = new CellEntry("ABCD");
        assertFalse(entry.isValid(), "ABCD should not be valid (more than 2 characters)");
    }

    /**
     * this tests check order of letters in the string
     */
    @Test
    public void testInvalidFirstCharacter() {
        CellEntry entry = new CellEntry("1a");
        assertFalse(entry.isValid(), "1a should not be valid (second character is not a digit)");

        entry = new CellEntry("Z101");
        assertFalse(entry.isValid(), "Z101 should not be valid (number is greater than 99)");

        entry = new CellEntry("A@");
        assertFalse(entry.isValid(), "A@ should not be valid (contains invalid character)");

        entry = new CellEntry("1A");
        assertFalse(entry.isValid(), "1A should not be valid (starts with digit then letter)");

        entry = new CellEntry("Z-5");
        assertFalse(entry.isValid(), "Z-5 should not be valid (negative number)");
    }



    @org.junit.jupiter.api.Test
    void getX() {
    }

    @org.junit.jupiter.api.Test
    void getY() {
    }
}