package entity;

public class Product {
    private int productID;
    private String productName;
    private String productImage;
    private double productImportPrice;
    private double productSalePrice;
    private int productQuantity;
    private int productStatus;
    private int categoryID;

    public Product(int productID, String productName, String productImage, double productImportPrice,
			double productSalePrice, int productQuantity, int productStatus, int categoryID) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productImage = productImage;
		this.productImportPrice = productImportPrice;
		this.productSalePrice = productSalePrice;
		this.productQuantity = productQuantity;
		this.productStatus = productStatus;
		this.categoryID = categoryID;
	}

	public Product() {
    }

    // Getters and Setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public double getProductImportPrice() {
        return productImportPrice;
    }

    public void setProductImportPrice(double productImportPrice) {
        this.productImportPrice = productImportPrice;
    }

    public double getProductSalePrice() {
        return productSalePrice;
    }

    public void setProductSalePrice(double productSalePrice) {
        this.productSalePrice = productSalePrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}

