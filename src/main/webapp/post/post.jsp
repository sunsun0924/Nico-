<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/post.css">

<div class="page-layout">

    <%@ include file="../sidebar.jsp" %>

    <main class="main-content">

        <div class="home-header">

            <h1>投稿</h1>

            <!-- 登録完了メッセージ -->
            <c:if test="${not empty message}">
                <p class="success-message">${message}</p>
            </c:if>

            <br>

            <!-- 新規投稿ボタン -->
            <a href="${pageContext.request.contextPath}/post/postnew.jsp"
               class="reserve-btn">
                新規投稿
            </a>

            <hr>

            <h3>投稿一覧</h3>

            <!-- 投稿一覧 -->
            <c:choose>

                <c:when test="${not empty postList}">

                    <c:forEach var="post" items="${postList}">

                        <div class="post-item">

                            <!-- 投稿者 -->
                            <strong>
                                <c:choose>
                                    <c:when test="${post.author_type == 'ADMIN'}">
                                        管理者
                                    </c:when>
                                    <c:otherwise>
                                        ユーザーID：${post.user_id}
                                    </c:otherwise>
                                </c:choose>
                            </strong>

                            <!-- 投稿日時 -->
                            <p>${post.post_datetime}</p>

                            <!-- 投稿本文 -->
                            <p>${post.post_text}</p>

                            <!-- 投稿画像 -->
                            <c:if test="${not empty post.post_img}">
                                <img
                                    src="${pageContext.request.contextPath}/images/${post.post_img}"
                                    alt="投稿画像"
                                    width="150">
                            </c:if>

                            <!-- コメント -->
                            <p>コメント：${post.comment}</p>

                        </div>

                        <hr>

                    </c:forEach>

                </c:when>

                <c:otherwise>
                    <p>まだ投稿はありません。</p>
                </c:otherwise>

            </c:choose>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>