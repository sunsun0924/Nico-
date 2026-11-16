
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="../header.jsp" %>

<!-- ギフト詳細画面のCSS -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/giftdetail.css">

<!-- サイドバーと詳細画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：ギフト詳細画面 -->
    <main class="main-content">

        <div class="detail-container">

            <!-- ギフト詳細 -->
            <section class="gift-detail">

                <!-- 商品画像 -->
                <c:choose>
                    <c:when test="${not empty giftProduct.giftImage}">
                        <img
                            src="${pageContext.request.contextPath}/gift-images/${giftProduct.giftImage}"
                            alt="<c:out value='${giftProduct.giftName}' />"
                            class="gift-image">
                    </c:when>

                    <c:otherwise>
                        <div class="gift-image-placeholder">
                            🎁
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- 商品名 -->
                <div class="gift-name-area">
                    <span class="gift-flower">♡</span>
                    <h1>
                        <c:out value="${giftProduct.giftName}" />
                    </h1>
                </div>

                <!-- 商品説明 -->
                <div class="gift-description">
                    <p>
                        <c:out value="${giftProduct.giftText}" />
                    </p>

                    <p>
                        大切な人に「ありがとう」や
                        「おめでとう」の気持ちを込めて
                        贈ることができます。
                    </p>
                </div>

                <!-- 贈る相手 -->
                <div class="gift-recipient">
                    <h3>贈る相手</h3>
                    <p>
                        <c:out value="${familyRelation}" />
                    </p>
                </div>

            </section>

            <!-- 下部ボタン -->
            <footer class="detail-footer">

                <!-- ギフト一覧に戻る -->
                <a
                    href="${pageContext.request.contextPath}/servlet.GiftServlet.action"
                    class="btn btn-back">

                    <span class="btn-arrow">戻る</span>
                </a>

                <!-- ギフト登録 -->
                <form
                    action="${pageContext.request.contextPath}/servlet.GiftSelectServlet.action"
                    method="post"
                    class="confirm-form">

                    <!-- 商品ID -->
                    <input
                        type="hidden"
                        name="giftProductId"
                        value="<c:out value='${giftProduct.giftProductId}' />">

                    <!-- 贈る相手のID -->
                    <input
                        type="hidden"
                        name="familyBirthdayId"
                        value="<c:out value='${familyBirthdayId}' />">

                    <button type="submit" class="btn btn-confirm">

                        <span>決定</span>
                        <span class="gift-icon">♡</span>

                    </button>

                </form>

            </footer>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>
