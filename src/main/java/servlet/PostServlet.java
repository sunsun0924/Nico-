package servlet;

import java.util.List;

import bean.Admin;
import bean.Comment;
import bean.Post;
import bean.User;
import dao.AdminDAO;
import dao.BookmarkDAO;
import dao.CommentDAO;
import dao.PostDAO;
import dao.UserDAO;
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
            UserDAO userDAO = new UserDAO();
            AdminDAO adminDAO = new AdminDAO();

            // 投稿一覧を取得
            List<Post> postList = postDAO.findAll();

            for (Post post : postList) {

                String postId = post.getPost_id();

                // ========================================
                // 投稿者のプロフィール情報を取得
                // ========================================

                if ("ADMIN".equals(post.getAuthor_type())) {

                    // 管理者のプロフィール情報
                    if (post.getAdmin_id() != null) {

                        Admin postAdmin = adminDAO.findByCpUserid(
                            post.getAdmin_id().trim()
                        );

                        if (postAdmin != null) {

                            post.setAuthorName(
                                postAdmin.getCpUsername()
                            );

                            post.setAuthorImage(
                                postAdmin.getCpUserimg()
                            );

                            post.setAuthorImagePositionX(
                                postAdmin.getCpUserimgPositionX()
                            );

                            post.setAuthorImagePositionY(
                                postAdmin.getCpUserimgPositionY()
                            );

                        }

                    }

                    // 管理者名が取得できない場合
                    if (post.getAuthorName() == null
                            || post.getAuthorName().trim().isEmpty()) {

                        post.setAuthorName(
                            post.getAdmin_id() != null
                                ? post.getAdmin_id().trim()
                                : "管理者"
                        );

                    }

                } else {

                    // 一般ユーザーのプロフィール情報
                    if (post.getUser_id() != null) {

                        User postUser = userDAO.findByUserId(
                            post.getUser_id().trim()
                        );

                        if (postUser != null) {

                            post.setAuthorName(
                                postUser.getUserName()
                            );

                            post.setAuthorImage(
                                postUser.getUserImg()
                            );

                            post.setAuthorImagePositionX(
                                postUser.getUserImgPositionX()
                            );

                            post.setAuthorImagePositionY(
                                postUser.getUserImgPositionY()
                            );

                        }

                    }

                    // ユーザー名が取得できない場合
                    if (post.getAuthorName() == null
                            || post.getAuthorName().trim().isEmpty()) {

                        post.setAuthorName(
                            post.getUser_id() != null
                                ? post.getUser_id().trim()
                                : "ユーザー"
                        );

                    }

                }

                // プロフィール画像が未設定の場合
                if (post.getAuthorImage() == null
                        || post.getAuthorImage().trim().isEmpty()) {

                    post.setAuthorImage("default-profile.png");

                }

                // ========================================
                // コメント一覧を取得
                // ========================================

                List<Comment> commentList =
                    commentDAO.findByPostId(postId);

                // ========================================
                // コメント投稿者のプロフィール情報を取得
                // ========================================

                for (Comment comment : commentList) {

                    if ("ADMIN".equals(comment.getAuthorType())) {

                        // 管理者ごとのプロフィール情報を取得
                        if (comment.getAdminId() != null) {

                            Admin commentAdmin =
                                adminDAO.findByCpUserid(
                                    comment.getAdminId().trim()
                                );

                            if (commentAdmin != null) {

                                // 管理者の名前
                                comment.setAuthorName(
                                    commentAdmin.getCpUsername()
                                );

                                // 管理者のプロフィール画像
                                comment.setAuthorImage(
                                    commentAdmin.getCpUserimg()
                                );

                            }

                        }

                        // 管理者名が取得できない場合は管理者IDを表示
                        if (comment.getAuthorName() == null
                                || comment.getAuthorName().trim().isEmpty()) {

                            comment.setAuthorName(
                                comment.getAdminId() != null
                                    ? comment.getAdminId().trim()
                                    : "管理者"
                            );

                        }

                    } else {

                        // 一般ユーザーごとのプロフィール情報を取得
                        if (comment.getUserId() != null) {

                            User commentUser =
                                userDAO.findByUserId(
                                    comment.getUserId().trim()
                                );

                            if (commentUser != null) {

                                // ユーザーの名前
                                comment.setAuthorName(
                                    commentUser.getUserName()
                                );

                                // ユーザーのプロフィール画像
                                comment.setAuthorImage(
                                    commentUser.getUserImg()
                                );

                            }

                        }

                        // ユーザー名が取得できない場合はユーザーIDを表示
                        if (comment.getAuthorName() == null
                                || comment.getAuthorName().trim().isEmpty()) {

                            comment.setAuthorName(
                                comment.getUserId() != null
                                    ? comment.getUserId().trim()
                                    : "ユーザー"
                            );

                        }

                    }

                    // プロフィール画像が未設定の場合
                    if (comment.getAuthorImage() == null
                            || comment.getAuthorImage().trim().isEmpty()) {

                        comment.setAuthorImage("default-profile.png");

                    }

                    // ========================================
                    // コメントの編集・削除権限を確認
                    // ========================================

                    boolean commentEditable = false;

                    if (admin != null) {

                        commentEditable =
                            "ADMIN".equals(comment.getAuthorType())
                            && comment.getAdminId() != null
                            && adminId.equals(
                                comment.getAdminId().trim()
                            );

                    } else {

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

                    editable =
                        "ADMIN".equals(post.getAuthor_type())
                        && post.getAdmin_id() != null
                        && adminId.equals(
                            post.getAdmin_id().trim()
                        );

                } else {

                    editable =
                        "USER".equals(post.getAuthor_type())
                        && post.getUser_id() != null
                        && userId.equals(
                            post.getUser_id().trim()
                        );

                }

                post.setEditable(editable);

            }

            // ========================================
            // 投稿一覧をJSPに渡す
            // ========================================

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