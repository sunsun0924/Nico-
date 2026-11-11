package servlet;

import bean.Admin;
import bean.User;
import dao.BookmarkDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ブックマーク登録・解除処理
public class BookmarkServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // セッションの取得
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null) {
            return "/login/userLogin.jsp";
        }

        User user = (User) session.getAttribute("user");
        Admin admin = (Admin) session.getAttribute("admin");

        if (user == null && admin == null) {
            return "/login/userLogin.jsp";
        }

        // 投稿IDの取得
        String postId = request.getParameter("postId");

        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("message", "投稿情報を取得できませんでした。");

            String returnTo = request.getParameter("returnTo");

            if ("bookmark".equals(returnTo)) {
                return "/servlet.BookmarkListServlet.action";
            }

            return "/servlet.PostServlet.action";
        }

        // ログインしているアカウントの情報を取得
        String userId = null;
        String adminId = null;
        String authorType;

        if (admin != null) {
            adminId = admin.getCpUserid();
            authorType = "ADMIN";
        } else {
            userId = user.getUserId();
            authorType = "USER";
        }

        // ブックマーク登録・解除
        try {
            BookmarkDAO dao = new BookmarkDAO();

            dao.toggle(
                    postId.trim(),
                    userId,
                    adminId,
                    authorType
            );

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute(
                    "message",
                    "ブックマークの変更に失敗しました。"
            );
        }

        // 戻り先の判定
        String returnTo = request.getParameter("returnTo");

        if ("bookmark".equals(returnTo)) {
            return "/servlet.BookmarkListServlet.action";
        }

        return "/servlet.PostServlet.action";
    }
}