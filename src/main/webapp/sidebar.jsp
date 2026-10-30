<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ include file="../header.jsp" %>

<!-- メニュー用CSS -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/sidebar.css">

<div class="side-menu">

    <!-- メニュー一覧 -->
    <nav class="menu-list">

        <!-- 通知 -->
        <a href="${pageContext.request.contextPath}/tuuti/notification.jsp"
           class="menu-item">
            <span class="menu-icon">🔔︎</span>
            <span>通知</span>
        </a>

        <!-- 投稿 -->
        <a href="${pageContext.request.contextPath}/post/post.jsp"
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

        <!-- 管理者の種類によって表示を切り替える -->
        <!-- 特別な管理者：admin000 -->
        <!-- それ以外の管理者：3・4文字目が ad -->
        <!-- 一般ユーザーには追加メニューを表示しない -->
        <c:choose>

            <c:when test="${sessionScope.userId == 'admin000'}">

                <a href="${pageContext.request.contextPath}/company/register.jsp"
                   class="menu-item">
                    <span class="menu-icon">🏢</span>
                    <span>企業新規登録</span>
                </a>

            </c:when>

            <c:when test="${fn:substring(sessionScope.userId, 2, 4) == 'ad'}">

                <a href="${pageContext.request.contextPath}/register/register.jsp"
                   class="menu-item">
                    <span class="menu-icon">👤</span>
                    <span>ユーザー新規登録</span>
                </a>

            </c:when>

            <c:otherwise>
            </c:otherwise>

        </c:choose>

    </nav>

    <!-- 一番下のマイページ -->
    <div class="menu-bottom">

        <a href="${pageContext.request.contextPath}/mypage/mypage.jsp"
           class="mypage-item">
            <span class="profile-icon">👤</span>
            <span>マイページ</span>
        </a>

    </div>

</div>

<%@ include file="../footer.jsp" %>