
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bean.ChatListBean;

public class ChatListDAO extends DAO {

    // ========================================
    // チャット一覧を取得
    // 実際にメッセージがある相手だけ表示する
    // ========================================

    public List<ChatListBean> getChatList(String userId) throws Exception {

        List<ChatListBean> chatList = new ArrayList<>();

        String sql =
            "SELECT u.user_id, u.user_name, " +
            "       latest.chat_text AS last_message, " +
            "       latest.chat_time AS last_time, " +
            "       (SELECT COUNT(*) " +
            "        FROM chat c2 " +
            "        INNER JOIN chat_message m2 ON m2.chat_id = c2.chat_id " +
            "        WHERE ((TRIM(c2.user_id_1) = ? AND TRIM(c2.user_id_2) = TRIM(u.user_id)) " +
            "            OR (TRIM(c2.user_id_2) = ? AND TRIM(c2.user_id_1) = TRIM(u.user_id))) " +
            "          AND TRIM(m2.sender_user_id) = TRIM(u.user_id) " +
            "          AND m2.read_at IS NULL) AS unread_count " +
            "FROM \"user\" u " +

            // メッセージが存在するチャットだけを取得
            "CROSS JOIN LATERAL ( " +
            "    SELECT m.chat_text, m.chat_time " +
            "    FROM chat c " +
            "    INNER JOIN chat_message m " +
            "        ON m.chat_id = c.chat_id " +
            "    WHERE ( " +
            "        TRIM(c.user_id_1) = ? " +
            "        AND TRIM(c.user_id_2) = TRIM(u.user_id) " +
            "    ) OR ( " +
            "        TRIM(c.user_id_2) = ? " +
            "        AND TRIM(c.user_id_1) = TRIM(u.user_id) " +
            "    ) " +
            "    ORDER BY m.chat_time DESC, m.message_id DESC " +
            "    LIMIT 1 " +
            ") latest " +

            "WHERE TRIM(u.user_id) <> ? " +

            // 自分がブロックしている相手はチャット一覧に表示しない
            "AND NOT EXISTS ( " +
            "    SELECT 1 FROM block_list bl " +
            "    WHERE TRIM(bl.blocker_user_id) = ? " +
            "      AND TRIM(bl.blocked_user_id) = TRIM(u.user_id) " +
            ") " +

            "ORDER BY latest.chat_time DESC";


        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, userId.trim());
            st.setString(2, userId.trim());
            st.setString(3, userId.trim());
            st.setString(4, userId.trim());
            st.setString(5, userId.trim());
            st.setString(6, userId.trim());

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {

                    ChatListBean chat = new ChatListBean();

                    String partnerId = rs.getString("user_id");

                    if (partnerId != null) {
                        chat.setUserId(partnerId.trim());
                    }

                    chat.setUserName(rs.getString("user_name"));
                    chat.setLastMessage(rs.getString("last_message"));

                    // ========================================
                    // 日時表示
                    // ========================================

                    if (rs.getTimestamp("last_time") != null) {

                        LocalDateTime ldt =
                            rs.getTimestamp("last_time").toLocalDateTime();

                        LocalDate nowDate = LocalDate.now();
                        LocalDate targetDate = ldt.toLocalDate();

                        long days =
                            ChronoUnit.DAYS.between(targetDate, nowDate);

                        String formattedTime;

                        if (targetDate.equals(nowDate)) {

                            formattedTime =
                                ldt.format(
                                    DateTimeFormatter.ofPattern("HH:mm")
                                );

                        } else if (days >= 0 && days < 7) {

                            formattedTime =
                                ldt.format(
                                    DateTimeFormatter.ofPattern(
                                        "M月d日 (E)",
                                        Locale.JAPANESE
                                    )
                                );

                        } else {

                            formattedTime =
                                ldt.format(
                                    DateTimeFormatter.ofPattern("yyyy/MM/dd")
                                );

                        }

                        chat.setLastTime(formattedTime);

                    }

                    chat.setUnreadCount(rs.getInt("unread_count"));

                    chatList.add(chat);

                }

            }

        } catch (Exception e) {

            throw new Exception("チャット一覧の取得に失敗しました。", e);

        }

        return chatList;

    }


    // ========================================
    // 自分以外の全ユーザー一覧を取得
    // 新規チャット選択用
    // ========================================

    public List<ChatListBean> getAllUsers(String myUserId) throws Exception {

        List<ChatListBean> userList = new ArrayList<>();

        String sql =
            "SELECT user_id, user_name " +
            "FROM \"user\" " +
            "WHERE TRIM(user_id) <> ? " +
            "ORDER BY user_name ASC";


        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, myUserId.trim());

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {

                    ChatListBean user = new ChatListBean();

                    String id = rs.getString("user_id");

                    if (id != null) {
                        user.setUserId(id.trim());
                    }

                    user.setUserName(rs.getString("user_name"));

                    userList.add(user);

                }

            }

        } catch (Exception e) {

            throw new Exception("ユーザー一覧の取得に失敗しました。", e);

        }

        return userList;

    }

}
