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
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // ログイン画面から管理者IDとパスワードを取得
        String cpUserid = request.getParameter("cpUserid");
        String cpUserpass = request.getParameter("cpUserpass");
        

        // 未入力チェック
        if (cpUserid == null || cpUserid.trim().isEmpty()
                || cpUserpass == null || cpUserpass.isEmpty()) {

            request.setAttribute("errorMessage",
                    "管理者IDとパスワードを入力してください");

            return "/login/adminLogin.jsp";
        }

        // 管理者情報を検索
        AdminDAO dao = new AdminDAO();
        Admin admin = dao.search(cpUserid.trim(), cpUserpass);

        // ログイン成功
        if (admin != null) {

            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);

            return "/post/post.jsp";

        } else {

            // ログイン失敗
            request.setAttribute("errorMessage",
                    "管理者IDまたはパスワードが違います");

            return "/login/adminLogin.jsp";
        }
    }
}