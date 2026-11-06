
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/company.css?v=3">

<div class="page-layout">

    <!-- サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- メイン画面 -->
    <main class="main-content">

        <!-- 企業登録フォーム -->
        <form action="${pageContext.request.contextPath}/servlet/CompanyRegisterServlet.action"
              method="post"
              class="company-register">

            <!-- タイトル -->
            <header>
                <h2>企業登録</h2>

                <p>
                    下記の情報を入力して、企業を登録してください。
                </p>
            </header>

            <!-- 企業ID -->
            <div class="input-group">

                <div class="input-content">

                    <label for="cpId">企業ID</label>

                    <input
                        type="text"
                        id="cpId"
                        name="cpId"
                        placeholder="企業IDを入力してください"
                        maxlength="4"
                        required>

                </div>

            </div>

            <!-- 企業名 -->
            <div class="input-group">

                <div class="input-content">

                    <label for="cpName">企業名</label>

                    <input
                        type="text"
                        id="cpName"
                        name="cpName"
                        placeholder="企業名を入力してください"
                        maxlength="50"
                        required>

                </div>

            </div>

            <!-- エラーメッセージ -->
            <% if (request.getAttribute("errorMessage") != null) { %>

                <p class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </p>

            <% } %>

            <!-- 登録完了メッセージ -->
            <% if (request.getAttribute("message") != null) { %>

                <p class="success-message">
                    <%= request.getAttribute("message") %>
                </p>

            <% } %>

            <!-- ボタン -->
            <div class="button-area">

                <input type="submit" value="登録">

                <input
                    type="button"
                    value="戻る"
                    onclick="history.back()">

            </div>

        </form>

    </main>

</div>

<%@ include file="../footer.jsp" %>
