
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

		 
		
            <!-- ユーザー登録アイコン -->
				<div class="register-icon">
				    <span class="user-symbol"
				          style="font-size: 32px !important;
				                 line-height: 1;
				                 display: inline-block;">♟</span>
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
			<div class="input-group">
			
			    <div class="input-icon">
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
			            minlength="8"
			            autocomplete="new-password"
			            required>
			    </div>
			
	<!-- パスワード表示切り替えボタン -->
		<button
		    type="button"
		    id="togglePassword"
		    class="eye-button"
		    aria-label="パスワードを表示"
		    aria-pressed="false"
		    style="border: none !important;
		           outline: none !important;
		           background: transparent !important;
		           box-shadow: none !important;
		           border-radius: 0 !important;
		           appearance: none;
		           -webkit-appearance: none;">
		
		    <svg
		        xmlns="http://www.w3.org/2000/svg"
		        viewBox="0 0 24 24"
		        width="22"
		        height="22"
		        fill="none"
		        stroke="#b58b78"
		        stroke-width="1.8"
		        stroke-linecap="round"
		        stroke-linejoin="round"
		        aria-hidden="true">
		
		        <!-- 目の輪郭 -->
		        <path d="M2 12s3.6-7 10-7 10 7 10 7-3.6 7-10 7S2 12 2 12Z"/>
		
		        <!-- 黒目 -->
		        <circle cx="12" cy="12" r="3"/>
		
		        <!-- パスワード非表示時の斜線 -->
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

            <!-- 登録ボタン -->
            <button type="submit" class="register-button">
                <span>登録する</span>
                <span class="button-arrow" aria-hidden="true">→</span>
            </button>

        </form>

    </main>

    <!-- パスワードの表示・非表示 -->
   

<script>
document.addEventListener("DOMContentLoaded", function () {
    const password = document.getElementById("password");
    const togglePassword = document.getElementById("togglePassword");
    const eyeSlash = document.getElementById("eyeSlash");

    // 必要な要素がそろっているか確認
    if (!password || !togglePassword || !eyeSlash) {
        console.error("パスワード表示切り替え用のHTMLが見つかりません。");
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
        togglePassword.setAttribute("aria-pressed", String(isHidden));
    });
});
</script>



</body>
</html>
