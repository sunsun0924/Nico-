
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%@ include file="../header.jsp" %>

<!-- CSSを読み込む -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/search.css">

<!-- サイドバーと検索画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：検索画面 -->
    <main class="main-content">

        <div class="container">

            <div class="question-box">

                <!-- ＋ボタン -->
                <button type="button" class="plus-button">+</button>

                <!-- 質問入力欄 -->
                <input
                    type="text"
                    class="question-input"
                    placeholder="質問してみましょう"
                >

                <!-- 紙飛行機の送信ボタン -->
                <button type="button"
                        class="send-button"
                        aria-label="送信">

                    <svg xmlns="http://www.w3.org/2000/svg"
                         viewBox="0 0 24 24"
                         fill="none"
                         stroke="currentColor"
                         stroke-width="1.8"
                         stroke-linecap="round"
                         stroke-linejoin="round">

                        <path d="M22 2 11 13"/>
                        <path d="m22 2-7 20-4-9-9-4Z"/>

                    </svg>

                </button>

            </div>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>
