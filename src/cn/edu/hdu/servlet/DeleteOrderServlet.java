package cn.edu.hdu.servlet;

import cn.edu.hdu.jdbc.DingdanJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Enzo
 * @Description:
 * @Date: Created in 2017/10/2213:20
 * @Modified By:
 */
@WebServlet("/DeleteOrderServlet")
public class DeleteOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        int userId =  Integer.parseInt(request.getParameter("userId"));
        long orderId = Long.parseLong((request.getParameter("orderId")));
        DingdanJdbc dingdanJdbc=new DingdanJdbc();
        dingdanJdbc.deleteOrder(userId, orderId);
        out.println(dingdanJdbc.showAllOrders());
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
