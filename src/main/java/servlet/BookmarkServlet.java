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

        // 投稿IDの入力確認
        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("message", "投稿情報を取得できませんでした。");
            return "/servlet.PostServlet.action";
        }

        // ログイン者の情報を格納
        String userId = null;
        String adminId = null;
        String authorType;

        // 管理者がログインしている場合
        if (admin != null) {

            // 管理者IDを設定
            adminId = admin.getCpUserid();

            // 投稿者の種類を設定
            authorType = "ADMIN";

        } else {

            // ユーザーIDを設定
            userId = user.getUserId();

            // 投稿者の種類を設定
            authorType = "USER";
        }

        try {

            // ブックマークDAOを作成
            BookmarkDAO dao = new BookmarkDAO();

            // ブックマーク状態を切り替える
            dao.toggle(
                postId.trim(),
                userId,
                adminId,
                authorType
            );

        } catch (Exception e) {

            // エラー内容を表示
            e.printStackTrace();

            request.setAttribute(
                "message",
                "ブックマークの変更に失敗しました。"
            );
        }

        // 投稿一覧へ戻る
        return "/servlet.PostServlet.action";
    }
}