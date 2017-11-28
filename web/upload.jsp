<%--
  Created by IntelliJ IDEA.
  User: Enzo
  Date: 2017/11/21
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/UploadPicServlet" method="post" enctype="multipart/form-data" >
    userId:<input type="number" name="userId"><br>
    选择文件： <input type="file" name="img.png" id="file"><br>
    点击上传： <input type="submit" value="Click Me" ><br>
</form>
</body>
</html>
