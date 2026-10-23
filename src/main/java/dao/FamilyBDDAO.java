
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.FamilyBD;

public class FamilyBDDAO extends DAO {

    // 家族の誕生日を登録する
    public int insert(FamilyBD familyBirthday) throws Exception {
        String sql = "INSERT INTO family_birthday "
                   + "(user_id, relation, birthday) "
                   + "VALUES (?, ?, ?)";

        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, familyBirthday.getUserId());
            st.setString(2, familyBirthday.getRelation());
            st.setDate(3, Date.valueOf(familyBirthday.getBirthday()));

            return st.executeUpdate();

        } catch (Exception e) {
            throw e;

        } finally {
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

    // ユーザーの家族の誕生日を取得する
    public List<FamilyBD> findByUserId(String userId)
            throws Exception {
        List<FamilyBD> list = new ArrayList<>();

        String sql = "SELECT family_birthday_id, user_id, relation, birthday "
                   + "FROM family_birthday "
                   + "WHERE user_id = ? "
                   + "ORDER BY birthday";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);
            st.setString(1, userId);

            rs = st.executeQuery();

            while (rs.next()) {
                FamilyBD familyBirthday = new FamilyBD();

                familyBirthday.setFamilyBirthdayId(
                        rs.getString("family_birthday_id"));
                familyBirthday.setUserId(
                        rs.getString("user_id"));
                familyBirthday.setRelation(
                        rs.getString("relation"));
                familyBirthday.setBirthday(
                        rs.getDate("birthday").toLocalDate());

                list.add(familyBirthday);
            }

        } catch (Exception e) {
            throw e;

        } finally {
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

    // 家族の誕生日情報を更新する
    public int update(FamilyBD familyBirthday) throws Exception {
        String sql = "UPDATE family_birthday "
                   + "SET relation = ?, birthday = ? "
                   + "WHERE family_birthday_id = ? AND user_id = ?";

        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, familyBirthday.getRelation());
            st.setDate(2, Date.valueOf(familyBirthday.getBirthday()));
            st.setString(3, familyBirthday.getFamilyBirthdayId());
            st.setString(4, familyBirthday.getUserId());

            return st.executeUpdate();

        } catch (Exception e) {
            throw e;

        } finally {
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

    // 家族の誕生日情報を削除する
    public int delete(String familyBirthdayId, String userId)
            throws Exception {
        String sql = "DELETE FROM family_birthday "
                   + "WHERE family_birthday_id = ? AND user_id = ?";

        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            st = con.prepareStatement(sql);

            st.setString(1, familyBirthdayId);
            st.setString(2, userId);

            return st.executeUpdate();

        } catch (Exception e) {
            throw e;

        } finally {
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
