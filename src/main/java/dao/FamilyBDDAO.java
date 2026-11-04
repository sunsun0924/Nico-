package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.FamilyBD;

public class FamilyBDDAO extends DAO {

    // 家族の誕生日を登録する
    public int insert(FamilyBD familyBirthday) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO birthday " +
            "(family_birthday_id, user_id, \"relation\", birthday) " +
            "VALUES (?, ?, ?, ?)"
        );

        // 家族の誕生日ID
        st.setString(1, familyBirthday.getFamilyBirthdayId());

        // ユーザーID
        st.setString(2, familyBirthday.getUserId());

        // 続柄
        st.setString(3, familyBirthday.getRelation());

        // 誕生日
        st.setDate(4, Date.valueOf(familyBirthday.getBirthday()));

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // ユーザーの家族の誕生日を取得する
    public List<FamilyBD> findByUserId(String userId) throws Exception {

        List<FamilyBD> list = new ArrayList<>();

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT family_birthday_id, user_id, \"relation\", birthday " +
            "FROM birthday " +
            "WHERE user_id = ? " +
            "ORDER BY birthday"
        );

        // ユーザーIDを設定
        st.setString(1, userId);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 検索結果をBeanに格納
        while (rs.next()) {

            FamilyBD familyBirthday = new FamilyBD();

            // 家族の誕生日ID
            familyBirthday.setFamilyBirthdayId(
                rs.getString("family_birthday_id")
            );

            // ユーザーID
            familyBirthday.setUserId(
                rs.getString("user_id")
            );

            // 続柄
            familyBirthday.setRelation(
                rs.getString("relation")
            );

            // 誕生日
            familyBirthday.setBirthday(
                rs.getDate("birthday").toLocalDate()
            );

            list.add(familyBirthday);
        }

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return list;
    }


    // 同じユーザーに同じ続柄が登録されているか確認する
    public boolean isExist(String userId, String relation) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM birthday " +
            "WHERE user_id = ? AND \"relation\" = ?"
        );

        // ユーザーIDを設定
        st.setString(1, userId);

        // 続柄を設定
        st.setString(2, relation);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 登録済みか確認
        boolean exists = rs.next();

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return exists;
    }


    // 家族の誕生日を削除する
    public int delete(String familyBirthdayId, String userId) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "DELETE FROM birthday " +
            "WHERE family_birthday_id = ? AND user_id = ?"
        );

        // 家族の誕生日IDを設定
        st.setString(1, familyBirthdayId);

        // ユーザーIDを設定
        st.setString(2, userId);

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }
}
