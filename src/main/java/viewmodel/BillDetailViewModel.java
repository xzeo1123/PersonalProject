package viewmodel;

public class BillDetailViewModel {
	private int productID;
    private String productName; // Thêm thuộc tính productName
    private int billDetailQuantity;
	public BillDetailViewModel() {
		super();
	}
	public BillDetailViewModel(int productID, String productName, int billDetailQuantity) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.billDetailQuantity = billDetailQuantity;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getBillDetailQuantity() {
		return billDetailQuantity;
	}
	public void setBillDetailQuantity(int billDetailQuantity) {
		this.billDetailQuantity = billDetailQuantity;
	}
    
    
}
