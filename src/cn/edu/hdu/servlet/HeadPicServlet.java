package cn.edu.hdu.servlet;

import cn.edu.hdu.jdbc.UserJdbc;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

@WebServlet("/HeadPicServlet")
public class HeadPicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        int userId = Integer.parseInt(request.getParameter("userId"));
        // 设置文件项目工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置文件上传路径
        String upload = this.getServletContext().getRealPath("upload/photo");
        // 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
        String temp = System.getProperty("java.io.tmpdir");
        // 设置缓冲区大小为 5M
        factory.setSizeThreshold(1024 * 1024 * 5);
        // 设置临时文件夹为temp
        factory.setRepository(new File(temp));
        // 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        String path = null;
        // 解析结果放在List中
        try {
            List<FileItem> list = servletFileUpload.parseRequest(request);
            for (FileItem item : list) {
                String name = item.getFieldName();
                InputStream is = item.getInputStream();

                if (name.contains("content")) {
                    System.out.println(inputStreamString(is));
                } else if (name.contains("img")) {
                    try {
                        path = upload + "\\" + item.getName();
                        inputStreamFile(is, path);
                        UserJdbc tm = new UserJdbc();
                        int c = tm.updateUser(userId, ReadPhoto(path));
                        System.out.println(c);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (path != null) {
                out.write(path);  //这里我把服务端成功后，返回给客户端的是上传成功后路径
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            System.out.println("failure");
            out.write("failure");
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private static String inputStreamString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    // 流转化成文件
    private static void inputStreamFile(InputStream is, String savePath) throws Exception {
        System.out.println("文件保存路径为:" + savePath);
        File file = new File(savePath);
        InputStream inputSteam = is;
        BufferedInputStream fis = new BufferedInputStream(inputSteam);
        FileOutputStream fos = new FileOutputStream(file);
        int f;
        while ((f = fis.read()) != -1) {
            fos.write(f);
        }
        fos.flush();
        fos.close();
        fis.close();
        inputSteam.close();
    }

    private static byte[] ReadPhoto(String path) {
        File file = new File(path);
        FileInputStream fin;
        // 建一个缓冲保存数据
        ByteBuffer nbf = ByteBuffer.allocate((int) file.length());
        byte[] array = new byte[1024];
        int offset = 0, length;
        byte[] content = null;
        try {
            fin = new FileInputStream(file);
            while ((length = fin.read(array)) > 0) {
                if (length != 1024) nbf.put(array, offset, length);
                else nbf.put(array);
                offset += length;
            }
            fin.close();
            content = nbf.array();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
