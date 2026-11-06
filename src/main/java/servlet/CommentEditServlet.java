package servlet;

import bean.Admin;
import bean.User;
import dao.CommentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// コメント編集処理
public class CommentEditServlet extends Action {

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

        // コメントIDを取得
        String commentId = request.getParameter("commentId");

        // 編集内容を取得
        String commentText = request.getParameter("commentText");

        // 未入力チェック
        if (commentId == null || commentId.trim().isEmpty()
                || commentText == null || commentText.trim().isEmpty()) {

            session.setAttribute(
                "deleteMessage",
                "コメントを入力してください"
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

        // コメントDAOを作成
        CommentDAO dao = new CommentDAO();

        // 本人のコメントだけ編集
        int result = dao.update(
            commentId.trim(),
            commentText.trim(),
            userId,
            adminId,
            isAdmin
        );

        // 編集結果を設定
        if (result > 0) {

            session.setAttribute(
                "deleteMessage",
                "コメントを編集しました"
            );

        } else {

            session.setAttribute(
                "deleteMessage",
                "自分のコメント以外は編集できません"
            );
        }

        // 投稿一覧へ戻る
        return "/servlet.PostServlet.action";
    }
}