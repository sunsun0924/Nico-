<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">

<div class="page-layout">

    <%@ include file="../sidebar.jsp" %>

    <main class="main-content">

        <div class="chat-room-container">

            <!-- ヘッダー（戻るボタン付き） -->
            <header class="chat-room-header">
                <div class="header-left">
                    <a href="${pageContext.request.contextPath}/chat-list" class="back-link">＜</a>
                    <div class="user-icon">
                        <div class="no-image"></div>
                    </div>
                    <div class="header-user-info">
                        <span class="header-user-name"><c:out value="${partnerUserId}" /> さん</span>
                        <span class="header-status"><span class="status-dot"></span>オンライン</span>
                    </div>
                </div>
                <div class="header-menu-icon">···</div>
            </header>

            <!-- メッセージ表示エリア -->
            <div class="chat-messages-area">
                <c:forEach var="msg" items="${messageList}">
                    <div class="message-row <c:choose><c:when test='${msg.senderId == "userA"}'>mine</c:when><c:otherwise>partner</c:otherwise></c:choose>">
                        <c:if test="${msg.senderId != 'userA'}">
                            <div class="message-user-icon">
                                <div class="no-image-small"></div>
                            </div>
                        </c:if>
                        <div class="message-content-wrap">
                            <div class="message-bubble">
                                <c:out value="${msg.message}" />
                            </div>
                            <span class="message-time"><c:out value="${msg.sentAt}" /></span>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- メッセージ送信フォーム -->
            <form action="${pageContext.request.contextPath}/chat-room" method="post" class="chat-input-form">
                <input type="hidden" name="partnerId" value="${partnerUserId}">
                <button type="button" class="btn-plus">＋</button>
                <input type="text" name="message" placeholder="メッセージを入力..." required autocomplete="off">
                <button type="submit" class="btn-send-round">
                    <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor"><path d="M2.01 21L23 12 2.01 3 2 10l15 2-15 2z"></path></svg>
                </button>
            </form>

        </div>

    </main>

</div>

</body>
</html>