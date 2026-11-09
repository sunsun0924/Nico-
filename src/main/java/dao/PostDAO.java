
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

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT user_id, admin_id, author_type, post_id, "
            + "post_datetime, post_text, post_img, comment "
            + "FROM post ORDER BY post_datetime DESC"
        );

        // SQLを実行
        ResultSet rs = st.executeQuery();

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

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return list;
    }


    // 投稿IDを指定して投稿を1件取得する
    public Post findByPostId(String postId) throws Exception {

        Post post = null;

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT user_id, admin_id, author_type, post_id, "
            + "post_datetime, post_text, post_img, comment "
            + "FROM post WHERE post_id = ?"
        );

        // 投稿IDを設定
        st.setString(1, postId);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 検索結果があればBeanに格納
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

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        return post;
    }


    // 新しい投稿をDBに登録する
    public boolean insert(Post post) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO post "
            + "(user_id, admin_id, author_type, post_datetime, "
            + "post_text, post_img, comment) "
            + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?)"
        );

        // 投稿情報を設定
        st.setString(1, post.getUser_id());
        st.setString(2, post.getAdmin_id());
        st.setString(3, post.getAuthor_type());
        st.setString(4, post.getPost_text());
        st.setString(5, post.getPost_img());
        st.setString(6, post.getComment());

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        return line > 0;
    }


    // ========================================
    // 投稿編集
    // 自分が投稿したものだけ編集可能
    // ========================================

    public int updatePost(
            String postId,
            String postText,
            String userId,
            String adminId,
            boolean isAdmin) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        PreparedStatement st = null;

        try {

            if (isAdmin) {

                // 管理者は自分が投稿した投稿だけ編集
                st = con.prepareStatement(
                    "UPDATE post SET post_text = ? "
                    + "WHERE post_id = ? "
                    + "AND admin_id = ? "
                    + "AND author_type = 'ADMIN'"
                );

                st.setString(1, postText);
                st.setString(2, postId);
                st.setString(3, adminId);

            } else {

                // ユーザーは自分が投稿した投稿だけ編集
                st = con.prepareStatement(
                    "UPDATE post SET post_text = ? "
                    + "WHERE post_id = ? "
                    + "AND user_id = ? "
                    + "AND author_type = 'USER'"
                );

                st.setString(1, postText);
                st.setString(2, postId);
                st.setString(3, userId);
            }

            // SQLを実行
            return st.executeUpdate();

        } finally {

            // リソースを解放
            if (st != null) {
                st.close();
            }

            con.close();
        }
    }


    // ========================================
    // 投稿削除
    // 自分が投稿したものだけ削除可能
    // ========================================

    public boolean deletePost(
            String postId,
            String userId,
            String adminId,
            boolean isAdmin) throws Exception {

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            // DB接続を取得
            con = getConnection();

            // トランザクション開始
            con.setAutoCommit(false);

            // 投稿情報を取得
            st = con.prepareStatement(
                "SELECT user_id, admin_id, author_type "
                + "FROM post WHERE post_id = ? FOR UPDATE"
            );

            st.setString(1, postId);

            // SQLを実行
            rs = st.executeQuery();

            String postUserId = null;
            String postAdminId = null;
            String authorType = null;

            // 検索結果を取得
            if (rs.next()) {

                postUserId = rs.getString("user_id");
                postAdminId = rs.getString("admin_id");
                authorType = rs.getString("author_type");

            } else {

                rs.close();
                rs = null;

                st.close();
                st = null;

                con.rollback();

                return false;
            }

            // リソースを解放
            rs.close();
            rs = null;

            st.close();
            st = null;


            // 投稿者本人か確認
            boolean canDelete;

            if (isAdmin) {

                // 管理者の場合
                canDelete =
                    "ADMIN".equals(authorType)
                    && adminId != null
                    && adminId.equals(postAdminId);

            } else {

                // ユーザーの場合
                canDelete =
                    "USER".equals(authorType)
                    && userId != null
                    && userId.equals(postUserId);
            }

            // 本人以外は削除できない
            if (!canDelete) {

                con.rollback();

                return false;
            }


            // ① 投稿に紐づく通知を削除
            st = con.prepareStatement(
                "DELETE FROM notification WHERE post_id = ?"
            );

            st.setString(1, postId);

            // SQLを実行
            st.executeUpdate();

            // リソースを解放
            st.close();
            st = null;


            // ② 投稿に紐づくコメントを削除
            st = con.prepareStatement(
                "DELETE FROM \"comment\" WHERE post_id = ?"
            );

            st.setString(1, postId);

            // SQLを実行
            st.executeUpdate();

            // リソースを解放
            st.close();
            st = null;


            // ③ 投稿を削除
            st = con.prepareStatement(
                "DELETE FROM post WHERE post_id = ?"
            );

            st.setString(1, postId);

            // SQLを実行
            int line = st.executeUpdate();

            // リソースを解放
            st.close();
            st = null;


            // トランザクションを確定
            con.commit();

            return line > 0;

        } catch (Exception e) {

            // エラー時はロールバック
            if (con != null) {
                con.rollback();
            }

            throw e;

        } finally {

            // リソースを解放
            if (rs != null) {
                rs.close();
            }

            if (st != null) {
                st.close();
            }

            if (con != null) {

                con.setAutoCommit(true);
                con.close();
            }
        }
    }
}
