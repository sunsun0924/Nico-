package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bean.ChatRoomBean;

public class ChatRoomDAO extends DAO {

    public boolean insertMessage(ChatRoomBean chat) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        boolean isSuccess = false;

        String sql = "INSERT INTO messages (sender_id, receiver_id, message, sent_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setInt(1, chat.getSenderId());
            st.setInt(2, chat.getReceiverId());
            st.setString(3, chat.getMessage());

            int affectedRows = st.executeUpdate();
            isSuccess = (affectedRows > 0);

        } finally {
            if (st != null) { try { st.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (con != null) { try { con.close(); } catch (Exception e) { e.printStackTrace(); } }
        }

        return isSuccess;
    }

    public List<ChatRoomBean> getMessageList(int myUserId, int partnerUserId) throws Exception {
        List<ChatRoomBean> messageList = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT message_id, sender_id, receiver_id, message, sent_at " +
                     "FROM messages " +
                     "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) " +
                     "ORDER BY sent_at ASC";

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setInt(1, myUserId);
            st.setInt(2, partnerUserId);
            st.setInt(3, partnerUserId);
            st.setInt(4, myUserId);

            rs = st.executeQuery();

            while (rs.next()) {
                ChatRoomBean chat = new ChatRoomBean();
                chat.setMessageId(rs.getInt("message_id"));
                chat.setSenderId(rs.getInt("sender_id"));
                chat.setReceiverId(rs.getInt("receiver_id"));
                chat.setMessage(rs.getString("message"));
                chat.setSentAt(rs.getString("sent_at"));
                messageList.add(chat);
            }
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (st != null) { try { st.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (con != null) { try { con.close(); } catch (Exception e) { e.printStackTrace(); } }
        }

        return messageList;
    }
}