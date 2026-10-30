<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<!-- パスワード変更画面のCSSを読み込む -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypage_detail.css">

<!-- サイドバーとパスワード変更画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：パスワード変更画面 -->
    <main class="main-content">

        <div class="container">

            <div class="page-title">パスワードの変更</div>

            <div class="page-desc">
                現在のパスワードと新しいパスワードを入力してください。
            </div>

            <form action="${pageContext.request.contextPath}/servlet.UserPasswordChangeServlet.action"
                  method="post">

                <div class="form-group">
                    <label class="form-label">現在のパスワード</label>
                    <input type="password"
                           name="currentPassword"
                           placeholder="現在のパスワードを入力"
                           class="form-input"
                           required>
                </div>

                <div class="form-group">
                    <label class="form-label">新しいパスワード</label>
                    <input type="password"
                           name="newPassword"
                           placeholder="新しいパスワードを入力"
                           class="form-input"
                           required>
                </div>

                <div class="form-group">
                    <label class="form-label">新しいパスワード（確認）</label>
                    <input type="password"
                           name="confirmPassword"
                           placeholder="新しいパスワードを再入力"
                           class="form-input"
                           required>
                </div>

                <div class="btn-container">
                    <button type="submit" class="btn-save">
                        保存
                    </button>

                    <a href="${pageContext.request.contextPath}/mypage/mypage_detail.jsp"
                       class="btn-back">
                        戻る
                    </a>
                </div>

            </form>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>