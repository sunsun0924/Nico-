<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<!-- メニュー用CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">

<div class="side-menu">

    <!-- メニュー一覧 -->
    <nav class="menu-list">

        <!-- 通知 -->
        <a href="${pageContext.request.contextPath}/tuuti/notification.jsp"
           class="menu-item">
            <span class="menu-icon">♧</span>
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
            <span class="menu-icon">☏</span>
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
            <span class="menu-icon">♔</span>
            <span>ギフト</span>
        </a>

    </nav>

    <!-- 一番下のマイページ -->
    <div class="menu-bottom">
        <a href="#"
           class="mypage-item">

            <span class="profile-icon">👤</span>
            <span>マイページ</span>

        </a>
    </div>

</div>

<%@ include file="../footer.jsp" %>
