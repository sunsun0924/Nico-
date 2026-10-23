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

    // 管理者を新規登録する
    public void register(String userId, String password,
                         String email, String companyId)
            throws Exception {

        // 管理者情報を登録するSQL
        String sql =
                "INSERT INTO cp_admin "
              + "(cp_userid, cp_userpass, cp_usermail, cp_id) "
              + "VALUES (?, ?, ?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {
            // 登録する管理者情報を設定
            st.setString(1, userId);
            st.setString(2, password);
            st.setString(3, email);
            st.setString(4, companyId);

            // データベースに登録
            st.executeUpdate();
        }
    }
}