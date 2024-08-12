package service;

import dbcontext.DBContext;
import entity.Bill;
import entity.BillDetail;
import viewmodel.BillDetailViewModel;
import viewmodel.OrderViewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private BillService billService = new BillService();
    private BillDetailService billDetailService = new BillDetailService();
    private DBContext dbContext = new DBContext();

    public List<OrderViewModel> getAllOrders() {
        List<OrderViewModel> orderViewModels = new ArrayList<>();
        Connection conn = null;

        try {
            conn = dbContext.getConnection();
            List<Bill> bills = billService.getAllBills();

            for (Bill bill : bills) {
                List<BillDetail> details = billDetailService.getBillDetailsByBillID(bill.getBillID());

                List<BillDetailViewModel> detailViewModels = new ArrayList<>();
                for (BillDetail detail : details) {
                    String productName = getProductNameById(conn, detail.getProductID());

                    BillDetailViewModel viewModel = new BillDetailViewModel(
                        detail.getProductID(),
                        productName,
                        detail.getBillDetailQuantity()
                    );
                    detailViewModels.add(viewModel);
                }

                OrderViewModel viewModel = new OrderViewModel(
                    bill.getBillID(),
                    bill.getBillTotal(),
                    bill.getBillDateCreate(),
                    bill.getBillNote(),
                    bill.getBillStatus(),
                    bill.getTableID(),
                    detailViewModels
                );
                orderViewModels.add(viewModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbContext.closeConnection(conn);
        }

        return orderViewModels;
    }

    // Phương thức để lấy tên sản phẩm từ ID
    private String getProductNameById(Connection conn, int productID) throws SQLException {
        String productName = null;
        String sql = "SELECT ProductName FROM Product WHERE ProductID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    productName = rs.getString("ProductName");
                }
            }
        }
        return productName;
    }


    public void cancelOrder(int billID) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbContext.getConnection();
            conn.setAutoCommit(false);

            // Delete BillDetails
            String deleteDetailsSQL = "DELETE FROM BillDetail WHERE billID = ?";
            pstmt = conn.prepareStatement(deleteDetailsSQL);
            pstmt.setInt(1, billID);
            pstmt.executeUpdate();

            // Delete Bill
            String deleteBillSQL = "DELETE FROM Bill WHERE billID = ?";
            pstmt = conn.prepareStatement(deleteBillSQL);
            pstmt.setInt(1, billID);
            pstmt.executeUpdate();

            conn.commit();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            dbContext.closeConnection(conn);
        }
    }

    public void confirmOrder(int billID, List<BillDetail> details) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbContext.getConnection();
            conn.setAutoCommit(false);

            // Update Bill Status
            String updateBillSQL = "UPDATE Bill SET billStatus = 2 WHERE billID = ?";
            pstmt = conn.prepareStatement(updateBillSQL);
            pstmt.setInt(1, billID);
            pstmt.executeUpdate();

            // Update Product Quantity
            String updateProductSQL = "UPDATE Product SET productQuantity = productQuantity - ? WHERE productID = ?";
            pstmt = conn.prepareStatement(updateProductSQL);

            for (BillDetail detail : details) {
                pstmt.setInt(1, detail.getBillDetailQuantity());
                pstmt.setInt(2, detail.getProductID());
                pstmt.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            dbContext.closeConnection(conn);
        }
    }

    public void completeOrder(int billID) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbContext.getConnection();
            String updateBillSQL = "UPDATE Bill SET billStatus = 1 WHERE billID = ?";
            pstmt = conn.prepareStatement(updateBillSQL);
            pstmt.setInt(1, billID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            dbContext.closeConnection(conn);
        }
    }
}
