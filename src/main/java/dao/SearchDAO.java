package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Search;

// 検索情報のDB操作を行うクラス
public class SearchDAO extends DAO {

    // ユーザーIDを指定して検索履歴を取得する
    public List<Search> findByUserId(String userId) throws Exception {

        List<Search> list = new ArrayList<>();

        String sql =
            "SELECT search_id, search_title, user_id, "
            + "search_created_at, search_updated_at "
            + "FROM search WHERE user_id = ? "
            + "ORDER BY search_updated_at DESC";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // ユーザーIDを設定
            st.setString(1, userId);

            // SQLを実行
            try (ResultSet rs = st.executeQuery()) {

                // 検索結果をBeanに格納
                while (rs.next()) {

                    Search search = new Search();

                    search.setSearchId(rs.getString("search_id"));
                    search.setSearchTitle(rs.getString("search_title"));
                    search.setUserId(rs.getString("user_id"));

                    Timestamp createdAt =
                        rs.getTimestamp("search_created_at");

                    Timestamp updatedAt =
                        rs.getTimestamp("search_updated_at");

                    if (createdAt != null) {
                        search.setSearchCreatedAt(createdAt.toString());
                    }

                    if (updatedAt != null) {
                        search.setSearchUpdatedAt(updatedAt.toString());
                    }

                    list.add(search);
                }
            }
        }

        return list;
    }


    // 検索IDを指定して検索情報を1件取得する
    public Search findBySearchId(String searchId) throws Exception {

        Search search = null;

        String sql =
            "SELECT search_id, search_title, user_id, "
            + "search_created_at, search_updated_at "
            + "FROM search WHERE search_id = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 検索IDを設定
            st.setString(1, searchId);

            // SQLを実行
            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {

                    search = new Search();

                    search.setSearchId(rs.getString("search_id"));
                    search.setSearchTitle(rs.getString("search_title"));
                    search.setUserId(rs.getString("user_id"));

                    Timestamp createdAt =
                        rs.getTimestamp("search_created_at");

                    Timestamp updatedAt =
                        rs.getTimestamp("search_updated_at");

                    if (createdAt != null) {
                        search.setSearchCreatedAt(createdAt.toString());
                    }

                    if (updatedAt != null) {
                        search.setSearchUpdatedAt(updatedAt.toString());
                    }
                }
            }
        }

        return search;
    }


    // 新しい検索履歴を登録する
    // 登録した検索IDを返す
    public String insert(Search search) throws Exception {

        String searchId = null;

        String sql =
            "INSERT INTO search (search_title, user_id) "
            + "VALUES (?, ?) RETURNING search_id";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 検索情報を設定
            st.setString(1, search.getSearchTitle());
            st.setString(2, search.getUserId());

            // SQLを実行
            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {
                    searchId = rs.getString("search_id");
                }
            }
        }

        return searchId;
    }


    // 検索タイトルを更新する
    public boolean updateTitle(String searchId, String searchTitle)
            throws Exception {

        String sql =
            "UPDATE search SET search_title = ?, "
            + "search_updated_at = CURRENT_TIMESTAMP "
            + "WHERE search_id = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 更新する情報を設定
            st.setString(1, searchTitle);
            st.setString(2, searchId);

            // SQLを実行
            int line = st.executeUpdate();

            return line > 0;
        }
    }


    // 検索履歴を削除する
    public boolean delete(String searchId) throws Exception {

        String sql = "DELETE FROM search WHERE search_id = ?";

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