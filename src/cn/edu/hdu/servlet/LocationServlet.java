package cn.edu.hdu.servlet;

import cn.edu.hdu.entity.InfoModel;
import cn.edu.hdu.jdbc.LocationJdbc;
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
 * @Description: 插入位置到数据库
 * @Date: Create in 16:44 2017/12/12
 * @Params:
 * @Modified by:
 */
@WebServlet("/LocationServlet")
public class LocationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");
        LocationJdbc locationJdbc = new LocationJdbc();
        InfoModel infoModel = new InfoModel();
        if (locationJdbc.insertLocation(userId, lon, lat)) {
            infoModel.setStatus("success");
            infoModel.setData(locationJdbc.showLocation(userId));
            String jsonString= JSON.toJSONString(infoModel);
            out.println(jsonString);
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
