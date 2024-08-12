package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import entity.Receipt;
import entity.ReceiptDetail;
import service.CategoryService;
import service.ProductService;
import service.ReceiptService;

@SuppressWarnings("serial")
@WebServlet({ "/InvoiceServlet", "/invoice" })
public class InvoiceServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();
    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAllCategory();
        List<Product> products = productService.getAllProduct();

        request.setAttribute("categories", categories);
        request.setAttribute("products", products);

        request.getRequestDispatcher("/view/invoice.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("filterProducts".equals(action)) {
            filterProducts(request, response);
        } else if ("addReceipt".equals(action)) {
            addReceipt(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void filterProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String categoryID = request.getParameter("categoryID");
        String sortOrder = request.getParameter("sortOrder");

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

        if (sortOrder != null) {
            if ("asc".equals(sortOrder)) {
                products = products.stream()
                    .sorted((p1, p2) -> Integer.compare(p1.getProductQuantity(), p2.getProductQuantity()))
                    .collect(Collectors.toList());
            } else if ("desc".equals(sortOrder)) {
                products = products.stream()
                    .sorted((p1, p2) -> Integer.compare(p2.getProductQuantity(), p1.getProductQuantity()))
                    .collect(Collectors.toList());
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(products));
    }
    
    private void addReceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");
    	
        double reciptTotal = Double.parseDouble(request.getParameter("totalAmount"));
        String reciptNote = request.getParameter("invoiceNote");

        List<ReceiptDetail> reciptDetails = new ArrayList<>();
        int index = 0;
        while (request.getParameter("productID" + index) != null) {
            int productID = Integer.parseInt(request.getParameter("productID" + index));
            int reciptDetailQuantity = Integer.parseInt(request.getParameter("quantity" + index));
            reciptDetails.add(new ReceiptDetail(0, productID, reciptDetailQuantity));
            index++;
        }

        Receipt receipt = new Receipt(0, reciptTotal, LocalDateTime.now(), reciptNote, 1);
        ReceiptService receiptService = new ReceiptService();
        receiptService.addReceipt(receipt, reciptDetails);

        response.sendRedirect(request.getContextPath() + "/invoice");
    }
}
