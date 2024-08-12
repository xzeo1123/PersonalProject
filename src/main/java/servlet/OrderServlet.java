package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.Bill;
import entity.BillDetail;
import service.BillService;
import service.OrderService;
import viewmodel.OrderViewModel;

@SuppressWarnings("serial")
@WebServlet({ "/OrderServlet", "/createOrder", "/adminorder"})
public class OrderServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        List<OrderViewModel> orders = orderService.getAllOrders();
        
        // Set the orders as a request attribute
        request.setAttribute("orders", orders);
        
        // Forward to JSP
        request.getRequestDispatcher("/view/order.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billNote = request.getParameter("billNote");
        String orderItemsJson = request.getParameter("orderItems");
        String orderTotalStr = request.getParameter("orderTotal");
        String tableIDStr = request.getParameter("tableID");

        double orderTotal = Double.parseDouble(orderTotalStr);
        int tableID = Integer.parseInt(tableIDStr);  

        Gson gson = new Gson();
        BillDetail[] orderItems = gson.fromJson(orderItemsJson, BillDetail[].class);

        Bill bill = new Bill();
        bill.setBillTotal(orderTotal);
        bill.setBillDateCreate(LocalDateTime.now());
        bill.setBillNote(billNote);
        bill.setBillStatus(3);
        bill.setTableID(tableID);  // Gán tableID cho Bill

        List<BillDetail> details = new ArrayList<>();
        for (BillDetail item : orderItems) {
            details.add(item);
        }

        BillService billService = new BillService();
        billService.addBill(bill, details);

        response.sendRedirect(request.getContextPath() + "/userorder");
    }

}
