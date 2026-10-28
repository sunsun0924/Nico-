
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Admin;

// 管理者情報を操作するDAO
public class AdminDAO extends DAO {

    // ログイン用
    public Admin search(String userId, String userPass)
            throws Exception {

        Admin admin = null;

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM cp_admin " +
            "WHERE cp_userid = ? AND cp_userpass = ?"
        );

        // 管理者IDとパスワードを設定
        st.setString(1, userId);
        st.setString(2, userPass);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 検索結果があればBeanに格納
        if (rs.next()) {

            admin = new Admin();

            admin.setCpUserid(rs.getString("cp_userid"));
            admin.setCpUserpass(rs.getString("cp_userpass"));
            admin.setCpUsermail(rs.getString("cp_usermail"));
            admin.setCpId(rs.getString("cp_id"));
        }

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return admin;
    }


    // 新規登録用
    public int insert(Admin admin) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO cp_admin " +
            "(cp_userid, cp_userpass, cp_usermail, cp_id) " +
            "VALUES (?, ?, ?, ?)"
        );

        // 管理者IDを設定
        st.setString(1, admin.getCpUserid());

        // パスワードを設定
        st.setString(2, admin.getCpUserpass());

        // メールアドレスを設定
        st.setString(3, admin.getCpUsermail());

        // 企業IDを設定
        st.setString(4, admin.getCpId());

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // 管理者IDの重複確認用
    public boolean isExist(String userId) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM cp_admin WHERE cp_userid = ?"
        );

        // 管理者IDを設定
        st.setString(1, userId);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 検索結果があればtrue
        boolean exists = rs.next();

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return exists;
    }

}
