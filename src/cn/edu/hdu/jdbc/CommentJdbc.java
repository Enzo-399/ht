package cn.edu.hdu.jdbc;

import cn.edu.hdu.dao.Comment;

import java.sql.*;

public class CommentJdbc {

    private Connection conn = null;
    private PreparedStatement psmt = null;
    private ResultSet resultSet = null;
    private Statement stmt = null;

    public Comment showOrder(long orderId) {
        String sql = "select * from comment where orderId = " + orderId;

        Comment comment = new Comment();
        try {
            conn = MySqlConn.getConnection();
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                comment.setCommentId(resultSet.getInt("commentId"));
                comment.setUserId(resultSet.getInt("userId"));
                comment.setReceiverId(resultSet.getInt("receiverId"));
                comment.setOrderId(resultSet.getLong("orderId"));
                comment.setCtime(resultSet.getString("ctime"));
                comment.setComment(resultSet.getString("comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return comment;
    }

    public int selectReceiverId(long orderId) {
        String sql = "select receiverId from dingdan where orderId = " + orderId;

        int receiverId = 0;
        try {
            conn = MySqlConn.getConnection();
            psmt = conn.prepareStatement(sql);
            resultSet = psmt.executeQuery();
            if (resultSet.next()) {
                receiverId = resultSet.getInt("receiverId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlConn.release(conn, psmt, resultSet);
        }
        return receiverId;
    }

    public boolean doComment(int userId, int receiverId, long orderId, String ctime, String comment) {
        String sql = "insert into comment (userId, receiverId, orderId, ctime, comment) " +
                "values (?,?,?,?,?)";
        try {
            conn = MySqlConn.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, userId);
            psmt.setInt(2, receiverId);
            psmt.setLong(3, orderId);
            psmt.setString(4, ctime);
            psmt.setString(5, comment);
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
}
