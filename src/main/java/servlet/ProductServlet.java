package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.Product;
import service.ProductService;

@SuppressWarnings("serial")
@WebServlet({ "/ProductServlet", "/product", "/adminDeleteProduct", "/adminRestoreProduct", "/userDeleteProduct"})
public class ProductServlet extends HttpServlet {
	private static final int PAGE_SIZE = 6;
    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/adminDeleteProduct":
            	adminDelete(request, response);
                break;
            case "/adminRestoreProduct":
            	adminRestore(request, response);
                break;
            case "/userDeleteProduct":
            	userDelete(request, response);
                break;
            default:
                Default(request, response);
                break;
        }
    }

    private void Default(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        int page = 1;
        String search = "";
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }

        List<Product> products = productService.getProductBySelection(page, PAGE_SIZE, search);
        int totalCategories = productService.getProductCount(search);
        int totalPages = (int) Math.ceil((double) totalCategories / PAGE_SIZE);

        if (isAjax) {
            // Return JSON for AJAX requests
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Create a response map
            JsonResponse jsonResponse = new JsonResponse(products, totalPages, page);

            // Convert to JSON
            String json = new Gson().toJson(jsonResponse);

            response.getWriter().write(json);
        } else {
            // Forward to JSP for non-AJAX requests
            request.setAttribute("products", products);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("/view/product.jsp").forward(request, response);
        }
    }
    
    // Inner class for JSON response structure
    private class JsonResponse {
        @SuppressWarnings("unused")
		List<Product> products;
        @SuppressWarnings("unused")
        int totalPages;
        @SuppressWarnings("unused")
        int currentPage;

        JsonResponse(List<Product> products, int totalPages, int currentPage) {
            this.products = products;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
        }
    }
    
    private void userDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        productService.userDeleteProduct(productId);

        response.sendRedirect(request.getContextPath() + "/product");
    }

    private void adminDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        productService.adminDeleteProduct(productId);

        response.sendRedirect(request.getContextPath() + "/product");
    }
    
    private void adminRestore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        productService.adminRestoreProduct(productId);

        response.sendRedirect(request.getContextPath() + "/product");
    }
}
