<%--
  Created by IntelliJ IDEA.
  User: Enzo
  Date: 2017/10/13
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <h1>第一个servlet小例子</h1>
    <form action="/RegisterServlet" method="post">
      姓名：<input type="text" name="name">
      密码：<input type="password" name="pw">
      <input type="submit" value="注册">
    </form>


    <form action="/LoginServlet" method="post">
        姓名：<input type="text" name="name">
        密码：<input type="password" name="pw">
        <input type="submit" value="登录">
    </form>
  <br>
  <a href="/AllOrdersServlet">所有订单</a><br>
  <a href="/UnFinisherOrdersServlet">所有未完成订单</a>
    <br>
    <form action="/AddOrderServlet" method="post">
        userId:<input type="number" name="userId">
        code:<input type="number" name="code">
        phone:<input type="tel" name="phone">
        location:<input type="text" name="location">
        time:<input type="number" name="time">
        remark:<input type="text" name="remark">
        value:<input type="number" name="value">
        urgency:<input type="number" name="urgency">
        price:<input type="number" name="price">
        receive_location:<input type="number" name="receive_location">
        <input type="submit" value="下订单">
    </form>
<br>
  <form action="/DeleteOrderServlet" method="get">
      userId:<input type="number" name="userId">
      订单号：<input type="number" name="orderId">
      <input type="submit" value="删除订单">
  </form>

    <form action="/ReceiveOrderServlet" method="post">
        userId:<input type="number" name="userId">
        订单号：<input type="number" name="orderId">
        <input type="submit" value="接受订单">
    </form>

    <form action="/PersonalServlet" method="post">
        userId:<input type="number" name="userId">
        个性签名<input type="text" name="motto">
        性别：<input type="number" name="sex">
        地址：<input type="text" name="address">
        手机号：<input type="tel" name="phone">
        <input type="submit" value="添加个人信息">
    </form>

    <form action="/CommentServlet" method="post">
        userId:<input type="number" name="userId">
        orderId:<input type="number" name="orderId">
        comment:<input type="text" name="comment">
        <input type="submit" value="评价">
    </form>

  <a href="upload.jsp" >132</a>

  <form action="/LocationServlet" method="post">
      userId:<input type="number" name="userId">
      lon:<input type="text" name="lon">
      lat:<input type="text" name="lat">
      <input type="submit" value="经纬度">
  </form>

    <a href="/ValueSortServlet">根据价值排序</a><br>

    <a href="/UrgencySortServlet">根据紧急程度排序</a><br>

  </body>
</html>
