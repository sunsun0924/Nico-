<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="../header.jsp" %>

<!-- 通知画面のCSSを読み込む -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/notification.css">

<!-- サイドバーと通知画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：通知画面 -->
    <main class="main-content">

        <!-- 通知画面全体 -->
        <div class="notification-container">

            <!-- 画面上部のタイトル -->
            <div class="notification-header">
                <h1>通知</h1>
            </div>

            <!-- 通知の種類を切り替えるタブ -->
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

            
		<!-- 通知一覧 -->
		<div class="notification-list">
		
		    <c:choose>
		
		        <%-- 通知がある場合 --%>
		        <c:when test="${not empty notifications}">
		
		            <c:forEach var="notification"
		                       items="${notifications}">
		
		                <!-- 通知1件分 -->
		                <div class="notification-item">
		
		                    <div class="notification-content">
		
		                        <!-- 通知内容 -->
		                        <!-- 通知タイトル -->
								<p class="notification-message">
								    <c:out value="${notification.title}" />
								</p>
								
								<!-- コメント本文 -->
								<c:if test="${not empty notification.detail}">
								    <p class="notification-detail">
								        <c:out value="${notification.detail}" />
								    </p>
								</c:if>
		
		                        <!-- 通知日時 -->
		                        <p class="notification-date">
		                            <c:out value="${notification.date}" />
		                        </p>
		
		                    </div>
		
		                </div>
		
		            </c:forEach>
		
		        </c:when>
		
		        <%-- 通知がない場合 --%>
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
