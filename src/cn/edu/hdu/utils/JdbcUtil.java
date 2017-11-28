package cn.edu.hdu.utils;

import cn.edu.hdu.jdbc.MySqlConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Enzo
 * @Description: jdbc都要用到的方法
 * @Date: Create in 14:49 2017/11/18
 * @Params:
 * @Modified by:
 * 返回1执行成功 返回0执行失败
 */
public class JdbcUtil {

    public int JdbcMethod(String sql) {
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = MySqlConn.getConnection();
            psmt = conn.prepareStatement(sql);
            if (!psmt.execute()) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResultSet resultSet = null;
            MySqlConn.release(conn, psmt, resultSet);
        }
        return 0;
    }
}
