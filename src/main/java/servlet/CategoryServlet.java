package servlet;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Category;
import service.CategoryService;

@SuppressWarnings("serial")
@WebServlet({ "/CategoryServlet", "/category", "/userDeleteCategory", "/adminDeleteCategory", "/adminRestoreCategory" })
public class CategoryServlet extends HttpServlet {
    private static final int PAGE_SIZE = 8;
    private final CategoryService categoryService = new CategoryService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/adminDeleteCategory":
            	adminDelete(request, response);
                break;
            case "/adminRestoreCategory":
            	adminRestore(request, response);
                break;
            case "/userDeleteCategory":
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

        List<Category> categories = categoryService.getCategoryBySelection(page, PAGE_SIZE, search);
        int totalCategories = categoryService.getCategoryCount(search);
        int totalPages = (int) Math.ceil((double) totalCategories / PAGE_SIZE);

        if (isAjax) {
            // Return JSON for AJAX requests
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Create a response map
            JsonResponse jsonResponse = new JsonResponse(categories, totalPages, page);

            // Convert to JSON
            String json = new Gson().toJson(jsonResponse);

            response.getWriter().write(json);
        } else {
            // Forward to JSP for non-AJAX requests
            request.setAttribute("categories", categories);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("/view/category.jsp").forward(request, response);
        }
    }
    
    // Inner class for JSON response structure
    private class JsonResponse {
        @SuppressWarnings("unused")
		List<Category> categories;
        @SuppressWarnings("unused")
        int totalPages;
        @SuppressWarnings("unused")
        int currentPage;

        JsonResponse(List<Category> categories, int totalPages, int currentPage) {
            this.categories = categories;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
        }
    }
    
    private void userDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        categoryService.userDeleteCategory(categoryId);

        // Redirect to the category page after deletion
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void adminDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        categoryService.adminDeleteCategory(categoryId);

        // Redirect to the category page after deletion
        response.sendRedirect(request.getContextPath() + "/category");
    }
    
    private void adminRestore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        categoryService.adminRestoreCategory(categoryId);

        // Redirect to the category page after deletion
        response.sendRedirect(request.getContextPath() + "/category");
    }
}
