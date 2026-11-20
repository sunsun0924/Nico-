
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

    // ========================================
    // メッセージ登録
    // ========================================

    public boolean insertMessage(ChatRoomBean chat) throws Exception {

        String senderId = chat.getSenderId();
        String receiverId = chat.getReceiverId();
        String message = chat.getMessage();

        if (senderId == null || senderId.trim().isEmpty()
                || receiverId == null || receiverId.trim().isEmpty()
                || message == null || message.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "送信者・受信者・メッセージを指定してください。"
            );

        }

        senderId = senderId.trim();
        receiverId = receiverId.trim();
        message = message.trim();


        // 参加者の組み合わせから既存チャットを検索
        String findChatSql =
            "SELECT chat_id " +
            "FROM chat " +
            "WHERE (TRIM(user_id_1) = ? AND TRIM(user_id_2) = ?) " +
            "   OR (TRIM(user_id_1) = ? AND TRIM(user_id_2) = ?) " +
            "ORDER BY chat_id " +
            "LIMIT 1";


        // 新しいチャットを登録
        // chat_idはDB側の自動採番を使用する
        String insertChatSql =
            "INSERT INTO chat (user_id_1, user_id_2) " +
            "VALUES (?, ?) " +
            "RETURNING chat_id";


        // メッセージ登録
        String insertMessageSql =
            "INSERT INTO chat_message " +
            "(chat_id, sender_user_id, chat_text, chat_time) " +
            "VALUES (?, ?, ?, NOW())";


        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {

                String chatId = null;


                // ========================================
                // ① 既存チャットを検索
                // ========================================

                try (PreparedStatement st =
                        con.prepareStatement(findChatSql)) {

                    st.setString(1, senderId);
                    st.setString(2, receiverId);
                    st.setString(3, receiverId);
                    st.setString(4, senderId);

                    try (ResultSet rs = st.executeQuery()) {

                        if (rs.next()) {

                            chatId = rs.getString("chat_id");

                        }

                    }

                }


                // ========================================
                // ② チャットがなければ新規作成
                // ========================================

                if (chatId == null) {

                    try (PreparedStatement st =
                            con.prepareStatement(insertChatSql)) {

                        st.setString(1, senderId);
                        st.setString(2, receiverId);

                        try (ResultSet rs = st.executeQuery()) {

                            if (rs.next()) {

                                chatId = rs.getString("chat_id");

                            }

                        }

                    }

                }


                if (chatId == null) {

                    throw new Exception(
                        "チャットIDを取得できませんでした。"
                    );

                }


                // ========================================
                // ③ メッセージを保存
                // ========================================

                try (PreparedStatement st =
                        con.prepareStatement(insertMessageSql)) {

                    st.setString(1, chatId);
                    st.setString(2, senderId);
                    st.setString(3, message);

                    st.executeUpdate();

                }


                // ========================================
                // ④ 保存成功
                // ========================================

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


    // ========================================
    // メッセージ一覧取得
    // ========================================

    public List<ChatRoomBean> getMessageList(
            String myUserId,
            String partnerUserId) throws Exception {

        List<ChatRoomBean> messageList = new ArrayList<>();

        String sql =
            "SELECT m.message_id, m.chat_id, " +
            "       m.sender_user_id, m.chat_text, m.chat_time " +
            "FROM chat c " +
            "INNER JOIN chat_message m " +
            "    ON m.chat_id = c.chat_id " +
            "WHERE ( " +
            "    TRIM(c.user_id_1) = ? " +
            "    AND TRIM(c.user_id_2) = ? " +
            ") OR ( " +
            "    TRIM(c.user_id_1) = ? " +
            "    AND TRIM(c.user_id_2) = ? " +
            ") " +
            "ORDER BY m.chat_time ASC, m.message_id ASC";


        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, myUserId.trim());
            st.setString(2, partnerUserId.trim());
            st.setString(3, partnerUserId.trim());
            st.setString(4, myUserId.trim());


            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {

                    ChatRoomBean chat = new ChatRoomBean();

                    chat.setMessageId(
                        rs.getString("message_id")
                    );

                    chat.setSenderId(
                        rs.getString("sender_user_id")
                    );

                    chat.setReceiverId(partnerUserId);

                    chat.setMessage(
                        rs.getString("chat_text")
                    );


                    if (rs.getTimestamp("chat_time") != null) {

                        LocalDateTime ldt =
                            rs.getTimestamp("chat_time").toLocalDateTime();

                        chat.setSentAt(
                            ldt.format(
                                DateTimeFormatter.ofPattern("HH:mm")
                            )
                        );

                    }

                    messageList.add(chat);

                }

            }

        } catch (Exception e) {

            throw new Exception("メッセージ一覧の取得に失敗しました。", e);

        }

        return messageList;

    }


    // ========================================
    // ユーザーIDからユーザー名を取得
    // ========================================

    public String getUserName(String userId) throws Exception {

        String userName = null;

        String sql =
            "SELECT user_name " +
            "FROM \"user\" " +
            "WHERE TRIM(user_id) = ?";


        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(
                1,
                userId != null ? userId.trim() : ""
            );


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
