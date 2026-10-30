
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDAO extends DAO {

    // 通知をすべて取得
    public List<Notification> findAll() throws Exception {
        List<Notification> notificationList = new ArrayList<>();

        String sql =
            "SELECT * FROM notification "
          + "ORDER BY notification_datetime DESC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Notification notification = new Notification();

                notification.setNotificationId(
                    rs.getString("notification_id"));
                notification.setUserId(
                    rs.getString("user_id"));
                notification.setSenderId(
                    rs.getString("sender_id"));
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

                notificationList.add(notification);
            }

        } catch (Exception e) {
            throw e;
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

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, userId);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    Notification notification = new Notification();

                    notification.setNotificationId(
                        rs.getString("notification_id"));
                    notification.setUserId(
                        rs.getString("user_id"));
                    notification.setSenderId(
                        rs.getString("sender_id"));
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

                    notificationList.add(notification);
                }
            }

        } catch (Exception e) {
            throw e;
        }

        return notificationList;
    }

    // 通知を登録
    public int insert(Notification notification) throws Exception {

        String sql =
            "INSERT INTO notification "
          + "(user_id, sender_id, notification_type, "
          + "message, notification_datetime, post_id, gift_id, comment_id) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, notification.getUserId());
            st.setString(2, notification.getSenderId());
            st.setString(3, notification.getNotificationType());
            st.setString(4, notification.getMessage());

            st.setTimestamp(5,
                Timestamp.valueOf(
                    notification.getNotificationDatetime()));

            st.setString(6, notification.getPostId());
            st.setString(7, notification.getGiftId());
            st.setString(8, notification.getCommentId());

            return st.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }
}
