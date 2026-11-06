
package servlet;

import bean.Admin;
import bean.User;
import dao.PostDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 投稿編集処理
public class PostEditServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // セッションを取得
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

        // 投稿IDを取得
        String postId = request.getParameter("postId");

        // 投稿本文を取得
        String postText = request.getParameter("postText");

        // 未入力チェック
        if (postId == null || postId.trim().isEmpty()
                || postText == null || postText.trim().isEmpty()) {

            session.setAttribute(
                "deleteMessage",
                "投稿本文を入力してください"
            );

            return "/servlet.PostServlet.action";
        }

        // ログイン情報を取得
        boolean isAdmin = admin != null;

        String userId = null;
        String adminId = null;

        if (isAdmin) {
            adminId = admin.getCpUserid();
        } else {
            userId = user.getUserId();
        }

        // 投稿DAOを作成
        PostDAO dao = new PostDAO();

        // 本人の投稿だけ編集
        int result = dao.updatePost(
            postId.trim(),
            postText.trim(),
            userId,
            adminId,
            isAdmin
        );

        // 編集結果を設定
        if (result > 0) {

            session.setAttribute(
                "deleteMessage",
                "投稿を編集しました"
            );

        } else {

            session.setAttribute(
                "deleteMessage",
                "自分の投稿以外は編集できません"
            );
        }

        // 投稿一覧へ戻る
        return "/servlet.PostServlet.action";
    }
}
