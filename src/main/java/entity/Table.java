package entity;

public class Table {
    private int tableID;
    private int tableNumber;
    private int tableStatus;

    public Table(int tableID, int tableNumber, int tableStatus) {
        this.tableID = tableID;
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
    }

    public Table() {
    }

    // Getters and Setters
    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(int tableStatus) {
        this.tableStatus = tableStatus;
    }
}
