
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Comment;

public class CommentDAO extends DAO {

    // 指定した投稿のコメント一覧を取得する
    public List<Comment> findByPostId(String postId) throws Exception {

        // コメント一覧を格納するリスト
        List<Comment> list = new ArrayList<>();

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM \"comment\" "
            + "WHERE post_id = ? "
            + "ORDER BY comment_datetime ASC"
        );

        // 投稿IDを設定
        st.setString(1, postId);

        // SQLを実行
        ResultSet rs = st.executeQuery();

        // 検索結果があればBeanに格納
        while (rs.next()) {

            // コメントBeanを作成
            Comment comment = new Comment();

            // コメントID
            comment.setCommentId(
                rs.getString("comment_id")
            );

            // 投稿ID
            comment.setPostId(
                rs.getString("post_id")
            );

            // ユーザーID
            comment.setUserId(
                rs.getString("user_id")
            );

            // 管理者ID
            comment.setAdminId(
                rs.getString("admin_id")
            );

            // 投稿者の種類
            comment.setAuthorType(
                rs.getString("author_type")
            );

            // コメント本文
            comment.setCommentText(
                rs.getString("comment_text")
            );

            // コメント日時を取得
            Timestamp datetime =
                rs.getTimestamp("comment_datetime");

            // 日時が存在する場合はLocalDateTimeに変換
            if (datetime != null) {
                comment.setCommentDatetime(
                    datetime.toLocalDateTime()
                );
            }

            // リストに追加
            list.add(comment);
        }

        // リソースを解放
        rs.close();
        st.close();
        con.close();

        // コメント一覧を返す
        return list;
    }


    // コメントを新規登録する
    public boolean insert(Comment comment) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備
        // comment_idはDB側で自動採番
        // comment_datetimeには現在日時を設定
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO \"comment\" "
            + "(post_id, user_id, admin_id, author_type, "
            + "comment_text, comment_datetime) "
            + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)"
        );

        // 投稿IDを設定
        st.setString(1, comment.getPostId());

        // ユーザーIDを設定
        // 管理者の場合はNULL
        st.setString(2, comment.getUserId());

        // 管理者IDを設定
        // ユーザーの場合はNULL
        st.setString(3, comment.getAdminId());

        // 投稿者の種類を設定
        st.setString(4, comment.getAuthorType());

        // コメント本文を設定
        st.setString(5, comment.getCommentText());

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放
        st.close();
        con.close();

        // 登録結果を返す
        return line > 0;
    }
}
