package servlet;

import java.util.List;

import bean.Admin;
import bean.Post;
import bean.User;
import dao.BookmarkDAO;
import dao.CommentDAO;
import dao.PostDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 投稿一覧表示処理
public class PostServlet extends Action {

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

        // ログイン者のIDと種類を格納
        String userId = null;
        String adminId = null;
        String authorType;

        // 管理者がログインしている場合
        if (admin != null) {

            adminId = admin.getCpUserid();
            authorType = "ADMIN";

        } else {

            // ユーザーがログインしている場合
            userId = user.getUserId();
            authorType = "USER";
        }

        try {

            // 投稿DAOを作成
            PostDAO postDAO = new PostDAO();

            // コメントDAOを作成
            CommentDAO commentDAO = new CommentDAO();

            // ブックマークDAOを作成
            BookmarkDAO bookmarkDAO = new BookmarkDAO();

            // 投稿一覧を取得
            List<Post> postList = postDAO.findAll();

            // 投稿ごとにコメントとブックマーク状態を取得
            for (Post post : postList) {

                // 投稿IDを取得
                String postId = post.getPost_id();

                // 投稿に紐づくコメント一覧を取得
                post.setComments(
                    commentDAO.findByPostId(postId)
                );

                // ログイン者のブックマーク状態を取得
                boolean bookmarked = bookmarkDAO.isBookmarked(
                    postId,
                    userId,
                    adminId,
                    authorType
                );

                // ブックマーク状態を設定
                post.setBookmarked(bookmarked);
            }

            // 投稿一覧をJSPに渡す
            request.setAttribute("postList", postList);

            // 投稿一覧画面を表示
            return "/post/post.jsp";

        } catch (Exception e) {

            // エラー内容を表示
            e.printStackTrace();

            throw new Exception("投稿一覧の取得に失敗しました。", e);
        }
    }
}