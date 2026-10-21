
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>ユーザーログイン</title>

    <!-- ユーザー用ログイン画面のCSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/userLogin.css">
</head>

<body>

    <!-- 画面全体 -->
    <main class="login-container">

        <!-- ユーザーアイコン -->
        <div class="user-icon">
            <span class="user-symbol">♙</span>
        </div>

        <!-- 見出し -->
        <h1>ログイン</h1>

        <!-- 説明文 -->
        <p class="description">
            アカウント情報を入力してください
        </p>

        <!-- ログインフォーム -->
        <form id="userLoginForm"
              action="${pageContext.request.contextPath}/login/userLogin.jsp"
              method="post">

            <!-- ID入力欄 -->
            <div class="form-group">
                <div class="input-box">

                    <span class="field-icon">♙</span>

                    <div class="input-content">
                        <label for="userId">ID</label>

                        <input
                            type="text"
                            id="userId"
                            name="userId"
                            placeholder="IDを入力してください"
                            autocomplete="username"
                            required>
                    </div>

                </div>
            </div>

            <!-- パスワード入力欄 -->
            <div class="form-group">
                <div class="input-box">

                    <span class="field-icon">♧</span>

                    <div class="input-content">
                        <label for="password">パスワード</label>

                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="パスワードを入力してください"
                            autocomplete="current-password"
                            required>
                    </div>

                    <!-- パスワード表示切り替え -->
                    <button
                        type="button"
                        class="eye-button"
                        id="togglePassword"
                        aria-label="パスワードを表示"
                        aria-pressed="false">
                        ◉
                    </button>

                </div>
            </div>

            <!-- エラーメッセージ -->
            <p class="error-message" id="errorMessage">
                <%
                    String errorMessage =
                        (String) request.getAttribute("errorMessage");

                    if (errorMessage != null) {
                %>
                    <%= errorMessage %>
                <%
                    }
                %>
            </p>

            <!-- ログインボタン -->
            <button type="submit" class="login-button">
                <span class="button-text">ログイン</span>
                <span class="button-arrow">→</span>
            </button>

        </form>

    </main>

    <!-- パスワード表示切り替え -->
    <script>
        const passwordInput = document.getElementById("password");
        const togglePassword = document.getElementById("togglePassword");

        togglePassword.addEventListener("click", function () {
            const isHidden = passwordInput.type === "password";

            passwordInput.type = isHidden ? "text" : "password";

            this.setAttribute(
                "aria-label",
                isHidden ? "パスワードを隠す" : "パスワードを表示"
            );

            this.setAttribute("aria-pressed", String(isHidden));
        });
    </script>
    

</body>
</html>
