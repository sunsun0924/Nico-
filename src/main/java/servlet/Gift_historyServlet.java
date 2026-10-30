
package servlet;

import java.util.ArrayList;
import java.util.List;

import bean.Gift;
import dao.GiftDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ギフト履歴表示処理
public class Gift_historyServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // ログイン中のユーザー情報を取得
        HttpSession session = request.getSession(false);

        // セッションが存在しない場合
        if (session == null || session.getAttribute("userId") == null) {

            request.setAttribute("giftHistoryList", new ArrayList<Gift>());

            return "/mypage/gift_history.jsp";
        }

        // ユーザーIDを取得
        String userId = (String) session.getAttribute("userId");

        // ギフト履歴を検索
        GiftDAO dao = new GiftDAO();
        List<Gift> giftHistoryList = dao.searchByUserId(userId);

        // ギフト履歴をJSPに渡す
        request.setAttribute("giftHistoryList", giftHistoryList);

        // ギフト履歴画面へ
        return "/mypage/gift_history.jsp";
    }
}
