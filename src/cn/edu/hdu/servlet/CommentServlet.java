package cn.edu.hdu.servlet;

import cn.edu.hdu.entity.CommentModel;
import cn.edu.hdu.jdbc.CommentJdbc;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        CommentJdbc commentJdbc = new CommentJdbc();
        CommentModel commentModel = new CommentModel();
        String comment = request.getParameter("comment");
        int userId =  Integer.parseInt(request.getParameter("userId"));
        long orderId = Long.parseLong(request.getParameter("orderId"));
        int receiverId = commentJdbc.selectReceiverId(orderId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = simpleDateFormat.format(new Date());
        if (commentJdbc.doComment(userId, receiverId, orderId, ctime, comment)) {
            commentModel.setStatus("success");
            if (commentJdbc.showOrder(orderId) != null) {
                commentModel.setData(commentJdbc.showOrder(orderId));
            }
        } else {
            commentModel.setStatus("false");
        }
        String jsonString = JSON.toJSONString(commentModel);
        out.println(jsonString);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
