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

        String sql =
            "SELECT user_id, admin_id, author_type, post_id, "
            + "post_datetime, post_text, post_img, comment "
            + "FROM post "
            + "ORDER BY post_datetime DESC";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            // DB接続を取得
            con = getConnection();

            // SQLを準備
            st = con.prepareStatement(sql);

            // SQLを実行
            rs = st.executeQuery();

            // 検索結果をBeanに格納
            while (rs.next()) {

                Post post = new Post();

                post.setUser_id(rs.getString("user_id"));
                post.setAdmin_id(rs.getString("admin_id"));
                post.setAuthor_type(rs.getString("author_type"));
                post.setPost_id(rs.getString("post_id"));

                Timestamp datetime = rs.getTimestamp("post_datetime");

                if (datetime != null) {
                    post.setPost_datetime(datetime.toLocalDateTime());
                }

                post.setPost_text(rs.getString("post_text"));
                post.setPost_img(rs.getString("post_img"));
                post.setComment(rs.getString("comment"));

                list.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {

            // リソースを解放
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


    // 投稿IDを指定して投稿を1件取得する
    public Post findByPostId(String postId) throws Exception {

        Post post = null;

        String sql =
            "SELECT user_id, admin_id, author_type, post_id, "
            + "post_datetime, post_text, post_img, comment "
            + "FROM post "
            + "WHERE post_id = ?";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            // DB接続を取得
            con = getConnection();

            // SQLを準備
            st = con.prepareStatement(sql);

            // 投稿IDを設定
            st.setString(1, postId);

            // SQLを実行
            rs = st.executeQuery();

            // 投稿が存在する場合
            if (rs.next()) {

                post = new Post();

                post.setUser_id(rs.getString("user_id"));
                post.setAdmin_id(rs.getString("admin_id"));
                post.setAuthor_type(rs.getString("author_type"));
                post.setPost_id(rs.getString("post_id"));

                Timestamp datetime = rs.getTimestamp("post_datetime");

                if (datetime != null) {
                    post.setPost_datetime(datetime.toLocalDateTime());
                }

                post.setPost_text(rs.getString("post_text"));
                post.setPost_img(rs.getString("post_img"));
                post.setComment(rs.getString("comment"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {

            // リソースを解放
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

        return post;
    }


    // 新しい投稿をDBに登録する
    public boolean insert(Post post) throws Exception {

        String sql =
            "INSERT INTO post "
            + "(user_id, admin_id, author_type, post_datetime, "
            + "post_text, post_img, comment) "
            + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";

        Connection con = null;
        PreparedStatement st = null;

        try {

            // DB接続を取得
            con = getConnection();

            // SQLを準備
            st = con.prepareStatement(sql);

            // 投稿情報を設定
            st.setString(1, post.getUser_id());
            st.setString(2, post.getAdmin_id());
            st.setString(3, post.getAuthor_type());
            st.setString(4, post.getPost_text());
            st.setString(5, post.getPost_img());
            st.setString(6, post.getComment());

            // SQLを実行
            int result = st.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {

            // リソースを解放
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
    }
}