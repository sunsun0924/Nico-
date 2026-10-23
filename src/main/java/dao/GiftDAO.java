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

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {

                Gift gift = new Gift();

                gift.setGiftId(rs.getString("gift_id").trim());
                gift.setGiftName(rs.getString("gift_name"));
                gift.setGiftText(rs.getString("gift_text"));

                list.add(gift);
            }
        }

        return list;
    }
}