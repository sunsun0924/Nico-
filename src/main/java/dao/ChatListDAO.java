package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ChatListBean;

public class ChatListDAO extends DAO {

    // 全ユーザーを取得（ログインなしのテスト用）
    public List<ChatListBean> getAllUsers() throws Exception {

        List<ChatListBean> userList = new ArrayList<>();

        String sql =
            "SELECT user_id, user_name " +
            "FROM \"user\" " +
            "ORDER BY user_name ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {

                ChatListBean user = new ChatListBean();

                user.setUserId(rs.getString("user_id").trim());
                user.setUserName(rs.getString("user_name"));

                userList.add(user);
            }

        } catch (Exception e) {
            throw new Exception("全ユーザーの取得に失敗しました。", e);
        }

        return userList;
    }

    // 自分以外のユーザー一覧を取得（ログイン時）
    public List<ChatListBean> getAllUsers(String myUserId)
            throws Exception {

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

                    user.setUserId(rs.getString("user_id").trim());
                    user.setUserName(rs.getString("user_name"));

                    userList.add(user);
                }
            }

        } catch (Exception e) {
            throw new Exception("ユーザー一覧の取得に失敗しました。", e);
        }

        return userList;
    }

    // チャット一覧を取得（chat_message テーブル対応・最新メッセージ表示用）
    public List<ChatListBean> getChatList(String userId)
            throws Exception {

        List<ChatListBean> chatList = new ArrayList<>();

        String sql =
            "SELECT u.user_id, u.user_name, " +
            "(SELECT chat_text FROM chat_message " +
            "WHERE (sender_user_id = u.user_id AND chat_id = ?) " +
            "OR (sender_user_id = ? AND chat_id = u.user_id) " +
            "ORDER BY chat_time DESC LIMIT 1) AS last_message, " +

            "(SELECT chat_time FROM chat_message " +
            "WHERE (sender_user_id = u.user_id AND chat_id = ?) " +
            "OR (sender_user_id = ? AND chat_id = u.user_id) " +
            "ORDER BY chat_time DESC LIMIT 1) AS last_time " +

            "FROM \"user\" u " +
            "WHERE u.user_id <> ? " +
            "ORDER BY last_time DESC NULLS LAST";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, userId);
            st.setString(2, userId);
            st.setString(3, userId);
            st.setString(4, userId);
            st.setString(5, userId);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {

                    ChatListBean chat = new ChatListBean();

                    chat.setUserId(rs.getString("user_id").trim());
                    chat.setUserName(rs.getString("user_name"));
                    chat.setLastMessage(rs.getString("last_message"));

                    if (rs.getTime("last_time") != null) {
                        chat.setLastTime(
                            rs.getTime("last_time").toString()
                        );
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
}