<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/gift.css">

<div class="page-layout">

    <jsp:include page="/sidebar.jsp" />

    <main class="main-content">

        <div class="gift-container">

            <!-- =========================
                 ページタイトル
            ========================= -->

            <div class="gift-header">

                <h1>ギフト</h1>

                <p class="gift-message">
                    大切な人へのプレゼントを選びましょう
                </p>

            </div>


            <!-- =========================
                 エラーメッセージ
            ========================= -->

            <c:if test="${not empty errorMessage}">

                <div class="error-message">
                    ${errorMessage}
                </div>

            </c:if>


            <!-- =========================
                 成功メッセージ
            ========================= -->

            <c:if test="${not empty successMessage}">

                <div class="success-message">
                    ${successMessage}
                </div>

            </c:if>


            <!-- =========================
                 商品検索フォーム
            ========================= -->

            <form class="gift-search-form"
                  action="${pageContext.request.contextPath}/servlet.GiftServlet.action"
                  method="get">

                <input
                    type="text"
                    name="keyword"
                    value="${keyword}"
                    placeholder="商品名・説明・カテゴリーで検索">

                <button type="submit">
                    検索
                </button>

            </form>


            <!-- =========================
                 誕生日が近い家族へのおすすめ
            ========================= -->

            <c:if test="${not empty recommendedGiftSections}">

                <section class="popular-gifts">

                    <div class="section-header">

                        <h2>🎂 誕生日が近い家族へのおすすめ</h2>

                    </div>

                    <c:forEach var="section"
                               items="${recommendedGiftSections}">

                        <div class="recommend-section">

                            <h3>
                                ${section.relation}へのおすすめ
                            </h3>

                            <p class="recommend-info">
                                誕生日：${section.birthday}
                                ／ 年齢：${section.age}歳
                            </p>

                            <div class="gift-grid">

                                <c:forEach var="gift"
                                           items="${section.giftList}">

                                    <a class="gift-card"
                                       href="${pageContext.request.contextPath}/servlet.GiftServlet.action?giftProductId=${gift.giftProductId}&familyBirthdayId=${section.familyBirthdayId}">

                                        <c:choose>

                                            <c:when test="${not empty gift.giftImage}">

                                                <img
                                                    src="${pageContext.request.contextPath}/gift-images/${gift.giftImage}"
                                                    alt="${gift.giftName}"
                                                    class="gift-image">

                                            </c:when>

                                            <c:otherwise>

                                                <div class="gift-placeholder">
                                                    🎁
                                                </div>

                                            </c:otherwise>

                                        </c:choose>

                                        <h3>${gift.giftName}</h3>

                                        <p class="gift-description">
                                            ${gift.giftText}
                                        </p>

                                    </a>

                                </c:forEach>

                                <c:if test="${empty section.giftList}">

                                    <p class="no-gift-message">
                                        おすすめの商品はありません。
                                    </p>

                                </c:if>

                            </div>

                        </div>

                    </c:forEach>

                </section>

            </c:if>


            <!-- =========================
                 全商品一覧
            ========================= -->

            <section class="all-gifts">

                <div class="section-header">

                    <h2>🎁 すべてのギフト</h2>

                </div>


                <!-- =========================
                     商品一覧
                ========================= -->

                <div class="gift-grid">

                    <c:forEach var="gift" items="${giftList}">

                        <a class="gift-card"
                           href="${pageContext.request.contextPath}/servlet.GiftServlet.action?giftProductId=${gift.giftProductId}">

                            <c:choose>

                                <c:when test="${not empty gift.giftImage}">

                                    <img
                                        src="${pageContext.request.contextPath}/gift-images/${gift.giftImage}"
                                        alt="${gift.giftName}"
                                        class="gift-image">

                                </c:when>

                                <c:otherwise>

                                    <div class="gift-placeholder">
                                        🎁
                                    </div>

                                </c:otherwise>

                            </c:choose>

                            <h3>${gift.giftName}</h3>

                            <p class="gift-description">
                                ${gift.giftText}
                            </p>

                        </a>

                    </c:forEach>


                    <c:if test="${empty giftList}">

                        <p class="no-gift-message">
                            該当する商品がありません。
                        </p>

                    </c:if>

                </div>

            </section>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>