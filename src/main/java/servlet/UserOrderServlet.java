package servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.Category;
import entity.Product;
import service.CategoryService;
import service.ProductService;

@SuppressWarnings("serial")
@WebServlet({ "/UserOrderServlet", "/userorder" })
public class UserOrderServlet extends HttpServlet {
	private final CategoryService categoryService = new CategoryService();
	private final ProductService productService = new ProductService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categories = categoryService.getAllCategory();
		List<Product> products = productService.getAllProduct();
		
		request.setAttribute("categories", categories);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/view/user/userOrder.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        filterProducts(request, response);
	}
	
	private void filterProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String categoryID = request.getParameter("categoryID");

        List<Product> products = productService.getAllProduct();

        if (search != null && !search.isEmpty()) {
            products = products.stream()
                .filter(p -> p.getProductName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        }

        if (categoryID != null && !categoryID.equals("all")) {
            int catId = Integer.parseInt(categoryID);
            products = products.stream()
                .filter(p -> p.getCategoryID() == catId)
                .collect(Collectors.toList());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(products));
    }

}
