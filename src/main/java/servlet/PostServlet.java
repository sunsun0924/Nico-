
package servlet;

import java.util.List;

import bean.Admin;
import bean.Post;
import dao.PostDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 投稿の一覧表示・編集・削除を行うServlet
public class PostServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字化けを防ぐ
        request.setCharacterEncoding("UTF-8");

        // ログイン状態を確認する
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("admin") == null) {

            // ログインしていない場合は管理者ログイン画面へ
            return "/login/adminLogin.jsp";
        }

        // ログイン中の管理者情報を取得する
        Admin admin = (Admin) session.getAttribute("admin");

        // リクエストの処理方法を取得する
        String action = request.getParameter("action");

        try {

            PostDAO dao = new PostDAO();

            // 投稿の編集処理
            if ("update".equals(action)) {

                Post post = new Post();

                post.setPost_id(request.getParameter("post_id"));
                post.setPost_text(request.getParameter("post_text"));
                post.setPost_img(request.getParameter("post_img"));
                post.setComment(request.getParameter("comment"));

                // 投稿を更新する
                int result = dao.update(post, admin.getCpUserid());

                if (result == 0) {
                    request.setAttribute("errorMessage",
                            "投稿を更新できませんでした。");

                    return "/post/post.jsp";
                }

            // 投稿の削除処理
            } else if ("delete".equals(action)) {

                String post_id = request.getParameter("post_id");

                // 投稿を削除する
                int result = dao.delete(post_id, admin.getCpUserid());

                if (result == 0) {
                    request.setAttribute("errorMessage",
                            "投稿を削除できませんでした。");

                    return "/post/post.jsp";
                }

            }

            // 投稿一覧を取得する
            List<Post> postList = dao.findAll();

            // 投稿一覧をJSPに渡す
            request.setAttribute("postList", postList);

            // 投稿一覧画面へ
            return "/post/post.jsp";

        } catch (Exception e) {

            throw new Exception("投稿の一覧表示・編集・削除に失敗しました。", e);
        }
    }
}
