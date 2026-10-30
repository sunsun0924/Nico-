<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminRegister.css">
</head>

<body>

    <main class="register-container">

        <!-- 管理者アイコン -->
        <div class="user-icon">
            <span class="user-symbol">♟</span>
        </div>

        <!-- 見出し -->
        <h1>新規登録</h1>

        <p class="description">
            管理者情報を入力して、アカウントを作成してください。
        </p>

        <!-- エラーメッセージ -->
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="error-message">
                <%= request.getAttribute("errorMessage") %>
            </p>
        <% } %>

        <!-- 新規登録フォーム -->
        <form id="adminRegisterForm"
              action="${pageContext.request.contextPath}/servlet/AdminRegisterServlet.action"
              method="post">

            <!-- メールアドレス -->
            <div class="form-group">
                <div class="input-box">

                    <span class="field-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="1.8"
                             stroke-linecap="round"
                             stroke-linejoin="round">

                            <rect x="3" y="5" width="18" height="14" rx="2"/>
                            <path d="m4 7 8 6 8-6"/>

                        </svg>
                    </span>

                    <div class="input-content">

                        <label for="email">メールアドレス</label>

                        <input
                            type="email"
                            id="email"
                            name="cpUsermail"
                            placeholder="例：admin@example.co.jp"
                            autocomplete="email"
                            maxlength="100"
                            required>

                    </div>
                </div>
            </div>

            <!-- 管理者ID -->
            <div class="form-group">
                <div class="input-box">

                    <span class="field-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="1.8"
                             stroke-linecap="round"
                             stroke-linejoin="round">

                            <circle cx="12" cy="8" r="4"/>
                            <path d="M4 21v-2a8 8 0 0 1 16 0v2"/>

                        </svg>
                    </span>

                    <div class="input-content">

                        <label for="userId">管理者ID</label>

                        <input
                            type="text"
                            id="userId"
                            name="cpUserid"
                            placeholder="例：admin001"
                            autocomplete="username"
                            minlength="8"
                            maxlength="8"
                            required>

                    </div>
                </div>
            </div>

            <!-- パスワード -->
            <div class="form-group">
                <div class="input-box">

                    <span class="field-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="1.8"
                             stroke-linecap="round"
                             stroke-linejoin="round">

                            <rect x="4" y="10" width="16" height="11" rx="2"/>
                            <path d="M8 10V7a4 4 0 0 1 8 0v3"/>
                            <circle cx="12" cy="15" r="1"/>
                            <path d="M12 16v2"/>

                        </svg>
                    </span>

                    <div class="input-content">

                        <label for="password">パスワード</label>

                        <input
                            type="password"
                            id="password"
                            name="cpUserpass"
                            placeholder="例：Admin1234"
                            autocomplete="new-password"
                            minlength="8"
                            maxlength="15"
                            pattern="[A-Za-z0-9]{8,15}"
                            title="パスワードは8～15文字の半角英数字で入力してください。"
                            required>

                    </div>

                    <!-- パスワード表示切り替え -->
                    <button
                        type="button"
                        id="togglePassword"
                        class="eye-button"
                        aria-label="パスワードを表示"
                        aria-pressed="false">

                        <svg xmlns="http://www.w3.org/2000/svg"
                             viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="1.8"
                             stroke-linecap="round"
                             stroke-linejoin="round"
                             aria-hidden="true">

                            <!-- 通常の目 -->
                            <g id="eyeOpen" style="display:none;">

                                <path d="M2 12s3.6-7 10-7 10 7 10 7-3.6 7-10 7S2 12 2 12Z"/>
                                <circle cx="12" cy="12" r="3"/>

                            </g>

                            <!-- 斜線入りの目 -->
                            <g id="eyeClosed">

                                <path d="M3 3l18 18"/>
                                <path d="M10.6 10.6a2 2 0 0 0 2.8 2.8"/>
                                <path d="M9.9 5.2A10.8 10.8 0 0 1 12 5c6.4 0 10 7 10 7a16 16 0 0 1-3.1 3.9"/>
                                <path d="M6.2 6.2C3.5 8 2 12 2 12s3.6 7 10 7a10.5 10.5 0 0 0 3-.4"/>

                            </g>

                        </svg>

                    </button>

                </div>
            </div>

            <!-- 企業コード -->
            <div class="form-group">
                <div class="input-box">

                    <span class="field-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"
                             fill="none"
                             stroke="currentColor"
                             stroke-width="1.8"
                             stroke-linecap="round"
                             stroke-linejoin="round">

                            <rect x="5" y="3" width="14" height="18" rx="1"/>
                            <path d="M9 7h2M14 7h1M9 11h2M14 11h1M9 15h2M14 15h1M11 21v-3h3v3"/>

                        </svg>
                    </span>

                    <div class="input-content">

                        <label for="companyCode">企業コード</label>

                        <input
                            type="text"
                            id="companyCode"
                            name="cpId"
                            placeholder="例：ABCD"
                            autocomplete="off"
                            minlength="4"
                            maxlength="4"
                            required>

                    </div>
                </div>
            </div>

            <!-- 登録ボタン -->
            <button type="submit" class="register-button">

                <span>登録する</span>

                <span class="button-arrow" aria-hidden="true">→</span>

            </button>

        </form>

    </main>

    <!-- パスワード表示切り替え -->
    <script>

        const password = document.getElementById("password");

        const togglePassword = document.getElementById("togglePassword");

        const eyeOpen = document.getElementById("eyeOpen");

        const eyeClosed = document.getElementById("eyeClosed");


        // パスワード表示切り替え
        togglePassword.addEventListener("click", function () {

            const isHidden = password.type === "password";

            password.type = isHidden ? "text" : "password";

            eyeOpen.style.display = isHidden ? "inline" : "none";

            eyeClosed.style.display = isHidden ? "none" : "inline";

            togglePassword.setAttribute(
                "aria-label",
                isHidden ? "パスワードを非表示" : "パスワードを表示"
            );

            togglePassword.setAttribute(
                "aria-pressed",
                String(isHidden)
            );

        });

    </script>

<%@ include file="../footer.jsp" %>