package cn.edu.hdu.servlet;

import cn.edu.hdu.dao.User;
import cn.edu.hdu.entity.LoginModel;
import cn.edu.hdu.jdbc.MySqlConn;
import cn.edu.hdu.jdbc.UserJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String pw = request.getParameter("pw");
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
                User user = new User();
                user.setName(name);
                UserJdbc userJdbc = new UserJdbc();
                LoginModel loginModel = new LoginModel(user);
                User user1 = userJdbc.showUser(user);
                if (user1.getPw() != null) {
                    if (user1.getPw().equals(pw)) {
                        loginModel.setStatus("success");
                        HttpSession sessionId = request.getSession();
                        sessionId.setAttribute("userId", user1.getUserId());
                    } else {
                        loginModel.setStatus("false");
                        if (loginModel.getStatus().equals("false")) {
                            loginModel.setData("");
                        }
                    }
                }
                str = loginModel.toString();
            } else {
                LoginModel loginModel = new LoginModel();
                loginModel.setStatus("false");
                if (loginModel.getStatus().equals("false")) {
                    loginModel.setData("");
                }
                str = loginModel.toString();
            }
            out.println(str);
            out.flush();
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MySqlConn.release(conn, stmt, rs);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
