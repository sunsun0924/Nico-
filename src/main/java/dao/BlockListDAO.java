package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.BlockList;

/** ブロックリストの登録・取得・解除を行うDAO */
public class BlockListDAO extends DAO {

    /** ログインユーザーがブロックしている相手を取得 */
    public List<BlockList> getBlockedUsers(String blockerUserId) throws Exception {

        List<BlockList> list = new ArrayList<>();

        String sql =
            "SELECT b.block_id, b.blocker_user_id, b.blocked_user_id, " +
            "       u.user_name " +
            "FROM block_list b " +
            "LEFT JOIN \"user\" u ON TRIM(u.user_id) = TRIM(b.blocked_user_id) " +
            "WHERE TRIM(b.blocker_user_id) = ? " +
            "ORDER BY u.user_name ASC, b.blocked_user_id ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, blockerUserId.trim());

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {

                    BlockList block = new BlockList();

                    block.setBlockId(rs.getString("block_id"));
                    block.setBlockerUserId(rs.getString("blocker_user_id").trim());
                    block.setBlockedUserId(rs.getString("blocked_user_id").trim());
                    block.setBlockedUserName(rs.getString("user_name"));

                    list.add(block);
                }
            }

        } catch (Exception e) {

            throw new Exception("ブロックリストの取得に失敗しました。", e);
        }

        return list;
    }


    /** 相手をブロックする（同じ組み合わせの二重登録を防止） */
    public void blockUser(String blockerUserId, String blockedUserId) throws Exception {

        if (blockerUserId == null || blockedUserId == null ||
                blockerUserId.trim().isEmpty() || blockedUserId.trim().isEmpty() ||
                blockerUserId.trim().equals(blockedUserId.trim())) {

            throw new IllegalArgumentException("ブロックするユーザーが正しくありません。");
        }

        String sql =
            "INSERT INTO block_list (blocker_user_id, blocked_user_id) " +
            "SELECT ?, ? WHERE NOT EXISTS (" +
            " SELECT 1 FROM block_list WHERE TRIM(blocker_user_id) = ? " +
            " AND TRIM(blocked_user_id) = ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, blockerUserId.trim());
            st.setString(2, blockedUserId.trim());
            st.setString(3, blockerUserId.trim());
            st.setString(4, blockedUserId.trim());

            st.executeUpdate();

        } catch (Exception e) {

            throw new Exception("ブロックの登録に失敗しました。", e);
        }
    }


    /** ログインユーザーが登録したブロックを解除 */
    public void unblockUser(String blockerUserId, String blockedUserId) throws Exception {

        String sql =
            "DELETE FROM block_list " +
            "WHERE TRIM(blocker_user_id) = ? AND TRIM(blocked_user_id) = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, blockerUserId.trim());
            st.setString(2, blockedUserId.trim());

            st.executeUpdate();

        } catch (Exception e) {

            throw new Exception("ブロック解除に失敗しました。", e);
        }
    }
}