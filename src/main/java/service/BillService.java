package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbcontext.DBContext;
import entity.Bill;
import entity.BillDetail;

public class BillService {
	public void addBill(Bill bill, List<BillDetail> details) {
	    Connection conn = null;
	    PreparedStatement stmtBill = null;
	    PreparedStatement pstmtGetId = null;
	    PreparedStatement stmtDetail = null;
	    ResultSet rs = null;

	    try {
	        conn = new DBContext().getConnection();
	        conn.setAutoCommit(false);

	        String sql = "INSERT INTO bill (billTotal, billDateCreate, billNote, billStatus, tableID) VALUES (?, ?, ?, ?, ?)";
	        stmtBill = conn.prepareStatement(sql);
	        stmtBill.setDouble(1, bill.getBillTotal());
	        stmtBill.setObject(2, bill.getBillDateCreate());
	        stmtBill.setString(3, bill.getBillNote());
	        stmtBill.setInt(4, bill.getBillStatus());
	        stmtBill.setInt(5, bill.getTableID());

	        int flag = stmtBill.executeUpdate();

	        if (flag == 1) {
	            int billID = 0;
	            String sqlGetBillID = "SELECT BillID FROM Bill ORDER BY BillID DESC LIMIT 1";
	            pstmtGetId = conn.prepareStatement(sqlGetBillID);
	            rs = pstmtGetId.executeQuery();
	            if (rs.next()) {
	            	billID = rs.getInt("BillID");
	            }

	            if (billID != 0) {
	                String sqlReceiptDetail = "INSERT INTO BillDetail (billID, productID, billDetailQuantity) VALUES (?, ?, ?)";
	                stmtDetail = conn.prepareStatement(sqlReceiptDetail);

	                for (BillDetail billDetail : details) {
	                    stmtDetail.setInt(1, billID);
	                    stmtDetail.setInt(2, billDetail.getProductID());
	                    stmtDetail.setInt(3, billDetail.getBillDetailQuantity());
	                    stmtDetail.executeUpdate();
	                }
	            }
	        }

	        conn.commit();

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback(); 
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    } finally {
	    	new DBContext().closeConnection(conn);
	    }
	}
	
	public void updateBillStatus(int billID, int status) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = new DBContext().getConnection();

            String sql = "UPDATE Bill SET BillStatus = ? WHERE BillID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, status);
            stmt.setInt(2, billID);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
	
	public void reduceProduct(List<BillDetail> details) {
		Connection conn = null;
	    PreparedStatement stmtUpdateProduct = null;

	    try {
	        conn = new DBContext().getConnection();
	        conn.setAutoCommit(false);
	        
	        String sqlUpdateProduct = "UPDATE product SET productQuantity = productQuantity - ? WHERE productID = ?";
            stmtUpdateProduct = conn.prepareStatement(sqlUpdateProduct);

            for (BillDetail billDetail : details) {
                stmtUpdateProduct.setInt(1, billDetail.getBillDetailQuantity());
                stmtUpdateProduct.setInt(2, billDetail.getProductID());
                stmtUpdateProduct.executeUpdate();
            }
	        conn.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	new DBContext().closeConnection(conn);
	    }
	}
	
	public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM Bill Where BillStatus != 1";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillID(rs.getInt("billID"));
                bill.setBillTotal(rs.getDouble("billTotal"));
                bill.setBillDateCreate(rs.getTimestamp("billDateCreate").toLocalDateTime());
                bill.setBillNote(rs.getString("billNote"));
                bill.setBillStatus(rs.getInt("billStatus"));
                bill.setTableID(rs.getInt("tableID"));
                
                bills.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }

        return bills;
    }
}
