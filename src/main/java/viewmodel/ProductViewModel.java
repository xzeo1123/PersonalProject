package viewmodel;

import entity.Product;

public class ProductViewModel {
    private Product product;
    private String categoryName;
    
	public ProductViewModel() {
		super();
	}
	
	public ProductViewModel(Product product, String categoryName) {
		super();
		this.product = product;
		this.categoryName = categoryName;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
