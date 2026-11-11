<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="dao.NotificationDAO" %>
<%@ page import="bean.User" %>
<%@ page import="bean.Admin" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- 通知件数を取得 --%>
<%
    int notificationCount = 0;

    // ログイン情報を取得
    User loginUser =
        (User) session.getAttribute("user");

    Admin loginAdmin =
        (Admin) session.getAttribute("admin");

    try {
        NotificationDAO notificationDAO =
            new NotificationDAO();

        // 一般ユーザーの場合
        if (loginUser != null) {
            notificationCount =
                notificationDAO.countByUserId(
                    loginUser.getUserId()
                );

        // 管理者の場合
        } else if (loginAdmin != null) {
            notificationCount =
                notificationDAO.countByAdminId(
                    loginAdmin.getCpUserid()
                );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    // 通知件数の表示文字を設定
    String badgeText = "";

    if (notificationCount > 0) {
        String[] circledNumbers = {
            "",
            "①", "②", "③", "④", "⑤",
            "⑥", "⑦", "⑧", "⑨", "⑩",
            "⑪", "⑫", "⑬", "⑭", "⑮",
            "⑯", "⑰", "⑱", "⑲", "⑳"
        };

        if (notificationCount <= 20) {
            badgeText = circledNumbers[notificationCount];
        } else {
            badgeText = "⑳+";
        }
    }
%>

<%-- サイドバーCSS --%>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/sidebar.css">

<div class="side-menu">

    <%-- ロゴエリア --%>
    <div class="sidebar-logo">
        <a href="${pageContext.request.contextPath}/servlet.PostServlet.action">
            <img
			    src="${pageContext.request.contextPath}/images/nico-logo.png"
			    alt="Nico+"
			    class="sidebar-logo-img">
        </a>
    </div>

    <%-- メニュー一覧 --%>
    <nav class="menu-list">

        <%-- 通知 --%>
        <a href="${pageContext.request.contextPath}/servlet.NotificationServlet.action"
           class="menu-item">

            <span class="menu-icon">🔔︎</span>
            <span>通知</span>

            <% if (notificationCount > 0) { %>
                <span class="notification-badge">
                    <%= badgeText %>
                </span>
            <% } %>

        </a>

        <%-- 投稿 --%>
        <a href="${pageContext.request.contextPath}/servlet.PostServlet.action"
           class="menu-item">

            <span class="menu-icon">✎</span>
            <span>投稿</span>

        </a>

        <%-- チャット --%>
        <a href="${pageContext.request.contextPath}/chat/chat_list.jsp"
           class="menu-item">

            <span class="menu-icon">✉</span>
            <span>チャット</span>

        </a>

        <%-- 検索 --%>
        <a href="${pageContext.request.contextPath}/search/search.jsp"
           class="menu-item">

            <span class="menu-icon">⌕</span>
            <span>検索</span>

        </a>

        <%-- ギフト --%>
        <a href="${pageContext.request.contextPath}/gift/gift.jsp"
           class="menu-item">

            <span class="menu-icon">🎁︎</span>
            <span>ギフト</span>

        </a>

        <%-- 管理者の種類によって表示を切り替える --%>
        <c:choose><c:when test="${sessionScope.admin.cpUserid == 'admin000'}">

            <%-- 企業新規登録 --%>
            <a href="${pageContext.request.contextPath}/company/company.jsp"
               class="menu-item">

                <span class="menu-icon">🏢</span>
                <span>企業新規登録</span>

            </a>

        </c:when><c:when test="${not empty sessionScope.admin.cpUserid and fn:substring(sessionScope.admin.cpUserid, 2, 4) == 'ad'}">

            <%-- ユーザー新規登録 --%>
            <a href="${pageContext.request.contextPath}/register/register.jsp"
               class="menu-item">

                <span class="menu-icon">👤</span>
                <span>ユーザー新規登録</span>

            </a>

        </c:when></c:choose>

    </nav>

    <%-- 一番下のマイページ --%>
    <div class="menu-bottom">

        <a href="${pageContext.request.contextPath}/mypage/mypage.jsp"
           class="mypage-item">

            <c:choose><c:when test="${not empty sessionScope.admin}">

                <%-- 管理者のプロフィール画像 --%>
                <img
                    src="${pageContext.request.contextPath}/images/${empty sessionScope.admin.cpUserimg ? 'default-profile.png' : sessionScope.admin.cpUserimg}"
                    alt="管理者アイコン"
                    class="profile-icon"
                    style="object-position: ${empty sessionScope.admin.cpUserimgPositionX ? 50 : sessionScope.admin.cpUserimgPositionX}% ${empty sessionScope.admin.cpUserimgPositionY ? 50 : sessionScope.admin.cpUserimgPositionY}%;">

            </c:when><c:otherwise>

                <%-- 一般ユーザーのプロフィール画像 --%>
                <img
                    src="${pageContext.request.contextPath}/images/${empty sessionScope.user.userImg ? 'default-profile.png' : sessionScope.user.userImg}"
                    alt="ユーザーアイコン"
                    class="profile-icon"
                    style="object-position: ${empty sessionScope.user.userImgPositionX ? 50 : sessionScope.user.userImgPositionX}% ${empty sessionScope.user.userImgPositionY ? 50 : sessionScope.user.userImgPositionY}%;">

            </c:otherwise></c:choose>

            <span>マイページ</span>

        </a>

    </div>

</div>
