package cn.edu.hdu.servlet;

import cn.edu.hdu.jdbc.UserJdbc;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

/**
 * @Author: Enzo
 * @Description: 头像上传
 * @Date: Create in 16:15 2017/11/18
 * @Params: [request, response]
 * @Modified by: http://blog.csdn.net/xuehuayous/article/details/51453077
 */
@WebServlet("/UploadPicServlet")
public class UploadPicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        String message = "";
        String filePath = null;
        int userId = 0;
        try {
            // 设置文件项目工厂对象
            DiskFileItemFactory dff = new DiskFileItemFactory();
            // 用工厂实例化上传组件, ServletFileUpload 用来解析文件上传请求
            ServletFileUpload sfu = new ServletFileUpload(dff);
            // 解析结果放在 items 中
            List<FileItem> items = sfu.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    //普通表单
                    userId = Integer.parseInt(item.getString());
                } else {// 获取上传字段
                    // 更改文件名为唯一的
                    String filename = item.getName();
                    if (filename != null) {
                        // FilenameUtils.getExtension() 函数获取文件的后缀名 (不包括 ".")
                        filename = generateGUID() + "." + FilenameUtils.getExtension(filename);
                    }
                    // 生成存储路径
                    String storeDirectory = getServletContext().getRealPath("/files/images");
                    File file = new File(storeDirectory);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String path = genericPath(filename, storeDirectory);
                    // 处理文件的上传
                    try {
                        if (filename != null) {
                            item.write(new File(storeDirectory + path, filename));
                        }
                        filePath = "/files/images" + path + "/" + filename;
                        message = filePath;
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = "上传图片失败";
                        jsonObject.put("status", "false");
                        jsonObject.put("url", "null");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "上传图片失败";
            jsonObject.put("status", "false");
            jsonObject.put("url", "null");
        } finally {
            message = message.replace("\\", "/");
            message = message.replace("//", "/");
            UserJdbc userJdbc = new UserJdbc();
            userJdbc.updateUser(userId, message);
            // 将 URL 和 status 以 json 格式发送给客户端
            jsonObject.put("status", "success");
            jsonObject.put("url", message);
            response.getWriter().write(String.valueOf(jsonObject));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 生成UUID
     *
     * @return UUID
     */
    private static String generateGUID() {
        //随机生成 0到2的numBits次方 -1的随机数
        //public BigInteger(int numBits, Random rnd)
        //BigInteger e = new BigInteger(10, new Random());
        return new BigInteger(165, new Random()).toString(36).toUpperCase();
    }

    //计算文件的存放目录
    private String genericPath(String filename, String storeDirectory) {
        int hashCode = filename.hashCode();
        int dir1 = hashCode & 0xf;
        int dir2 = (hashCode & 0xf0) >> 4;

        String dir = "/" + dir1 + "/" + dir2;

        File file = new File(storeDirectory, dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }
}

