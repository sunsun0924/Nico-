
package servlet;

import bean.Admin;
import dao.AdminDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 管理者ログイン処理
public class AdminLoginServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // セッションを取得（なければ新しく作成）
        HttpSession session = request.getSession();

        // フォームから管理者IDを取得
        String userId = request.getParameter("userId");

        // フォームからパスワードを取得
        String password = request.getParameter("password");

        // 未入力チェック
        if (userId == null || userId.trim().isEmpty()
                || password == null || password.isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "＊管理者IDとパスワードを入力してください");

            return "/login/adminLogin.jsp";
        }

        // 管理者IDの前後の空白を除去
        userId = userId.trim();

        // DAOを使って管理者を検索（認証処理）
        AdminDAO dao = new AdminDAO();
        Admin admin = dao.search(userId, password);

        // 管理者が見つかった場合（ログイン成功）
        if (admin != null) {

            // セッションに管理者情報を保存
            session.setAttribute("admin", admin);

            // 管理者画面へ遷移
            return "/post/post.jsp";

        } else {

            // IDまたはパスワードが一致しない場合
            request.setAttribute(
                    "errorMessage",
                    "＊ログインに失敗しました\n＊管理者IDまたはパスワードが違います");

            return "/login/adminLogin.jsp";
        }
    }
}
