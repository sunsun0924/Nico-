
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新規登録</title>

    <!-- 管理者新規登録画面のCSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/adminRegister.css?v=3">
</head>
<body>

    <!-- 新規登録画面全体 -->
    <main class="register-container">

        <!-- 管理者アイコン -->
        <div class="user-icon">
            <span class="user-symbol">♟</span>
            <span class="plus-symbol">＋</span>
        </div>

        <!-- 見出し -->
        <h1>新規登録</h1>

        <p class="description">
            管理者情報を入力して、アカウントを作成してください。
        </p>

        <!-- 新規登録フォーム -->
        <form id="adminRegisterForm" novalidate>

            <!-- メールアドレス -->
            <div class="form-group">
                <div class="input-box" id="emailBox">
                    <span class="field-icon"></span>

                    <div class="input-content">
                        <label for="email">メールアドレス</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="例：admin@example.co.jp"
                            autocomplete="email">
                    </div>
                </div>
                <p class="error-message" id="emailError"></p>
            </div>

            <!-- ID -->
            <div class="form-group">
                <div class="input-box" id="idBox">
                    <span class="field-icon"></span>

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
                    <span class="field-icon"></span>

                    <div class="input-content">
                        <label for="password">パスワード</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="例：Admin1234"
                            autocomplete="new-password">
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

            <!-- 企業コード -->
            <div class="form-group">
                <div class="input-box" id="companyCodeBox">
                    <span class="field-icon"></span>

                    <div class="input-content">
                        <label for="companyCode">企業コード</label>
                        <input
                            type="text"
                            id="companyCode"
                            name="companyCode"
                            placeholder="例：COMP001"
                            autocomplete="off">
                    </div>
                </div>
                <p class="error-message" id="companyCodeError"></p>
            </div>

            <!-- 登録ボタン -->
            <button type="submit" class="register-button">
                <span>登録する</span>
                <span class="button-arrow">→</span>
            </button>

          

        </form>
    </main>

    <!-- 入力チェックと画面遷移 -->
    <script>
        const form = document.getElementById("adminRegisterForm");

        const email = document.getElementById("email");
        const userId = document.getElementById("userId");
        const password = document.getElementById("password");
        const companyCode = document.getElementById("companyCode");

        // 入力エラーの表示を共通処理する
        function setError(inputId, boxId, errorId, message) {
            document.getElementById(errorId).textContent = message;

            document.getElementById(boxId).classList.toggle(
                "invalid", message !== ""
            );

            document.getElementById(inputId).setAttribute(
                "aria-invalid", message !== "" ? "true" : "false"
            );

            return message === "";
        }

        // メールアドレスをチェック
        function validateEmail() {
            const value = email.value.trim();
            const valid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);

            let message = "";

            if (!value) {
                message = "メールアドレスを入力してください。";
            } else if (!valid) {
                message = "正しいメールアドレスを入力してください。";
            }

            return setError(
                "email", "emailBox", "emailError", message
            );
        }

        // IDをチェック
        function validateId() {
            const value = userId.value.trim();

            let message = "";

            if (!value) {
                message = "IDを入力してください。";
            } else if (value.length < 4) {
                message = "IDは4文字以上で入力してください。";
            }

            return setError(
                "userId", "idBox", "idError", message
            );
        }

        // パスワードをチェック
        function validatePassword() {
            const value = password.value;

            let message = "";

            if (!value) {
                message = "パスワードを入力してください。";
            } else if (!/^[a-zA-Z0-9]{8,}$/.test(value)) {
                message =
                    "パスワードは8文字以上の半角英数字で入力してください。";
            }

            return setError(
                "password", "passwordBox", "passwordError", message
            );
        }

        // 企業コードをチェック
        function validateCompanyCode() {
            const value = companyCode.value.trim();

            let message = "";

            if (!value) {
                message = "企業コードを入力してください。";
            }

            return setError(
                "companyCode", "companyCodeBox",
                "companyCodeError", message
            );
        }

        // 入力欄から離れたときにチェック
        email.addEventListener("blur", validateEmail);
        userId.addEventListener("blur", validateId);
        password.addEventListener("blur", validatePassword);
        companyCode.addEventListener("blur", validateCompanyCode);

        // パスワードの表示・非表示を切り替える
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

        // 登録ボタンを押したときの処理
        form.addEventListener("submit", function(event) {
            event.preventDefault();

            const results = [
                validateEmail(),
                validateId(),
                validatePassword(),
                validateCompanyCode()
            ];

            // すべての入力チェックに成功した場合
            if (results.every(Boolean)) {
                alert(
                    "入力チェックに成功しました。\n" +
                    "現在は仮実装のため、ログイン画面へ移動します。"
                );

                // 管理者ログイン画面へ移動
                window.location.href =
                    "${pageContext.request.contextPath}/login/adminLogin.jsp";
            } else {
                // 最初の入力エラーにフォーカスを当てる
                const firstInvalid =
                    form.querySelector('[aria-invalid="true"]');

                if (firstInvalid) {
                    firstInvalid.focus();
                }
            }
        });
    </script>

</body>
</html>
