
package servlet;

import java.util.List;

import bean.Gift;
import dao.GiftDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ギフト一覧を表示するServlet
public class GiftServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字化けを防ぐ
        request.setCharacterEncoding("UTF-8");

        // ログイン状態を確認する
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("admin") == null) {

            // ログインしていない場合は管理者ログイン画面へ
            return "/login/adminLogin.jsp";
        }

        try {

            // DBからギフト一覧を取得
            GiftDAO dao = new GiftDAO();
            List<Gift> giftList = dao.selectAll();

            // ギフト一覧をJSPに渡す
            request.setAttribute("giftList", giftList);

            // ギフト画面を表示
            return "/gift/gift.jsp";

        } catch (Exception e) {

            throw new Exception("ギフト一覧の取得に失敗しました。", e);
        }
    }
}
