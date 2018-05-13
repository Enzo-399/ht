package cn.edu.hdu.servlet;

import cn.edu.hdu.utils.MapUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/DistanceServlet")
public class DistanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        MapUtils mapUtils = new MapUtils();
        double lat1 = 30.3167054991;        //A 弗雷德
        double lng1 = 120.3464913368;
        double lat2 = 30.3167795923;        //B 五号楼
        double lng2 = 120.3435140848;

        String s = request.getParameter("lat");
        double lat = Double.parseDouble(s);
        String sk = request.getParameter("lng");
        double lng = Double.parseDouble(sk);

        double s1 = mapUtils.GetDistance(lat, lng, lat1, lng1);
        double s2 = mapUtils.GetDistance(lat, lng, lat2, lng2);

        if (s1 <= s2) {
            out.println("A");
        } else {
            out.println("B");
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
