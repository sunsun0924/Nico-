package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.User;

// ユーザー情報を操作するDAO
public class UserDAO extends DAO {

    // ユーザーIDとパスワードでログイン認証する
    public User login(String userId, String password)
            throws Exception {

        // ユーザー情報を検索するSQL
        String sql =
                "SELECT user_id, user_pass, user_name, user_mail "
              + "FROM \"user\" "
              + "WHERE user_id = ? AND user_pass = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {
            // SQLにユーザーIDとパスワードを設定する
            st.setString(1, userId);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {

                // ユーザーが見つかった場合
                if (rs.next()) {
                    User user = new User();

                    user.setUserId(rs.getString("user_id"));
                    user.setUserPass(rs.getString("user_pass"));
                    user.setUserName(rs.getString("user_name"));
                    user.setUserMail(rs.getString("user_mail"));

                    return user;
                }
            }
        }

        // ユーザーが見つからなかった場合
        return null;
    }
}