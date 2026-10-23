<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理者ログイン</title>

    <!-- 管理者ログイン画面のCSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/adminLogin.css?v=3">
</head>
<body>

    <!-- ログイン画面全体 -->
    <main class="login-container">

        <!-- 管理者アイコン -->
        <div class="admin-icon">
            <span class="admin-symbol">♟</span>
        </div>

        <!-- 見出し -->
        <h1>管理者ログイン</h1>

        <p class="description">
            IDとパスワードを入力してログインしてください。
        </p>

        <!-- ログインフォーム -->
        <form id="adminLoginForm"
              action="${pageContext.request.contextPath}/AdminLoginServlet"
              method="post"
              novalidate>

            <!-- ID -->
            <div class="form-group">
                <div class="input-box" id="idBox">
                    <span class="field-icon">♙</span>

                    <div class="input-content">
                        <label for="userId">ID</label>
                        <input
                            type="text"
                            id="userId"
                            name="userId"
                            placeholder="例：admin001"
                            autocomplete="username">
                    </div>
                </div>

                <p class="error-message" id="idError"></p>
            </div>

            <!-- パスワード -->
            <div class="form-group">
                <div class="input-box" id="passwordBox">
                    <span class="field-icon">♙</span>

                    <div class="input-content">
                        <label for="password">パスワード</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="パスワードを入力"
                            autocomplete="current-password">
                    </div>

                    <button
                        type="button"
                        id="togglePassword"
                        class="eye-button"
                        aria-label="パスワードを表示">
                        ◎
                    </button>
                </div>

                <p class="error-message" id="passwordError"></p>
            </div>

            <!-- DB認証エラーメッセージ -->
            <%
                String errorMessage =
                        (String) request.getAttribute("errorMessage");

                if (errorMessage != null) {
            %>
                <p class="error-message">
                    <%= errorMessage %>
                </p>
            <%
                }
            %>

            <!-- ログインボタン -->
            <button type="submit" class="login-button">
                <span>ログイン</span>
                <span class="button-arrow">→</span>
            </button>

            <!-- 新規登録画面へ移動 -->
            <a class="register-button"
               href="${pageContext.request.contextPath}/register/adminRegister.jsp">
                <span>新規登録</span>
                <span class="button-arrow">→</span>
            </a>

        </form>
    </main>

    <!-- 入力チェック用JavaScript -->
    <script>
        const form = document.getElementById("adminLoginForm");
        const userId = document.getElementById("userId");
        const password = document.getElementById("password");

        // IDの入力チェック
        function validateId() {
            const value = userId.value.trim();
            const error = document.getElementById("idError");
            const box = document.getElementById("idBox");

            if (!value) {
                error.textContent = "IDを入力してください。";
                box.classList.add("invalid");
                userId.setAttribute("aria-invalid", "true");
                return false;
            }

            error.textContent = "";
            box.classList.remove("invalid");
            userId.setAttribute("aria-invalid", "false");
            return true;
        }

        // パスワードの入力チェック
        function validatePassword() {
            const value = password.value;
            const error = document.getElementById("passwordError");
            const box = document.getElementById("passwordBox");

            if (!value) {
                error.textContent = "パスワードを入力してください。";
                box.classList.add("invalid");
                password.setAttribute("aria-invalid", "true");
                return false;
            }

            if (!/^[a-zA-Z0-9]{8,}$/.test(value)) {
                error.textContent =
                    "8文字以上の半角英数字で入力してください。";
                box.classList.add("invalid");
                password.setAttribute("aria-invalid", "true");
                return false;
            }

            error.textContent = "";
            box.classList.remove("invalid");
            password.setAttribute("aria-invalid", "false");
            return true;
        }

        // 入力欄から離れたときにチェック
        userId.addEventListener("blur", validateId);
        password.addEventListener("blur", validatePassword);

        // パスワード表示・非表示の切り替え
        document.getElementById("togglePassword")
            .addEventListener("click", function() {

                const hidden = password.type === "password";

                password.type = hidden ? "text" : "password";
                this.textContent = hidden ? "◉" : "◎";

                this.setAttribute(
                    "aria-label",
                    hidden ? "パスワードを隠す" : "パスワードを表示"
                );
            });

        // ログインボタンを押したとき
        form.addEventListener("submit", function(event) {

            // 入力チェックに失敗した場合だけ送信を止める
            const idValid = validateId();
            const passwordValid = validatePassword();

            if (!idValid || !passwordValid) {
                event.preventDefault();

                if (!idValid) {
                    userId.focus();
                } else {
                    password.focus();
                }

                return;
            }

            // 入力チェック成功時はAdminLoginServletへ送信する
        });
    </script>

</body>
</html>