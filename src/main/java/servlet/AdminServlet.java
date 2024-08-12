package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet({ "/AdminServlet", "/admin" })
public class AdminServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/view/admin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.equals("nguyendiem") && password.equals("123")) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            request.getRequestDispatcher("/view/admin.jsp").forward(request, response);
        } else {
        	request.setAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu!");
        	request.setAttribute("enteredUsername", username);
        	request.setAttribute("enteredPassword", password);
        	request.getRequestDispatcher("/view/login.jsp").forward(request, response);
        }
	}

}
