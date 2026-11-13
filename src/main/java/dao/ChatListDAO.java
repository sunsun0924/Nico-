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

    // チャット一覧を取得（メッセージのやり取りがある相手のみ、最新メッセージと時刻を表示用）
    public List<ChatListBean> getChatList(String userId) throws Exception {
        List<ChatListBean> chatList = new ArrayList<>();

        String sql =
            "SELECT u.user_id, u.user_name, " +
            "(SELECT chat_text FROM chat_message " +
            " WHERE (sender_user_id = u.user_id AND chat_id = ?) " +
            "    OR (sender_user_id = ? AND chat_id = u.user_id) " +
            " ORDER BY chat_time DESC LIMIT 1) AS last_message, " +
            "(SELECT chat_time FROM chat_message " +
            " WHERE (sender_user_id = u.user_id AND chat_id = ?) " +
            "    OR (sender_user_id = ? AND chat_id = u.user_id) " +
            " ORDER BY chat_time DESC LIMIT 1) AS last_time " +
            "FROM \"user\" u " +
            "WHERE u.user_id <> ? " +
            "  AND EXISTS (" +
            "    SELECT 1 FROM chat_message " +
            "    WHERE (sender_user_id = u.user_id AND chat_id = ?) " +
            "       OR (sender_user_id = ? AND chat_id = u.user_id)" +
            "  ) " +
            "ORDER BY last_time DESC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, userId);
            st.setString(2, userId);
            st.setString(3, userId);
            st.setString(4, userId);
            st.setString(5, userId);
            st.setString(6, userId);
            st.setString(7, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ChatListBean chat = new ChatListBean();

                    if (rs.getString("user_id") != null) {
                        chat.setUserId(rs.getString("user_id").trim());
                    }
                    chat.setUserName(rs.getString("user_name"));
                    chat.setLastMessage(rs.getString("last_message"));

                    // ▼ ここでLINE風のスマートな時間・曜日・日付の切り替えを行っています
                    if (rs.getTimestamp("last_time") != null) {
                        LocalDateTime ldt = rs.getTimestamp("last_time").toLocalDateTime();
                        LocalDateTime now = LocalDateTime.now();
                        LocalDate targetDate = ldt.toLocalDate();
                        LocalDate nowDate = now.toLocalDate();

                        String formattedTime;
                        if (targetDate.equals(nowDate)) {
                            // 1. 今日：「12:20」のように時間だけ表示
                            formattedTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm"));
                        } else if (ChronoUnit.DAYS.between(targetDate, nowDate) < 7) {
                            // 2. 1週間以内：「11月13日 (金)」のように日付と曜日を表示（曜日だけにしたい場合は "E" や "E曜日" に変更可能）
                            formattedTime = ldt.format(DateTimeFormatter.ofPattern("M月d日 (E)", Locale.JAPANESE));
                        } else {
                            // 3. それより前：「2026/11/05」のように日付を表示
                            formattedTime = ldt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                        }
                        chat.setLastTime(formattedTime);
                    }

                    chat.setUnreadCount(0);
                    chatList.add(chat);
                }
            }
        } catch (Exception e) {
            throw new Exception("チャット一覧の取得に失敗しました。", e);
        }

        return chatList;
    }

    // 自分以外の全ユーザー一覧を取得（新規チャット選択用モーダル用：こちらは全員表示でOK）
    public List<ChatListBean> getAllUsers(String myUserId) throws Exception {
        List<ChatListBean> userList = new ArrayList<>();

        String sql =
            "SELECT user_id, user_name " +
            "FROM \"user\" " +
            "WHERE user_id <> ? " +
            "ORDER BY user_name ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, myUserId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ChatListBean user = new ChatListBean();
                    if (rs.getString("user_id") != null) {
                        user.setUserId(rs.getString("user_id").trim());
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