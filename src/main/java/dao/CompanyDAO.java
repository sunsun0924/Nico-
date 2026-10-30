
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 企業情報を操作するDAO
public class CompanyDAO extends DAO {

    // 新規登録用
    public int insert(String cpId, String cpName)
            throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO company (cp_id, cp_name) VALUES (?, ?)"
        );

        // 1番目の ? に企業IDを設定
        st.setString(1, cpId);

        // 2番目の ? に企業名を設定
        st.setString(2, cpName);

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line;
    }


    // 企業IDの重複確認用
    public boolean isExist(String cpId) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM company WHERE cp_id = ?"
        );

        // 企業IDを設定
        st.setString(1, cpId);

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
