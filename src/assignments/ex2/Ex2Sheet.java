package assignments.ex2;

import java.io.*;
import java.util.*;

public class Ex2Sheet implements Sheet {
    private String[][] data; // המידע בכל תא (כולל נוסחאות)
    private boolean[][] evaluating; // מערך שמצביע אם תא נמצא בתהליך חישוב
    private int width, height;

    public Ex2Sheet(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new String[height][width];
        this.evaluating = new boolean[height][width]; // מאתחל מערך חדש
    }

    @Override
    public boolean isIn(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public void set(int x, int y, String c) {
        if (isIn(x, y)) {
            data[y][x] = c;
        }
    }

    @Override
    public SCell get(int x, int y) {
        if (isIn(x, y)) {
            return new SCell(data[y][x]);
        }
        return null;
    }

    @Override
    public SCell get(String entry) {
        int x = entry.charAt(0) - 'A';
        int y = Integer.parseInt(entry.substring(1)) - 1;

        if (isIn(x, y)) {
            return new SCell(data[y][x]);
        }
        return null;
    }

    @Override
    public String value(int x, int y) {
        if (isIn(x, y)) {
            return data[y][x];
        }
        return "";
    }

    /**
     * Evaluates (computes) the value of the cell in the x,y coordinate.
     * @param x integer, x-coordinate of the cell.
     * @param y integer, y-coordinate of the cell.
     * @return the string that will be presented in the x,y cell
     */
    // מערך למעקב אחר תאים בתהליך חישוב
    private boolean[] isBeingEvaluated;

    // מערך למעקב אחר תאים בתהליך חישוב
    public String eval(int x, int y) {
        // אתחול של isBeingEvaluated רק אם לא אתחלנו אותו עדיין
        if (isBeingEvaluated == null) {
            isBeingEvaluated = new boolean[width * height];
        }

        if (data[y][x] == null) {
            return null;  // אם התא ריק, מחזירים null
        }

        String cellContent = data[y][x].trim();

        // אם התא מכיל מספר, נחזיר את הערך
        if (cellContent.matches("-?\\d+(\\.\\d+)?")) {
            return cellContent;  // אם התא מכיל רק מספר (כולל שליליים)
        }

        // אם התא מכיל מחרוזת, נחזיר אותה
        if (!cellContent.startsWith("=")) {
            return cellContent;  // אם זה לא נוסחה, מחזירים את המחרוזת
        }

        // אם התא מכיל נוסחה, נבצע את החישוב
        return evalFormula(x, y);
    }

    private String evalFormula(int x, int y) {
        // אם יש כבר תהליך חישוב בתא זה, זיהינו תלות מעגלית
        if (isBeingEvaluated[y * width + x]) {
            return "ERR_CYCLE";  // מחזירים שגיאה על תלות מעגלית
        }

        isBeingEvaluated[y * width + x] = true;  // מסמן את התא ככזה בתהליך חישוב

        String formula = data[y][x].substring(1).trim();  // חותך את ה "=" ומסיר רווחים

        try {
            // טיפול בנוסחאות עם אופרטורים (חיבור, חיסור, כפל, חילוק)
            if (formula.contains("+")) {
                String[] parts = formula.split("\\+");
                double left = evalExpression(parts[0].trim(), x, y);
                double right = evalExpression(parts[1].trim(), x, y);
                return String.valueOf(left + right);
            } else if (formula.contains("-")) {
                String[] parts = formula.split("-");
                double left = evalExpression(parts[0].trim(), x, y);
                double right = evalExpression(parts[1].trim(), x, y);
                return String.valueOf(left - right);
            } else if (formula.contains("*")) {
                String[] parts = formula.split("\\*");
                double left = evalExpression(parts[0].trim(), x, y);
                double right = evalExpression(parts[1].trim(), x, y);
                return String.valueOf(left * right);
            } else if (formula.contains("/")) {
                String[] parts = formula.split("/");
                double left = evalExpression(parts[0].trim(), x, y);
                double right = evalExpression(parts[1].trim(), x, y);
                if (right == 0) {
                    return "ERR_DIV_ZERO";  // טיפול בחלוקה באפס
                }
                return String.valueOf(left / right);
            } else {
                // אם אין אופרטור, מדובר בנוסחה של תא אחר
                double value = evalExpression(formula, x, y);
                return String.valueOf(value);
            }
        } catch (Exception e) {
            return "ERR";  // אם יש שגיאה כלשהי, נחזיר שגיאה כללית
        } finally {
            isBeingEvaluated[y * width + x] = false;  // מסמן את התא כגמור בתהליך חישוב
        }
    }

    private double evalExpression(String expression, int x, int y) {
        // אם מדובר בהפנייה לתא אחר (למשל, A1, B2 וכו')
        if (expression.matches("[A-Z]+\\d+")) {
            int refX = expression.charAt(0) - 'A';  // המרה לעמודה (עבור A, B, C וכו')
            int refY = Integer.parseInt(expression.substring(1)) - 1;  // המרה לשורה (למשל 1 -> 0)
            return Double.parseDouble(eval(refX, refY));  // חישוב הערך של התא המפנה אליו
        } else {
            return Double.parseDouble(expression);  // אם מדובר במספר, נחזיר את הערך
        }
    }





    /**
     * Evaluates (computes) all the values of all the cells in this spreadsheet.
     */
    @Override
    public void eval() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                eval(x, y);  // מחשבים את הערך של כל תא
            }
        }
    }

    /**
     *  Computes a 2D array of the same dimension as this SpreadSheet, each entry holds its dependency depth.
     *  if a cell is not dependent on any other cell its depth is 0.
     *  else assuming the cell depends on cell_1, cell_2... cell_n, the depth of a cell is
     *  1+max(depth(cell_1), depth(cell_2), ... depth(cell_n)).
     *  In case a cell os a circular dependency (e.g., c1 depends on c2 & c2 depends on c1) its depth should be -1.
     */

    @Override
    public int[][] depth() {
        int[][] depths = new int[height][width];
        // אתחול מערך העומקים: -2 מציין שלא נבדק עדיין
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                depths[i][j] = -2;  // -2 מציין שלא נבדק
            }
        }

        // חישוב העומק של כל תא
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (depths[i][j] == -2) { // אם התא לא נבדק
                    calculateDepth(j, i, depths, new HashSet<>());
                }
            }
        }
        return depths;
    }

    private int calculateDepth(int x, int y, int[][] depths, Set<String> visited) {
        // אם התא כבר ביקרנו בו (תלות מעגלית)
        if (visited.contains(x + "," + y)) {
            depths[y][x] = -1;  // תלות מעגלית
            return -1;
        }

        // אם כבר חישבנו את העומק עבור התא, פשוט מחזירים את הערך
        if (depths[y][x] != -2) {
            return depths[y][x];
        }

        // סימון התא כביקור
        visited.add(x + "," + y);

        String cellData = data[y][x];

        // אם התא לא מכיל נוסחה, החזר 0
        if (cellData == null || !cellData.startsWith("=")) {
            depths[y][x] = 0;
            visited.remove(x + "," + y); // נסיים את הביקור בתא
            return 0;
        }

        // חישוב העומק של כל התאים שהנוסחה תלויה בהם
        String formula = cellData.substring(1); // חותכים את הסימן "="
        String[] parts = formula.split("(?=[\\+\\-\\*/])|(?<=[\\+\\-\\*/])");

        int maxDepth = 0;
        for (String part : parts) {
            part = part.trim();  // אם אין ערך, אל תנסה לקרוא trim()

            // אם החלק הוא תא (למשל A1)
            if (part.matches("[A-Z]+\\d+")) {
                // שליפת קואורדינטות התא
                int cellX = part.charAt(0) - 'A'; // המרה מעמודה כמו A ל-x
                int cellY = Integer.parseInt(part.substring(1)) - 1; // המרה משורה כמו 1 ל-y

                int dep = calculateDepth(cellX, cellY, depths, visited);
                if (dep == -1) {
                    depths[y][x] = -1;  // אם מצאנו תלות מעגלית, כל התאים בתלות הזו יהיו -1
                    visited.remove(x + "," + y); // נסיים את הביקור בתא
                    return -1;
                }

                maxDepth = Math.max(maxDepth, dep);
            }
        }

        // העומק של התא הוא 1 + מקסימום העומקים של התאים שהוא תלוי בהם
        depths[y][x] = maxDepth + 1;

        visited.remove(x + "," + y); // נסיים את הביקור בתא
        return depths[y][x];
    }



    @Override
    public void save(String fileName) throws IOException {
        // יצירת קובץ חדש בכתובת שנמסרה
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        // כותב את הכותרת
        writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method");
        writer.newLine();

        // עבור על כל התאים בגיליון
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                String cellData = data[i][j];
                if (cellData != null && !cellData.isEmpty()) {
                    // כותב את המיקום של התא (x,y), הערך שלו
                    writer.write(j + "," + i + "," + cellData);
                    writer.newLine();
                }
            }
        }
        // סוגר את ה-BufferedWriter
        writer.close();
    }


    @Override
    public void load(String fileName) throws IOException {
        // קורא את הקובץ שהוזן
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        // שורה ראשונה שהיא כותרת ויש להתעלם ממנה
        String line = reader.readLine();

        // קורא את כל שאר השורות בקובץ
        while ((line = reader.readLine()) != null) {
            // אם השורה מכילה מידע, נפרק אותה לפי פסיקים
            String[] parts = line.split(",");

            if (parts.length >= 3) {
                try {
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    String cellData = parts[2];

                    // אם התא בתא זה לא מחוץ לגבולות, נעדכן את הנתון
                    if (isIn(x, y)) {
                        set(x, y, cellData);
                    }
                } catch (NumberFormatException e) {
                    // אם יש טעות בפירוק המספרים, פשוט מתעלמים מהשורה הזו
                }
            }
        }
        // סוגר את ה-BufferedReader
        reader.close();
    }


    public static class Cell {
        private String data;

        public Cell(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }
}
