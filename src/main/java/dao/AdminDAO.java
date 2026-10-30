package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Admin;

public class AdminDAO extends DAO {

    // ログイン用
    public Admin search(String cpUserid, String cpUserpass) throws Exception {

        Admin a = null;

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"cp_admin\" WHERE cp_userid = ? AND cp_userpass = ?"
        );

        // ユーザーIDとパスワードを設定
        st.setString(1, cpUserid);
        st.setString(2, cpUserpass);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 検索結果があればBeanに格納
        if (rs.next()) {

            a = new Admin();

            a.setCpUserid(rs.getString("cp_userid"));
            a.setCpUserpass(rs.getString("cp_userpass"));
        }

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return a;
    }


    // 新規登録用
    public int insert(Admin admin) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO \"cp_admin\" (cp_userid, cp_userpass, cp_usermail, cp_id) VALUES (?, ?, ?, ?)"
        );

        // ユーザーID、パスワード、メールアドレス、企業コードを設定
        st.setString(1, admin.getCpUserid());
        st.setString(2, admin.getCpUserpass());
        st.setString(3, admin.getCpUsermail());
        st.setString(4, admin.getCpId());


        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // ユーザーIDの重複確認用
    public boolean isExist(String cpUserid) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"cp_admin\" WHERE cp_userid = ?"
        );

        // ユーザーIDを設定
        st.setString(1, cpUserid);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        boolean exists = rs.next();

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return exists;
    }
}