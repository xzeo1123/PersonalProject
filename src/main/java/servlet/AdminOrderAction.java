package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BillDetailService;
import service.OrderService;
import entity.BillDetail;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet({ "/AdminOrderAction", "/orderaction" })
public class AdminOrderAction extends HttpServlet {
    private OrderService orderService = new OrderService();
    private BillDetailService billDetailService = new BillDetailService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int billID = Integer.parseInt(request.getParameter("billID"));

        try {
            switch (action) {
                case "delete":
                    orderService.cancelOrder(billID);
                    break;
                case "confirm":
                    List<BillDetail> details = billDetailService.getBillDetailsByBillID(billID);
                    orderService.confirmOrder(billID, details);
                    break;
                case "complete":
                    orderService.completeOrder(billID);
                    break;
                default:
                    throw new ServletException("Unknown action: " + action);
            }
            response.sendRedirect(request.getContextPath() + "/adminorder"); // Redirect to order list page
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error occurred.", e);
        }
    }
}
