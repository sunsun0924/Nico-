package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ChatRoomBean;

public class ChatRoomDAO extends DAO {

    // メッセージ登録
    public boolean insertMessage(ChatRoomBean chat) throws Exception {

        String sql =
            "INSERT INTO messages " +
            "(sender_id, receiver_id, message, sent_at) " +
            "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, chat.getSenderId());
            st.setString(2, chat.getReceiverId());
            st.setString(3, chat.getMessage());

            return st.executeUpdate() > 0;

        } catch (Exception e) {
            throw new Exception("メッセージの登録に失敗しました。", e);
        }
    }

    // メッセージ一覧取得
    public List<ChatRoomBean> getMessageList(
            String myUserId, String partnerUserId) throws Exception {

        List<ChatRoomBean> messageList = new ArrayList<>();

        String sql =
            "SELECT message_id, sender_id, receiver_id, message, sent_at " +
            "FROM messages " +
            "WHERE (sender_id = ? AND receiver_id = ?) " +
            "OR (sender_id = ? AND receiver_id = ?) " +
            "ORDER BY sent_at ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, myUserId);
            st.setString(2, partnerUserId);
            st.setString(3, partnerUserId);
            st.setString(4, myUserId);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {

                    ChatRoomBean chat = new ChatRoomBean();

                    chat.setMessageId(rs.getInt("message_id"));
                    chat.setSenderId(rs.getString("sender_id"));
                    chat.setReceiverId(rs.getString("receiver_id"));
                    chat.setMessage(rs.getString("message"));
                    chat.setSentAt(rs.getString("sent_at"));

                    messageList.add(chat);
                }
            }

        } catch (Exception e) {
            throw new Exception("メッセージ一覧の取得に失敗しました。", e);
        }

        return messageList;
    }
}