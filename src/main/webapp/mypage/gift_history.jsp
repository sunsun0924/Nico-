<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/gifthistory.css">

<%@ include file="../sidebar.jsp" %>

<main class="main-container">

    <div class="content-card">

        <%-- ページタイトル --%>
        <div class="card-header">

            <i class="fa-solid fa-gift"></i>

            <h2>ギフト履歴</h2>

        </div>

        <%-- ギフト一覧 --%>
        <section class="gift-history-section">

            <c:choose>

                <%-- ギフト履歴がある場合 --%>
                <c:when test="${not empty giftHistoryList}">

                    <div class="gift-history-list">

                        <c:forEach var="gift" items="${giftHistoryList}">

                            <%-- ギフト1件分 --%>
                            <div class="gift-history-item">

                                <%-- ギフト画像 --%>
                                <div class="gift-image">

                                    <img
                                        src="${pageContext.request.contextPath}/images/gift/${gift.giftImage}"
                                        alt="${gift.giftName}">

                                </div>

                                <%-- ギフト情報 --%>
                                <div class="gift-info">

                                    <h4 class="gift-name">
                                        <c:out value="${gift.giftName}" />
                                    </h4>

                                    <p class="gift-date">

                                        <i class="fa-regular fa-calendar"></i>

                                        受け取った日：

                                        <c:out value="${gift.receivedDate}" />

                                    </p>

                                </div>

                            </div>

                        </c:forEach>

                    </div>

                </c:when>

                <%-- ギフト履歴がない場合 --%>
                <c:otherwise>

                    <div class="empty-message">

                        <i class="fa-solid fa-gift"></i>

                        <p>まだ受け取ったギフトはありません。</p>

                    </div>

                </c:otherwise>

            </c:choose>

        </section>

        <%-- 戻るボタン --%>
        <div class="back-area">

            <a href="${pageContext.request.contextPath}/mypage/mypage.jsp"
               class="back-button">
                戻る
            </a>

        </div>

    </div>

</main>

<%@ include file="../footer.jsp" %>