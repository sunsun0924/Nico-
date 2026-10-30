
package servlet;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            // ログインしていない場合はログイン画面へ
            return "/login/userLogin.jsp";
        }

        // ログイン中のユーザー情報を取得
        User user = (User) session.getAttribute("user");

        // ユーザーIDを取得
        String userId = user.getUserId();

        try {

            // 通知情報を取得
            NotificationDAO dao = new NotificationDAO();

            List<Notification> notificationList =
                    dao.findByUserId(userId);

            // JSPに渡す通知一覧を作成
            List<Map<String, String>> notifications =
                    new ArrayList<>();

            // 日時の表示形式を設定
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

            // 通知を1件ずつ取り出す
            for (Notification notification : notificationList) {

                // JSPに渡すデータを作成
                Map<String, String> map = new HashMap<>();

                // 通知タイプを取得
                String type = notification.getNotificationType();

                // 通知の種類を設定
                map.put("type", type);

                // 通知メッセージを設定
                map.put("message",
                        notification.getMessage() != null
                                ? notification.getMessage()
                                : "");

                // 通知詳細を設定
                // 現在のNotification Beanには詳細専用の項目がないため空欄
                map.put("detail", "");

                // 通知日時を設定
                if (notification.getNotificationDatetime() != null) {

                    map.put("date",
                            notification.getNotificationDatetime()
                                    .format(formatter));

                } else {

                    map.put("date", "");
                }

                // 通知一覧に追加
                notifications.add(map);
            }

            // JSPに通知一覧を渡す
            request.setAttribute("notifications", notifications);

            // 通知画面を表示
            return "/notification/notification.jsp";

        } catch (Exception e) {

            throw new Exception(
                    "通知一覧の取得に失敗しました。", e);
        }
    }
}
