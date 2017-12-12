package cn.edu.hdu.jdbc;

import cn.edu.hdu.dao.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationJdbc {

    private Connection conn = null;
    private PreparedStatement psmt = null;
    private ResultSet resultSet = null;

    public boolean insertLocation(int userId, String lon, String lat) {
        /**
         * @Author: Enzo
         * @Description:
         * @Date: Create in 15:20 2017/12/12
         * @Params: [userId, lon, lat]
         * @Modified by:
         */
        String sql = "insert into location(userId,lon,lat) values (?,?,?)";
        try {
            conn = MySqlConn.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, userId);
            psmt.setString(2, lon);
            psmt.setString(3, lat);
            if (!psmt.execute()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return false;
    }

    public Location showLocation(int userId){
        String sql = "SELECT * FROM location WHERE userId=" + userId;
        Location location = new Location();
        try {
            conn = MySqlConn.getConnection();
            psmt =  conn.prepareStatement(sql);
            resultSet = psmt.executeQuery();
            if (resultSet.next()) {
                location.setUserId(resultSet.getInt("userId"));
                location.setLon(resultSet.getString("lon"));
                location.setLat(resultSet.getString("lat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return location;
    }

}
