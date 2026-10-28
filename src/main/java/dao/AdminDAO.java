package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Admin;

public class AdminDAO extends DAO {

    // 管理者IDとパスワードで管理者情報を検索
    public Admin search(String cpUserid, String cpUserpass)
            throws Exception {

        Admin admin = null;

        Connection con = getConnection();

        String sql =
            "SELECT * FROM cp_admin "
          + "WHERE cp_userid = ? AND cp_userpass = ?";

        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, cpUserid);
        st.setString(2, cpUserpass);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {

            admin = new Admin();

            admin.setCpUserid(rs.getString("cp_userid"));
            admin.setCpUserpass(rs.getString("cp_userpass"));
            admin.setCpUsermail(rs.getString("cp_usermail"));
            admin.setCpId(rs.getString("cp_id"));
        }

        rs.close();
        st.close();
        con.close();

        return admin;
    }
}