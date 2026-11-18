<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/notification.css">

<div class="page-layout">

    <%@ include file="../sidebar.jsp" %>

    <main class="main-content">

        <div class="notification-container">

            <div class="notification-header">
                <h1>通知</h1>
            </div>

            <div class="notification-tabs">

                <a href="?filter=all"
                   class="tab ${filter == 'all' ? 'active' : ''}">
                    すべて
                </a>

                <a href="?filter=comment"
                   class="tab ${filter == 'comment' ? 'active' : ''}">
                    コメント
                </a>

                <a href="?filter=gift"
                   class="tab ${filter == 'gift' ? 'active' : ''}">
                    ギフト
                </a>

            </div>

            <div class="notification-list">

                <c:choose>

                    <c:when test="${not empty notifications}">

                        <c:forEach var="notification"
                                   items="${notifications}">

                            <div class="notification-item">

                                <%-- 未登録のギフト通知だけリンクにする --%>
                                <c:if test="${notification.type == 'gift'
                                             and not notification.giftRegistered}">

                                    <a href="${pageContext.request.contextPath}/servlet.GiftServlet.action"
                                       class="notification-link">

                                </c:if>

                                <div class="notification-content">

                                    <p class="notification-message">
                                        <c:out value="${notification.title}" />
                                    </p>

                                    <c:if test="${not empty notification.detail}">
                                        <p class="notification-detail">
                                            <c:out value="${notification.detail}" />
                                        </p>
                                    </c:if>

                                    <%-- ギフト通知の表示 --%>
                                    <c:if test="${notification.type == 'gift'}">

                                        <c:choose>

                                            <%-- 登録済みの場合 --%>
                                            <c:when test="${notification.giftRegistered}">

                                                <p class="notification-link-text">
                                                    プレゼント登録済み
                                                </p>

                                            </c:when>

                                            <%-- 未登録の場合 --%>
                                            <c:otherwise>

                                                <p class="notification-link-text">
                                                    プレゼントを選ぶ →
                                                </p>

                                            </c:otherwise>

                                        </c:choose>

                                    </c:if>

                                    <p class="notification-date">
                                        <c:out value="${notification.date}" />
                                    </p>

                                </div>

                                <%-- 未登録のギフト通知だけリンクを閉じる --%>
                                <c:if test="${notification.type == 'gift'
                                             and not notification.giftRegistered}">

                                    </a>

                                </c:if>

                            </div>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <p class="no-notification">
                            通知はありません。
                        </p>

                    </c:otherwise>

                </c:choose>

            </div>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>