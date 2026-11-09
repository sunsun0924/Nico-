
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Admin;
import tool.PasswordUtil;

public class AdminDAO extends DAO {

    // ========================================
    // ログイン用
    // ========================================

    public Admin search(String cpUserid, String cpUserpass) throws Exception {

        Admin a = null;

        // 入力されたパスワードをハッシュ化
        String hashPassword = PasswordUtil.hash(cpUserpass);

        // DB接続
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"cp_admin\" WHERE cp_userid = ? AND cp_userpass = ?"
        );

        st.setString(1, cpUserid);
        st.setString(2, hashPassword);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {

            a = new Admin();

            a.setCpUserid(rs.getString("cp_userid"));
            a.setCpUserpass(rs.getString("cp_userpass"));
            a.setCpUsermail(rs.getString("cp_usermail"));
            a.setCpId(rs.getString("cp_id"));

            // プロフィール情報
            a.setCpUsername(rs.getString("cp_username"));
            a.setCpUserimg(rs.getString("cp_userimg"));

            int positionX = rs.getInt("cp_userimg_position_x");
            if (rs.wasNull()) {
                positionX = 50;
            }
            a.setCpUserimgPositionX(positionX);

            int positionY = rs.getInt("cp_userimg_position_y");
            if (rs.wasNull()) {
                positionY = 50;
            }
            a.setCpUserimgPositionY(positionY);
        }

        rs.close();
        st.close();
        con.close();

        return a;
    }


    // ========================================
    // 投稿者の管理者プロフィール取得用
    // ========================================

    public Admin findByCpUserid(String cpUserid) throws Exception {

        Admin a = null;

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT cp_userid, cp_username, cp_userimg, " +
            "cp_userimg_position_x, cp_userimg_position_y " +
            "FROM \"cp_admin\" WHERE cp_userid = ?"
        );

        st.setString(1, cpUserid);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {

            a = new Admin();

            // 管理者ID
            a.setCpUserid(rs.getString("cp_userid"));

            // 管理者名
            a.setCpUsername(rs.getString("cp_username"));

            // プロフィール画像
            a.setCpUserimg(rs.getString("cp_userimg"));

            // 画像の横位置
            int positionX = rs.getInt("cp_userimg_position_x");

            if (rs.wasNull()) {
                positionX = 50;
            }

            a.setCpUserimgPositionX(positionX);

            // 画像の縦位置
            int positionY = rs.getInt("cp_userimg_position_y");

            if (rs.wasNull()) {
                positionY = 50;
            }

            a.setCpUserimgPositionY(positionY);
        }

        rs.close();
        st.close();
        con.close();

        return a;
    }


    // ========================================
    // 新規登録用
    // ========================================

    public int insert(Admin admin) throws Exception {

        // パスワードをハッシュ化
        String hashPassword = PasswordUtil.hash(admin.getCpUserpass());

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "INSERT INTO \"cp_admin\" " +
            "(cp_userid, cp_userpass, cp_usermail, cp_id) " +
            "VALUES (?, ?, ?, ?)"
        );

        st.setString(1, admin.getCpUserid());
        st.setString(2, hashPassword);
        st.setString(3, admin.getCpUsermail());
        st.setString(4, admin.getCpId());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }


    // ========================================
    // 管理者IDの重複確認用
    // ========================================

    public boolean isExist(String cpUserid) throws Exception {

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"cp_admin\" WHERE cp_userid = ?"
        );

        st.setString(1, cpUserid);

        ResultSet rs = st.executeQuery();

        boolean exists = rs.next();

        rs.close();
        st.close();
        con.close();

        return exists;
    }


    // ========================================
    // 管理者プロフィール更新用
    // ========================================

    public int updateProfile(Admin admin) throws Exception {

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "UPDATE \"cp_admin\" SET " +
            "cp_username = ?, " +
            "cp_userimg = ?, " +
            "cp_userimg_position_x = ?, " +
            "cp_userimg_position_y = ? " +
            "WHERE cp_userid = ?"
        );

        st.setString(1, admin.getCpUsername());
        st.setString(2, admin.getCpUserimg());
        st.setInt(3, admin.getCpUserimgPositionX());
        st.setInt(4, admin.getCpUserimgPositionY());
        st.setString(5, admin.getCpUserid());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }
}
