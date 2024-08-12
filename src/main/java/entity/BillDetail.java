package entity;

public class BillDetail {
    private int billID;
    private int productID;
    private int billDetailQuantity;

    public BillDetail() {
    }

    public BillDetail(int billID, int productID, int billDetailQuantity) {
        this.billID = billID;
        this.productID = productID;
        this.billDetailQuantity = billDetailQuantity;
    }

    // Getters and Setters
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getBillDetailQuantity() {
        return billDetailQuantity;
    }

    public void setBillDetailQuantity(int billDetailQuantity) {
        this.billDetailQuantity = billDetailQuantity;
    }
}
