package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftDAO extends DAO {

    // ギフト一覧を取得
    public List<Map<String, String>> selectAll() throws Exception {

        List<Map<String, String>> list = new ArrayList<>();

        String sql =
                "SELECT gift_id, gift_name, gift_text " +
                "FROM gift ORDER BY gift_id";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {

                Map<String, String> gift = new HashMap<>();

                gift.put("giftId", rs.getString("gift_id").trim());
                gift.put("giftName", rs.getString("gift_name"));
                gift.put("giftText", rs.getString("gift_text"));

                list.add(gift);
            }
        }

        return list;
    }
}