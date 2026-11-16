package servlet;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import bean.FamilyBD;
import bean.User;
import dao.FamilyBDDAO;
import dao.GiftDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ギフト登録処理

public class GiftSelectServlet extends Action {

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

        // ログイン中のユーザー情報を取得
        User user = (User) session.getAttribute("user");

        // ユーザーIDを取得
        String userId = user.getUserId().trim();

        // 商品IDを取得
        String giftProductId = request.getParameter("giftProductId");

        // 贈る相手のIDを取得
        String familyBirthdayId =
                request.getParameter("familyBirthdayId");

        // 商品IDがない場合
        if (giftProductId == null || giftProductId.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "ギフト商品が選択されていません。"
            );

            return "/gift/gift.jsp";
        }

        // 贈る相手がない場合
        if (familyBirthdayId == null
                || familyBirthdayId.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "贈る相手を確認できません。"
            );

            return "/gift/gift.jsp";
        }

        // DAOを生成
        GiftDAO giftDAO = new GiftDAO();
        FamilyBDDAO familyBDDAO = new FamilyBDDAO();

        // 商品が存在するか確認
        if (giftDAO.searchByGiftProductId(giftProductId) == null) {

            request.setAttribute(
                    "errorMessage",
                    "指定されたギフトが見つかりません。"
            );

            return "/gift/gift.jsp";
        }

        // 家族の誕生日一覧を取得
        List<FamilyBD> familyBDList =
                familyBDDAO.findByUserId(userId);

        // 贈る相手を確認
        FamilyBD selectedFamily = null;

        for (FamilyBD familyBD : familyBDList) {

            if (familyBD.getFamilyBirthdayId().equals(
                    familyBirthdayId)) {

                selectedFamily = familyBD;
                break;
            }
        }

        // 自分の家族ではない場合
        if (selectedFamily == null) {

            request.setAttribute(
                    "errorMessage",
                    "贈る相手の情報が正しくありません。"
            );

            return "/gift/gift.jsp";
        }

        // 誕生日を取得
        LocalDate birthday = selectedFamily.getBirthday();

        // 誕生日が登録されていない場合
        if (birthday == null) {

            request.setAttribute(
                    "errorMessage",
                    "家族の誕生日が登録されていません。"
            );

            return "/gift/gift.jsp";
        }

        // 現在の日付
        LocalDate today = LocalDate.now();

        // 次の誕生日を取得
        LocalDate nextBirthday =
                getNextBirthday(birthday, today);

        // 登録開始日
        LocalDate startDate =
                nextBirthday.minusMonths(1);

        // 誕生日の登録期間外の場合
        if (today.isBefore(startDate)
                || today.isAfter(nextBirthday)) {

            request.setAttribute(
                    "errorMessage",
                    "ギフトを登録できる期間ではありません。誕生日の1か月前から誕生日当日までに登録してください。"
            );

            return "/gift/gift.jsp";
        }

        // 登録期間をSQLの日付に変換
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(nextBirthday);

        // ギフト履歴を登録
        boolean result = giftDAO.insertHistory(
                userId,
                familyBirthdayId,
                giftProductId,
                sqlStartDate,
                sqlEndDate
        );

        // 登録できなかった場合
        if (!result) {

            request.setAttribute(
                    "errorMessage",
                    "この家族には、今回の誕生日のギフトをすでに登録しているか、登録期間外です。"
            );

            return "/gift/gift.jsp";
        }

        // 登録成功
        request.setAttribute(
                "successMessage",
                selectedFamily.getRelation() + "へのギフトを登録しました。"
        );

        // ギフト一覧画面へ
        request.setAttribute(
                "giftList",
                giftDAO.selectAll()
        );

        return "/gift/gift.jsp";
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
