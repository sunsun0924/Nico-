package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bean.ChatListBean;

// チャット一覧情報のDB操作を行うクラス
public class ChatListDAO extends DAO {

    // 指定したユーザーのチャット一覧を取得する
    public List<ChatListBean> getChatList(int userId) throws Exception {
        List<ChatListBean> chatList = new ArrayList<>();

        String sql = "SELECT u.user_id, u.user_name, " +
                     "(SELECT message FROM messages WHERE (sender_id = u.user_id AND receiver_id = ?) OR (sender_id = ? AND receiver_id = u.user_id) ORDER BY sent_at DESC LIMIT 1) AS last_message, " +
                     "(SELECT sent_at FROM messages WHERE (sender_id = u.user_id AND receiver_id = ?) OR (sender_id = ? AND receiver_id = u.user_id) ORDER BY sent_at DESC LIMIT 1) AS last_time, " +
                     "(SELECT COUNT(*) FROM messages WHERE sender_id = u.user_id AND receiver_id = ? AND is_read = 0) AS unread_count " +
                     "FROM users u WHERE u.user_id != ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, userId);
            st.setInt(2, userId);
            st.setInt(3, userId);
            st.setInt(4, userId);
            st.setInt(5, userId);
            st.setInt(6, userId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ChatListBean chat = new ChatListBean();
                chat.setUserId(rs.getInt("user_id"));
                chat.setUserName(rs.getString("user_name"));
                chat.setLastMessage(rs.getString("last_message"));
                chat.setLastTime(rs.getString("last_time"));
                chat.setUnreadCount(rs.getInt("unread_count"));
                chatList.add(chat);
            }
        }

        return chatList;
    }
}