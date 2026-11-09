package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

// 通知情報のDB操作を行うクラス
public class NotificationDAO extends DAO {

    // ResultSetの内容をNotificationに設定する
    private Notification mapNotification(ResultSet rs) throws Exception {

        Notification notification = new Notification();

        notification.setNotificationId(rs.getString("notification_id"));
        notification.setUserId(rs.getString("user_id"));
        notification.setAdminId(rs.getString("admin_id"));
        notification.setSenderId(rs.getString("sender_id"));
        notification.setSenderAdminId(rs.getString("sender_admin_id"));
        notification.setNotificationType(rs.getString("notification_type"));
        notification.setMessage(rs.getString("message"));

        Timestamp datetime = rs.getTimestamp("notification_datetime");

        if (datetime != null) {
            notification.setNotificationDatetime(
                datetime.toLocalDateTime()
            );
        }

        notification.setPostId(rs.getString("post_id"));
        notification.setGiftId(rs.getString("gift_id"));

        return notification;
    }

    // ユーザーIDを指定して通知を取得する
    public List<Notification> findByUserId(String userId) throws Exception {

        List<Notification> list = new ArrayList<>();

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
                list.add(mapNotification(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    // 管理者IDを指定して通知を取得する
    public List<Notification> findByAdminId(String adminId) throws Exception {

        List<Notification> list = new ArrayList<>();

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
                list.add(mapNotification(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    // ユーザーの未読通知件数を取得する
    public int countByUserId(String userId) throws Exception {

        String sql =
            "SELECT COUNT(*) FROM notification "
            + "WHERE user_id = ? AND is_read = FALSE";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);
            st.setString(1, userId);

            rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    // 管理者の未読通知件数を取得する
    public int countByAdminId(String adminId) throws Exception {

        String sql =
            "SELECT COUNT(*) FROM notification "
            + "WHERE admin_id = ? AND is_read = FALSE";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);
            st.setString(1, adminId);

            rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    // ユーザーの通知をすべて既読にする
    public int markAllReadByUserId(String userId) throws Exception {

        String sql =
            "UPDATE notification SET is_read = TRUE "
            + "WHERE user_id = ? AND is_read = FALSE";

        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);
            st.setString(1, userId);

            return st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 管理者の通知をすべて既読にする
    public int markAllReadByAdminId(String adminId) throws Exception {

        String sql =
            "UPDATE notification SET is_read = TRUE "
            + "WHERE admin_id = ? AND is_read = FALSE";

        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);
            st.setString(1, adminId);

            return st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 通知を登録する
    public int insert(Notification notification) throws Exception {

        String sql =
            "INSERT INTO notification "
            + "(user_id, admin_id, sender_id, sender_admin_id, "
            + "notification_type, message, notification_datetime, "
            + "post_id, gift_id) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement st = null;

        try {
            // 通知先がユーザーか管理者のどちらか一方か確認
            boolean validReceiver =
                (notification.getUserId() != null
                    && notification.getAdminId() == null)
                ||
                (notification.getUserId() == null
                    && notification.getAdminId() != null);

            // 通知元がユーザーか管理者のどちらか一方か確認
            boolean validSender =
                (notification.getSenderId() != null
                    && notification.getSenderAdminId() == null)
                ||
                (notification.getSenderId() == null
                    && notification.getSenderAdminId() != null);

            if (!validReceiver || !validSender) {
                throw new Exception(
                    "通知先または通知元のIDが正しく設定されていません。"
                );
            }

            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, notification.getUserId());
            st.setString(2, notification.getAdminId());
            st.setString(3, notification.getSenderId());
            st.setString(4, notification.getSenderAdminId());
            st.setString(5, notification.getNotificationType());
            st.setString(6, notification.getMessage());

            java.time.LocalDateTime datetime =
                notification.getNotificationDatetime();

            if (datetime == null) {
                datetime = java.time.LocalDateTime.now();
            }

            st.setTimestamp(7, Timestamp.valueOf(datetime));
            st.setString(8, notification.getPostId());
            st.setString(9, notification.getGiftId());

            // is_readはDEFAULT FALSEが設定される
            return st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
