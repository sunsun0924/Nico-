<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- 投稿一覧画面のCSS -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/post.css">

<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：投稿画面 -->
    <main class="main-content">

        <div class="home-header">

            <h1>投稿</h1>

            <!-- 登録完了メッセージ -->
            <p class="success-message">${message}</p>

            <br>

            <!-- 新規投稿ボタン -->
            <form action="postnew.jsp" method="post">
                <input type="submit"
                       value="新規投稿"
                       class="reserve-btn">
            </form>

            <hr>

            <h3>投稿一覧</h3>

            <!-- 投稿一覧 -->
            <c:forEach var="post" items="${postList}">

                <div class="post-item">

                    <!-- ユーザーID -->
                    <strong>ユーザーID：${post.user_id}</strong>

                    <!-- 投稿日時 -->
                    <p>${post.post_datetime}</p>

                    <!-- 投稿本文 -->
                    <p>${post.post_text}</p>

                    <!-- 投稿画像 -->
                    <c:if test="${not empty post.post_img}">
                        <img
                            src="${pageContext.request.contextPath}/images/${post.post_img}"
                            alt="投稿画像"
                            width="150">
                    </c:if>

                    <!-- コメント -->
                    <p>コメント：${post.comment}</p>

                </div>

                <hr>

            </c:forEach>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>
