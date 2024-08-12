package servlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entity.Category;
import entity.Product;
import service.CategoryService;
import service.ProductService;
import viewmodel.ProductViewModel;

@SuppressWarnings("serial")
@MultipartConfig
@WebServlet({"/ProductActionServlet", "/addProduct", "/updateProduct"})
public class ProductActionServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equalsIgnoreCase("/updateProduct")) {
            showUpdateProductForm(request, response);
        } else if (action.equalsIgnoreCase("/addProduct")) {
            showAddProductForm(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equalsIgnoreCase("/addProduct")) {
            addProduct(request, response);
        } else if (action.equalsIgnoreCase("/updateProduct")) {
            updateProduct(request, response);
        }
    }

    private void showAddProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "add");
        List<Category> categories = categoryService.getAllCategory();
        
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("/view/actionProduct.jsp").forward(request, response);
    }

    private void showUpdateProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productID"));
        ProductViewModel productViewModel = productService.getProductByID(productId);
        List<Category> categories = categoryService.getAllCategory();

        request.setAttribute("action", "update");
        request.setAttribute("product", productViewModel);
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("/view/actionProduct.jsp").forward(request, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        double productImportPrice = Double.parseDouble(request.getParameter("productImportPrice"));
        double productSalePrice = Double.parseDouble(request.getParameter("productSalePrice"));
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        Part productImagePart = request.getPart("productImage");

        Product product = new Product();
        product.setProductName(productName);
        product.setProductImportPrice(productImportPrice);
        product.setProductSalePrice(productSalePrice);
        product.setProductQuantity(0);
        product.setProductStatus(1);
        product.setCategoryID(categoryID);

        // Lưu sản phẩm trước
        productService.addProduct(product);

        // Lấy sản phẩm mới nhất để lấy ID của nó
        Product latestProduct = productService.getLatestProduct();
        int productId = latestProduct.getProductID();

        // Lưu ảnh với tên duy nhất
        String productImage = saveProductImage(request, productImagePart, productId);

        // Cập nhật sản phẩm với tên ảnh mới
        product.setProductID(productId);
        product.setProductImage(productImage);

        productService.updateProduct(product, false);

        response.sendRedirect(request.getContextPath() + "/product");
    }
    
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        double productImportPrice = Double.parseDouble(request.getParameter("productImportPrice"));
        double productSalePrice = Double.parseDouble(request.getParameter("productSalePrice"));
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        Part productImagePart = request.getPart("productImage");

        // Get the current product details
        ProductViewModel currentProduct = productService.getProductByID(productID);

        // Determine if the price has changed
        boolean isPriceChanged = productSalePrice != currentProduct.getProduct().getProductSalePrice();

        // If a new image is uploaded, delete the old image
        if (productImagePart != null && productImagePart.getSize() > 0) {
            String currentImageFileName = currentProduct.getProduct().getProductImage();
            String realPath = request.getServletContext().getRealPath("/images");
            Path currentImagePath = Paths.get(realPath, currentImageFileName);

            // Delete the old image file
            Files.deleteIfExists(currentImagePath);

            // Save the new image
            String newProductImage = saveProductImage(request, productImagePart, productID);
            currentProduct.getProduct().setProductImage(newProductImage);
        }

        // Update other product details
        currentProduct.getProduct().setProductName(productName);
        currentProduct.getProduct().setProductImportPrice(productImportPrice);
        currentProduct.getProduct().setProductSalePrice(productSalePrice);
        currentProduct.getProduct().setCategoryID(categoryID);

        // Call the updated updateProduct method in ProductService
        productService.updateProduct(currentProduct.getProduct(), isPriceChanged);

        response.sendRedirect(request.getContextPath() + "/product");
    }

    private String saveProductImage(HttpServletRequest request, Part imagePart, int productId) throws ServletException, IOException {
        String realPath = request.getServletContext().getRealPath("/images");
        String fileName = "";

        if (!Files.exists(Path.of(realPath))) {
            Files.createDirectories(Path.of(realPath));
        }

        // Kiểm tra nếu người dùng không upload ảnh mới
        if (imagePart == null || imagePart.getSize() == 0) {
            // Đường dẫn đến ảnh default
            String defaultImagePath = Paths.get(realPath, "default.jpg").toString();
            // Đường dẫn đến ảnh mới được sao chép
            String newImagePath = Paths.get(realPath, productId + "_default.jpg").toString();
            
            // Sao chép ảnh default.jpg thành productId_default.jpg
            Files.copy(Paths.get(defaultImagePath), Paths.get(newImagePath));
            
            fileName = productId + "_default.jpg";
        } else {
            String originalFileName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = productId + "_" + originalFileName;
            fileName = uniqueFileName;
            imagePart.write(Paths.get(realPath, fileName).toString());
        }

        return fileName;
    }


}
