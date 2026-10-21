<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">

    <!-- ページのタイトル -->
    <title>まとめ検索</title>

    <!-- CSSを読み込む -->
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>

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

        <!-- 送信ボタン -->
        <button type="button" class="send-button">送信</button>

    </div>

</div>

</body>
</html>