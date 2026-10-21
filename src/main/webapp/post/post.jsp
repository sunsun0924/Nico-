<%@ page contentType="text/html; charset=UTF-8" %>
<!-- JSTL（繰り返し処理など）を使う宣言 -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- 投稿一覧画面のCSSを読み込む -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/post.css">

<div class="home-header">
    <h1>投稿</h1>
    <br>

    <!-- 投稿入力欄 -->
    <form action="postnew.jsp" method="post">
        <input type="submit" value="新規投稿" class="reserve-btn">
    </form>

    <hr>

    <h3>投稿一覧</h3>

    <!-- posts（投稿一覧）を繰り返し表示 -->
    <c:forEach var="post" items="${posts}">
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