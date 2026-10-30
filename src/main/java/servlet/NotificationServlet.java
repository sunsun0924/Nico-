
package servlet;

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

            // JSPに渡す通知データを作成
            List<Map<String, String>> notifications =
                    new ArrayList<>();

            // 通知を1件ずつ取り出す
            for (Notification notification : notificationList) {

                Map<String, String> map = new HashMap<>();

                // 通知タイプを取得
                String notificationType =
                        notification.getNotificationType();

                // 通知タイプをJSP用に変換
                if ("comment".equals(notificationType)) {
                    map.put("type", "comment");

                } else if ("gift".equals(notificationType)) {
                    map.put("type", "gift");

                } else {
                    map.put("type", notificationType);
                }

                // 通知メッセージ
                map.put("message", notification.getMessage());

                // 通知日時
                if (notification.getNotificationDatetime() != null) {

                    map.put("date",
                            notification.getNotificationDatetime()
                                    .toString()
                                    .replace("T", " "));

                } else {
                    map.put("date", "");
                }

                // コメント・ギフトの詳細
                map.put("detail", notification.getMessage());

                // 通知一覧に追加
                notifications.add(map);
            }

            // JSPに通知一覧を渡す
            request.setAttribute("notifications", notifications);

            // 通知画面を表示
            return "/notification/notification.jsp";

        } catch (Exception e) {

            throw new Exception("通知一覧の取得に失敗しました。", e);
        }
    }
}
