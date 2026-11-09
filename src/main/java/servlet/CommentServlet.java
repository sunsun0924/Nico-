package servlet;

import java.time.LocalDateTime;

import bean.Admin;
import bean.Comment;
import bean.Notification;
import bean.Post;
import bean.User;
import dao.CommentDAO;
import dao.NotificationDAO;
import dao.PostDAO;
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

        if (session == null) {
            return "/login/userLogin.jsp";
        }

        // ログイン情報を取得
        User user = (User) session.getAttribute("user");
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
            request.setAttribute(
                "message",
                "投稿情報を取得できませんでした。"
            );
            return "/servlet.PostServlet.action";
        }

        // コメント本文の入力確認
        if (commentText == null || commentText.trim().isEmpty()) {
            request.setAttribute(
                "message",
                "コメントを入力してください。"
            );
            return "/servlet.PostServlet.action";
        }

        // 前後の空白を削除
        postId = postId.trim();
        commentText = commentText.trim();

        // コメントの文字数を確認
        if (commentText.length() > 1000) {
            request.setAttribute(
                "message",
                "コメントは1000文字以内で入力してください。"
            );
            return "/servlet.PostServlet.action";
        }

        try {
            // 投稿情報を取得
            PostDAO postDao = new PostDAO();

            Post post = postDao.findByPostId(postId);

            if (post == null) {
                request.setAttribute(
                    "message",
                    "投稿情報が見つかりませんでした。"
                );
                return "/servlet.PostServlet.action";
            }

            // コメントBeanを作成
            Comment comment = new Comment();

            comment.setPostId(postId);
            comment.setCommentText(commentText);

            // 管理者がログインしている場合
            if (admin != null) {

                comment.setAdminId(admin.getCpUserid());
                comment.setUserId(null);
                comment.setAuthorType("ADMIN");

            } else {

                comment.setUserId(user.getUserId());
                comment.setAdminId(null);
                comment.setAuthorType("USER");
            }

            // コメントを登録
            CommentDAO commentDao = new CommentDAO();

            String commentId = commentDao.insert(comment);

            if (commentId == null) {
                request.setAttribute(
                    "message",
                    "コメントの投稿に失敗しました。"
                );
                return "/servlet.PostServlet.action";
            }

            // 自分の投稿へのコメントか確認
            boolean isSelfComment = false;

            // 投稿者がユーザーの場合
            if ("USER".equals(post.getAuthor_type())) {

                if (user != null
                        && post.getUser_id() != null
                        && post.getUser_id().equals(user.getUserId())) {

                    isSelfComment = true;
                }

            // 投稿者が管理者の場合
            } else if ("ADMIN".equals(post.getAuthor_type())) {

                if (admin != null
                        && post.getAdmin_id() != null
                        && post.getAdmin_id().equals(admin.getCpUserid())) {

                    isSelfComment = true;
                }
            }

            // 自分の投稿でなければ通知を作成
            if (!isSelfComment) {

                Notification notification = new Notification();

                // 通知種別
                notification.setNotificationType("comment");

             // コメント本文と送信者名を含めた通知メッセージ
                if (admin != null) {

                    // 管理者からのコメント
                    notification.setMessage(
                        "管理者からコメントがありました。\n"
                        + commentText
                    );

                } else {

                    // ユーザーからのコメント
                    notification.setMessage(
                        user.getUserName()
                        + "さんからコメントがありました。\n"
                        + commentText
                    );
                }

                // 通知日時
                notification.setNotificationDatetime(
                    LocalDateTime.now()
                );

                // 投稿ID
                notification.setPostId(postId);

                // ギフトIDはNULL
                notification.setGiftId(null);

                // 通知先がユーザーの場合
                if ("USER".equals(post.getAuthor_type())) {

                    notification.setUserId(post.getUser_id());
                    notification.setAdminId(null);

                // 通知先が管理者の場合
                } else if ("ADMIN".equals(post.getAuthor_type())) {

                    notification.setAdminId(post.getAdmin_id());
                    notification.setUserId(null);

                } else {

                    request.setAttribute(
                        "message",
                        "コメントは投稿しましたが、通知先を判定できませんでした。"
                    );
                    return "/servlet.PostServlet.action";
                }

                // 通知元が管理者の場合
                if (admin != null) {

                    notification.setSenderAdminId(
                        admin.getCpUserid()
                    );

                    notification.setSenderId(null);

                // 通知元がユーザーの場合
                } else {

                    notification.setSenderId(user.getUserId());
                    notification.setSenderAdminId(null);
                }

                // 通知を登録
                NotificationDAO notificationDao =
                    new NotificationDAO();

                // =================================
                // 通知登録前の確認（コンソールに表示）
                // =================================
                System.out.println("===== 通知登録確認 =====");

                System.out.println(
                    "コメント本文：" + commentText
                );

                System.out.println(
                    "通知メッセージ：" + notification.getMessage()
                );

                System.out.println(
                    "通知先ユーザーID：" + notification.getUserId()
                );

                System.out.println(
                    "通知先管理者ID：" + notification.getAdminId()
                );

                // DBに通知を登録
                int result = notificationDao.insert(notification);

                System.out.println(
                    "通知登録結果：" + result
                );

                System.out.println("========================");

                if (result > 0) {

                    request.setAttribute(
                        "message",
                        "コメントを投稿しました。"
                    );

                } else {

                    request.setAttribute(
                        "message",
                        "コメントは投稿しましたが、通知の登録に失敗しました。"
                    );
                }

            } else {

                // 自分の投稿には通知しない
                request.setAttribute(
                    "message",
                    "コメントを投稿しました。"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "message",
                "コメントの登録中にエラーが発生しました。"
            );
        }

        // 投稿一覧へ戻る
        return "/servlet.PostServlet.action";
    }
}