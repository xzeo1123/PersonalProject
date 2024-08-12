package service;

import dbcontext.DBContext;
import entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    public List<Category> getCategoryBySelection(int page, int pageSize, String search) {
        List<Category> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
        	conn = new DBContext().getConnection();
            String sql = "SELECT * FROM Category WHERE CategoryName LIKE ? LIMIT ?, ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + search + "%");
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setCategoryDescription(rs.getString("CategoryDescription"));
                category.setCategoryStatus(rs.getInt("CategoryStatus"));
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }

        return categories;
    }

    public int getCategoryCount(String search) {
        Connection conn = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT COUNT(*) FROM Category WHERE CategoryName LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + search + "%");
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }

        return 0;
    }
    
    public Category getCategoryByID(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Category category = new Category();

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM Category WHERE CategoryID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setCategoryDescription(rs.getString("CategoryDescription"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	new DBContext().closeConnection(conn);
        }

        return category;
    }
    
    public List<Category> getAllCategory() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Category> categories = new ArrayList<>();

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM Category";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	new DBContext().closeConnection(conn);
        }

        return categories;
    }

    
    public void userDeleteCategory(int categoryId) {
    	Connection conn = null;
        PreparedStatement stmt;

        try {
            conn = new DBContext().getConnection();
            String sql = "UPDATE Category SET CategoryStatus = ? WHERE CategoryID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 0);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
    public void adminDeleteCategory(int categoryId) {
        Connection conn = null;
        PreparedStatement stmt;

        try {
            conn = new DBContext().getConnection();
            String sql = "DELETE FROM Category WHERE CategoryID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
    public void adminRestoreCategory(int categoryId) {
    	Connection conn = null;
        PreparedStatement stmt;

        try {
            conn = new DBContext().getConnection();
            String sql = "UPDATE Category SET CategoryStatus = ? WHERE CategoryID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
    // Method to add a new category
    public void addCategory(Category category) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = new DBContext().getConnection();
            String sql = "INSERT INTO Category (CategoryName, CategoryDescription, CategoryStatus) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());
            stmt.setInt(3, 1);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }

    // Method to update an existing category
    public void updateCategory(Category category) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = new DBContext().getConnection();
            String sql = "UPDATE Category SET CategoryName = ?, CategoryDescription = ? WHERE CategoryID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());
            stmt.setInt(3, category.getCategoryID());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
 

}
