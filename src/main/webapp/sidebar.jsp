<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="dao.NotificationDAO" %>
<%@ page import="bean.User" %>
<%@ page import="bean.Admin" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- 通知件数を取得 -->
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

        // ユーザーの場合
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
%>

<!-- メニュー用CSS -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/sidebar.css">

<div class="side-menu">

    <!-- メニュー一覧 -->
    <nav class="menu-list">

        <!-- 通知 -->
        <a href="${pageContext.request.contextPath}/servlet.NotificationServlet.action"
           class="menu-item">

            <span class="menu-icon">🔔︎</span>

            <span>通知</span>

            <!-- 通知件数が1件以上の場合に表示 -->
            <% if (notificationCount > 0) { %>

                <span class="notification-badge">
                    <%= notificationCount %>
                </span>

            <% } %>

        </a>

        <!-- 投稿 -->
        <a href="${pageContext.request.contextPath}/servlet.PostServlet.action"
           class="menu-item">

            <span class="menu-icon">✎</span>
            <span>投稿</span>

        </a>

        <!-- チャット -->
        <a href="${pageContext.request.contextPath}/chat/chat_list.jsp"
           class="menu-item">

            <span class="menu-icon">✉</span>
            <span>チャット</span>

        </a>

        <!-- 検索 -->
        <a href="${pageContext.request.contextPath}/search/search.jsp"
           class="menu-item">

            <span class="menu-icon">⌕</span>
            <span>検索</span>

        </a>

        <!-- ギフト -->
        <a href="${pageContext.request.contextPath}/gift/gift.jsp"
           class="menu-item">

            <span class="menu-icon">🎁︎</span>
            <span>ギフト</span>

        </a>

        
        <%-- 管理者の種類によって表示を切り替える --%>
        <c:choose>

            <%-- 管理者番号がadmin000の場合 --%>
            <c:when test="${sessionScope.admin.cpUserid == 'admin000'}">

                <a href="${pageContext.request.contextPath}/company/company.jsp"
                   class="menu-item">

                    <span class="menu-icon">🏢</span>
                    <span>企業新規登録</span>

                </a>

            </c:when>

            <%-- 企業管理者の場合 --%>
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

    <!-- 一番下のマイページ -->
    <div class="menu-bottom">

        <a href="${pageContext.request.contextPath}/mypage/mypage.jsp"
           class="mypage-item">

            <img
                src="${pageContext.request.contextPath}/images/${empty sessionScope.user.userImg ? 'default-profile.png' : sessionScope.user.userImg}"
                alt="ユーザーアイコン"
                class="profile-icon"
                style="object-position: ${sessionScope.user.userImgPositionX}% ${sessionScope.user.userImgPositionY}%;"
            >

            <span>マイページ</span>

        </a>

    </div>

</div>