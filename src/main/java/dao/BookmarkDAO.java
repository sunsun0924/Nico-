package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Post;

// ブックマーク情報のDB操作を行うクラス
public class BookmarkDAO extends DAO {

    // ========================================
    // ブックマーク状態確認用
    // ========================================

    public boolean isBookmarked(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        // DB接続
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT COUNT(*) FROM bookmark " +
            "WHERE post_id = ? " +
            "AND author_type = ? " +
            "AND ( " +
            "    (? = 'USER' AND user_id = ?) " +
            "    OR " +
            "    (? = 'ADMIN' AND admin_id = ?) " +
            ")"
        );

        st.setString(1, postId);
        st.setString(2, authorType);
        st.setString(3, authorType);
        st.setString(4, userId);
        st.setString(5, authorType);
        st.setString(6, adminId);

        ResultSet rs = st.executeQuery();

        boolean bookmarked = false;

        if (rs.next()) {
            bookmarked = rs.getInt(1) > 0;
        }

        rs.close();
        st.close();
        con.close();

        return bookmarked;
    }


    // ========================================
    // ブックマーク登録用
    // ========================================

    public int insert(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "INSERT INTO bookmark " +
            "(user_id, admin_id, post_id, author_type) " +
            "VALUES (?, ?, ?, ?)"
        );

        st.setString(1, userId);
        st.setString(2, adminId);
        st.setString(3, postId);
        st.setString(4, authorType);

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }


    // ========================================
    // ブックマーク解除用
    // ========================================

    public int delete(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        Connection con = getConnection();

        PreparedStatement st;

        if ("ADMIN".equals(authorType)) {

            // 管理者のブックマークを解除
            st = con.prepareStatement(
                "DELETE FROM bookmark " +
                "WHERE post_id = ? " +
                "AND admin_id = ? " +
                "AND author_type = 'ADMIN'"
            );

            st.setString(1, postId);
            st.setString(2, adminId);

        } else {

            // 一般ユーザーのブックマークを解除
            st = con.prepareStatement(
                "DELETE FROM bookmark " +
                "WHERE post_id = ? " +
                "AND user_id = ? " +
                "AND author_type = 'USER'"
            );

            st.setString(1, postId);
            st.setString(2, userId);
        }

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }


    // ========================================
    // ブックマーク登録・解除切り替え用
    // ========================================

    public void toggle(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        // ブックマーク状態を確認
        boolean bookmarked = isBookmarked(
            postId,
            userId,
            adminId,
            authorType
        );

        if (bookmarked) {

            // 登録済みの場合は解除
            delete(
                postId,
                userId,
                adminId,
                authorType
            );

        } else {

            // 未登録の場合は登録
            insert(
                postId,
                userId,
                adminId,
                authorType
            );
        }
    }


    // ========================================
    // ブックマークした投稿を新しい順に取得する
    // ========================================

    public List<Post> findBookmarkedPosts(
            String userId,
            String adminId,
            String authorType) throws Exception {

        List<Post> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT p.user_id, p.admin_id, p.author_type, p.post_id, " +
            "p.post_datetime, p.post_text, p.post_img, p.comment " +
            "FROM bookmark b " +
            "INNER JOIN post p ON b.post_id = p.post_id " +
            "WHERE b.author_type = ? " +
            "AND ( " +
            "    (? = 'USER' AND b.user_id = ?) " +
            "    OR " +
            "    (? = 'ADMIN' AND b.admin_id = ?) " +
            ") " +
            "ORDER BY p.post_datetime DESC"
        );

        st.setString(1, authorType);
        st.setString(2, authorType);
        st.setString(3, userId);
        st.setString(4, authorType);
        st.setString(5, adminId);

        ResultSet rs = st.executeQuery();

        // 検索結果をBeanに格納
        while (rs.next()) {

            Post post = new Post();

            post.setUser_id(rs.getString("user_id"));
            post.setAdmin_id(rs.getString("admin_id"));
            post.setAuthor_type(rs.getString("author_type"));
            post.setPost_id(rs.getString("post_id"));

            Timestamp datetime =
                rs.getTimestamp("post_datetime");

            if (datetime != null) {
                post.setPost_datetime(
                    datetime.toLocalDateTime()
                );
            }

            post.setPost_text(rs.getString("post_text"));

            // カンマ区切りの画像名はPostBean側でリスト化される
            post.setPost_img(rs.getString("post_img"));

            post.setComment(rs.getString("comment"));

            list.add(post);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }
}