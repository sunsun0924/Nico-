package servlet;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.Admin;
import bean.Notification;
import bean.User;
import dao.GiftDAO;
import dao.NotificationDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 通知一覧表示処理

public class NotificationServlet extends Action {

    // 誕生日通知の識別情報
    private static final Pattern BIRTHDAY_PATTERN =
            Pattern.compile(
                "\\[\\[BIRTHDAY_NOTICE:(ONE_MONTH|TODAY):([^:]+):(\\d{4}-\\d{2}-\\d{2})\\]\\]"
            );

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログインしていない場合
        if (session == null
                || (session.getAttribute("user") == null
                && session.getAttribute("admin") == null)) {

            return "/login/userLogin.jsp";
        }

        try {

            // 通知の絞り込み条件を取得
            String filter = request.getParameter("filter");

            // 絞り込み条件がない場合
            if (filter == null || filter.isEmpty()) {
                filter = "all";
            }

            // 不正な絞り込み条件を防止
            if (!filter.equals("all")
                    && !filter.equals("comment")
                    && !filter.equals("gift")) {

                filter = "all";
            }

            // DAOを生成
            NotificationDAO notificationDAO = new NotificationDAO();
            GiftDAO giftDAO = new GiftDAO();

            // 通知一覧
            List<Notification> notificationList;

            // ユーザーID
            String userId = null;

            // ユーザーまたは管理者の通知を取得
            if (session.getAttribute("user") != null) {

                // ログイン中のユーザー
                User user = (User) session.getAttribute("user");

                userId = user.getUserId().trim();

                // ユーザーの通知を取得
                notificationList =
                        notificationDAO.findByUserId(userId);

                // ユーザーの通知を既読にする
                notificationDAO.markAllReadByUserId(userId);

                request.setAttribute("accountType", "user");

            } else {

                // ログイン中の管理者
                Admin admin = (Admin) session.getAttribute("admin");

                // 管理者の通知を取得
                notificationList =
                        notificationDAO.findByAdminId(
                                admin.getCpUserid()
                        );

                // 管理者の通知を既読にする
                notificationDAO.markAllReadByAdminId(
                        admin.getCpUserid()
                );

                request.setAttribute("accountType", "admin");
            }

            // JSPに渡す通知一覧
            List<Map<String, Object>> notifications =
                    new ArrayList<>();

            // 通知ごとに処理
            for (Notification notification : notificationList) {

                // 通知の種類
                String notificationType =
                        notification.getNotificationType();

                // 絞り込み条件に合わない通知は除外
                if (filter.equals("comment")
                        && !"comment".equals(notificationType)) {

                    continue;
                }

                if (filter.equals("gift")
                        && !"gift".equals(notificationType)) {

                    continue;
                }

                // 通知メッセージ
                String message = notification.getMessage();

                if (message == null) {
                    message = "";
                }

                // ギフト登録済みかどうか
                boolean giftRegistered = false;

                // 誕生日通知の識別情報を取得
                Matcher matcher =
                        BIRTHDAY_PATTERN.matcher(message);

                if ("gift".equals(notificationType)
                        && userId != null
                        && matcher.find()) {

                    // 家族の誕生日ID
                    String familyBirthdayId =
                            matcher.group(2);

                    // 次の誕生日
                    LocalDate nextBirthday =
                            LocalDate.parse(matcher.group(3));

                    // ギフト登録開始日
                    LocalDate startDate =
                            nextBirthday.minusMonths(1);

                    // ギフト登録期間
                    Date sqlStartDate =
                            Date.valueOf(startDate);

                    Date sqlEndDate =
                            Date.valueOf(nextBirthday);

                    // ギフト登録済みか確認
                    giftRegistered =
                            giftDAO.existsHistoryInPeriod(
                                    userId,
                                    familyBirthdayId,
                                    sqlStartDate,
                                    sqlEndDate
                            );
                }

                // 表示用メッセージから識別情報を削除
                message = message.replaceAll(
                        "\\[\\[BIRTHDAY_NOTICE:[^\\]]+\\]\\]",
                        ""
                ).trim();

                // 通知情報を分割
                String[] messageParts =
                        message.split("\\r?\\n", 2);

                // 通知情報を作成
                Map<String, Object> map = new HashMap<>();

                map.put("type", notificationType);

                map.put("title", messageParts[0]);

                map.put(
                        "detail",
                        messageParts.length > 1
                                ? messageParts[1].trim()
                                : ""
                );

                map.put(
                        "postId",
                        notification.getPostId() == null
                                ? ""
                                : notification.getPostId()
                );

                map.put(
                        "giftId",
                        notification.getGiftId() == null
                                ? ""
                                : notification.getGiftId()
                );

                // ギフト登録済みかどうかを設定
                map.put("giftRegistered", giftRegistered);

                // 通知日時
                if (notification.getNotificationDatetime() != null) {

                    map.put(
                            "date",
                            notification.getNotificationDatetime().format(
                                    DateTimeFormatter.ofPattern(
                                            "yyyy/MM/dd HH:mm"
                                    )
                            )
                    );

                } else {

                    map.put("date", "");
                }

                notifications.add(map);
            }

            // 通知一覧を設定
            request.setAttribute("notifications", notifications);

            // 絞り込み条件を設定
            request.setAttribute("filter", filter);

            // 通知件数
            request.setAttribute("notificationCount", 0);

            // 通知一覧画面へ
            return "/tuuti/notification.jsp";

        } catch (Exception e) {

            // エラーを呼び出し元へ渡す
            throw new Exception(
                    "通知一覧の取得に失敗しました。",
                    e
            );
        }
    }
}