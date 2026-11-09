package servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ログアウト処理
public class LogoutServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 現在のセッションを取得
        HttpSession session = request.getSession(false);

        // デフォルトはユーザー用ログイン画面
        String loginPage = "/login/userLogin.jsp";

        if (session != null) {

            // 管理者としてログインしている場合
            if (session.getAttribute("admin") != null) {
                loginPage = "/login/adminLogin.jsp";
            }

            // セッションを破棄
            session.invalidate();
        }

        return loginPage;
    }
}