package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import bean.ChatRoomBean;

public class ChatRoomDAO extends DAO {

	// メッセージ登録（チャットルームが存在しない場合は参加者IDも含めて自動作成する）
    public boolean insertMessage(ChatRoomBean chat) throws Exception {
        String chatId = chat.getReceiverId();

        // 1. chatテーブルにルームがなければ、IDと参加者（自分・相手）をセットで登録するSQL
        String insertChatSql = "INSERT INTO chat (chat_id, user_id_1, user_id_2) VALUES (?, ?, ?) ON CONFLICT (chat_id) DO NOTHING";

        // 2. メッセージ登録用SQL（? を4つにして、時刻は NOW() を使用）
        String insertMsgSql =
            "INSERT INTO chat_message " +
            "(message_id, chat_id, sender_user_id, chat_text, chat_time) " +
            "VALUES (?, ?, ?, ?, NOW())";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            try {
                // ① chat テーブルにルームがなければ、参加者情報も含めて自動登録
                try (PreparedStatement stChat = con.prepareStatement(insertChatSql)) {
                    stChat.setString(1, chatId);
                    stChat.setString(2, chat.getSenderId());   // 送信者（自分）
                    stChat.setString(3, chat.getReceiverId()); // 受信者（相手）
                    stChat.executeUpdate();
                }

                // ② chat_message テーブルにメッセージを登録（? は4つなので 1〜4 をセット）
                String messageId = "MSG" + System.currentTimeMillis();
                try (PreparedStatement stMsg = con.prepareStatement(insertMsgSql)) {
                    stMsg.setString(1, messageId);
                    stMsg.setString(2, chatId);
                    stMsg.setString(3, chat.getSenderId());
                    stMsg.setString(4, chat.getMessage());
                    stMsg.executeUpdate();
                }

                con.commit();
                return true;

            } catch (Exception e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            throw new Exception("メッセージの登録に失敗しました。", e);
        }
    }

    // メッセージ一覧取得
    public List<ChatRoomBean> getMessageList(String myUserId, String partnerUserId) throws Exception {
        List<ChatRoomBean> messageList = new ArrayList<>();

        String sql =
            "SELECT message_id, chat_id, sender_user_id, chat_text, chat_time " +
            "FROM chat_message " +
            "WHERE (sender_user_id = ? AND chat_id = ?) " +
            "   OR (sender_user_id = ? AND chat_id = ?) " +
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

                    // ▼ ここでデータベースの時間を "HH:mm"（例: 12:20）に変換しています
                    if (rs.getTimestamp("chat_time") != null) {
                        LocalDateTime ldt = rs.getTimestamp("chat_time").toLocalDateTime();
                        chat.setSentAt(ldt.format(DateTimeFormatter.ofPattern("HH:mm")));
                    }

                    messageList.add(chat);
                }
            }
        } catch (Exception e) {
            throw new Exception("メッセージ一覧の取得に失敗しました。", e);
        }

        return messageList;
    }

    //ユーザーIDからユーザー名を取得（TRIMでスペースのズレに対応）
    public String getUserName(String userId) throws Exception {
        String userName = null;
        String sql = "SELECT user_name FROM \"user\" WHERE TRIM(user_id) = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, userId != null ? userId.trim() : "");

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    userName = rs.getString("user_name");
                }
            }
        } catch (Exception e) {
            throw new Exception("ユーザー名の取得に失敗しました。", e);
        }

        return userName;
    }
}