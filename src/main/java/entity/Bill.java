package entity;

import java.time.LocalDateTime;

public class Bill {
    private int billID;
    private double billTotal;
    private LocalDateTime billDateCreate;
    private String billNote;
    private int billStatus; // 1: finished, 2: confirmed, 3: ordering
    private int tableID;

    public Bill(int billID, double billTotal, LocalDateTime billDateCreate, String billNote, int billStatus, int tableID) {
        this.billID = billID;
        this.billTotal = billTotal;
        this.billDateCreate = billDateCreate;
        this.billNote = billNote;
        this.billStatus = billStatus;
        this.tableID = tableID;
    }

    public Bill() {
    }

    // Getters and Setters
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
}
