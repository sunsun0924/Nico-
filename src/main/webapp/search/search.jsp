
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

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

        <!-- 外側の白いエリア -->
        <div class="container">

            <!-- エラーメッセージ -->
            <c:if test="${not empty errorMessage}">
                <p class="search-error">
                    <c:out value="${errorMessage}" />
                </p>
            </c:if>

            <!-- 会話表示エリア -->
            <div class="chat-area" id="chatArea">

                <c:forEach var="detail" items="${detailList}">

                    <!-- ユーザーの質問 -->
                    <c:if test="${detail.senderType == 'user'}">
                        <div class="message-row user-row">
                            <div class="user-message"><c:out value="${fn:trim(detail.searchDetailText)}" /></div>
                        </div>
                    </c:if>

                    <!-- AIの回答 -->
                    <c:if test="${detail.senderType == 'assistant'}">
                        <div class="message-row assistant-row">
                            <div class="assistant-message"><c:out value="${fn:trim(detail.searchDetailText)}" /></div>
                        </div>
                    </c:if>

                </c:forEach>

                <!-- Web検索の検索元 -->
                <c:if test="${webSearchUsed and not empty searchResults}">
                    <div class="search-sources">

                        <p class="search-sources-title">
                            参考にしたWebサイト
                        </p>

                        <ul class="search-sources-list">

                            <c:forEach var="result" items="${searchResults}">

                                <c:if test="${fn:startsWith(result.url, 'https://')
                                            or fn:startsWith(result.url, 'http://')}">
                                    <li>
                                        <a href="<c:out value='${result.url}' />"
                                           target="_blank"
                                           rel="noopener noreferrer">
                                            <c:out value="${result.title}" />
                                        </a>
                                    </li>
                                </c:if>

                            </c:forEach>

                        </ul>
                    </div>
                </c:if>

            </div>

            <!-- 質問入力欄全体 -->
            <form action="${pageContext.request.contextPath}/servlet/SearchServlet.action"
                  method="post"
                  class="question-box">

                <!-- ＋ボタン -->
                <button type="button" class="plus-button">+</button>

                <!-- 質問を入力する部分 -->
                <textarea
                    name="question"
                    class="question-input"
                    placeholder="質問してみましょう"
                    rows="1"
                    required
                ></textarea>

                <!-- 検索履歴ID -->
                <input
                    type="hidden"
                    name="searchId"
                    value="<c:out value='${searchId}' />"
                >

                <!-- 送信ボタン -->
                <button type="submit"
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

            </form>

        </div>

    </main>

</div>

<!-- 入力欄の高さ調整・会話の最新位置までスクロール -->
<script>
    window.addEventListener("load", function() {

        const chatArea = document.getElementById("chatArea");
        const questionInput = document.querySelector(".question-input");

        // 会話を最新位置までスクロール
        if (chatArea) {
            chatArea.scrollTop = chatArea.scrollHeight;
        }

        // 入力内容に合わせて高さを調整
        if (questionInput) {

            const adjustHeight = function() {

                questionInput.style.height = "auto";

                const maxHeight = 100;

                questionInput.style.height =
                    Math.min(questionInput.scrollHeight, maxHeight) + "px";

                questionInput.style.overflowY =
                    questionInput.scrollHeight > maxHeight ? "auto" : "hidden";
            };

            questionInput.addEventListener("input", adjustHeight);

            // 初期表示時にも高さを調整
            adjustHeight();
        }

    });
</script>

<!-- フッター -->
<%@ include file="../footer.jsp" %>
