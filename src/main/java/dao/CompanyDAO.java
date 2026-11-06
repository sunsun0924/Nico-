
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanyDAO extends DAO {

    // 企業IDの重複確認
    public boolean isExist(String cpId) throws Exception {

        String sql = "SELECT COUNT(*) FROM company WHERE cp_id = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            st.setString(1, cpId);

            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    // 企業情報の新規登録
    public int insert(String cpId, String cpName) throws Exception {

        String sql =
                "INSERT INTO company (cp_id, cp_name) VALUES (?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            st.setString(1, cpId);
            st.setString(2, cpName);

            return st.executeUpdate();
        }
    }
}
