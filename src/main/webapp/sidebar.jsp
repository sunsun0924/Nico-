<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="dao.NotificationDAO" %>
<%@ page import="dao.ChatRoomDAO" %>
<%@ page import="bean.User" %>
<%@ page import="bean.Admin" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%
    int notificationCount = 0;
    int chatUnreadCount = 0;

    User loginUser = (User) session.getAttribute("user");
    Admin loginAdmin = (Admin) session.getAttribute("admin");

    try {
        NotificationDAO notificationDAO = new NotificationDAO();

        if (loginUser != null) {
            notificationCount =
                notificationDAO.countByUserId(loginUser.getUserId());

            ChatRoomDAO chatRoomDAO = new ChatRoomDAO();

            chatUnreadCount =
                chatRoomDAO.countUnreadMessages(loginUser.getUserId());

        } else if (loginAdmin != null) {
            notificationCount =
                notificationDAO.countByAdminId(loginAdmin.getCpUserid());
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

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

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/sidebar.css">

<div class="side-menu">

    <div class="sidebar-logo">
        <a href="${pageContext.request.contextPath}/servlet.PostServlet.action">
            <img
                src="${pageContext.request.contextPath}/images/nico-logo.png"
                alt="Nico+"
                class="sidebar-logo-img">
        </a>
    </div>

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
        <a href="${pageContext.request.contextPath}/servlet.ChatListServlet.action"
           class="menu-item">

            <span class="menu-icon">✉</span>
            <span>チャット</span>

            <% if (chatUnreadCount > 0) { %>
                <span class="chat-unread-badge">
                    <%= chatUnreadCount %>
                </span>
            <% } %>
        </a>

        <%-- 検索 --%>
        <a href="${pageContext.request.contextPath}/servlet.SearchServlet.action"
           class="menu-item">

            <span class="menu-icon">⌕</span>
            <span>検索</span>
        </a>

        <%-- ギフト --%>
        <a href="${pageContext.request.contextPath}/servlet.GiftServlet.action"
           class="menu-item">

            <span class="menu-icon">🎁︎</span>
            <span>ギフト</span>
        </a>

        <c:choose>

            <c:when test="${sessionScope.admin.cpUserid == 'admin000'}">

                <a href="${pageContext.request.contextPath}/company/company.jsp"
                   class="menu-item">

                    <span class="menu-icon">🏢</span>
                    <span>企業新規登録</span>
                </a>

            </c:when>

            <c:when test="${not empty sessionScope.admin.cpUserid
                            and fn:substring(sessionScope.admin.cpUserid, 2, 4) == 'ad'}">

                <a href="${pageContext.request.contextPath}/register/register.jsp"
                   class="menu-item">

                    <span class="menu-icon">👤</span>
                    <span>ユーザー新規登録</span>
                </a>

            </c:when>

        </c:choose>

    </nav>

    <div class="menu-bottom">

        <a href="${pageContext.request.contextPath}/mypage/mypage.jsp"
           class="mypage-item">

            <c:choose>

                <c:when test="${not empty sessionScope.admin}">

                    <img
                        src="${empty sessionScope.admin.cpUserimg
                            or sessionScope.admin.cpUserimg eq 'default-profile.png'
                            ? pageContext.request.contextPath.concat('/images/default-profile.png')
                            : pageContext.request.contextPath.concat('/profile-image/').concat(sessionScope.admin.cpUserimg)}"
                        alt="管理者アイコン"
                        class="profile-icon"
                        style="object-position: ${empty sessionScope.admin.cpUserimgPositionX ? 50 : sessionScope.admin.cpUserimgPositionX}% ${empty sessionScope.admin.cpUserimgPositionY ? 50 : sessionScope.admin.cpUserimgPositionY}%;">
                </c:when>

                <c:otherwise>

                    <img
                        src="${empty sessionScope.user.userImg
                            or sessionScope.user.userImg eq 'default-profile.png'
                            ? pageContext.request.contextPath.concat('/images/default-profile.png')
                            : pageContext.request.contextPath.concat('/profile-image/').concat(sessionScope.user.userImg)}"
                        alt="ユーザーアイコン"
                        class="profile-icon"
                        style="object-position: ${empty sessionScope.user.userImgPositionX ? 50 : sessionScope.user.userImgPositionX}% ${empty sessionScope.user.userImgPositionY ? 50 : sessionScope.user.userImgPositionY}%;">
                </c:otherwise>

            </c:choose>

            <span>マイページ</span>

        </a>

    </div>

</div>