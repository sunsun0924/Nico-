<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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

                <!-- DBから取得したギフト一覧 -->
                <div class="gift-grid">

                    <c:forEach var="gift" items="${giftList}">

                        <!-- ギフトカード -->
                        <a href="${pageContext.request.contextPath}/gift/giftdetail.jsp?giftId=${gift.giftId}"
                           class="gift-card">

                            <div class="gift-placeholder">🎁</div>

                            <h3>
                                <c:out value="${gift.giftName}" />
                            </h3>

                            <p class="gift-description">
                                <c:out value="${gift.giftText}" />
                            </p>

                        </a>

                    </c:forEach>

                    <!-- ギフトが登録されていない場合 -->
                    <c:if test="${empty giftList}">
                        <p>現在、ギフトは登録されていません。</p>
                    </c:if>

                </div>
            </section>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>