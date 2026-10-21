
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>ユーザー新規登録</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/register.css">
</head>

<body>

    <main class="register-container">

        <!-- ヘッダー -->
        <header class="register-header">

            <div class="register-icon">
                <svg viewBox="0 0 48 48" aria-hidden="true">
                    <circle cx="20" cy="14" r="8" fill="currentColor"/>
                    <path d="M5 36c0-8 6-13 15-13s15 5 15 13"
                          fill="currentColor"/>
                    <path d="M37 28v13M30.5 34.5h13"
                          fill="none"
                          stroke="currentColor"
                          stroke-width="3"
                          stroke-linecap="round"/>
                </svg>
            </div>

            <h1>ユーザー新規登録</h1>

            <p>
                下記の情報を入力して、アカウントを作成してください。
            </p>

        </header>

        <!-- 登録フォーム -->
        <form action="${pageContext.request.contextPath}/RegisterServlet"
              method="post"
              class="register-form">

            <!-- ID入力欄 -->
            <div class="input-group">

                <div class="input-icon">
                    <svg viewBox="0 0 24 24" aria-hidden="true">
                        <circle cx="12" cy="7" r="4"/>
                        <path d="M4 21v-2a8 8 0 0 1 16 0v2"/>
                    </svg>
                </div>

                <div class="input-content">
                    <label for="userId">ID</label>

                    <input
                        type="text"
                        id="userId"
                        name="userId"
                        placeholder="例）admin123"
                        autocomplete="username"
                        required>
                </div>

            </div>

            <!-- パスワード入力欄 -->
            <div class="input-group">

                <div class="input-icon">
                    <svg viewBox="0 0 24 24" aria-hidden="true">
                        <rect x="4" y="10" width="16" height="12" rx="2"/>
                        <path d="M8 10V7a4 4 0 0 1 8 0v3"/>
                        <circle cx="12" cy="15" r="1"/>
                        <path d="M12 16v2"/>
                    </svg>
                </div>

                <div class="input-content">
                    <label for="password">パスワード</label>

                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="例）8文字以上の半角英数字"
                        minlength="8"
                        autocomplete="new-password"
                        required>
                </div>

                <!-- パスワード表示切り替え -->
                <button
                    type="button"
                    class="toggle-password"
                    id="togglePassword"
                    aria-label="パスワードを表示"
                    aria-pressed="false">

                    <svg viewBox="0 0 24 24" aria-hidden="true">
                        <path d="M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7S2 12 2 12z"/>
                        <circle cx="12" cy="12" r="3"/>
                    </svg>

                </button>

            </div>

            <!-- エラーメッセージ -->
            <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </p>
            <% } %>

            <!-- 登録ボタン -->
            <button type="submit" class="register-button">
                <span>登録する</span>
                <span class="button-arrow" aria-hidden="true">→</span>
            </button>

        </form>

    </main>

    <!-- パスワードの表示・非表示 -->
    <script>
        const passwordInput = document.getElementById("password");
        const toggleButton = document.getElementById("togglePassword");

        toggleButton.addEventListener("click", function () {
            const isHidden = passwordInput.type === "password";

            passwordInput.type = isHidden ? "text" : "password";

            toggleButton.setAttribute(
                "aria-pressed",
                String(isHidden)
            );

            toggleButton.setAttribute(
                "aria-label",
                isHidden ? "パスワードを隠す" : "パスワードを表示"
            );
        });
    </script>

</body>
</html>
