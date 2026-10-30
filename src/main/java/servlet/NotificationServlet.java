
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

// 通知一覧を表示
public class NotificationServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // ログイン確認
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {
            return "/login/userLogin.jsp";
        }

        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        try {
            // 通知の分類を取得
            String filter = request.getParameter("filter");

            if (filter == null || filter.isEmpty()) {
                filter = "all";
            }

            // 不正な分類を防止
            if (!filter.equals("all")
                    && !filter.equals("comment")
                    && !filter.equals("gift")) {
                filter = "all";
            }

            // 通知一覧を取得
            NotificationDAO dao = new NotificationDAO();
            List<Notification> notificationList =
                    dao.findByUserId(userId);

            List<Map<String, String>> notifications =
                    new ArrayList<>();

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

            // 通知を分類して画面表示用に変換
            for (Notification notification : notificationList) {

                String type = notification.getNotificationType();

                if (!filter.equals("all") && !filter.equals(type)) {
                    continue;
                }

                Map<String, String> map = new HashMap<>();

                map.put("type", type != null ? type : "");

                map.put("message",
                        notification.getMessage() != null
                                ? notification.getMessage() : "");

                map.put("detail", "");

                map.put("postId",
                        notification.getPostId() != null
                                ? notification.getPostId() : "");

                map.put("commentId",
                        notification.getCommentId() != null
                                ? notification.getCommentId() : "");

                map.put("giftId",
                        notification.getGiftId() != null
                                ? notification.getGiftId() : "");

                if (notification.getNotificationDatetime() != null) {
                    map.put("date",
                            notification.getNotificationDatetime()
                                    .format(formatter));
                } else {
                    map.put("date", "");
                }

                notifications.add(map);
            }

            // JSPにデータを渡す
            request.setAttribute("notifications", notifications);
            request.setAttribute("filter", filter);

            return "/notification/notification.jsp";

        } catch (Exception e) {
            throw new Exception("通知一覧の取得に失敗しました。", e);
        }
    }
}
