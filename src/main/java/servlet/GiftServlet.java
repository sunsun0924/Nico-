package servlet;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.FamilyBD;
import bean.GiftProduct;
import bean.User;
import dao.FamilyBDDAO;
import dao.GiftDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ギフト一覧・詳細表示処理

public class GiftServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログインしていない場合
        if (session == null || session.getAttribute("user") == null) {
            return "/login/userLogin.jsp";
        }

        try {

            // ログイン中のユーザー情報を取得
            User user = (User) session.getAttribute("user");

            // ユーザーIDを取得
            String userId = user.getUserId().trim();

            // DAOを生成
            GiftDAO dao = new GiftDAO();
            FamilyBDDAO familyBDDao = new FamilyBDDAO();

            // 現在の日付を取得
            LocalDate today = LocalDate.now();

            // 家族の誕生日一覧を取得
            List<FamilyBD> familyBDList =
                    familyBDDao.findByUserId(userId);

            // 家族ごとのおすすめギフト一覧
            List<Map<String, Object>> recommendedGiftSections =
                    new ArrayList<>();

            // 家族の誕生日を確認
            for (FamilyBD familyBD : familyBDList) {

                // 誕生日を取得
                LocalDate birthday = familyBD.getBirthday();

                // 誕生日が登録されていない場合
                if (birthday == null) {
                    continue;
                }

                // 次の誕生日を取得
                LocalDate nextBirthday =
                        getNextBirthday(birthday, today);

                // 登録開始日を取得
                LocalDate startDate = nextBirthday.minusMonths(1);

                // 誕生日が1か月以内の場合
                if (today.isBefore(startDate)
                        || today.isAfter(nextBirthday)) {
                    continue;
                }

                // 誕生日の登録期間
                Date sqlStartDate = Date.valueOf(startDate);
                Date sqlEndDate = Date.valueOf(nextBirthday);

                // すでにギフトを登録している場合
                if (dao.existsHistoryInPeriod(
                        userId,
                        familyBD.getFamilyBirthdayId(),
                        sqlStartDate,
                        sqlEndDate)) {

                    continue;
                }

                // 誕生日当日に迎える年齢を計算
                int age = nextBirthday.getYear()
                        - birthday.getYear();

                // 年齢に合ったおすすめ商品を取得
                List<GiftProduct> recommendedGiftList =
                        dao.selectRecommendedByAge(age);

                // 家族ごとのおすすめ情報を作成
                Map<String, Object> section = new HashMap<>();

                // 家族の誕生日ID
                section.put(
                        "familyBirthdayId",
                        familyBD.getFamilyBirthdayId()
                );

                // 続柄
                section.put(
                        "relation",
                        familyBD.getRelation()
                );

                // 次の誕生日
                section.put(
                        "birthday",
                        nextBirthday
                );

                // 誕生日に迎える年齢
                section.put(
                        "age",
                        age
                );

                // おすすめ商品一覧
                section.put(
                        "giftList",
                        recommendedGiftList
                );

                // 家族ごとのおすすめ情報を追加
                recommendedGiftSections.add(section);
            }

            // 家族ごとのおすすめギフトを設定
            request.setAttribute(
                    "recommendedGiftSections",
                    recommendedGiftSections
            );

            // 商品IDを取得
            String giftProductId =
                    request.getParameter("giftProductId");

            // 商品IDが取得できない場合
            if (giftProductId == null || giftProductId.isEmpty()) {
                giftProductId = request.getParameter("gift");
            }

            // ギフト詳細画面の場合
            if (giftProductId != null && !giftProductId.isEmpty()) {

                // 商品IDから商品情報を検索
                GiftProduct giftProduct =
                        dao.searchByGiftProductId(giftProductId);

                // 商品が存在しない場合
                if (giftProduct == null) {

                    request.setAttribute(
                            "errorMessage",
                            "指定されたギフトが見つかりません。"
                    );

                    request.setAttribute(
                            "giftList",
                            dao.selectAll()
                    );

                    return "/gift/gift.jsp";
                }

                // 家族の誕生日IDを取得
                String familyBirthdayId =
                        request.getParameter("familyBirthdayId");

                // 贈る相手を取得
                FamilyBD selectedFamily = null;

                // おすすめ欄から来た場合
                if (familyBirthdayId != null
                        && !familyBirthdayId.trim().isEmpty()) {

                    for (FamilyBD familyBD : familyBDList) {

                        if (familyBD.getFamilyBirthdayId().equals(
                                familyBirthdayId)) {

                            selectedFamily = familyBD;
                            break;
                        }
                    }

                } else {

                    // すべての商品から選んだ場合
                    // 誕生日が最も近い家族を自動設定

                    LocalDate nearestBirthday = null;

                    for (FamilyBD familyBD : familyBDList) {

                        LocalDate birthday = familyBD.getBirthday();

                        if (birthday == null) {
                            continue;
                        }

                        LocalDate nextBirthday =
                                getNextBirthday(birthday, today);

                        LocalDate startDate =
                                nextBirthday.minusMonths(1);

                        // 登録期間外の場合
                        if (today.isBefore(startDate)
                                || today.isAfter(nextBirthday)) {
                            continue;
                        }

                        // 登録期間
                        Date sqlStartDate = Date.valueOf(startDate);
                        Date sqlEndDate = Date.valueOf(nextBirthday);

                        // すでに登録済みの場合
                        if (dao.existsHistoryInPeriod(
                                userId,
                                familyBD.getFamilyBirthdayId(),
                                sqlStartDate,
                                sqlEndDate)) {

                            continue;
                        }

                        // 最も誕生日が近い家族を選択
                        if (nearestBirthday == null
                                || nextBirthday.isBefore(nearestBirthday)) {

                            nearestBirthday = nextBirthday;
                            selectedFamily = familyBD;
                        }
                    }
                }

                // 贈る相手が決まらない場合
                if (selectedFamily == null) {

                    request.setAttribute(
                            "errorMessage",
                            "現在ギフトを登録できる家族がいません。"
                    );

                    request.setAttribute(
                            "giftList",
                            dao.selectAll()
                    );

                    return "/gift/gift.jsp";
                }

                // 選択された家族の誕生日を取得
                LocalDate selectedBirthday =
                        selectedFamily.getBirthday();

                LocalDate nextBirthday =
                        getNextBirthday(selectedBirthday, today);

                LocalDate startDate =
                        nextBirthday.minusMonths(1);

                // 登録期間を確認
                if (today.isBefore(startDate)
                        || today.isAfter(nextBirthday)) {

                    request.setAttribute(
                            "errorMessage",
                            "この家族は現在ギフトを登録できる期間ではありません。"
                    );

                    request.setAttribute(
                            "giftList",
                            dao.selectAll()
                    );

                    return "/gift/gift.jsp";
                }

                // すでに登録済みか確認
                if (dao.existsHistoryInPeriod(
                        userId,
                        selectedFamily.getFamilyBirthdayId(),
                        Date.valueOf(startDate),
                        Date.valueOf(nextBirthday))) {

                    request.setAttribute(
                            "errorMessage",
                            "この家族には、すでに今回の誕生日のギフトを登録しています。"
                    );

                    request.setAttribute(
                            "giftList",
                            dao.selectAll()
                    );

                    return "/gift/gift.jsp";
                }

                // 商品情報を設定
                request.setAttribute(
                        "giftProduct",
                        giftProduct
                );

                request.setAttribute(
                        "giftName",
                        giftProduct.getGiftName()
                );

                request.setAttribute(
                        "giftDescription",
                        giftProduct.getGiftText()
                );

                // 贈る相手の情報を設定
                request.setAttribute(
                        "familyBirthdayId",
                        selectedFamily.getFamilyBirthdayId()
                );

                request.setAttribute(
                        "familyRelation",
                        selectedFamily.getRelation()
                );

                // ギフト詳細画面へ
                return "/gift/giftdetail.jsp";

            } else {

                // 検索キーワードを取得
                String keyword = request.getParameter("keyword");

                List<GiftProduct> giftList;

                // 検索キーワードが入力されている場合
                if (keyword != null && !keyword.trim().isEmpty()) {

                    // 商品を検索
                    giftList = dao.searchProducts(keyword.trim());

                    // 検索キーワードを設定
                    request.setAttribute(
                            "keyword",
                            keyword
                    );

                } else {

                    // すべての商品を取得
                    giftList = dao.selectAll();
                }

                // 商品一覧を設定
                request.setAttribute(
                        "giftList",
                        giftList
                );

                // ギフト一覧画面へ
                return "/gift/gift.jsp";
            }

        } catch (Exception e) {

            // エラーを呼び出し元へ渡す
            throw new Exception(
                    "ギフト情報の取得に失敗しました。",
                    e
            );
        }
    }

    // ==============================
    // 次の誕生日を取得
    // ==============================

    // 誕生日から次に迎える誕生日を取得する
    private LocalDate getNextBirthday(
            LocalDate birthday,
            LocalDate today) {

        LocalDate nextBirthday;

        // 2月29日生まれで今年がうるう年ではない場合
        if (birthday.getMonthValue() == 2
                && birthday.getDayOfMonth() == 29
                && !today.isLeapYear()) {

            nextBirthday = LocalDate.of(
                    today.getYear(),
                    2,
                    28
            );

        } else {

            nextBirthday = birthday.withYear(today.getYear());
        }

        // 今年の誕生日が過ぎている場合
        if (nextBirthday.isBefore(today)) {

            int nextYear = today.getYear() + 1;

            // 来年がうるう年ではない場合
            if (birthday.getMonthValue() == 2
                    && birthday.getDayOfMonth() == 29
                    && !LocalDate.of(nextYear, 1, 1).isLeapYear()) {

                nextBirthday = LocalDate.of(
                        nextYear,
                        2,
                        28
                );

            } else {

                nextBirthday = birthday.withYear(nextYear);
            }
        }

        return nextBirthday;
    }
}