package entity;

public class ReceiptDetail {
    private int receiptID;
    private int productID;
    private int receiptDetailQuantity;
    
	public ReceiptDetail() {
		super();
	}
	
	public ReceiptDetail(int receiptID, int productID, int receiptDetailQuantity) {
		super();
		this.receiptID = receiptID;
		this.productID = productID;
		this.receiptDetailQuantity = receiptDetailQuantity;
	}
	
	public int getReceiptID() {
		return receiptID;
	}
	
	public void setReceiptID(int receiptID) {
		this.receiptID = receiptID;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public int getReceiptDetailQuantity() {
		return receiptDetailQuantity;
	}
	
	public void setReceiptDetailQuantity(int receiptDetailQuantity) {
		this.receiptDetailQuantity = receiptDetailQuantity;
	}
  
}
