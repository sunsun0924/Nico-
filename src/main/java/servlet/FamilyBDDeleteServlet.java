package servlet;

import bean.User;
import dao.FamilyBDDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 家族の誕生日削除処理
public class FamilyBDDeleteServlet extends Action {

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

        // 削除する家族誕生日IDを取得
        String familyBirthdayId =
            request.getParameter("familyBirthdayId");

        FamilyBDDAO dao = new FamilyBDDAO();

        // IDが取得できなかった場合
        if (familyBirthdayId == null
                || familyBirthdayId.trim().isEmpty()) {

            request.setAttribute("errorMessage",
                "＊削除する家族の情報を取得できませんでした");

        } else {

            // データベースから削除
            int line = dao.delete(
                familyBirthdayId,
                user.getUserId()
            );

            if (line > 0) {

                request.setAttribute("message",
                    "＊家族の誕生日を削除しました");

            } else {

                request.setAttribute("errorMessage",
                    "＊削除する家族の誕生日が見つかりませんでした");

            }
        }

        // 最新の家族情報を取得
        request.setAttribute(
            "familyList",
            dao.findByUserId(user.getUserId())
        );

        // 登録情報変更画面に戻る
        return "/mypage/user_information.jsp";
    }
}
