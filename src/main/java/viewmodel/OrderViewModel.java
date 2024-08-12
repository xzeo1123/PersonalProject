package viewmodel;

import java.time.LocalDateTime;
import java.util.List;

public class OrderViewModel {
	private int billID;
    private double billTotal;
    private LocalDateTime billDateCreate;
    private String billNote;
    private int billStatus;
    private int tableID;
    private List<BillDetailViewModel> details;
	public OrderViewModel() {
		super();
	}
	public OrderViewModel(int billID, double billTotal, LocalDateTime billDateCreate, String billNote, int billStatus,
			int tableID, List<BillDetailViewModel> details) {
		super();
		this.billID = billID;
		this.billTotal = billTotal;
		this.billNote = billNote;
		this.billStatus = billStatus;
		this.tableID = tableID;
		this.details = details;
        this.billDateCreate = billDateCreate;
	}
	public int getBillID() {
		return billID;
	}
	public void setBillID(int billID) {
		this.billID = billID;
	}
	public double getBillTotal() {
		return billTotal;
	}
	public void setBillTotal(double billTotal) {
		this.billTotal = billTotal;
	}
	public LocalDateTime getBillDateCreate() {
		return billDateCreate;
	}
	public void setBillDateCreate(LocalDateTime billDateCreate) {
		this.billDateCreate = billDateCreate;
	}
	public String getBillNote() {
		return billNote;
	}
	public void setBillNote(String billNote) {
		this.billNote = billNote;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}
	public int getTableID() {
		return tableID;
	}
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	public List<BillDetailViewModel> getDetails() {
		return details;
	}
	public void setDetails(List<BillDetailViewModel> details) {
		this.details = details;
	}
    
    
}
