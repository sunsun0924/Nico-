package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ブックマークテーブルに対するDB操作を行うDAOクラス
 *
 * ユーザーと管理者の両方に対応する。
 */
public class BookmarkDAO extends DAO {

    /**
     * ブックマーク済みか確認する
     *
     * @param postId 投稿ID
     * @param userId ユーザーID
     * @param adminId 管理者ID
     * @param authorType 投稿者の種類
     * @return 登録済みならtrue
     */
    public boolean isBookmarked(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        // ブックマークの存在確認SQL
        String sql =
            "SELECT COUNT(*) "
            + "FROM bookmark "
            + "WHERE post_id = ? "
            + "AND author_type = ? "
            + "AND ("
            + "(? = 'USER' AND user_id = ?) "
            + "OR "
            + "(? = 'ADMIN' AND admin_id = ?)"
            + ")";

        // DB接続とSQL実行
        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 投稿IDを設定する
            st.setString(1, postId);

            // 投稿者の種類を設定する
            st.setString(2, authorType);

            // USERの場合の条件を設定する
            st.setString(3, authorType);
            st.setString(4, userId);

            // ADMINの場合の条件を設定する
            st.setString(5, authorType);
            st.setString(6, adminId);

            // SQLを実行する
            try (ResultSet rs = st.executeQuery()) {

                // 件数を確認する
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    /**
     * ブックマークを登録する
     *
     * @param postId 投稿ID
     * @param userId ユーザーID
     * @param adminId 管理者ID
     * @param authorType 投稿者の種類
     * @return 登録成功ならtrue
     */
    public boolean insert(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        // ブックマーク登録SQL
        // bookmark_idはDB側のデフォルト値で自動採番する
        String sql =
            "INSERT INTO bookmark "
            + "(user_id, admin_id, post_id, author_type) "
            + "VALUES (?, ?, ?, ?)";

        // DB接続とSQL実行
        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // ユーザーIDを設定する
            st.setString(1, userId);

            // 管理者IDを設定する
            st.setString(2, adminId);

            // 投稿IDを設定する
            st.setString(3, postId);

            // 投稿者の種類を設定する
            st.setString(4, authorType);

            // INSERTを実行する
            return st.executeUpdate() > 0;
        }
    }

    /**
     * ブックマークを解除する
     *
     * @param postId 投稿ID
     * @param userId ユーザーID
     * @param adminId 管理者ID
     * @param authorType 投稿者の種類
     * @return 解除成功ならtrue
     */
    public boolean delete(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        // SQLを格納する変数
        String sql;

        // 管理者の場合
        if ("ADMIN".equals(authorType)) {

            sql =
                "DELETE FROM bookmark "
                + "WHERE post_id = ? "
                + "AND admin_id = ? "
                + "AND author_type = 'ADMIN'";

        } else {

            // ユーザーの場合
            sql =
                "DELETE FROM bookmark "
                + "WHERE post_id = ? "
                + "AND user_id = ? "
                + "AND author_type = 'USER'";
        }

        // DB接続とSQL実行
        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 投稿IDを設定する
            st.setString(1, postId);

            // ログイン者のIDを設定する
            if ("ADMIN".equals(authorType)) {
                st.setString(2, adminId);
            } else {
                st.setString(2, userId);
            }

            // DELETEを実行する
            return st.executeUpdate() > 0;
        }
    }

    /**
     * ブックマーク登録・解除を切り替える
     *
     * 登録済みなら解除し、未登録なら登録する。
     */
    public void toggle(
            String postId,
            String userId,
            String adminId,
            String authorType) throws Exception {

        // 現在のブックマーク状態を確認する
        boolean bookmarked = isBookmarked(
            postId,
            userId,
            adminId,
            authorType
        );

        // 登録済みなら解除する
        if (bookmarked) {

            delete(postId, userId, adminId, authorType);

        } else {

            // 未登録なら登録する
            insert(postId, userId, adminId, authorType);
        }
    }
}