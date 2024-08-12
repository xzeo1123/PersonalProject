package entity;

import java.time.LocalDateTime;

public class Receipt {
    private int receiptID;
    private double receiptTotal;
    private LocalDateTime receiptDateCreate;
    private String receiptNote;
    private int receiptStatus;
    
	public Receipt(int receiptID, double receiptTotal, LocalDateTime receiptDateCreate, String receiptNote,
			int receiptStatus) {
		super();
		this.receiptID = receiptID;
		this.receiptTotal = receiptTotal;
		this.receiptDateCreate = receiptDateCreate;
		this.receiptNote = receiptNote;
		this.receiptStatus = receiptStatus;
	}
	
	public Receipt() {
		super();
	}
	
	public int getReceiptID() {
		return receiptID;
	}
	
	public void setReceiptID(int receiptID) {
		this.receiptID = receiptID;
	}
	
	public double getReceiptTotal() {
		return receiptTotal;
	}
	
	public void setReceiptTotal(double receiptTotal) {
		this.receiptTotal = receiptTotal;
	}
	
	public LocalDateTime getReceiptDateCreate() {
		return receiptDateCreate;
	}
	public void setReceiptDateCreate(LocalDateTime receiptDateCreate) {
		this.receiptDateCreate = receiptDateCreate;
	}
	
	public String getReceiptNote() {
		return receiptNote;
	}
	
	public void setReceiptNote(String receiptNote) {
		this.receiptNote = receiptNote;
	}
	public int getReceiptStatus() {
		return receiptStatus;
	}
	
	public void setReceiptStatus(int receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

    
}
