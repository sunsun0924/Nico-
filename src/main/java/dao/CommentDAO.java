package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Comment;

// コメントDAO
public class CommentDAO extends DAO {

    // 投稿IDからコメント一覧を取得
    public List<Comment> findByPostId(String postId) throws Exception {

        List<Comment> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"comment\" " +
            "WHERE post_id = ? " +
            "ORDER BY comment_datetime ASC"
        );

        st.setString(1, postId);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            Comment comment = new Comment();

            comment.setCommentId(rs.getString("comment_id"));
            comment.setPostId(rs.getString("post_id"));
            comment.setUserId(rs.getString("user_id"));
            comment.setAdminId(rs.getString("admin_id"));
            comment.setAuthorType(rs.getString("author_type"));
            comment.setCommentText(rs.getString("comment_text"));

            Timestamp datetime = rs.getTimestamp("comment_datetime");

            if (datetime != null) {
                comment.setCommentDatetime(datetime.toLocalDateTime());
            }

            list.add(comment);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // コメント登録
    public String insert(Comment comment) throws Exception {

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "INSERT INTO \"comment\" " +
            "(post_id, user_id, admin_id, author_type, " +
            "comment_text, comment_datetime) " +
            "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP) " +
            "RETURNING comment_id"
        );

        st.setString(1, comment.getPostId());
        st.setString(2, comment.getUserId());
        st.setString(3, comment.getAdminId());
        st.setString(4, comment.getAuthorType());
        st.setString(5, comment.getCommentText());

        ResultSet rs = st.executeQuery();

        String commentId = null;

        if (rs.next()) {
            commentId = rs.getString("comment_id");
            comment.setCommentId(commentId);
        }

        rs.close();
        st.close();
        con.close();

        return commentId;
    }

    // コメント削除
    // 自分が投稿したコメントだけ削除できる
    public int deleteComment(
            String commentId,
            String userId,
            String adminId,
            boolean isAdmin
    ) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = null;

        try {

            if (isAdmin) {

                // 管理者は自分のコメントだけ削除
                st = con.prepareStatement(
                    "DELETE FROM \"comment\" " +
                    "WHERE comment_id = ? " +
                    "AND admin_id = ? " +
                    "AND author_type = 'ADMIN'"
                );

                st.setString(1, commentId);
                st.setString(2, adminId);

            } else {

                // ユーザーは自分のコメントだけ削除
                st = con.prepareStatement(
                    "DELETE FROM \"comment\" " +
                    "WHERE comment_id = ? " +
                    "AND user_id = ? " +
                    "AND author_type = 'USER'"
                );

                st.setString(1, commentId);
                st.setString(2, userId);
            }

            return st.executeUpdate();

        } finally {

            if (st != null) {
                st.close();
            }

            con.close();
        }
    }

    // コメント編集
    // 自分が投稿したコメントだけ編集できる
    public int update(
            String commentId,
            String commentText,
            String userId,
            String adminId,
            boolean isAdmin
    ) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = null;

        try {

            if (isAdmin) {

                // 管理者は自分のコメントだけ編集
                st = con.prepareStatement(
                    "UPDATE \"comment\" " +
                    "SET comment_text = ? " +
                    "WHERE comment_id = ? " +
                    "AND admin_id = ? " +
                    "AND author_type = 'ADMIN'"
                );

                st.setString(1, commentText);
                st.setString(2, commentId);
                st.setString(3, adminId);

            } else {

                // ユーザーは自分のコメントだけ編集
                st = con.prepareStatement(
                    "UPDATE \"comment\" " +
                    "SET comment_text = ? " +
                    "WHERE comment_id = ? " +
                    "AND user_id = ? " +
                    "AND author_type = 'USER'"
                );

                st.setString(1, commentText);
                st.setString(2, commentId);
                st.setString(3, userId);
            }

            return st.executeUpdate();

        } finally {

            if (st != null) {
                st.close();
            }

            con.close();
        }
    }
}