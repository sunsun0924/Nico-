package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bean.ChatListBean;

public class ChatListDAO extends DAO {

    public List<ChatListBean> getChatList(int userId) throws Exception {
        List<ChatListBean> chatList = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT u.user_id, u.user_name, " +
                     "(SELECT message FROM messages WHERE (sender_id = u.user_id AND receiver_id = ?) OR (sender_id = ? AND receiver_id = u.user_id) ORDER BY sent_at DESC LIMIT 1) AS last_message, " +
                     "(SELECT sent_at FROM messages WHERE (sender_id = u.user_id AND receiver_id = ?) OR (sender_id = ? AND receiver_id = u.user_id) ORDER BY sent_at DESC LIMIT 1) AS last_time, " +
                     "(SELECT COUNT(*) FROM messages WHERE sender_id = u.user_id AND receiver_id = ? AND is_read = 0) AS unread_count " +
                     "FROM \"user\" u WHERE u.user_id != ?";

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setInt(1, userId);
            st.setInt(2, userId);
            st.setInt(3, userId);
            st.setInt(4, userId);
            st.setInt(5, userId);
            st.setInt(6, userId);

            rs = st.executeQuery();

            while (rs.next()) {
                ChatListBean chat = new ChatListBean();
                chat.setUserId(rs.getInt("user_id"));
                chat.setUserName(rs.getString("user_name"));
                chat.setLastMessage(rs.getString("last_message"));
                chat.setLastTime(rs.getString("last_time"));
                chat.setUnreadCount(rs.getInt("unread_count"));
                chatList.add(chat);
            }
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (st != null) { try { st.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (con != null) { try { con.close(); } catch (Exception e) { e.printStackTrace(); } }
        }

        return chatList;
    }
}