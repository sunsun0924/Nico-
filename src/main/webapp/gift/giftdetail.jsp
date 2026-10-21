
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
    String gift = request.getParameter("gift");

    String giftName = "花束";
    String giftPrice = "3,000";
    String giftDescription = "やさしい色合いの花束です。";

    if ("sweets".equals(gift)) {
        giftName = "スイーツセット";
        giftPrice = "2,500";
        giftDescription = "いろいろな味を楽しめるスイーツセットです。";
    } else if ("cafe".equals(gift)) {
        giftName = "カフェチケット";
        giftPrice = "1,500";
        giftDescription = "カフェでゆっくり過ごせるチケットです。";
    } else if ("handcream".equals(gift)) {
        giftName = "ハンドクリーム";
        giftPrice = "2,000";
        giftDescription = "手肌をいたわるハンドクリームです。";
    } else if ("photo".equals(gift)) {
        giftName = "フォトフレーム";
        giftPrice = "2,800";
        giftDescription = "大切な思い出を飾れるフォトフレームです。";
    } else if ("goods".equals(gift)) {
        giftName = "雑貨セット";
        giftPrice = "3,500";
        giftDescription = "日常で使える雑貨を集めたセットです。";
    }
%>


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>ギフト詳細</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/giftdetail.css">
</head>
<body>

    <main class="detail-container">

      

        <!-- ギフト詳細 -->
        <section class="gift-detail">

            <!-- 写真なし -->
            <div class="gift-image-placeholder"></div>

            <!-- 商品名 -->
            <div class="gift-name-area">
                <span class="gift-flower"></span>
                <h1><%= giftName %></h1>
            </div>

            <!-- 価格 -->
            <p class="gift-price">¥<%= giftPrice %></p>

            <!-- 商品説明 -->
            <div class="gift-description">
                <p><%= giftDescription %></p>
                <p>
                    大切な人に「ありがとう」や
                    「おめでとう」の気持ちを込めて
                    贈ることができます。
                </p>
            </div>

        </section>

        <!-- 下部ボタン -->
        <footer class="detail-footer">

            <!-- gift.jspに戻る -->
           <a href="${pageContext.request.contextPath}/gift/gift.jsp"
			   class="btn btn-back">
			    <span class="btn-arrow">←</span>
			    <span>戻る</span>
			</a>

            <!-- 決定ボタン -->
            <form action="${pageContext.request.contextPath}/giftConfirm"
                  method="post"
                  class="confirm-form">

                <input type="hidden"
                       name="giftName"
                       value="<%= giftName %>">

                <input type="hidden"
                       name="giftPrice"
                       value="<%= giftPrice %>">

                <button type="submit" class="btn btn-confirm">
                    <span>決定</span>
                    <span class="gift-icon"></span>
                </button>

            </form>

        </footer>

    </main>

</body>
</html>
