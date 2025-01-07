package assignments.ex2;

import java.io.*;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here
    private int width;
    private int height;

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i = i + 1) {
            for (int j = 0; j < y; j = j + 1) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here

        Cell c = get(x, y);
        if (c != null) {
            ans = c.toString();
        }

        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        // Add your code here

        /////////////////////
        return ans;
    }

    @Override
    public int width() {
        return table.length;
    }

    @Override
    public int height() {
        return table[0].length;
    }

    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }

    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                Cell cell = table[y][x]; // מקבלים את התא הנוכחי
                if (cell != null) {
                    String cellValue = eval(x, y);
                    table[y][x] = new SCell(cellValue);
                }
            }
        }
        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = false;
        // Add your code here
        if (xx >= 0 && yy >= 0 && xx < width() && yy < height()) {
            ans = true;
        }
        /////////////////////
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        // Add your code here

        // ///////////////////
        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        BufferedReader reader = null;
        try {
            // פתיחת הקובץ לקריאה
            reader = new BufferedReader(new FileReader(fileName));

            // דילוג על שורת הכותרת הראשונה (לא רלוונטית)
            String line = reader.readLine();

            // קריאת הקובץ שורה אחרי שורה
            while ((line = reader.readLine()) != null) {
                // סינון שורות לא תקינות (יש לוודא שהשורה מתאימה לפורמט x,y,value)
                String[] parts = line.split(",", -1); // פיצול לפי פסיקים

                // ודא שהשורה מכילה בדיוק 3 חלקים: x, y וערך
                if (parts.length >= 3) {
                    try {
                        // המרת x, y לערכים
                        int x = Integer.parseInt(parts[0].trim());
                        int y = Integer.parseInt(parts[1].trim());
                        String value = parts[2].trim();

                        // אם התא נמצא בטווח הגיליון, נעדכן אותו
                        if (isIn(x, y)) {
                            set(x, y, value);
                        }
                    } catch (NumberFormatException e) {
                        // במקרה של חריגה בהמרה לא נוסיף את השורה, לכן מדלגים עליה
                        continue;
                    }
                }
            }
        } finally {
            // סגירת הקובץ אחרי קריאה
            if (reader != null) {
                reader.close();
            }
        }
    }
    @Override
    public void save(String fileName) throws IOException {
        BufferedWriter writer = null;
        try {
            // יצירת קובץ חדש או פתיחה לקובץ קיים
            writer = new BufferedWriter(new FileWriter(fileName));

            // כתיבת כותרת ראשונית (שורה שלא מפורשת בהמשך)
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method\n");

            // עבור כל תא במערך, נוודא שהוא לא ריק, ואז נכתוב אותו לקובץ
            for (int x = 0; x < width(); x++) {
                for (int y = 0; y < height(); y++) {
                    // מקבלים את התא במיקום x, y
                    String value = value(x, y); // הערך של התא בתור מיתר
                    if (!value.equals(Ex2Utils.EMPTY_CELL)) { // אם התא לא ריק
                        // כתיבת הערך לקובץ בפורמט x,y,value
                        writer.write(x + "," + y + "," + value + "\n");
                    }
                }
            }
        } finally {
            // סגירת ה-BufferedWriter אם פתחנו אותו בהצלחה
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public String eval(int x, int y) {
        String ans = null;

        // קבלת התא במיקום (x, y)
        Cell cell = get(x, y);

        if (cell != null) {
            String cellData = cell.toString();

            // אם התא מכיל נוסחה (מתחיל ב- "="), יש לבצע חישוב
            if (cellData.startsWith("=")) {
                String formula = cellData.substring(1); // חותכים את ה"=" מהנוסחה

                // מחשבים את הערך של הנוסחה
                ans = evaluateFormula(formula);
            } else {
                // אם לא נוסחה, מחזירים את הערך של התא ישירות
                ans = cellData;
            }
        }
        return ans;
    }
    private String evaluateFormula(String formula) {
        double result = 0;
        int index = 0;
        boolean negative = false; // כדי לדעת אם החישוב צריך להיות שלילי או חיובי
        char operator = '+'; // ברירת מחדל היא חיבור

        // תו אחרי תו, נבצע את החישוב
        while (index < formula.length()) {
            char currentChar = formula.charAt(index);

            // אם התו הוא ספרה או תו של מספר שלם
            if (Character.isDigit(currentChar)) {
                int numberStart = index;
                while (index < formula.length() && Character.isDigit(formula.charAt(index))) {
                    index++;
                }
                int number = Integer.parseInt(formula.substring(numberStart, index));

                // אם יש פעולת כפל/חילוק (לפני) נבצע את הפעולה המתאימה
                if (operator == '+') {
                    result += (negative ? -number : number);
                } else if (operator == '-') {
                    result -= (negative ? -number : number);
                } else if (operator == '*') {
                    result *= (negative ? -number : number);
                } else if (operator == '/') {
                    result /= (negative ? -number : number);
                }
            }

            // אם ישנו פעולה כמו חיבור, חיסור, כפל או חילוק
            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                operator = currentChar; // שימור הפעולה הנוכחית
                index++;
            }

            // בדוק אם הפסקנו בגלל תו שגוי
            else if (currentChar == ' ' || currentChar == '(' || currentChar == ')') {
                index++;
            }
        }

        return String.valueOf(result);
    }




}
