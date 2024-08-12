package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Category;
import service.CategoryService;

@SuppressWarnings("serial")
@WebServlet({ "/CategoryActionServlet", "/updateCategory", "/addCategory" })
public class CategoryActionServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");

        String action = request.getServletPath();

        if (action.equalsIgnoreCase("/updateCategory")) {
            showUpdateCategoryForm(request, response);
        } else if (action.equalsIgnoreCase("/addCategory")) {
            showAddCategoryForm(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");

        String action = request.getServletPath();

        if (action.equalsIgnoreCase("/addCategory")) {
            addCategory(request, response);
        } else if (action.equalsIgnoreCase("/updateCategory")) {
            updateCategory(request, response);
        }
    }
    
    private void showAddCategoryForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "add");
        request.getRequestDispatcher("/view/actionCategory.jsp").forward(request, response);
    }

    private void showUpdateCategoryForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryID"));
        Category category = categoryService.getCategoryByID(categoryId);

        request.setAttribute("action", "update");
        request.setAttribute("category", category);
        
        request.getRequestDispatcher("/view/actionCategory.jsp").forward(request, response);
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        String categoryDescription = request.getParameter("categoryDescription");

        Category category = new Category(0, categoryName, categoryDescription, 1);
        categoryService.addCategory(category);

        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryID"));
        String categoryName = request.getParameter("categoryName");
        String categoryDescription = request.getParameter("categoryDescription");

        Category category = new Category(categoryId, categoryName, categoryDescription, 1);
        categoryService.updateCategory(category);

        response.sendRedirect(request.getContextPath() + "/category");
    }
}
