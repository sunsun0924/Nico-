
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/company.css">


<form action="${pageContext.request.contextPath}/servlet/CompanyRegisterServlet.action" method="post" class="company-register">
<!-- 企業登録画面 -->
<h2>企業登録</h2>

<!-- 企業情報を送信するフォーム -->

    <!-- 企業IDの入力欄 -->
    <p>
        企業ID：
        <input type="text" name="companyId" required>
    </p>

    <!-- 企業名の入力欄 -->
    <p>
        企業名：
        <input type="text" name="companyName" required>
    </p>

    <!-- 登録ボタン -->
    <input type="submit" value="登録">

    <!-- 前のページに戻るボタン -->
    <!-- 後でメインページに飛ぶように変更予定 -->
    <input type="button" value="戻る" onclick="history.back()">

</form>

<%@ include file="../footer.jsp" %>