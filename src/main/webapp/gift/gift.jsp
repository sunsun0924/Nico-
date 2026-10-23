<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<!-- ギフト画面のCSSを読み込む -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/gift.css">


<!-- サイドバーとギフト画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：ギフト画面 -->
    <main class="main-content">

        <div class="gift-container">

            <!-- ヘッダー -->
            <header class="gift-header">
                <span class="gift-icon"></span>
                <h1>ギフト</h1>
            </header>

            <!-- メッセージ -->
            <section class="gift-message">
                <div class="message-text">
                    <p>大切な人に</p>
                    <p>「ありがとう」を届けよう <span></span></p>
                </div>
            </section>

            <!-- 人気のギフト -->
            <section class="popular-gifts">
                <div class="section-header">
                    <h2>人気のギフト</h2>
                </div>

                <div class="gift-grid">

                    <!-- 花束 -->
                    <a href="${pageContext.request.contextPath}/gift/giftdetail.jsp?gift=flower"
                       class="gift-card">
                        <div class="gift-placeholder">🌷</div>
                        <h3>花束</h3>
                        <p class="gift-price">¥3,000</p>
                    </a>

                    <!-- スイーツセット -->
                    <a href="${pageContext.request.contextPath}/gift/giftdetail.jsp?gift=sweets"
                       class="gift-card">
                        <div class="gift-placeholder">🍪</div>
                        <h3>スイーツセット</h3>
                        <p class="gift-price">¥2,500</p>
                    </a>

                    <!-- カフェチケット -->
                    <a href="${pageContext.request.contextPath}/gift/giftdetail.jsp?gift=cafe"
                       class="gift-card">
                        <div class="gift-placeholder">☕</div>
                        <h3>カフェチケット</h3>
                        <p class="gift-price">¥1,500</p>
                    </a>

                    <!-- ハンドクリーム -->
                    <a href="${pageContext.request.contextPath}/gift/giftdetail.jsp?gift=handcream"
                       class="gift-card">
                        <div class="gift-placeholder">🧴</div>
                        <h3>ハンドクリーム</h3>
                        <p class="gift-price">¥2,000</p>
                    </a>

                    <!-- フォトフレーム -->
                    <a href="${pageContext.request.contextPath}/gift/giftdetail.jsp?gift=photo"
                       class="gift-card">
                        <div class="gift-placeholder">🖼️</div>
                        <h3>フォトフレーム</h3>
                        <p class="gift-price">¥2,800</p>
                    </a>

                    <!-- 雑貨セット -->
                    <a href="${pageContext.request.contextPath}/gift/giftdetail.jsp?gift=goods"
                       class="gift-card">
                        <div class="gift-placeholder">🍫</div>
                        <h3>雑貨セット</h3>
                        <p class="gift-price">¥3,500</p>
                    </a>

                </div>
            </section>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>
