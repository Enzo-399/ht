package cn.edu.hdu.servlet;

import cn.edu.hdu.jdbc.DingdanJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: Enzo
 * @Description: add one order
 * @Date: Created in 2017/10/21:23:16
 * @Modified By:
 */
@WebServlet("/AddOrderServlet")
public class AddOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        Random random = new Random();
        long orderId = Long.parseLong(random.nextInt(10)
                + ((System.currentTimeMillis() + "").substring(1)
                + (System.nanoTime() + "").substring(7, 10)));
        int code = Integer.parseInt(request.getParameter("code"));
        long phone = Long.parseLong((request.getParameter("phone")));
        String location = request.getParameter("location");
        int time = Integer.parseInt(request.getParameter("time"));
        String remark = request.getParameter("remark");
        int value = Integer.parseInt(request.getParameter("value"));
        int userId =  Integer.parseInt(request.getParameter("userId"));
        int urgency = Integer.parseInt(request.getParameter("urgency"));
        int price = Integer.parseInt(request.getParameter("price"));
        int receive_location = Integer.parseInt(request.getParameter("receive_location"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderCreatetime = simpleDateFormat.format(new Date());
        DingdanJdbc dingdanJdbc = new DingdanJdbc();
        dingdanJdbc.addOrder(userId, orderId, code, phone, location, time, remark, value, urgency, orderCreatetime, price,receive_location);
        out.println(dingdanJdbc.showAllUnFinishedOrders());
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
