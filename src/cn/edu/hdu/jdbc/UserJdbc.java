package cn.edu.hdu.jdbc;

import cn.edu.hdu.dao.User;
import cn.edu.hdu.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserJdbc {

    private Connection conn = null;
    private PreparedStatement psmt = null;
    private ResultSet resultSet = null;

    public User addUser(User user) throws SQLException {
        String sql = "insert into user (userId, name, pw, createtime) values(?,?,?,?)";
        try {
            conn = MySqlConn.getConnection();
            psmt =  conn.prepareStatement(sql);
            psmt.setInt(1, user.getUserId());
            psmt.setString(2, user.getName());
            psmt.setString(3, user.getPw());
            psmt.setString(4, user.getCreatetime());
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return user;
    }

    public User showUser(User user) throws SQLException {
        String sql = "select * from user where name='" + user.getName() + "'";
        showUserMethod(user, sql);
        return user;
    }

    public User showUser(int userId) throws SQLException {
        String sql = "select * from user where userId=" + userId;
        User user = new User();
        showUserMethod(user, sql);
        return user;
    }

    public int updateUser(int userId, String motto, int sex, String address, long phone) throws SQLException {
        String sql = "update user set motto = '" + motto + "',sex = " + sex
                + ",address = '" + address + "',phone = " + phone + " where userId = " + userId;
        JdbcUtil jdbcUtil = new JdbcUtil();
        return jdbcUtil.JdbcMethod(sql);
    }

    private void showUserMethod(User user, String sql) throws SQLException {
        try {
            conn = MySqlConn.getConnection();
            psmt =  conn.prepareStatement(sql);
            resultSet = psmt.executeQuery();
            if (resultSet.next()) {
                user.setName(resultSet.getString("name"));
                user.setPw(resultSet.getString("pw"));
                user.setUserId(resultSet.getInt("userId"));
                user.setAddress(resultSet.getString("address"));
                user.setMotto(resultSet.getString("motto"));
                user.setSex(resultSet.getInt("sex"));
                user.setSupport(resultSet.getInt("support"));
                user.setAddress(resultSet.getString("address"));
                user.setCreatetime(resultSet.getString("createtime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
    }
}
