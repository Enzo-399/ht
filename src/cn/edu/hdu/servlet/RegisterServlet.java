package cn.edu.hdu.servlet;

import cn.edu.hdu.dao.User;
import cn.edu.hdu.jdbc.MySqlConn;
import cn.edu.hdu.jdbc.UserJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String pw = request.getParameter("pw");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String userCreatetime = simpleDateFormat.format(new Date());
        //检查该用户是否存在
        String sql = "select * from user where name='" + name + "'";
        String str;
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = MySqlConn.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                str = "{\"status\":\"false\",\"data\":{}}";
            } else {
                User user = new User();
                user.setName(name);
                user.setPw(pw);
                user.setCreatetime(userCreatetime);
                UserJdbc userJdbc = new UserJdbc();
                User user1= userJdbc.addUser(user);
                if (user1 != null) {
                    str = "{\"status\":\"success\",\"name\":" + user1.getName() + ",\"pw\":\"" + user1.getPw() + "\"}";
                } else {
                    str = "{\"status\":\"false\",\"data\":{}}";
                }
            }
            out.println(str);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MySqlConn.release(conn, stmt, rs);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
