
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%@ include file="../header.jsp" %>

<!-- 通知画面のCSSを読み込む -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/notification.css">

<!-- サイドバーと通知画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：通知画面 -->
    <main class="main-content">

        <!-- 通知画面全体 -->
        <div class="notification-container">

            <!-- 画面上部のタイトル -->
            <div class="notification-header">
                <h1>通知</h1>
            </div>

            <!-- 通知の種類を切り替えるタブ -->
            <div class="notification-tabs">

                <!-- すべての通知を表示 -->
                <a href="?type=all"
                   class="tab ${empty param.type || param.type == 'all' ? 'active' : ''}">
                    すべて
                </a>

                <!-- コメント通知だけを表示 -->
                <a href="?type=comment"
                   class="tab ${param.type == 'comment' ? 'active' : ''}">
                    コメント
                </a>

                <!-- ギフト通知だけを表示 -->
                <a href="?type=gift"
                   class="tab ${param.type == 'gift' ? 'active' : ''}">
                    ギフト
                </a>

            </div>

            <!-- 通知一覧を表示する領域 -->
            <div class="notification-list">

                <%
                    // URLから選択されている通知の種類を取得する
                    String type = request.getParameter("type");

                    // 種類が指定されていない場合は「すべて」にする
                    if (type == null || type.isEmpty()) {
                        type = "all";
                    }

                    // Servletから通知一覧を取得する
                    List<Map<String, String>> notifications =
                        (List<Map<String, String>>) request.getAttribute("notifications");

                    // 通知データがない場合は画面確認用の仮データを作成する
                    if (notifications == null) {

                        notifications = new ArrayList<>();

                        // 仮のコメント通知1
                        Map<String, String> n1 = new HashMap<>();
                        n1.put("type", "comment");
                        n1.put("message", "たくみさんがあなたの投稿にコメントしました");
                        n1.put("detail", "「もう疲れた！」");
                        n1.put("date", "2026/10/21 10:15");
                        notifications.add(n1);

                        // 仮のコメント通知2
                        Map<String, String> n2 = new HashMap<>();
                        n2.put("type", "comment");
                        n2.put("message", "れいなさんがあなたの投稿にコメントしました");
                        n2.put("detail", "「利益とは…」");
                        n2.put("date", "2026/10/21 09:30");
                        notifications.add(n2);

                        // 仮のギフト通知1
                        Map<String, String> n3 = new HashMap<>();
                        n3.put("type", "gift");
                        n3.put("message", "ゆいさんがあなたにギフトを送りました");
                        n3.put("detail", "「子育てって大変よね」");
                        n3.put("date", "2026/10/21 09:00");
                        notifications.add(n3);

                        // 仮のコメント通知3
                        Map<String, String> n4 = new HashMap<>();
                        n4.put("type", "comment");
                        n4.put("message", "はるかさんがあなたの投稿にコメントしました");
                        n4.put("detail", "「今週まだあるよ」");
                        n4.put("date", "2026/10/20 18:45");
                        notifications.add(n4);

                        // 仮のギフト通知2
                        Map<String, String> n5 = new HashMap<>();
                        n5.put("type", "gift");
                        n5.put("message", "みさきさんがあなたにギフトを送りました");
                        n5.put("detail", "「感謝してほしいのはこっちだわ」");
                        n5.put("date", "2026/10/20 15:20");
                        notifications.add(n5);
                    }

                    // 表示できる通知があるかを確認する
                    boolean hasNotification = false;

                    // 通知を1件ずつ取り出す
                    for (Map<String, String> notification : notifications) {

                        // 通知の種類を取得する
                        String notificationType = notification.get("type");

                        // 「すべて」または選択した種類に一致する通知を表示する
                        if ("all".equals(type) || type.equals(notificationType)) {

                            // 表示する通知が存在することを記録する
                            hasNotification = true;
                %>

                <!-- 通知1件分の表示枠 -->
                <div class="notification-item">

                    <!-- 通知の文章と日時 -->
                    <div class="notification-content">

                        <!-- 誰が何をしたのかを表示する -->
                        <p class="notification-message">
                            <%= notification.get("message") %>
                        </p>

                        <!-- コメント内容またはギフト内容を表示する -->
                        <p class="notification-detail">
                            <%= notification.get("detail") %>
                        </p>

                        <!-- 通知が届いた日時を表示する -->
                        <p class="notification-date">
                            <%= notification.get("date") %>
                        </p>

                    </div>

                </div>

                <%
                        }
                    }

                    // 表示できる通知がなかった場合
                    if (!hasNotification) {
                %>

                <!-- 通知がないことを表示する -->
                <p class="no-notification">
                    通知はありません。
                </p>

                <%
                    }
                %>

            </div>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>
