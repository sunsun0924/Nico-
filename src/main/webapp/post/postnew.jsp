
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<!-- 新規投稿画面のCSSを読み込む -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/postnew.css">

<!-- サイドバーと投稿画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：新規投稿画面 -->
    <main class="main-content">

        <div class="home-header">

            <h1>投稿</h1>

            <!-- 投稿フォーム -->
            <form action="#" method="post" enctype="multipart/form-data">

                <!-- ユーザー表示と画像選択 -->
                <div class="post-toolbar">
                    <span class="user-icon">👤</span>
                    <span class="user-name">${user_name}</span>
                    <input type="text" readonly>

                    <label for="post_img" class="image-button"></label>

                    <input type="file"
                           id="post_img"
                           name="post_img"
                           accept="image/*"
                           class="image-input">
                </div>

                <!-- 投稿内容 -->
                <textarea
                    id="post_text"
                    name="post_text"
                    placeholder="ここに投稿内容を入力してください..."
                    maxlength="200"
                    rows="8"
                    required
                    oninput="updateCount()"></textarea>

                <!-- 文字数表示 -->
                <p>
                    <span id="count">0</span>/200
                </p>

                <!-- 投稿ボタン -->
                <input type="submit"
                       value="投稿する ➤"
                       class="reserve-btn">

            </form>

            <br>

            <!-- 投稿一覧に戻る -->
            <a href="post.jsp">投稿一覧に戻る</a>

        </div>

    </main>

</div>

<!-- 本文の文字数表示 -->
<script>
    function updateCount() {
        var text = document.getElementById("post_text").value;

        document.getElementById("count").textContent = text.length;
    }
</script>

<%@ include file="../footer.jsp" %>
