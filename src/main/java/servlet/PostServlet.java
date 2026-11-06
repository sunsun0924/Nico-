
package servlet;

import java.util.List;

import bean.Admin;
import bean.Comment;
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

        // ログイン情報
        String userId = null;
        String adminId = null;
        String authorType;

        if (admin != null) {

            adminId = admin.getCpUserid().trim();
            authorType = "ADMIN";

        } else {

            userId = user.getUserId().trim();
            authorType = "USER";
        }

        try {

            PostDAO postDAO = new PostDAO();
            CommentDAO commentDAO = new CommentDAO();
            BookmarkDAO bookmarkDAO = new BookmarkDAO();

            // 投稿一覧を取得
            List<Post> postList = postDAO.findAll();

            for (Post post : postList) {

                String postId = post.getPost_id();

                // ========================================
                // コメント一覧を取得
                // ========================================

                List<Comment> commentList =
                    commentDAO.findByPostId(postId);

                // ========================================
                // コメントの編集・削除権限を確認
                // ========================================

                for (Comment comment : commentList) {

                    boolean commentEditable = false;

                    if (admin != null) {

                        // 管理者は自分のコメントだけ操作できる
                        commentEditable =
                            "ADMIN".equals(comment.getAuthorType())
                            && comment.getAdminId() != null
                            && adminId.equals(
                                comment.getAdminId().trim()
                            );

                    } else {

                        // ユーザーは自分のコメントだけ操作できる
                        commentEditable =
                            "USER".equals(comment.getAuthorType())
                            && comment.getUserId() != null
                            && userId.equals(
                                comment.getUserId().trim()
                            );
                    }

                    comment.setEditable(commentEditable);
                }

                // コメント一覧を投稿に設定
                post.setComments(commentList);

                // ========================================
                // ブックマーク状態を取得
                // ========================================

                boolean bookmarked = bookmarkDAO.isBookmarked(
                    postId,
                    userId,
                    adminId,
                    authorType
                );

                post.setBookmarked(bookmarked);

                // ========================================
                // 投稿の編集・削除権限を確認
                // ========================================

                boolean editable = false;

                if (admin != null) {

                    // 管理者は自分の投稿だけ操作できる
                    editable =
                        "ADMIN".equals(post.getAuthor_type())
                        && post.getAdmin_id() != null
                        && adminId.equals(
                            post.getAdmin_id().trim()
                        );

                } else {

                    // ユーザーは自分の投稿だけ操作できる
                    editable =
                        "USER".equals(post.getAuthor_type())
                        && post.getUser_id() != null
                        && userId.equals(
                            post.getUser_id().trim()
                        );
                }

                post.setEditable(editable);
            }

            // 投稿一覧をJSPに渡す
            request.setAttribute("postList", postList);

            // 処理結果メッセージ
            String deleteMessage =
                (String) session.getAttribute("deleteMessage");

            if (deleteMessage != null) {

                request.setAttribute("message", deleteMessage);

                session.removeAttribute("deleteMessage");
            }

            return "/post/post.jsp";

        } catch (Exception e) {

            e.printStackTrace();

            throw new Exception(
                "投稿一覧の取得に失敗しました。",
                e
            );
        }
    }
}
