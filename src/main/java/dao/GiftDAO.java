package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Gift;
import bean.GiftProduct;

// ギフト情報を管理するDAO

public class GiftDAO extends DAO {

    // ==============================
    // 商品一覧
    // ==============================

    // 商品一覧を取得する
    public List<GiftProduct> selectAll() throws Exception {

        List<GiftProduct> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM gift_product ORDER BY gift_product_id"
        );

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            GiftProduct product = new GiftProduct();

            product.setGiftProductId(rs.getString("gift_product_id"));
            product.setGiftName(rs.getString("gift_name"));
            product.setGiftText(rs.getString("gift_text"));
            product.setGiftImage(rs.getString("gift_image"));
            product.setGiftCategory(rs.getString("gift_category"));

            int minAge = rs.getInt("target_age_min");
            product.setTargetAgeMin(rs.wasNull() ? null : minAge);

            int maxAge = rs.getInt("target_age_max");
            product.setTargetAgeMax(rs.wasNull() ? null : maxAge);

            list.add(product);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // ==============================
    // 商品検索
    // ==============================

    // 商品名・商品説明・カテゴリーから検索する
    public List<GiftProduct> searchProducts(String keyword) throws Exception {

        List<GiftProduct> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM gift_product " +
            "WHERE gift_name ILIKE ? " +
            "OR gift_text ILIKE ? " +
            "OR gift_category ILIKE ? " +
            "ORDER BY gift_product_id"
        );

        String searchWord = "%" + keyword + "%";

        st.setString(1, searchWord);
        st.setString(2, searchWord);
        st.setString(3, searchWord);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            GiftProduct product = new GiftProduct();

            product.setGiftProductId(rs.getString("gift_product_id"));
            product.setGiftName(rs.getString("gift_name"));
            product.setGiftText(rs.getString("gift_text"));
            product.setGiftImage(rs.getString("gift_image"));
            product.setGiftCategory(rs.getString("gift_category"));

            int minAge = rs.getInt("target_age_min");
            product.setTargetAgeMin(rs.wasNull() ? null : minAge);

            int maxAge = rs.getInt("target_age_max");
            product.setTargetAgeMax(rs.wasNull() ? null : maxAge);

            list.add(product);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // ==============================
    // 商品詳細
    // ==============================

    // 商品IDから商品情報を取得する
    public GiftProduct searchByGiftProductId(String giftProductId) throws Exception {

        GiftProduct product = null;

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM gift_product WHERE gift_product_id = ?"
        );

        st.setString(1, giftProductId);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {

            product = new GiftProduct();

            product.setGiftProductId(rs.getString("gift_product_id"));
            product.setGiftName(rs.getString("gift_name"));
            product.setGiftText(rs.getString("gift_text"));
            product.setGiftImage(rs.getString("gift_image"));
            product.setGiftCategory(rs.getString("gift_category"));

            int minAge = rs.getInt("target_age_min");
            product.setTargetAgeMin(rs.wasNull() ? null : minAge);

            int maxAge = rs.getInt("target_age_max");
            product.setTargetAgeMax(rs.wasNull() ? null : maxAge);
        }

        rs.close();
        st.close();
        con.close();

