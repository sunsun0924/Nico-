package servlet;

import java.util.List;

import bean.FamilyBD;
import bean.User;
import dao.FamilyBDDAO;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 登録情報変更画面の表示処理
public class FamilyBDPageServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null || session.getAttribute("user") == null) {
            return "/login/userLogin.jsp";
        }

        // ログイン中のユーザー情報を取得
        User sessionUser = (User) session.getAttribute("user");

        // DAOを作成
        UserDAO userDao = new UserDAO();
        FamilyBDDAO familyDao = new FamilyBDDAO();

        // データベースから最新のユーザー情報を取得
        User user = userDao.findByUserId(sessionUser.getUserId());

        // ユーザー情報が取得できなかった場合
        if (user == null) {
            request.setAttribute(
                "errorMessage",
                "＊ユーザー情報を取得できませんでした"
            );

            return "/mypage/mypage_detail.jsp";
        }

        // セッションのユーザー情報を最新の内容に更新
        session.setAttribute("user", user);

        // 家族の誕生日を取得
        List<FamilyBD> familyList =
            familyDao.findByUserId(user.getUserId());

        request.setAttribute("familyList", familyList);

        // 登録情報変更画面を表示
        return "/mypage/user_information.jsp";
    }
}