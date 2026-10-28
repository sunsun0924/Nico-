package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bean.ChatRoomBean;

// チャットルームのDB操作を行うクラス
public class ChatRoomDAO extends DAO {

    // 新しいメッセージを送信（保存）する
    public boolean insertMessage(ChatRoomBean chat) throws Exception {
        String sql = "INSERT INTO messages (sender_id, receiver_id, message, sent_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, chat.getSenderId());
            st.setInt(2, chat.getReceiverId());
            st.setString(3, chat.getMessage());

            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        }
    }

    // 特定の相手とのメッセージ履歴を全件取得する
    public List<ChatRoomBean> getMessageList(int myUserId, int partnerUserId) throws Exception {
        List<ChatRoomBean> messageList = new ArrayList<>();

        String sql = "SELECT message_id, sender_id, receiver_id, message, sent_at " +
                     "FROM messages " +
                     "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) " +
                     "ORDER BY sent_at ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, myUserId);
            st.setInt(2, partnerUserId);
            st.setInt(3, partnerUserId);
            st.setInt(4, myUserId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ChatRoomBean chat = new ChatRoomBean();
                chat.setMessageId(rs.getInt("message_id"));
                chat.setSenderId(rs.getInt("sender_id"));
                chat.setReceiverId(rs.getInt("receiver_id"));
                chat.setMessage(rs.getString("message"));
                chat.setSentAt(rs.getString("sent_at"));
                messageList.add(chat);
            }
        }

        return messageList;
    }
}