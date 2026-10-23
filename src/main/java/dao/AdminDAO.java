package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 管理者情報を操作するDAO
public class AdminDAO extends DAO {

    // 管理者IDとパスワードでログイン認証する
    public boolean login(String userId, String password)
            throws Exception {

        // 管理者情報を検索するSQL
        String sql =
                "SELECT cp_userid FROM cp_admin "
              + "WHERE cp_userid = ? AND cp_userpass = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {
            // 入力されたIDとパスワードを設定
            st.setString(1, userId);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                // 管理者が存在する場合
                return rs.next();
            }
        }
    }
}