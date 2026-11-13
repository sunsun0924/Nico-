<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/bookmark.css">

<%@ include file="../sidebar.jsp" %>

<main class="main-container">

    <div class="content-card">

        <%-- ページタイトル --%>
        <div class="card-header">
            <i class="fa-solid fa-bookmark"></i>
            <h2>ブックマーク</h2>
        </div>

        <%-- 成功メッセージ --%>
        <c:if test="${not empty message}">
            <p class="success-message">
                <c:out value="${message}" />
            </p>
        </c:if>

        <hr>

        <%-- ブックマーク一覧 --%>
        <c:choose>

            <%-- 投稿がある場合 --%>
            <c:when test="${not empty postList}">

                <c:forEach var="post" items="${postList}">

                    <div class="post-item">

                        <%-- 投稿者情報 --%>
                        <div class="post-author">

                            <img
                                src="${pageContext.request.contextPath}/images/${post.authorImage}"
                                alt="プロフィール画像"
                                class="post-author-icon"
                                style="object-position: ${post.authorImagePositionX}% ${post.authorImagePositionY}%">

                            <div class="post-author-info">

                                <span class="post-author-name">
                                    <c:out value="${post.authorName}" />
                                </span>

                                <c:choose>
                                    <c:when test="${post.author_type eq 'ADMIN'}">
                                        <span class="post-author-label">管理者</span>
                                    </c:when>

                                    <c:otherwise>
                                        <span class="post-author-label">ユーザー</span>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>

                        <%-- 投稿日時 --%>
                        <p class="post-date">
                            <c:out value="${post.post_datetime}" />
                        </p>

                        <%-- 投稿本文 --%>
                        <c:if test="${not empty post.post_text}">
                            <p class="post-text">
                                <c:out value="${post.post_text}" />
                            </p>
                        </c:if>

                        <%-- 投稿画像 --%>
                        <c:if test="${not empty post.postImages}">
                            <div class="post-image-list">

                                <c:forEach var="image" items="${post.postImages}">
                                    <div class="post-image-wrapper">

                                        <img
                                            src="${pageContext.request.contextPath}/images/${image}"
                                            alt="投稿画像"
                                            class="post-image">

                                    </div>
                                </c:forEach>

                            </div>
                        </c:if>

                        <%-- コメント一覧 --%>
                        <div class="comment-list">

                            <h4>コメント</h4>

                            <c:choose>

                                <c:when test="${not empty post.comments}">

                                    <c:forEach var="comment" items="${post.comments}">

                                        <div class="comment-item">

                                            <%-- コメント投稿者 --%>
                                            <div class="comment-author">

                                                <img
                                                    src="${pageContext.request.contextPath}/images/${comment.authorImage}"
                                                    alt="プロフィール画像"
                                                    class="comment-author-icon">

                                                <div class="comment-author-info">

                                                    <span class="comment-author-name">
                                                        <c:out value="${comment.authorName}" />
                                                    </span>

                                                    <c:choose>
                                                        <c:when test="${comment.authorType eq 'ADMIN'}">
                                                            <span class="comment-author-label">管理者</span>
                                                        </c:when>

                                                        <c:otherwise>
                                                            <span class="comment-author-label">ユーザー</span>
                                                        </c:otherwise>
                                                    </c:choose>

                                                </div>
                                            </div>

                                            <%-- コメント本文 --%>
                                            <p class="comment-text">
                                                <c:out value="${comment.commentText}" />
                                            </p>

                                            <%-- コメント日時 --%>
                                            <small>
                                                <c:out value="${comment.commentDatetime}" />
                                            </small>

                                        </div>

                                    </c:forEach>

                                </c:when>

                                <c:otherwise>
                                    <p>まだコメントはありません。</p>
                                </c:otherwise>

                            </c:choose>

                        </div>

                        <%-- ブックマーク解除ボタン --%>
                        <div class="bookmark-form">

                            <form
                                action="${pageContext.request.contextPath}/servlet.BookmarkServlet.action"
                                method="post">

                                <input
                                    type="hidden"
                                    name="postId"
                                    value="${post.post_id}">

                                <input
                                    type="hidden"
                                    name="returnTo"
                                    value="bookmark">

                                <button
                                    type="submit"
                                    class="bookmark-btn active"
                                    aria-label="ブックマーク解除">

                                    <svg viewBox="0 0 24 28"
                                         xmlns="http://www.w3.org/2000/svg">

                                        <path d="M5 2.5H19C19.8 2.5 20.5 3.2 20.5 4V25L12 19.5L3.5 25V4C3.5 3.2 4.2 2.5 5 2.5Z"/>

                                    </svg>

                                </button>

                            </form>

                        </div>

                    </div>

                </c:forEach>

            </c:when>

            <%-- 投稿がない場合 --%>
            <c:otherwise>
                <p class="empty-bookmark">
                    ブックマークした投稿はありません。
                </p>
            </c:otherwise>

        </c:choose>

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