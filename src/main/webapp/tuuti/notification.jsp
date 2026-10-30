
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
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
                <a href="?filter=all"
                   class="tab ${filter == 'all' ? 'active' : ''}">
                    すべて
                </a>

                <!-- コメント通知だけを表示 -->
                <a href="?filter=comment"
                   class="tab ${filter == 'comment' ? 'active' : ''}">
                    コメント
                </a>

                <!-- ギフト通知だけを表示 -->
                <a href="?filter=gift"
                   class="tab ${filter == 'gift' ? 'active' : ''}">
                    ギフト
                </a>

            </div>

            <!-- 通知一覧を表示する領域 -->
            <div class="notification-list">

                <%
                    // Servletから通知一覧を取得する
                    List<Map<String, String>> notifications =
                        (List<Map<String, String>>) request.getAttribute("notifications");

                    // 表示できる通知があるかを確認する
                    boolean hasNotification = false;

                    // 通知を1件ずつ取り出す
                    if (notifications != null) {
                        for (Map<String, String> notification : notifications) {

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
