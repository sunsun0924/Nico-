
package servlet;

import java.util.List;

import bean.Notification;
import bean.User;
import dao.NotificationDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 通知一覧を表示するServlet
public class NotificationServlet extends Action {

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
                || session.getAttribute("user") == null) {

            // ログインしていない場合はユーザーログイン画面へ
            return "/login/userLogin.jsp";
        }

        // ログイン中のユーザー情報を取得
        User user = (User) session.getAttribute("user");

        // ユーザーIDを取得
        String userId = user.getUserId();

        try {

            // 通知情報を検索
            NotificationDAO dao = new NotificationDAO();

            List<Notification> notificationList =
                    dao.findByUserId(userId);

            // 通知一覧をJSPに渡す
            request.setAttribute("notificationList", notificationList);

            // 通知画面を表示
            return "/notification/notification.jsp";

        } catch (Exception e) {

            throw new Exception("通知一覧の取得に失敗しました。", e);
        }
    }
}
