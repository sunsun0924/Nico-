package servlet;

import bean.Admin;
import bean.Post;
import bean.User;
import dao.PostDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 新規投稿をDBに登録するServlet
public class PostInsertServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字化け防止
        request.setCharacterEncoding("UTF-8");

        // ログイン状態を確認
        HttpSession session = request.getSession(false);

        if (session == null) {

            request.setAttribute(
                "message", "ログインしてください。");

            return "/post/postnew.jsp";
        }

        // ユーザー情報と管理者情報を取得
        User user = (User) session.getAttribute("user");
        Admin admin = (Admin) session.getAttribute("admin");

        // どちらもログインしていない場合
        if (user == null && admin == null) {

            request.setAttribute(
                "message", "ログインしてください。");

            return "/post/postnew.jsp";
        }

        try {

            // 投稿本文を取得
            String postText = request.getParameter("post_text");

            // 未入力チェック
            if (postText == null || postText.trim().isEmpty()) {

                request.setAttribute(
                    "message", "投稿内容を入力してください。");

                return "/post/postnew.jsp";
            }

            // 前後の空白を削除
            postText = postText.trim();

            // 200文字以内か確認
            if (postText.length() > 200) {

                request.setAttribute(
                    "message",
                    "投稿内容は200文字以内で入力してください。");

                return "/post/postnew.jsp";
            }

            // 投稿Beanを作成
            Post post = new Post();

            // 管理者がログインしている場合
            if (admin != null) {

                // 管理者IDを保存
                post.setUser_id(null);
                post.setAdmin_id(admin.getCpUserid());
                post.setAuthor_type("ADMIN");

            } else {

                // ユーザーIDを保存
                post.setUser_id(user.getUserId());
                post.setAdmin_id(null);
                post.setAuthor_type("USER");
            }

            // 投稿内容を設定
            post.setPost_text(postText);
            post.setPost_img(null);
            post.setComment(null);

            // 投稿DAOを作成
            PostDAO dao = new PostDAO();

            // DBに登録
            boolean result = dao.insert(post);

            if (result) {

                request.setAttribute(
                    "message", "投稿を登録しました！");

            } else {

                request.setAttribute(
                    "message", "投稿の登録に失敗しました。");
            }

            // 投稿一覧へ戻る
            return "/servlet.PostServlet.action";

        } catch (Exception e) {

            // エラー内容を表示
            e.printStackTrace();

            request.setAttribute(
                "message",
                "投稿の登録中にエラーが発生しました。");

            return "/post/postnew.jsp";
        }
    }
}