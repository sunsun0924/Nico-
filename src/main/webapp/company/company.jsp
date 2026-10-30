<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/company.css">

<div class="page-layout">

    <!-- サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- メイン画面 -->
    <main class="main-content">

        <form action="${pageContext.request.contextPath}/servlet/CompanyRegisterServlet.action"
              method="post"
              class="company-register">

            <!-- 企業登録画面 -->
            <header>

                <h2>企業登録</h2>

                <p>
                    下記の情報を入力して、企業を登録してください。
                </p>

            </header>


            <!-- 企業情報を送信するフォーム -->

            <!-- 企業IDの入力欄 -->
            <div class="input-group">

                <div class="input-content">

                    <label for="cpId">企業ID</label>

                    <input
                        type="text"
                        id="cpId"
                        name="cpId"
                        placeholder="企業IDを入力してください"
                        required>

                </div>

            </div>


            <!-- 企業名の入力欄 -->
            <div class="input-group">

                <div class="input-content">

                    <label for="cpName">企業名</label>

                    <input
                        type="text"
                        id="cpName"
                        name="cpName"
                        placeholder="企業名を入力してください"
                        required>

                </div>

            </div>


            <!-- エラーメッセージ -->
            <% if (request.getAttribute("errorMessage") != null) { %>

                <p class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </p>

            <% } %>


            <!-- 登録ボタン -->
            <input type="submit" value="登録">


            <!-- 前のページに戻るボタン -->
            <!-- 後でメインページに飛ぶように変更予定 -->
            <input type="button"
                   value="戻る"
                   onclick="history.back()">

        </form>

    </main>

</div>

<%@ include file="../footer.jsp" %>