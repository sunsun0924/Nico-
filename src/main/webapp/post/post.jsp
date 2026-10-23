
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<!-- JSTL（繰り返し処理など）を使う宣言 -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- 投稿一覧画面のCSSを読み込む -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/post.css">

<!-- サイドバーと投稿画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：投稿画面 -->
    <main class="main-content">

        <div class="home-header">

            <h1>投稿</h1>
            <br>

            <!-- 新規投稿ボタン -->
            <form action="postnew.jsp" method="post">
                <input type="submit" value="新規投稿" class="reserve-btn">
            </form>

            <hr>

            <h3>投稿一覧</h3>

            <!-- posts（投稿一覧）を繰り返し表示 -->
            <c:forEach var="post" items="${postList}">

                <div class="post-item">

                    <!-- ユーザーID -->
                    <strong>ユーザーID：${post.user_id}</strong>

                    <!-- 投稿日時 -->
                    <p>${post.post_datetime}</p>

                    <!-- 投稿本文 -->
                    <p>${post.post_text}</p>

                    <!-- 投稿画像がある場合だけ表示 -->
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
