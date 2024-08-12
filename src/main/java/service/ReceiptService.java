package service;

import dbcontext.DBContext;
import entity.Receipt;
import entity.ReceiptDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ReceiptService {   
	public void addReceipt(Receipt receipt, List<ReceiptDetail> details) {
	    Connection conn = null;
	    PreparedStatement stmtReceipt = null;
	    PreparedStatement pstmtGetId = null;
	    PreparedStatement stmtDetail = null;
	    PreparedStatement stmtUpdateProduct = null;
	    ResultSet rs = null;

	    try {
	        conn = new DBContext().getConnection();
	        conn.setAutoCommit(false); // Begin transaction

	        String sql = "INSERT INTO receipt (receiptTotal, receiptDateCreate, receiptNote, receiptStatus) VALUES (?, ?, ?, ?)";
	        stmtReceipt = conn.prepareStatement(sql);
	        stmtReceipt.setDouble(1, receipt.getReceiptTotal());
	        stmtReceipt.setObject(2, receipt.getReceiptDateCreate());
	        stmtReceipt.setString(3, receipt.getReceiptNote());
	        stmtReceipt.setInt(4, receipt.getReceiptStatus());

	        int flag = stmtReceipt.executeUpdate();

	        if (flag == 1) {
	            int receiptID = 0;
	            String sqlGetReciptID = "SELECT ReceiptID FROM Receipt ORDER BY ReceiptID DESC LIMIT 1";
	            pstmtGetId = conn.prepareStatement(sqlGetReciptID);
	            rs = pstmtGetId.executeQuery();
	            if (rs.next()) {
	                receiptID = rs.getInt("ReceiptID");
	            }

	            if (receiptID != 0) {
	                String sqlReceiptDetail = "INSERT INTO receiptDetail (receiptID, productID, receiptDetailQuantity) VALUES (?, ?, ?)";
	                stmtDetail = conn.prepareStatement(sqlReceiptDetail);

	                String sqlUpdateProduct = "UPDATE product SET productQuantity = productQuantity + ? WHERE productID = ?";
	                stmtUpdateProduct = conn.prepareStatement(sqlUpdateProduct);

	                for (ReceiptDetail receiptDetail : details) {
	                    // Insert into receiptDetail
	                    stmtDetail.setInt(1, receiptID);
	                    stmtDetail.setInt(2, receiptDetail.getProductID());
	                    stmtDetail.setInt(3, receiptDetail.getReceiptDetailQuantity());
	                    stmtDetail.executeUpdate();

	                    // Update product quantity
	                    stmtUpdateProduct.setInt(1, receiptDetail.getReceiptDetailQuantity());
	                    stmtUpdateProduct.setInt(2, receiptDetail.getProductID());
	                    stmtUpdateProduct.executeUpdate();
	                }
	            }
	        }

	        conn.commit(); // Commit transaction

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback(); // Rollback transaction in case of error
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmtReceipt != null) stmtReceipt.close();
	            if (pstmtGetId != null) pstmtGetId.close();
	            if (stmtDetail != null) stmtDetail.close();
	            if (stmtUpdateProduct != null) stmtUpdateProduct.close();
	            if (conn != null) conn.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}

}
