package servlet;

import java.util.List;

import bean.Gift;
import bean.User;
import dao.GiftDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ギフト履歴表示処理

public class Gift_historyServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null) {
            return "/login/userLogin.jsp";
        }

        // ユーザー情報を取得
        User user = (User) session.getAttribute("user");

        // ログインしていない場合
        if (user == null) {
            return "/login/userLogin.jsp";
        }

        // ユーザーIDを取得
        String userId = user.getUserId().trim();

        // ギフト履歴を検索
        GiftDAO dao = new GiftDAO();

        List<Gift> giftHistoryList = dao.searchByUserId(userId);

        // ギフト履歴をJSPに渡す
        request.setAttribute(
            "giftHistoryList",
            giftHistoryList
        );

        // ギフト履歴画面へ
        return "/mypage/gift_history.jsp";
    }

}