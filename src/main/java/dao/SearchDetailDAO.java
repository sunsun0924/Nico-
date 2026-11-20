package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.SearchDetail;

// 検索詳細情報のDB操作を行うクラス
public class SearchDetailDAO extends DAO {

    // 検索IDを指定して会話内容を取得する
    public List<SearchDetail> findBySearchId(String searchId)
            throws Exception {

        List<SearchDetail> list = new ArrayList<>();

        String sql =
            "SELECT search_detail_id, search_id, sender_type, "
            + "search_detail_text, sent_at "
            + "FROM search_detail WHERE search_id = ? "
            + "ORDER BY sent_at ASC, search_detail_id ASC";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 検索IDを設定
            st.setString(1, searchId);

            // SQLを実行
            try (ResultSet rs = st.executeQuery()) {

                // 検索結果をBeanに格納
                while (rs.next()) {

                    SearchDetail detail = new SearchDetail();

                    detail.setSearchDetailId(
                        rs.getString("search_detail_id")
                    );

                    detail.setSearchId(rs.getString("search_id"));
                    detail.setSenderType(rs.getString("sender_type"));

                    detail.setSearchDetailText(
                        rs.getString("search_detail_text")
                    );

                    Timestamp sentAt = rs.getTimestamp("sent_at");

                    if (sentAt != null) {
                        detail.setSentAt(sentAt.toString());
                    }

                    list.add(detail);
                }
            }
        }

        return list;
    }


    // ユーザーまたはAIの会話内容を登録する
    public boolean insert(SearchDetail detail) throws Exception {

        String sql =
            "INSERT INTO search_detail "
            + "(search_id, sender_type, search_detail_text) "
            + "VALUES (?, ?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 会話情報を設定
            st.setString(1, detail.getSearchId());
            st.setString(2, detail.getSenderType());
            st.setString(3, detail.getSearchDetailText());

            // SQLを実行
            int line = st.executeUpdate();

            return line > 0;
        }
    }


    // 検索IDに紐づく会話内容をすべて削除する
    public boolean deleteBySearchId(String searchId) throws Exception {

        String sql =
            "DELETE FROM search_detail WHERE search_id = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 検索IDを設定
            st.setString(1, searchId);

            // SQLを実行
            int line = st.executeUpdate();

            return line > 0;
        }
    }
}