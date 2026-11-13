package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Announcement;

// お知らせテーブルのDB操作を行うクラス
public class AnnouncementDAO extends DAO {

    // お知らせを新しい順にすべて取得する
    public List<Announcement> findAll() throws Exception {

        List<Announcement> list = new ArrayList<>();

        String sql =
            "SELECT announcement_id, announcement_text, announcement_datetime " +
            "FROM announcement " +
            "ORDER BY announcement_datetime DESC";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()
        ) {

            while (rs.next()) {

                Announcement announcement = new Announcement();

                announcement.setAnnouncementId(
                    rs.getLong("announcement_id")
                );

                announcement.setAnnouncementText(
                    rs.getString("announcement_text")
                );

                announcement.setAnnouncementDatetime(
                    rs.getTimestamp("announcement_datetime")
                );

                list.add(announcement);
            }
        }

        return list;
    }

    // お知らせを1件登録する
    public int insert(Announcement announcement) throws Exception {

        String sql =
            "INSERT INTO announcement (announcement_text) VALUES (?)";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            st.setString(1, announcement.getAnnouncementText());

            return st.executeUpdate();
        }
    }

    // お知らせを編集する
    public int update(Announcement announcement) throws Exception {

        String sql =
            "UPDATE announcement " +
            "SET announcement_text = ? " +
            "WHERE announcement_id = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            st.setString(1, announcement.getAnnouncementText());
            st.setLong(2, announcement.getAnnouncementId());

            return st.executeUpdate();
        }
    }

    // お知らせを削除する
    public int delete(long announcementId) throws Exception {

        String sql =
            "DELETE FROM announcement WHERE announcement_id = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            st.setLong(1, announcementId);

            return st.executeUpdate();
        }
    }
}