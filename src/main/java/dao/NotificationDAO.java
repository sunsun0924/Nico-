
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDAO extends DAO {

    // 通知一覧をすべて取得
    public List<Notification> findAll() throws Exception {

        List<Notification> notificationList = new ArrayList<>();

        Connection con = getConnection();

        String sql =
            "SELECT * FROM notification "
          + "ORDER BY notification_datetime DESC";

        PreparedStatement st = con.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

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

            notificationList.add(notification);
        }

        rs.close();
        st.close();
        con.close();

        return notificationList;
    }


    // ユーザーIDで通知を検索
    public List<Notification> findByUserId(String userId)
            throws Exception {

        List<Notification> notificationList = new ArrayList<>();

        Connection con = getConnection();

        String sql =
            "SELECT * FROM notification "
          + "WHERE user_id = ? "
          + "ORDER BY notification_datetime DESC";

        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, userId);

        ResultSet rs = st.executeQuery();

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

            notificationList.add(notification);
        }

        rs.close();
        st.close();
        con.close();

        return notificationList;
    }
}
