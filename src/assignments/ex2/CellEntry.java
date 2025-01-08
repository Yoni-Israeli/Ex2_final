package assignments.ex2;

public class CellEntry implements Index2D {
    private String _index;

    public CellEntry(String index) {
        this._index = index;  // field שדה
    }

    @Override
    public boolean isValid() {
        if (_index == null || _index.length() < 2) {
            return false;
        }

        char firstChar = _index.charAt(0);

        // אם האות הראשונה אינה אות ולא מספר, החזר false
        if (!Character.isLetter(firstChar) && !Character.isDigit(firstChar)) {
            return false;
        }

        // אם האות הראשונה היא מספר
        if (Character.isDigit(firstChar)) {
            // המחרוזת צריכה להיות באורך 2, כלומר, התו הראשון הוא מספר והאחרון גם מספר
            return _index.length() == 2 && Character.isDigit(_index.charAt(1));
        }

        // אם האות הראשונה היא אות
        if (Character.isLetter(firstChar)) {
            // אם המחרוזת בגודל 2 (אות+מספר)
            if (_index.length() == 2 && Character.isDigit(_index.charAt(1))) {
                return true;
            }
            // אם המחרוזת בגודל 3 (אות+2 מספרים)
            else if (_index.length() == 3) {
                char secondChar1 = _index.charAt(1);
                char secondChar2 = _index.charAt(2);
                if (Character.isDigit(secondChar1) && Character.isDigit(secondChar2)) {
                    int number = (secondChar1 - '0') * 10 + (secondChar2 - '0');
                    return number >= 0 && number <= 99;
                }
            }
        }
        return false;  // אם המחרוזת לא עומדת בתנאים הנ"ל
    }

    @Override
    public int getX() {
        // אם המחרוזת מכילה יותר משני תווים או שהתו הראשון לא אות, החזר Ex2Utils.ERR
        if (_index.length() > 2 || !Character.isLetter(_index.charAt(0))) {
            return Ex2Utils.ERR; // החזר -1 במקרה של אינדקס לא תקני
        }

        // אם האות הראשונה היא אות, נחשב את העמודה (A=0, B=1, C=2, ...)
        char firstChar = _index.charAt(0);
        return Character.toUpperCase(firstChar) - 'A';
    }

    @Override
    public int getY() {
        // נשלוף את המספרים מהמחרוזת ונחזיר אותם כ-Y
        String numberPart = _index.replaceAll("[^0-9]", "");  // משאיר רק את המספרים

        // אם לא נמצאו מספרים, נחזיר ERR
        if (numberPart.isEmpty()) {
            return Ex2Utils.ERR;
        }

        // אם נמצאו מספרים, נחזיר את הערך
        try {
            int y = Integer.parseInt(numberPart);

            // אם המספר לא בטווח 0-99, נחזיר ERR
            if (y < 0 || y > 99) {
                return Ex2Utils.ERR;
            }

            return y;
        } catch (NumberFormatException e) {
            // אם יש בעיה בהמרה למספר, נחזיר ERR
            return Ex2Utils.ERR;
        }
    }

    @Override
    public String toString() {
        return _index;  // מחזיר את המחרוזת עצמה
    }
}