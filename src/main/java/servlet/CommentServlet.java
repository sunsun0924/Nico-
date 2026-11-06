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
            CommentDAO commentDao = new CommentDAO();

            // コメントを登録してコメントIDを取得
            String commentId = commentDao.insert(comment);

            // コメント登録結果を確認
            if (commentId == null) {
                request.setAttribute("message", "コメントの投稿に失敗しました。");
                return "/servlet.PostServlet.action";
            }

            // 投稿DAOを作成
            PostDAO postDao = new PostDAO();

            // コメント対象の投稿情報を取得
            Post post = postDao.findByPostId(postId);

            // 投稿情報の確認
            if (post == null) {
                request.setAttribute("message", "コメントは投稿しましたが、投稿情報を取得できませんでした。");
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

            // 自分の投稿へのコメントでなければ通知を作成
            if (!isSelfComment) {

                // 通知Beanを作成
                Notification notification = new Notification();

                // 通知種別を設定
                notification.setNotificationType("comment");

                // 通知メッセージを設定
                notification.setMessage("あなたの投稿にコメントがありました。");

                // 通知日時を設定
                notification.setNotificationDatetime(LocalDateTime.now());

                // 投稿IDを設定
                notification.setPostId(postId);

                // コメントIDを設定
                notification.setCommentId(commentId);

                // ギフトIDはNULL
                notification.setGiftId(null);

                // 通知先がユーザーの場合
                if ("USER".equals(post.getAuthor_type())) {

                    // 通知先のユーザーIDを設定
                    notification.setUserId(post.getUser_id());

                    // 管理者IDはNULL
                    notification.setAdminId(null);

                // 通知先が管理者の場合
                } else if ("ADMIN".equals(post.getAuthor_type())) {

                    // 通知先の管理者IDを設定
                    notification.setAdminId(post.getAdmin_id());

                    // ユーザーIDはNULL
                    notification.setUserId(null);

                } else {
                    request.setAttribute("message", "コメントは投稿しましたが、通知先を判定できませんでした。");
                    return "/servlet.PostServlet.action";
                }

                // コメントした人が管理者の場合
                if (admin != null) {

                    // 送信者の管理者IDを設定
                    notification.setSenderAdminId(admin.getCpUserid());

                    // ユーザーIDはNULL
                    notification.setSenderId(null);

                } else {

                    // 送信者のユーザーIDを設定
                    notification.setSenderId(user.getUserId());

                    // 管理者IDはNULL
                    notification.setSenderAdminId(null);
                }

                // 通知DAOを作成
                NotificationDAO notificationDao = new NotificationDAO();

                // 通知を登録
                int result = notificationDao.insert(notification);

                // 通知登録結果を確認
                if (result > 0) {
                    request.setAttribute("message", "コメントを投稿しました。");
                } else {
                    request.setAttribute("message", "コメントは投稿しましたが、通知の登録に失敗しました。");
                }

            } else {

                // 自分の投稿へのコメントの場合は通知しない
                request.setAttribute("message", "コメントを投稿しました。");
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