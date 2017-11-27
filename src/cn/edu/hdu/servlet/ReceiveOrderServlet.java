package cn.edu.hdu.servlet;

import cn.edu.hdu.entity.InfoModel;
import cn.edu.hdu.jdbc.DingdanJdbc;
import com.alibaba.fastjson.JSON;

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
 * @Date: Created in 2017/10/2314:51
 * @Modified By:
 */
@WebServlet("/ReceiveOrderServlet")
public class ReceiveOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        int userId =  Integer.parseInt(request.getParameter("userId"));
        DingdanJdbc jdbc = new DingdanJdbc();
        InfoModel infoModel = new InfoModel();
        if (request.getParameter("orderId") != null) {
            long orderId = Long.parseLong(request.getParameter("orderId"));
            if (jdbc.receiveOrder(userId, orderId) == 1) {
                infoModel.setStatus("success");
                infoModel.setData(jdbc.showOrder(orderId));
                String jsonString= JSON.toJSONString(infoModel);
                out.println(jsonString);
            }
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
