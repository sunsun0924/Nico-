
package servlet;

import bean.Admin;
import bean.User;
import dao.PostDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 投稿削除処理
public class PostDeleteServlet extends Action {

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

        // 未入力チェック
        if (postId == null || postId.trim().isEmpty()) {

            session.setAttribute(
                "deleteMessage",
                "投稿を削除できませんでした"
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

        // 本人の投稿だけ削除
        boolean result = dao.deletePost(
            postId.trim(),
            userId,
            adminId,
            isAdmin
        );

        // 削除結果を設定
        if (result) {

            session.setAttribute(
                "deleteMessage",
                "投稿を削除しました"
            );

        } else {

            session.setAttribute(
                "deleteMessage",
                "自分の投稿以外は削除できません"
            );
        }

        // 投稿一覧へ戻る
        return "/servlet.PostServlet.action";
    }
}
