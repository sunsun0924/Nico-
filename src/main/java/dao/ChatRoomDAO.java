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
            "INSERT INTO chat_message " +
            "(message_id, chat_id, sender_user_id, chat_text, chat_time) " +
            "VALUES (?, ?, ?, ?, CURRENT_TIME)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // message_id は一意になるように生成
            String messageId = "MSG" + System.currentTimeMillis();
            // chat_id にはやり取りする相手のIDを指定
            String chatId = chat.getReceiverId();

            st.setString(1, messageId);
            st.setString(2, chatId);
            st.setString(3, chat.getSenderId());
            st.setString(4, chat.getMessage());

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
            "SELECT message_id, chat_id, sender_user_id, chat_text, chat_time " +
            "FROM chat_message " +
            "WHERE (sender_user_id = ? AND chat_id = ?) " +
            "OR (sender_user_id = ? AND chat_id = ?) " +
            "ORDER BY chat_time ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, myUserId);
            st.setString(2, partnerUserId);
            st.setString(3, partnerUserId);
            st.setString(4, myUserId);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {

                    ChatRoomBean chat = new ChatRoomBean();

                    chat.setMessageId(rs.getString("message_id"));
                    chat.setSenderId(rs.getString("sender_user_id"));
                    chat.setReceiverId(partnerUserId);
                    chat.setMessage(rs.getString("chat_text"));
                    
                    if (rs.getTime("chat_time") != null) {
                        chat.setSentAt(rs.getTime("chat_time").toString());
                    }

                    messageList.add(chat);
                }
            }

        } catch (Exception e) {
            throw new Exception("メッセージ一覧の取得に失敗しました。", e);
        }

        return messageList;
    }
}