package servlet;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import bean.FamilyBD;
import bean.User;
import dao.FamilyBDDAO;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 登録情報と家族の誕生日を保存する処理
public class FamilyBDServlet extends Action {

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null || session.getAttribute("user") == null) {
            return "/login/userLogin.jsp";
        }

        User user = (User) session.getAttribute("user");

        // ユーザー情報を取得
        String userMail = request.getParameter("userMail");
        String userNumber = request.getParameter("userNumber");
        String userAddress = request.getParameter("userAddress");

        // 家族情報を取得
        String[] familyTypes = request.getParameterValues("familyType");
        String[] childGenders = request.getParameterValues("childGender");
        String[] childOrders = request.getParameterValues("childOrder");
        String[] familyBirthdays = request.getParameterValues("familyBirthday");

        UserDAO userDao = new UserDAO();
        FamilyBDDAO familyDao = new FamilyBDDAO();

        // nullの場合は空文字にする
        if (userMail == null) {
            userMail = "";
        }

        if (userNumber == null) {
            userNumber = "";
        }

        if (userAddress == null) {
            userAddress = "";
        }

        // 前後の空白を削除
        userMail = userMail.trim();
        userNumber = userNumber.trim();
        userAddress = userAddress.trim();

        // ユーザー情報を設定
        user.setUserMail(userMail);
        user.setUserNumber(userNumber);
        user.setUserAddress(userAddress);

        // 登録する家族情報を一時保存
        List<FamilyBD> newFamilyList = new ArrayList<>();

        // 続柄の重複確認
        Set<String> relationSet = new HashSet<>();

        // 家族情報が送信された場合
        if (familyTypes != null || familyBirthdays != null
                || childGenders != null || childOrders != null) {

            // 配列の数を確認
            if (familyTypes == null || familyBirthdays == null
                    || childGenders == null || childOrders == null
                    || familyTypes.length != familyBirthdays.length
                    || familyTypes.length != childGenders.length
                    || familyTypes.length != childOrders.length) {

                request.setAttribute("errorMessage",
                    "＊家族の入力情報を正しく取得できませんでした");

                request.setAttribute("familyList",
                    familyDao.findByUserId(user.getUserId()));

                return "/mypage/user_information.jsp";
            }

            // 子供の続柄
            String[] maleRelations = {
                "", "長男", "次男", "三男", "四男",
                "五男", "六男", "七男", "八男", "九男", "十男"
            };

            String[] femaleRelations = {
                "", "長女", "次女", "三女", "四女",
                "五女", "六女", "七女", "八女", "九女", "十女"
            };

            // 家族情報を1件ずつ確認
            for (int i = 0; i < familyTypes.length; i++) {

                String familyType = familyTypes[i];
                String familyBirthday = familyBirthdays[i];
                String childGender = childGenders[i];
                String childOrder = childOrders[i];

                // nullの場合は空文字にする
                if (familyType == null) familyType = "";
                if (familyBirthday == null) familyBirthday = "";
                if (childGender == null) childGender = "";
                if (childOrder == null) childOrder = "";

                // 前後の空白を削除
                familyType = familyType.trim();
                familyBirthday = familyBirthday.trim();
                childGender = childGender.trim();
                childOrder = childOrder.trim();

                // 何も入力されていない欄は飛ばす
                if (familyType.isEmpty() && familyBirthday.isEmpty()) {
                    continue;
                }

                // 続柄が選択されていない場合
                if (familyType.isEmpty()) {

                    request.setAttribute("errorMessage",
                        "＊家族の続柄を選択してください");

                    request.setAttribute("familyList",
                        familyDao.findByUserId(user.getUserId()));

                    return "/mypage/user_information.jsp";
                }

                // 誕生日が入力されていない場合
                if (familyBirthday.isEmpty()) {

                    request.setAttribute("errorMessage",
                        "＊家族の誕生日を入力してください");

                    request.setAttribute("familyList",
                        familyDao.findByUserId(user.getUserId()));

                    return "/mypage/user_information.jsp";
                }

                // 続柄を設定
                String relation = "";

                if (familyType.equals("夫")) {

                    relation = "夫";

                } else if (familyType.equals("妻")) {

                    relation = "妻";

                } else if (familyType.equals("子供")) {

                    // 性別確認
                    if (!childGender.equals("男")
                            && !childGender.equals("女")) {

                        request.setAttribute("errorMessage",
                            "＊子供の性別を選択してください");

                        request.setAttribute("familyList",
                            familyDao.findByUserId(user.getUserId()));

                        return "/mypage/user_information.jsp";
                    }

                    // 順番確認
                    int order;

                    try {
                        order = Integer.parseInt(childOrder);
                    } catch (NumberFormatException e) {

                        request.setAttribute("errorMessage",
                            "＊子供の順番を正しく入力してください");

                        request.setAttribute("familyList",
                            familyDao.findByUserId(user.getUserId()));

                        return "/mypage/user_information.jsp";
                    }

                    if (order < 1 || order > 99) {

                        request.setAttribute("errorMessage",
                            "＊子供の順番は1～99で入力してください");

                        request.setAttribute("familyList",
                            familyDao.findByUserId(user.getUserId()));

                        return "/mypage/user_information.jsp";
                    }

                    // 性別と順番から続柄を決める
                    if (childGender.equals("男")) {

                        if (order <= 10) {
                            relation = maleRelations[order];
                        } else {
                            relation = "第" + order + "子（男）";
                        }

                    } else {

                        if (order <= 10) {
                            relation = femaleRelations[order];
                        } else {
                            relation = "第" + order + "子（女）";
                        }

                    }

                } else {

                    request.setAttribute("errorMessage",
                        "＊続柄を正しく選択してください");

                    request.setAttribute("familyList",
                        familyDao.findByUserId(user.getUserId()));

                    return "/mypage/user_information.jsp";
                }

                // 誕生日を変換
                LocalDate birthday;

                try {
                    birthday = LocalDate.parse(familyBirthday);
                } catch (DateTimeParseException e) {

                    request.setAttribute("errorMessage",
                        "＊誕生日を正しく入力してください");

                    request.setAttribute("familyList",
                        familyDao.findByUserId(user.getUserId()));

                    return "/mypage/user_information.jsp";
                }

                // 未来の日付を確認
                if (birthday.isAfter(LocalDate.now())) {

                    request.setAttribute("errorMessage",
                        "＊未来の日付は登録できません");

                    request.setAttribute("familyList",
                        familyDao.findByUserId(user.getUserId()));

                    return "/mypage/user_information.jsp";
                }

                // 同じ画面内で続柄が重複していないか確認
                if (!relationSet.add(relation)) {

                    request.setAttribute("errorMessage",
                        "＊同じ続柄は複数登録できません");

                    request.setAttribute("familyList",
                        familyDao.findByUserId(user.getUserId()));

                    return "/mypage/user_information.jsp";
                }

                // DBに同じ続柄が登録済みでないか確認
                if (familyDao.isExist(user.getUserId(), relation)) {

                    request.setAttribute("errorMessage",
                        "＊この続柄はすでに登録されています");

                    request.setAttribute("familyList",
                        familyDao.findByUserId(user.getUserId()));

                    return "/mypage/user_information.jsp";
                }

                // 家族情報を作成
                FamilyBD family = new FamilyBD();

                family.setUserId(user.getUserId());
                family.setRelation(relation);
                family.setBirthday(birthday);

                newFamilyList.add(family);
            }
        }

        // ユーザー情報を更新
        // 自分の誕生日は更新しない
        int userLine = userDao.updateUserInfo(user);

        if (userLine == 0) {

            request.setAttribute("errorMessage",
                "＊登録情報を更新できませんでした");

            request.setAttribute("familyList",
                familyDao.findByUserId(user.getUserId()));

            return "/mypage/user_information.jsp";
        }

        // セッションのユーザー情報を更新
        session.setAttribute("user", user);

        try {

            // 家族情報を登録
            for (FamilyBD family : newFamilyList) {

                // 家族誕生日IDを作成
                String familyBirthdayId =
                    UUID.randomUUID().toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();

                family.setFamilyBirthdayId(familyBirthdayId);

                familyDao.insert(family);
            }

        } catch (java.sql.SQLException e) {

            // 続柄が重複した場合
            if ("23505".equals(e.getSQLState())) {

                request.setAttribute("errorMessage",
                    "＊同じ続柄は登録できません");

                request.setAttribute("familyList",
                    familyDao.findByUserId(user.getUserId()));

                return "/mypage/user_information.jsp";

            } else {

                throw e;
            }
        }

        // 保存成功メッセージをセッションに保存
        session.setAttribute("mypageMessage",
            "＊登録情報と家族の誕生日を保存しました");

        // マイページ詳細画面に戻る
        return "/mypage/mypage_detail.jsp";
    }
}
