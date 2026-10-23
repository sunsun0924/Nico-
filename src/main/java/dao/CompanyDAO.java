package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CompanyDAO extends DAO {

    // 企業情報を登録
    public void insert(String companyId, String companyName)
            throws Exception {

        String sql =
                "INSERT INTO company (cp_id, cp_name) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, companyId);
            st.setString(2, companyName);

            st.executeUpdate();
        }
    }
}