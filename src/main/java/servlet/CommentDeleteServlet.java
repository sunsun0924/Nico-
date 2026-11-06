package servlet;

import bean.Admin;
import bean.User;
import dao.CommentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// コメント削除処理
public class CommentDeleteServlet extends Action {

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

        // 未入力チェック
        if (commentId == null || commentId.trim().isEmpty()) {

            session.setAttribute(
                "deleteMessage",
                "コメントを削除できませんでした"
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

        // 本人のコメントだけ削除
        int result = dao.deleteComment(
            commentId.trim(),
            userId,
            adminId,
            isAdmin
        );

        // 削除結果を設定
        if (result > 0) {

            session.setAttribute(
                "deleteMessage",
                "コメントを削除しました"
            );

        } else {

            session.setAttribute(
                "deleteMessage",
                "自分のコメント以外は削除できません"
            );
        }

        // 投稿一覧へ戻る
        return "/servlet.PostServlet.action";
    }
}