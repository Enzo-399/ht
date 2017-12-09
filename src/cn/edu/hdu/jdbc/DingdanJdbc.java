package cn.edu.hdu.jdbc;

import cn.edu.hdu.dao.Dingdan;
import cn.edu.hdu.entity.DingdanModel;
import cn.edu.hdu.utils.JdbcUtil;
import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DingdanJdbc {

    private DingdanModel dingdanModel = new DingdanModel();
    private Connection conn = null;
    private PreparedStatement psmt = null;
    private ResultSet resultSet = null;

    /*
     * 显示所有订单
     */
    public DingdanModel showAllOrders() {
        String sql = "select * from dingdan";
        myMethod(sql);
        return dingdanModel;
    }

    public Dingdan showOrder(long orderId) {
        String sql = "select * from dingdan where orderId = " + orderId;
        Dingdan dingdan = new Dingdan();
        try {
            conn = MySqlConn.getConnection();
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                dingdan.setUserId(resultSet.getInt("userId"));
                dingdan.setReceiverId(resultSet.getInt("receiverId"));
                dingdan.setOrderId(resultSet.getLong("orderId"));
                dingdan.setCode(resultSet.getInt("code"));
                dingdan.setPhone(resultSet.getLong("phone"));
                dingdan.setLocation(resultSet.getString("location"));
                dingdan.setTime(resultSet.getInt("time"));
                dingdan.setRemark(resultSet.getString("remark"));
                dingdan.setValue(resultSet.getInt("value"));
                dingdan.setUrgency(resultSet.getInt("urgency"));
                dingdan.setStatus(resultSet.getInt("status"));
                dingdan.setPrice(resultSet.getInt("price"));
                dingdan.setReceive_location(resultSet.getInt("receive_location"));
                dingdan.setOrderCreatetime(resultSet.getString("orderCreatetime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return dingdan;
    }

    /**
     * @Author: Enzo
     * @Description: 显示所有未完成订单
     * @Date: Created in 2017/10/23 14:57
     * @Params:
     * @Modified By:
     */
    public DingdanModel showAllUnFinishedOrders() {
        String sql = "select * from dingdan WHERE status = 0 ";
        myMethod(sql);
        return dingdanModel;
    }

    public DingdanModel addOrder(int userId, long orderId, int code,
                                 long phone, String location, int time,
                                 String remark, int value, int urgency,
                                 String orderCreatetime, int price, int receive_location) {
        /**
         * @Author: Enzo
         * @Description: addOneOrder
         * @Date: Created in 2017/10/22 11:46
         * @Params: [userId, orderId, code, phone, location, time, remark, value, urgency]
         * @Modified By:
         */
        String sql = "insert into dingdan (userId, orderId, code, phone, location," +
                "time, remark, value, urgency, orderCreatetime, price, receive_location) values (?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            conn = MySqlConn.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, userId);
            psmt.setLong(2, orderId);
            psmt.setInt(3, code);
            psmt.setLong(4, phone);
            psmt.setString(5, location);
            psmt.setInt(6, time);
            psmt.setString(7, remark);
            psmt.setInt(8, value);
            psmt.setInt(9, urgency);
            psmt.setString(10, orderCreatetime);
            psmt.setInt(11, price);
            psmt.setInt(12,receive_location);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return dingdanModel;
    }

    public DingdanModel deleteOrder(int userId, long orderId) {
        String sql = "delete from dingdan " +
                "where userId = " + userId + " and orderId = " + orderId;
        try {
            conn = MySqlConn.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return dingdanModel;
    }

    public int receiveOrder(int receiverId, long orderId) {
        String sql = "update dingdan set receiverId = " + receiverId
                + ",status = 1 where orderId = " + orderId;
        JdbcUtil jdbcUtil = new JdbcUtil();
        return jdbcUtil.JdbcMethod(sql);
    }

    private void myMethod(String sql) {
        try {
            conn = MySqlConn.getConnection();
            psmt = conn.prepareStatement(sql);
            resultSet = psmt.executeQuery();
            List<String> dingdanList = new ArrayList<>();
            Dingdan dingdan = new Dingdan();
            while (resultSet.next()) {
                dingdan.setUserId(resultSet.getInt("userId"));
                dingdan.setCode(resultSet.getInt("code"));
                dingdan.setOrderId(resultSet.getLong("orderId"));
                dingdan.setPhone(resultSet.getLong("phone"));
                dingdan.setLocation(resultSet.getString("location"));
                dingdan.setTime(resultSet.getInt("time"));
                dingdan.setRemark(resultSet.getString("remark"));
                dingdan.setValue(resultSet.getInt("value"));
                dingdan.setUrgency(resultSet.getInt("urgency"));
                dingdan.setStatus(resultSet.getInt("status"));
                dingdan.setPrice(resultSet.getInt("price"));
                dingdan.setReceive_location(resultSet.getInt("receive_location"));
                dingdan.setOrderCreatetime(resultSet.getString("orderCreatetime"));
                String jsonString = JSON.toJSONString(dingdan);
                dingdanList.add(jsonString);
            }
            dingdanModel.setTotal(dingdanList.size());
            dingdanModel.setStatus("success");
            dingdanModel.setData(dingdanList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
    }
}
