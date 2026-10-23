
package servlet;

import java.io.IOException;
import java.util.List;

import bean.Post;
import bean.User;
import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 投稿の一覧表示・編集・削除を行うServlet
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 投稿一覧を表示する
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ログイン状態を確認する
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login/userLogin.jsp");
            return;
        }

        try {
            // 全投稿を取得する
            PostDAO dao = new PostDAO();
            List<Post> postList = dao.findAll();

            // 投稿一覧をJSPに渡す
            request.setAttribute("postList", postList);

            request.getRequestDispatcher(
                "/post.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                "投稿一覧の取得に失敗しました。", e);
        }
    }

    // 投稿の編集・削除を処理する
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化けを防ぐ
        request.setCharacterEncoding("UTF-8");

        // ログイン状態を確認する
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login/userLogin.jsp");
            return;
        }

        // ログイン中のユーザーを取得する
        User user = (User) session.getAttribute("user");
        String user_id = user.getUserId();

        // 実行する処理を取得する
        String action = request.getParameter("action");

        try {
            PostDAO dao = new PostDAO();

            // 投稿を編集する
            if ("update".equals(action)) {

                Post post = new Post();

                post.setPost_id(request.getParameter("post_id"));
                post.setPost_text(request.getParameter("post_text"));
                post.setPost_img(request.getParameter("post_img"));
                post.setComment(request.getParameter("comment"));

                // 自分の投稿だけ更新する
                int result = dao.update(post, user_id);

                if (result == 0) {
                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "投稿を更新できません。");
                    return;
                }

            // 投稿を削除する
            } else if ("delete".equals(action)) {

                String post_id = request.getParameter("post_id");

                // 自分の投稿だけ削除する
                int result = dao.delete(post_id, user_id);

                if (result == 0) {
                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "投稿を削除できません。");
                    return;
                }

            } else {
                // 不正な処理を拒否する
                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "不正なリクエストです。");
                return;
            }

            // 処理後に投稿一覧へ戻る
            response.sendRedirect(
                request.getContextPath() + "/PostServlet");

        } catch (Exception e) {
            throw new ServletException(
                "投稿の編集・削除に失敗しました。", e);
        }
    }
}
