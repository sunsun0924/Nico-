package servlet;

import bean.Admin;
import bean.User;
import dao.AdminDAO;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 管理者ログイン処理
public class AdminLoginServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // ログイン画面から管理者IDとパスワードを取得
        String cpUserid = request.getParameter("cpUserid");
        String cpUserpass = request.getParameter("cpUserpass");

        // 未入力チェック
        if (cpUserid == null || cpUserid.trim().isEmpty()
                || cpUserpass == null || cpUserpass.isEmpty()) {

            request.setAttribute(
                "errorMessage",
                "管理者IDとパスワードを入力してください"
            );

            return "/login/adminLogin.jsp";
        }

        // 管理者情報を検索
        AdminDAO dao = new AdminDAO();

        Admin admin = dao.search(cpUserid.trim(), cpUserpass);

        // ログイン成功
        if (admin != null) {

            // セッションを取得
            HttpSession session = request.getSession();

            // ユーザー情報を削除
            session.removeAttribute("user");

            // 管理者情報を保存
            session.setAttribute("admin", admin);

            // 投稿一覧Servletを経由して投稿画面を表示
            return "/servlet.PostServlet.action";

        } else {

            // ユーザー情報を検索
            UserDAO userDao = new UserDAO();

            User user = userDao.search(cpUserid.trim(), cpUserpass);

            // 一般ユーザーアカウントでログインしようとした場合
            if (user != null) {

                request.setAttribute(
                    "errorMessage",
                    "＊一般ユーザーアカウントでは管理者ログインできません\n＊ユーザーログイン画面からログインしてください"
                );

            } else {

                // ログイン失敗
                request.setAttribute(
                    "errorMessage",
                    "管理者IDまたはパスワードが違います"
                );
            }

            return "/login/adminLogin.jsp";
        }
    }
}