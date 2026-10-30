
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Gift;

// ギフト情報を操作するDAO
public class GiftDAO extends DAO {

    // ギフト一覧を取得
    public List<Gift> selectAll() throws Exception {

        List<Gift> list = new ArrayList<>();

        String sql =
                "SELECT gift_id, gift_name, gift_text " +
                "FROM gift ORDER BY gift_id";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {

                Gift gift = new Gift();

                gift.setGiftId(rs.getString("gift_id").trim());
                gift.setGiftName(rs.getString("gift_name"));
                gift.setGiftText(rs.getString("gift_text"));

                list.add(gift);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    // ユーザーIDからギフトを検索
    public List<Gift> searchByUserId(String userId) throws Exception {

        List<Gift> list = new ArrayList<>();

        String sql =
                "SELECT gift_id, gift_name, gift_text " +
                "FROM gift WHERE user_id = ? ORDER BY gift_id";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, userId);

            rs = st.executeQuery();

            while (rs.next()) {

                Gift gift = new Gift();

                gift.setGiftId(rs.getString("gift_id").trim());
                gift.setGiftName(rs.getString("gift_name"));
                gift.setGiftText(rs.getString("gift_text"));

                list.add(gift);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
}
