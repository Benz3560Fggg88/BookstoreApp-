package bookstore.model;

public class Book {
    private String title;
    private int quantity;
    private String description;

    public Book(String title, int quantity, String description) {
        this.title = title;
        this.quantity = quantity;
        this.description = description;
    }

    // ✅ Getter & Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ✅ ลดจำนวนเมื่อมีการสั่งซื้อ
    public boolean purchase(int amount) {
        if (amount > 0 && amount <= quantity) {
            quantity -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return title + " | Quantity: " + quantity;
    }

    // ✅ สำหรับบันทึกข้อมูลในไฟล์
    public String toFileString() {
        return title + "|" + quantity + "|" + description;
    }
}
