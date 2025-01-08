package assignments.ex2;
// Add your documentation below:

public class SCell implements Cell {
    private String data;  // שדה המייצג את הנתונים של התא
    private int type;     // שדה המייצג את סוג התא
    private int order;    // שדה המייצג את הסדר שבו יש לחשב את התא

    public SCell(String data) {
        this.data = data;  // אתחול עם המידע שקיבלת בתור פרמטר
        this.type = Ex2Utils.ERR;  // ערך ברירת מחדל (נוכל לעדכן זאת מאוחר יותר)
        this.order = Ex2Utils.ERR; // ערך ברירת מחדל (נוכל לעדכן זאת מאוחר יותר)
    }

    @Override
    public String getData() {
        return data;  // מחזיר את המידע של התא
    }

    @Override
    public void setData(String data) {
        this.data = data;  // מעדכן את המידע של התא
    }

    @Override
    public int getType() {
        return type;  // מחזיר את סוג התא
    }

    @Override
    public void setType(int type) {
        this.type = type;  // מעדכן את סוג התא
    }

    @Override
    public int getOrder() {
        return order;  // מחזיר את הסדר שבו יש לחשב את התא
    }

    @Override
    public void setOrder(int order) {
        this.order = order;  // מעדכן את הסדר של התא
    }

    @Override
    public String toString() {
        return getData();  // מחזיר את המידע של התא בפורמט טקסט
    }
}