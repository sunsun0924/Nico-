
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ギフト履歴 - Nico+</title>

    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/gifthistory.css">

    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

    <%-- 共通パーツ --%>
    <jsp:include page="../header.jsp" />
    <jsp:include page="../sidebar.jsp" />

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

                    <c:when test="${not empty giftHistoryList}">

                        <div class="gift-history-list">

                            <c:forEach var="gift" items="${giftHistoryList}">

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
                    <i class="fa-solid fa-arrow-left"></i>
                    マイページに戻る
                </a>
            </div>

        </div>

    </main>

    <%-- 共通フッター --%>
    <jsp:include page="../footer.jsp" />

</body>
</html>
