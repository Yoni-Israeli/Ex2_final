package assignments.ex2;
// Add your documentation below:

public class CellEntry implements Index2D {
    private String _index;

    public CellEntry(String index) {//builder
        this._index = index;// field שדה
    }

    /**
     * this function check if a string is valid
     * if string is null or shorter than 2 its invalid
     * if first char in the string is not a letter or a number its invalid
     * if first char is letter there can be only numbers after and only between 0-99
     * if first char is number there can be no letter after
     *
     * @return validation of String : true/false
     */
    @Override
    public boolean isValid() {

        if (_index == null || _index.length() < 2) {
            return false;
        }


        char firstChar = _index.charAt(0);


        if (!Character.isLetter(firstChar) && !Character.isDigit(firstChar)) {
            return false;
        }


        if (Character.isDigit(firstChar)) {

            if (_index.length() == 2 && Character.isDigit(_index.charAt(1))) {
                return true;
            }
            return false;
        }


        if (Character.isLetter(firstChar)) {

            if (_index.length() == 2 && Character.isDigit(_index.charAt(1))) {
                return true;
            } else if (_index.length() == 3) {

                char secondChar1 = _index.charAt(1);
                char secondChar2 = _index.charAt(2);


                if (Character.isDigit(secondChar1) && Character.isDigit(secondChar2)) {
                    int number = (secondChar1 - '0') * 10 + (secondChar2 - '0');
                    if (number >= 0 && number <= 99) {
                        return true;
                    }
                }
            }
        }


        return false;
    }

    @Override
    public int getX() {
        return Ex2Utils.ERR;
    }

    @Override
    public int getY() {
        return Ex2Utils.ERR;
    }
}