        return product;
    }

    // ==============================
    // おすすめ商品
    // ==============================

    // 年齢に合うおすすめ商品を取得する
    public List<GiftProduct> selectRecommendedByAge(int age) throws Exception {

        List<GiftProduct> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM gift_product " +
            "WHERE (target_age_min IS NULL OR target_age_min <= ?) " +
            "AND (target_age_max IS NULL OR target_age_max >= ?) " +
            "ORDER BY gift_product_id"
        );

        st.setInt(1, age);
        st.setInt(2, age);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            GiftProduct product = new GiftProduct();

            product.setGiftProductId(rs.getString("gift_product_id"));
            product.setGiftName(rs.getString("gift_name"));
            product.setGiftText(rs.getString("gift_text"));
            product.setGiftImage(rs.getString("gift_image"));
            product.setGiftCategory(rs.getString("gift_category"));

            int minAge = rs.getInt("target_age_min");
            product.setTargetAgeMin(rs.wasNull() ? null : minAge);

            int maxAge = rs.getInt("target_age_max");
            product.setTargetAgeMax(rs.wasNull() ? null : maxAge);

            list.add(product);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // ==============================
    // ギフト履歴の重複確認
    // ==============================

    // 指定した誕生日期間にギフトを登録済みか確認する
    public boolean existsHistoryInPeriod(
            String userId,
            String familyBirthdayId,
            Date startDate,
            Date endDate) throws Exception {

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT COUNT(*) FROM gift " +
            "WHERE user_id = ? " +
            "AND family_birthday_id = ? " +
            "AND selected_date BETWEEN ? AND ?"
        );

        st.setString(1, userId);
        st.setString(2, familyBirthdayId);
        st.setDate(3, startDate);
        st.setDate(4, endDate);

        ResultSet rs = st.executeQuery();

        rs.next();

        boolean result = rs.getInt(1) > 0;

        rs.close();
        st.close();
        con.close();

        return result;
    }

    // ==============================
    // ギフト履歴登録
    // ==============================

    // 選択した商品をギフト履歴に登録する
    // 同じ家族への同じ誕生日期間の登録は1回のみ
    public boolean insertHistory(
            String userId,
            String familyBirthdayId,
            String giftProductId,
            Date startDate,
            Date endDate) throws Exception {

        Connection con = getConnection();

        try {

            // トランザクション開始
            con.setAutoCommit(false);

            // 対象の家族情報をロックする
            PreparedStatement lockSt = con.prepareStatement(
                "SELECT family_birthday_id FROM birthday " +
                "WHERE user_id = ? " +
                "AND family_birthday_id = ? " +
                "AND CURRENT_DATE BETWEEN ? AND ? " +
                "FOR UPDATE"
            );

            lockSt.setString(1, userId);
            lockSt.setString(2, familyBirthdayId);
            lockSt.setDate(3, startDate);
            lockSt.setDate(4, endDate);

            ResultSet lockRs = lockSt.executeQuery();

            boolean familyExists = lockRs.next();

            lockRs.close();
            lockSt.close();

            // 登録期間外または家族情報が存在しない場合
            if (!familyExists) {

                con.rollback();
                con.setAutoCommit(true);
                con.close();

                return false;
            }

            // 同じ誕生日期間に登録済みか確認する
            PreparedStatement checkSt = con.prepareStatement(
                "SELECT COUNT(*) FROM gift " +
                "WHERE user_id = ? " +
                "AND family_birthday_id = ? " +
                "AND selected_date BETWEEN ? AND ?"
            );

            checkSt.setString(1, userId);
            checkSt.setString(2, familyBirthdayId);
            checkSt.setDate(3, startDate);
            checkSt.setDate(4, endDate);

            ResultSet checkRs = checkSt.executeQuery();

            checkRs.next();

            boolean alreadyRegistered = checkRs.getInt(1) > 0;

            checkRs.close();
            checkSt.close();

            // すでに登録済みの場合
            if (alreadyRegistered) {

                con.rollback();
                con.setAutoCommit(true);
                con.close();

                return false;
            }

            // ギフト履歴を登録する
            PreparedStatement st = con.prepareStatement(
                "INSERT INTO gift " +
                "(user_id, family_birthday_id, gift_product_id, " +
                "gift_name, gift_text, gift_image) " +
                "SELECT ?, ?, gift_product_id, gift_name, gift_text, gift_image " +
                "FROM gift_product " +
                "WHERE gift_product_id = ?"
            );

            st.setString(1, userId);
            st.setString(2, familyBirthdayId);
            st.setString(3, giftProductId);

            int result = st.executeUpdate();

            st.close();

            // 登録成功
            if (result > 0) {

                con.commit();
                con.setAutoCommit(true);
                con.close();

                return true;
            }

            // 商品が存在しない場合
            con.rollback();
            con.setAutoCommit(true);
            con.close();

            return false;

        } catch (Exception e) {

            con.rollback();
            con.close();

            throw e;
        }
    }

    // ==============================
    // ギフト履歴一覧
    // ==============================

    // ユーザーIDからギフト履歴を取得する
    // birthdayテーブルと結合して家族の続柄も取得する
    public List<Gift> searchByUserId(String userId) throws Exception {

        List<Gift> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT g.*, b.relation " +
            "FROM gift g " +
            "LEFT JOIN birthday b " +
            "ON g.user_id = b.user_id " +
            "AND g.family_birthday_id = b.family_birthday_id " +
            "WHERE g.user_id = ? " +
            "ORDER BY g.selected_date DESC, g.gift_id DESC"
        );

        st.setString(1, userId);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            Gift gift = new Gift();

            gift.setGiftId(rs.getString("gift_id"));
            gift.setUserId(rs.getString("user_id"));
            gift.setFamilyBirthdayId(rs.getString("family_birthday_id"));
            gift.setGiftProductId(rs.getString("gift_product_id"));
            gift.setGiftName(rs.getString("gift_name"));
            gift.setGiftText(rs.getString("gift_text"));
            gift.setGiftImage(rs.getString("gift_image"));
            gift.setSelectedDate(rs.getDate("selected_date"));

            // 家族の続柄
            gift.setRelation(rs.getString("relation"));

            list.add(gift);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

}