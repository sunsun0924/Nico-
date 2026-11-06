package servlet;

import bean.Admin;
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

            // 管理者情報を取得
            Admin admin = (Admin) session.getAttribute("admin");

            // 管理者としてログインしている場合
            if (admin != null) {

                String adminId = admin.getCpUserid();

                // admin000 または3文字目・4文字目が ad
                if ("admin000".equals(adminId)
                        || (adminId != null
                            && adminId.length() >= 4
                            && "ad".equals(adminId.substring(2, 4)))) {

                    loginPage = "/login/adminLogin.jsp";
                }
            }

            // セッションを破棄
            session.invalidate();
        }

        return loginPage;
    }
}