<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャットルーム - Nico</title>
</head>
<body>

    <!-- チャット一覧に戻るリンク（絶対パス） -->
    <p>
        <a href="${pageContext.request.contextPath}/chat-list">＜ チャット一覧に戻る</a>
    </p>

    <h1>チャットルーム</h1>

    <!-- メッセージのやり取り一覧表示エリア -->
    <div style="border: 1px solid #ccc; height: 300px; overflow-y: scroll; padding: 10px; margin-bottom: 10px;">
        <c:forEach var="msg" items="${messageList}">
            <div style="margin-bottom: 8px;">
                <small style="color: gray;">[<c:out value="${msg.sentAt}" />]</small>
                <strong>ID: <c:out value="${msg.senderId}" />:</strong>
                <span><c:out value="${msg.message}" /></span>
            </div>
        </c:forEach>
    </div>

    <!-- メッセージ送信フォーム（actionを絶対パスに指定） -->
    <form action="${pageContext.request.contextPath}/chat-room" method="post">
        <!-- 誰宛てのメッセージか保持するための隠しパラメータ -->
        <input type="hidden" name="partnerId" value="${partnerUserId}">
        
        <input type="text" name="message" placeholder="メッセージを入力..." required style="width: 300px;">
        <button type="submit">送信</button>
    </form>

</body>
</html>