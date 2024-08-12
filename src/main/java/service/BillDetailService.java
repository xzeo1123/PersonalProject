package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbcontext.DBContext;
import entity.BillDetail;

public class BillDetailService {

    public List<BillDetail> getBillDetailsByBillID(int billID) {
        List<BillDetail> details = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM BillDetail WHERE billID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, billID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                BillDetail detail = new BillDetail();
                detail.setBillID(rs.getInt("billID"));
                detail.setProductID(rs.getInt("productID"));
                detail.setBillDetailQuantity(rs.getInt("billDetailQuantity"));
                
                details.add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }

        return details;
    }
}
