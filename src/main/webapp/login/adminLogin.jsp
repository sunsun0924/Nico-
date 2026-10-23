
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>管理者用ログイン</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/adminLogin.css">
</head>

<body>

    <main class="login-container">

        <!-- ヘッダー -->
        <header class="login-header">

            <!-- 管理者アイコン -->
            <div class="admin-icon">
                      
			            
			    <svg viewBox="0 0 48 48" aria-hidden="true">
			
			        <!-- 人の頭：中央に移動 -->
			        <circle cx="24" cy="14" r="8" fill="currentColor"/>
			
			        <!-- 人の体：中央に移動 -->
			        <path d="M9 36c0-8 6-13 15-13s15 5 15 13"
			              fill="currentColor"/>
			
			    </svg>
            </div>

            <h1>管理者用ログイン</h1>

            <p class="description">
                アカウント情報を入力してください
            </p>

        </header>

        <!-- ログインフォーム -->
        <form id="adminLoginForm"
              action="${pageContext.request.contextPath}/AdminLoginServlet"
              method="post">

            <!-- ID入力欄 -->
            <div class="input-box">

                <div class="field-icon">
                    <svg viewBox="0 0 24 24"
                         fill="none"
                         stroke="currentColor"
                         stroke-width="1.8"
                         stroke-linecap="round"
                         stroke-linejoin="round"
                         aria-hidden="true">
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
            <div class="input-box">

                <div class="field-icon">
                    <svg viewBox="0 0 24 24"
                         fill="none"
                         stroke="currentColor"
                         stroke-width="1.8"
                         stroke-linecap="round"
                         stroke-linejoin="round"
                         aria-hidden="true">
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
                        autocomplete="current-password"
                        required>
                </div>

                <!-- パスワード表示切り替え -->
                <button
                    type="button"
                    id="togglePassword"
                    class="eye-button"
                    aria-label="パスワードを表示"
                    aria-pressed="false">

                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        width="22"
                        height="22"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="1.8"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        aria-hidden="true">

                        <path d="M2 12s3.6-7 10-7 10 7 10 7-3.6 7-10 7S2 12 2 12Z"/>
                        <circle cx="12" cy="12" r="3"/>
                        <path id="eyeSlash" d="M3 3l18 18"/>
                    </svg>

                </button>

            </div>

            <!-- エラーメッセージ -->
            <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </p>
            <% } %>

            <!-- ログインボタン -->
            <button type="submit" class="login-button">
                <span>ログイン</span>
                <span class="button-arrow" aria-hidden="true">→</span>
            </button>

        </form>

        <!-- 新規登録画面へ移動 -->
        <a class="register-button"
           href="${pageContext.request.contextPath}/register/adminRegister.jsp">
            <span>新規登録</span>
            <span class="button-arrow" aria-hidden="true">→</span>
        </a>

    </main>

    <!-- パスワードの表示・非表示 -->
    <script>
        document.addEventListener("DOMContentLoaded", function () {

            const password = document.getElementById("password");
            const togglePassword = document.getElementById("togglePassword");
            const eyeSlash = document.getElementById("eyeSlash");

            if (!password || !togglePassword || !eyeSlash) {
                console.error("パスワード切り替え用の要素が見つかりません。");
                return;
            }

            togglePassword.addEventListener("click", function () {

                const isHidden = password.type === "password";

                password.type = isHidden ? "text" : "password";
                eyeSlash.style.display = isHidden ? "none" : "inline";

                togglePassword.setAttribute(
                    "aria-label",
                    isHidden ? "パスワードを非表示" : "パスワードを表示"
                );

                togglePassword.setAttribute(
                    "aria-pressed",
                    String(isHidden)
                );
            });
        });
    </script>

</body>
</html>
