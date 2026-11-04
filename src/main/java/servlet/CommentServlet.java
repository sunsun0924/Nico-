package servlet;

import bean.Admin;
import bean.Comment;
import bean.User;
import dao.CommentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// コメント登録処理
public class CommentServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);

        // セッションが存在しない場合
        if (session == null) {
            return "/login/userLogin.jsp";
        }

        // ユーザー情報を取得
        User user = (User) session.getAttribute("user");

        // 管理者情報を取得
        Admin admin = (Admin) session.getAttribute("admin");

        // ログイン確認
        if (user == null && admin == null) {
            return "/login/userLogin.jsp";
        }

        // 投稿IDを取得
        String postId = request.getParameter("postId");

        // コメント本文を取得
        String commentText = request.getParameter("commentText");

        // 投稿IDの入力確認
        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("message", "投稿情報を取得できませんでした。");
            return "/servlet.PostServlet.action";
        }

        // コメント本文の入力確認
        if (commentText == null || commentText.trim().isEmpty()) {
            request.setAttribute("message", "コメントを入力してください。");
            return "/servlet.PostServlet.action";
        }

        // 前後の空白を削除
        postId = postId.trim();
        commentText = commentText.trim();

        // コメントの文字数を確認
        if (commentText.length() > 1000) {
            request.setAttribute("message", "コメントは1000文字以内で入力してください。");
            return "/servlet.PostServlet.action";
        }

        try {

            // コメントBeanを作成
            Comment comment = new Comment();

            // 投稿IDを設定
            comment.setPostId(postId);

            // コメント本文を設定
            comment.setCommentText(commentText);

            // 管理者がログインしている場合
            if (admin != null) {

                // 管理者IDを設定
                comment.setAdminId(admin.getCpUserid());

                // ユーザーIDはNULL
                comment.setUserId(null);

                // 投稿者の種類を設定
                comment.setAuthorType("ADMIN");

            } else {

                // ユーザーIDを設定
                comment.setUserId(user.getUserId());

                // 管理者IDはNULL
                comment.setAdminId(null);

                // 投稿者の種類を設定
                comment.setAuthorType("USER");
            }

            // コメントDAOを作成
            CommentDAO dao = new CommentDAO();

            // コメントを登録
            boolean result = dao.insert(comment);

            // 登録結果を設定
            if (result) {
                request.setAttribute("message", "コメントを投稿しました。");
            } else {
                request.setAttribute("message", "コメントの投稿に失敗しました。");
            }

        } catch (Exception e) {

            // エラー内容を表示
            e.printStackTrace();

            request.setAttribute("message", "コメントの登録中にエラーが発生しました。");
        }

        // 投稿一覧へ戻る
        return "/servlet.PostServlet.action";
    }
}