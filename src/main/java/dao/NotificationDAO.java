
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDAO extends DAO {

    // ResultSetの内容をNotification Beanに格納
    private Notification mapNotification(ResultSet rs)
            throws Exception {

        Notification notification = new Notification();

        notification.setNotificationId(
            rs.getString("notification_id"));
        notification.setUserId(
            rs.getString("user_id"));
        notification.setAdminId(
            rs.getString("admin_id"));
        notification.setSenderId(
            rs.getString("sender_id"));
        notification.setSenderAdminId(
            rs.getString("sender_admin_id"));
        notification.setNotificationType(
            rs.getString("notification_type"));
        notification.setMessage(
            rs.getString("message"));

        Timestamp datetime =
            rs.getTimestamp("notification_datetime");

        if (datetime != null) {
            notification.setNotificationDatetime(
                datetime.toLocalDateTime());
        }

        notification.setPostId(
            rs.getString("post_id"));
        notification.setGiftId(
            rs.getString("gift_id"));
        notification.setCommentId(
            rs.getString("comment_id"));

        return notification;
    }


    // 通知をすべて取得
    public List<Notification> findAll() throws Exception {

        List<Notification> notificationList = new ArrayList<>();

        String sql =
            "SELECT * FROM notification "
          + "ORDER BY notification_datetime DESC";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                notificationList.add(mapNotification(rs));
            }

        } catch (Exception e) {
            throw e;

        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return notificationList;
    }


    // ユーザーIDで通知を取得
    public List<Notification> findByUserId(String userId)
            throws Exception {

        List<Notification> notificationList = new ArrayList<>();

        String sql =
            "SELECT * FROM notification "
          + "WHERE user_id = ? "
          + "ORDER BY notification_datetime DESC";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, userId);

            rs = st.executeQuery();

            while (rs.next()) {
                notificationList.add(mapNotification(rs));
            }

        } catch (Exception e) {
            throw e;

        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return notificationList;
    }


    // 管理者IDで通知を取得
    public List<Notification> findByAdminId(String adminId)
            throws Exception {

        List<Notification> notificationList = new ArrayList<>();

        String sql =
            "SELECT * FROM notification "
          + "WHERE admin_id = ? "
          + "ORDER BY notification_datetime DESC";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, adminId);

            rs = st.executeQuery();

            while (rs.next()) {
                notificationList.add(mapNotification(rs));
            }

        } catch (Exception e) {
            throw e;

        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return notificationList;
    }


    // 通知を登録
    public int insert(Notification notification) throws Exception {

        String sql =
            "INSERT INTO notification "
          + "(user_id, admin_id, sender_id, sender_admin_id, "
          + "notification_type, message, notification_datetime, "
          + "post_id, gift_id, comment_id) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, notification.getUserId());
            st.setString(2, notification.getAdminId());
            st.setString(3, notification.getSenderId());
            st.setString(4, notification.getSenderAdminId());
            st.setString(5, notification.getNotificationType());
            st.setString(6, notification.getMessage());

            st.setTimestamp(7,
                Timestamp.valueOf(
                    notification.getNotificationDatetime()));

            st.setString(8, notification.getPostId());
            st.setString(9, notification.getGiftId());
            st.setString(10, notification.getCommentId());

            return st.executeUpdate();

        } catch (Exception e) {
            throw e;

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }
}
