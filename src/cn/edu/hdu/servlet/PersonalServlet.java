package cn.edu.hdu.servlet;

import cn.edu.hdu.entity.InfoModel;
import cn.edu.hdu.jdbc.UserJdbc;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/PersonalServlet")
public class PersonalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        int userId = (int) request.getSession().getAttribute("userId");
        String motto = request.getParameter("motto");
        int sex = Integer.parseInt(request.getParameter("sex"));
        String address = request.getParameter("address");
        long phone = Long.parseLong(request.getParameter("phone"));
        UserJdbc userJdbc = new UserJdbc();
        InfoModel infoModel = new InfoModel();
        try {
            if (userJdbc.updateUser(userId, motto, sex, address, phone) == 1) {
                infoModel.setStatus("success");
                infoModel.setData(userJdbc.showUser(userId));
                String jsonString= JSON.toJSONString(infoModel);
                out.println(jsonString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
