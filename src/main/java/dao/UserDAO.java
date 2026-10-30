
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ChatListBean;
import bean.User;
import tool.PasswordUtil;

public class UserDAO extends DAO {

    // ログイン用
    public User search(String userId, String userPass) throws Exception {

        User u = null;

        // 入力されたパスワードをハッシュ化
        String hashPassword = PasswordUtil.hash(userPass);

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"user\" WHERE user_id = ? AND user_pass = ?"
        );

        // ユーザーIDとハッシュ化したパスワードを設定
        st.setString(1, userId);
        st.setString(2, hashPassword);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 検索結果があればBeanに格納
        if (rs.next()) {

            u = new User();

            u.setUserId(rs.getString("user_id"));
            u.setUserPass(rs.getString("user_pass"));
            u.setUserAddress(rs.getString("user_address"));
            u.setUserNumber(rs.getString("user_number"));
            u.setUserMail(rs.getString("user_mail"));
            u.setUserBirthday(rs.getDate("user_birthday"));
            u.setUserName(rs.getString("user_name"));
            u.setUserImg(rs.getString("user_img"));
        }

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return u;
    }


    // 新規登録用
    public int insert(User user) throws Exception {

        // パスワードをハッシュ化
        String hashPassword = PasswordUtil.hash(user.getUserPass());

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO \"user\" (user_id, user_pass) VALUES (?, ?)"
        );

        // ユーザーIDとハッシュ化したパスワードを設定
        st.setString(1, user.getUserId());
        st.setString(2, hashPassword);

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // ユーザーIDの重複確認用
    public boolean isExist(String userId) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"user\" WHERE user_id = ?"
        );

        // ユーザーIDを設定
        st.setString(1, userId);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        boolean exists = rs.next();

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return exists;
    }


    // プロフィール更新用
    public int update(User user) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "UPDATE \"user\" SET " +
            "user_address = ?, " +
            "user_number = ?, " +
            "user_mail = ?, " +
            "user_birthday = ?, " +
            "user_name = ?, " +
            "user_img = ? " +
            "WHERE user_id = ?"
        );

        // 住所
        st.setString(1, user.getUserAddress());

        // 電話番号
        st.setString(2, user.getUserNumber());

        // メールアドレス
        st.setString(3, user.getUserMail());

        // 生年月日
        st.setDate(4, user.getUserBirthday());

        // 名前
        st.setString(5, user.getUserName());

        // 画像
        st.setString(6, user.getUserImg());

        // ユーザーID
        st.setString(7, user.getUserId());

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // パスワード変更用
    public int updatePassword(String userId, String newPassword)
            throws Exception {

        // 新しいパスワードをハッシュ化
        String hashPassword = PasswordUtil.hash(newPassword);

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "UPDATE \"user\" SET user_pass = ? WHERE user_id = ?"
        );

        // 新しいパスワードを設定
        st.setString(1, hashPassword);

        // ユーザーIDを設定
        st.setString(2, userId);

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // ユーザー情報変更用
    // メール・電話番号・住所・誕生日を更新
    public int updateUserInfo(User user) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "UPDATE \"user\" SET " +
            "user_address = ?, " +
            "user_number = ?, " +
            "user_mail = ?, " +
            "user_birthday = ? " +
            "WHERE user_id = ?"
        );

        // 住所
        st.setString(1, user.getUserAddress());

        // 電話番号
        st.setString(2, user.getUserNumber());

        // メールアドレス
        st.setString(3, user.getUserMail());

        // 生年月日
        st.setDate(4, user.getUserBirthday());

        // ユーザーID
        st.setString(5, user.getUserId());

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // 新規チャット用：自分以外の全ユーザーを取得するメソッド
    public List<ChatListBean> getAllUsers(int myUserId) throws Exception {

        List<ChatListBean> userList = new ArrayList<>();

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            con = getConnection();

            // 自分以外のユーザーを取得
            String sql =
                "SELECT user_id, user_name FROM \"user\" " +
                "WHERE user_id != ? ORDER BY user_name ASC";

            st = con.prepareStatement(sql);

            // 自分のユーザーIDを設定
            st.setInt(1, myUserId);

            // SQLを実行
            rs = st.executeQuery();

            while (rs.next()) {

                ChatListBean user = new ChatListBean();

                // ユーザーID
                user.setUserId(rs.getInt("user_id"));

                // ユーザー名
                user.setUserName(rs.getString("user_name"));

                userList.add(user);
            }

        } finally {

            // リソースを解放
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return userList;
    }
}
