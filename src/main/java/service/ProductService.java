package service;

import dbcontext.DBContext;
import entity.Product;
import viewmodel.ProductViewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
	public List<Product> getProductBySelection(int page, int pageSize, String search) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT p.*, c.CategoryName FROM Product p "
                       + "JOIN Category c ON p.CategoryID = c.CategoryID "
                       + "WHERE p.ProductName LIKE ? LIMIT ?, ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + search + "%");
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductImage(rs.getString("ProductImage"));
                product.setProductImportPrice(rs.getDouble("ProductImportPrice"));
                product.setProductSalePrice(rs.getDouble("ProductSalePrice"));
                product.setProductQuantity(rs.getInt("ProductQuantity"));
                product.setProductStatus(rs.getInt("ProductStatus"));
                product.setCategoryID(rs.getInt("CategoryID"));
                
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }

        return products;
    }

    public int getProductCount(String search) {
        Connection conn = null;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT COUNT(*) FROM Product WHERE ProductName LIKE ?";
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
    
    public ProductViewModel getProductByID(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Product product = new Product();
        ProductViewModel productViewModel = new ProductViewModel();

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT p.*, c.CategoryName FROM Product p "
                    + "JOIN Category c ON p.CategoryID = c.CategoryID "
                    + "WHERE p.ProductID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductImage(rs.getString("ProductImage"));
                product.setProductImportPrice(rs.getDouble("ProductImportPrice"));
                product.setProductSalePrice(rs.getDouble("ProductSalePrice"));
                product.setProductQuantity(rs.getInt("ProductQuantity"));
                product.setProductStatus(rs.getInt("ProductStatus"));
                product.setCategoryID(rs.getInt("CategoryID"));
                
                productViewModel.setProduct(product);
                productViewModel.setCategoryName(rs.getString("CategoryName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	new DBContext().closeConnection(conn);
        }

        return productViewModel;
    }
    
    public List<Product> getAllProduct() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList<Product>();

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * From Product";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
            	Product product = new Product();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductImage(rs.getString("ProductImage"));
                product.setProductImportPrice(rs.getDouble("ProductImportPrice"));
                product.setProductSalePrice(rs.getDouble("ProductSalePrice"));
                product.setProductQuantity(rs.getInt("ProductQuantity"));
                product.setProductStatus(rs.getInt("ProductStatus"));
                product.setCategoryID(rs.getInt("CategoryID"));
                
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	new DBContext().closeConnection(conn);
        }

        return products;
    }
    
    public List<Product> getAllProductByCateID(int cateID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList<Product>();

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * From Product Where CategoryID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cateID);
            rs = stmt.executeQuery();

            while (rs.next()) {
            	Product product = new Product();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductImage(rs.getString("ProductImage"));
                product.setProductImportPrice(rs.getDouble("ProductImportPrice"));
                product.setProductSalePrice(rs.getDouble("ProductSalePrice"));
                product.setProductQuantity(rs.getInt("ProductQuantity"));
                product.setProductStatus(rs.getInt("ProductStatus"));
                product.setCategoryID(rs.getInt("CategoryID"));
                
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	new DBContext().closeConnection(conn);
        }

        return products;
    }

    
    public void userDeleteProduct(int productId) {
    	Connection conn = null;
        PreparedStatement stmt;

        try {
            conn = new DBContext().getConnection();
            String sql = "UPDATE Product SET ProductStatus = ? WHERE ProductID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 0);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
    public void adminDeleteProduct(int productId) {
        Connection conn = null;
        PreparedStatement stmt;

        try {
            conn = new DBContext().getConnection();
            String sql = "DELETE FROM Product WHERE ProductID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
    public void adminRestoreProduct(int productId) {
    	Connection conn = null;
        PreparedStatement stmt;

        try {
            conn = new DBContext().getConnection();
            String sql = "UPDATE Product SET ProductStatus = ? WHERE ProductID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
    public void addProduct(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = new DBContext().getConnection();
            String sql = "INSERT INTO Product (ProductName, ProductImage, ProductImportPrice, ProductSalePrice, "
            			+ "ProductQuantity, ProductStatus, CategoryID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductImage());
            stmt.setDouble(3, product.getProductImportPrice());
            stmt.setDouble(4, product.getProductSalePrice());
            stmt.setInt(5, 0);
            stmt.setInt(6, 1);
            stmt.setInt(7, product.getCategoryID());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }

    public void updateProduct(Product product, boolean isPriceChanged) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = new DBContext().getConnection();

            if (isPriceChanged) {
                // Thay đổi giá bán thì tạo sản phẩm mới
                String newProductSql = "INSERT INTO Product (ProductName, ProductImage, ProductImportPrice, ProductSalePrice, "
                        + "ProductQuantity, ProductStatus, CategoryID) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(newProductSql);
                stmt.setString(1, product.getProductName());
                stmt.setString(2, product.getProductImage());
                stmt.setDouble(3, product.getProductImportPrice());
                stmt.setDouble(4, product.getProductSalePrice());
                stmt.setInt(5, product.getProductQuantity()); // Số lượng chuyển từ sản phẩm cũ
                stmt.setInt(6, 1);
                stmt.setInt(7, product.getCategoryID());
                stmt.executeUpdate();

                // Lấy ID của sản phẩm mới
                Product newProduct = getLatestProduct();
                int newProductId = newProduct.getProductID();

                // Cập nhật số lượng của sản phẩm cũ thành 0
                String oldProductSql = "UPDATE Product SET ProductQuantity = 0 WHERE ProductID = ?";
                stmt = conn.prepareStatement(oldProductSql);
                stmt.setInt(1, product.getProductID());
                stmt.executeUpdate();

                // Cập nhật ID của sản phẩm mới vào đối tượng sản phẩm
                product.setProductID(newProductId);
            } else {
                // Nếu không thay đổi giá bán, chỉ cập nhật thông tin sản phẩm hiện tại
                String sql = "UPDATE Product SET ProductName = ?, ProductImage = ?, ProductImportPrice = ?, "
                        + "ProductSalePrice = ?, ProductQuantity = ?, ProductStatus = ?, CategoryID = ? WHERE ProductID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, product.getProductName());
                stmt.setString(2, product.getProductImage());
                stmt.setDouble(3, product.getProductImportPrice());
                stmt.setDouble(4, product.getProductSalePrice());
                stmt.setInt(5, product.getProductQuantity());
                stmt.setInt(6, 1);
                stmt.setInt(7, product.getCategoryID());
                stmt.setInt(8, product.getProductID());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new DBContext().closeConnection(conn);
        }
    }
    
    public Product getLatestProduct() {
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Product product = new Product();

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM Product ORDER BY productID DESC LIMIT 1";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductImage(rs.getString("ProductImage"));
                product.setProductImportPrice(rs.getDouble("ProductImportPrice"));
                product.setProductSalePrice(rs.getDouble("ProductSalePrice"));
                product.setProductQuantity(rs.getInt("ProductQuantity"));
                product.setProductStatus(rs.getInt("ProductStatus"));
                product.setCategoryID(rs.getInt("CategoryID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	new DBContext().closeConnection(conn);
        }

        return product;
    }

}
