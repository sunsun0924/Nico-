package servlet;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Admin;
import bean.Notification;
import bean.User;
import dao.NotificationDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 通知一覧を表示するServlet
public class NotificationServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

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

        try {

            // タブの種類を取得
            String filter = request.getParameter("filter");

            if (filter == null || filter.isEmpty()) {
                filter = "all";
            }

            if (!filter.equals("all")
                    && !filter.equals("comment")
                    && !filter.equals("gift")) {

                filter = "all";
            }

            NotificationDAO dao = new NotificationDAO();

            List<Notification> notificationList;

            // ユーザーの場合
            if (user != null) {

                notificationList =
                    dao.findByUserId(user.getUserId());

                request.setAttribute("accountType", "user");

            // 管理者の場合
            } else {

                notificationList =
                    dao.findByAdminId(admin.getCpUserid());

                request.setAttribute("accountType", "admin");
            }

            // JSPに渡す通知一覧
            List<Map<String, String>> notifications =
                new ArrayList<>();

            DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

            for (Notification notification : notificationList) {

                String type =
                    notification.getNotificationType();

                // タブによる絞り込み
                if (!filter.equals("all")
                        && !filter.equals(type)) {
                    continue;
                }

                Map<String, String> map = new HashMap<>();

                map.put("type", type != null ? type : "");

                map.put(
                    "message",
                    notification.getMessage() != null
                        ? notification.getMessage() : ""
                );

                map.put(
                    "postId",
                    notification.getPostId() != null
                        ? notification.getPostId() : ""
                );

                map.put(
                    "giftId",
                    notification.getGiftId() != null
                        ? notification.getGiftId() : ""
                );

                // 通知日時
                if (notification.getNotificationDatetime() != null) {

                    map.put(
                        "date",
                        notification.getNotificationDatetime()
                            .format(formatter)
                    );

                } else {
                    map.put("date", "");
                }

                notifications.add(map);
            }

            request.setAttribute("notifications", notifications);
            request.setAttribute("filter", filter);

            return "/tuuti/notification.jsp";

        } catch (Exception e) {

            e.printStackTrace();

            throw new Exception(
                "通知一覧の取得に失敗しました。",
                e
            );
        }
    }
}