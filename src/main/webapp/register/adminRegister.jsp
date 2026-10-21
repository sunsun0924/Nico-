<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理者新規登録</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/adminRegister.css?v=1">
</head>
<body>

<div class="register-container">

    <div class="user-icon">
        <span class="user-symbol">♟</span>
        <span class="plus-symbol">＋</span>
    </div>

    <h1>新規登録</h1>
    <p class="description">
        管理者情報を入力して、アカウントを作成してください。
    </p>

    <form id="adminRegisterForm" novalidate>

        <!-- メールアドレス -->
        <div class="form-group">
            <div class="input-box" id="emailBox">
                <span class="field-icon">✉</span>
                <div class="input-content">
                    <label for="email">メールアドレス</label>
                    <input type="email" id="email" name="email"
                           placeholder="例：admin@example.co.jp"
                           autocomplete="email">
                </div>
            </div>
            <p class="error-message" id="emailError"></p>
        </div>

        <!-- ID -->
        <div class="form-group">
            <div class="input-box" id="idBox">
                <span class="field-icon">♙</span>
                <div class="input-content">
                    <label for="userId">ID</label>
                    <input type="text" id="userId" name="userId"
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
                    <input type="password" id="password" name="password"
                           placeholder="例：Admin1234"
                           autocomplete="new-password">
                </div>
                <button type="button" id="togglePassword"
                        class="eye-button"
                        aria-label="パスワードを表示">◎</button>
            </div>
            <p class="error-message" id="passwordError"></p>
        </div>

        <!-- 企業コード -->
        <div class="form-group">
            <div class="input-box" id="companyCodeBox">
                <span class="field-icon">▣</span>
                <div class="input-content">
                    <label for="companyCode">企業コード</label>
                    <input type="text" id="companyCode" name="companyCode"
                           placeholder="例：COMP001"
                           autocomplete="off">
                </div>
            </div>
            <p class="error-message" id="companyCodeError"></p>
        </div>

        <button type="submit" class="register-button">
            登録する <span>→</span>
        </button>

    </form>

</div>

<script>
    const form = document.getElementById("adminRegisterForm");

    const email = document.getElementById("email");
    const userId = document.getElementById("userId");
    const password = document.getElementById("password");
    const companyCode = document.getElementById("companyCode");

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

    function validateEmail() {
        const value = email.value.trim();
        const valid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);

        return setError(
            "email", "emailBox", "emailError",
            !value ? "メールアドレスを入力してください。"
                : !valid ? "正しいメールアドレスを入力してください。" : ""
        );
    }

    function validateId() {
        const value = userId.value.trim();

        return setError(
            "userId", "idBox", "idError",
            !value ? "IDを入力してください。"
                : value.length < 4
                    ? "IDは4文字以上で入力してください。" : ""
        );
    }

    function validatePassword() {
        const value = password.value;
        const valid = /^[a-zA-Z0-9]+$/.test(value);

        return setError(
            "password", "passwordBox", "passwordError",
            !value ? "パスワードを入力してください。"
                : value.length < 8 || !valid
                    ? "パスワードは8文字以上の半角英数字で入力してください。" : ""
        );
    }

    function validateCompanyCode() {
        const value = companyCode.value.trim();

        return setError(
            "companyCode", "companyCodeBox", "companyCodeError",
            !value ? "企業コードを入力してください。" : ""
        );
    }

    email.addEventListener("blur", validateEmail);
    userId.addEventListener("blur", validateId);
    password.addEventListener("blur", validatePassword);
    companyCode.addEventListener("blur", validateCompanyCode);

    document.getElementById("togglePassword").addEventListener("click", function() {
        const hidden = password.type === "password";
        password.type = hidden ? "text" : "password";
        this.textContent = hidden ? "◉" : "◎";
        this.setAttribute(
            "aria-label", hidden ? "パスワードを隠す" : "パスワードを表示"
        );
    });

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const results = [
            validateEmail(),
            validateId(),
            validatePassword(),
            validateCompanyCode()
        ];

        if (results.every(Boolean)) {
            alert("入力チェックに成功しました。データベース接続は未実装です。");
        } else {
            const firstInvalid = form.querySelector('[aria-invalid="true"]');
            if (firstInvalid) {
                firstInvalid.focus();
            }
        }
    });
</script>

</body>
</html>
