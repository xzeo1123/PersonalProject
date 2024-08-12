package entity;

public class Category {
    private int categoryID;
    private String categoryName;
    private String categoryDescription;
    private int categoryStatus;
    // 0: deleted
    // 1: working

    public Category(int categoryID, String categoryName, String categoryDescription, int categoryStatus) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryStatus = categoryStatus;
    }

    public Category() {
    }

    // Getters and Setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public int getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(int categoryStatus) {
        this.categoryStatus = categoryStatus;
    }
}
