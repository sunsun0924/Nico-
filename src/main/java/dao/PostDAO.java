
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Post;

// 投稿情報のDB操作を行うクラス
public class PostDAO extends DAO {

    // 全投稿を新しい順に取得する
    public List<Post> findAll() throws Exception {

        List<Post> list = new ArrayList<>();

        // 投稿テーブルから全件取得するSQL
        String sql =
            "SELECT user_id, post_id, post_datetime, "
            + "post_text, post_img, comment "
            + "FROM post "
            + "ORDER BY post_datetime DESC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            // 取得した投稿をBeanに格納する
            while (rs.next()) {

                Post post = new Post();

                post.setUser_id(rs.getString("user_id"));
                post.setPost_id(rs.getString("post_id"));

                // 投稿日時をLocalDateTimeに変換する
                Timestamp datetime =
                    rs.getTimestamp("post_datetime");

                if (datetime != null) {
                    post.setPost_datetime(
                        datetime.toLocalDateTime());
                }

                post.setPost_text(rs.getString("post_text"));
                post.setPost_img(rs.getString("post_img"));
                post.setComment(rs.getString("comment"));

                list.add(post);
            }
        }

        return list;
    }

    // 自分の投稿を更新する
    public int update(Post post, String user_id)
            throws Exception {

        // 投稿IDとユーザーIDが一致する投稿を更新する
        String sql =
            "UPDATE post "
            + "SET post_text = ?, post_img = ?, comment = ? "
            + "WHERE post_id = ? AND user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // 更新する投稿内容を設定する
            st.setString(1, post.getPost_text());
            st.setString(2, post.getPost_img());
            st.setString(3, post.getComment());
            st.setString(4, post.getPost_id());
            st.setString(5, user_id);

            // 更新した件数を返す
            return st.executeUpdate();
        }
    }

    // 自分の投稿を削除する
    public int delete(String post_id, String user_id)
            throws Exception {

        // 投稿IDとユーザーIDが一致する投稿を削除する
        String sql =
            "DELETE FROM post "
            + "WHERE post_id = ? AND user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // 削除対象の投稿IDとユーザーIDを設定する
            st.setString(1, post_id);
            st.setString(2, user_id);

            // 削除した件数を返す
            return st.executeUpdate();
        }
    }
}
