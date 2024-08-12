package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbcontext.DBContext;
import entity.Table;

public class TableService {

	public List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM `table`";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
            	Table table = new Table();
                table.setTableID(rs.getInt("tableID"));
                table.setTableNumber(rs.getInt("tableNumber"));
                table.setTableStatus(rs.getInt("tableStatus"));
                
                tables.add(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }

        return tables;
    }
    
}
