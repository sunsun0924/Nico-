
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/post.css">

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/post-delete.css">

<%@ include file="../sidebar.jsp" %>

<main class="home-header"
      data-context-path="${pageContext.request.contextPath}">

    <%-- ========================================
         お知らせ一覧・スライド
    ======================================== --%>

    <c:if test="${not empty announcementList}">

        <section class="announcement-section">

            <h2 class="announcement-heading">お知らせ</h2>

            <%-- スライド表示エリア --%>
            <div class="announcement-slider">

                <button type="button"
                        class="announcement-arrow announcement-prev"
                        aria-label="前のお知らせ">
                    &#10094;
                </button>

                <div class="announcement-viewport">

                    <div class="announcement-track">

                        <c:forEach var="announcement"
                                   items="${announcementList}">

                            <div class="announcement-item">

                                <%-- 管理者用三点メニュー --%>
                                <c:if test="${not empty sessionScope.admin}">

                                    <div class="announcement-menu">

                                        <button type="button"
                                                class="menu-button"
                                                aria-label="お知らせメニュー">
                                            ⋯
                                        </button>

                                        <div class="menu-dropdown">

                                            <button type="button"
                                                    class="edit-button"
                                                    data-edit-type="announcement"
                                                    data-announcement-id="${announcement.announcementId}"
                                                    data-edit-text="${fn:escapeXml(announcement.announcementText)}">
                                                編集
                                            </button>

                                            <button type="button"
                                                    class="delete-button"
                                                    data-delete-type="announcement"
                                                    data-announcement-id="${announcement.announcementId}">
                                                削除
                                            </button>

                                        </div>

                                    </div>

                                </c:if>

                                <%-- 本文と日時 --%>
                                <div class="announcement-content">

                                    <div class="announcement-text">
                                        <c:out value="${announcement.announcementText}" />
                                    </div>

                                    <div class="announcement-date">
                                        <fmt:formatDate
                                            value="${announcement.announcementDatetime}"
                                            pattern="yyyy年M月d日" />
                                    </div>

                                </div>

                            </div>

                        </c:forEach>

                    </div>

                </div>

                <button type="button"
                        class="announcement-arrow announcement-next"
                        aria-label="次のお知らせ">
                    &#10095;
                </button>

            </div>

        </section>

    </c:if>


    <%-- ========================================
         投稿エリア
    ======================================== --%>

    <h1>投稿</h1>

    <a href="${pageContext.request.contextPath}/post/postnew.jsp"
       class="reserve-btn">
        投稿する
    </a>

    <hr>

    <h3>投稿一覧</h3>

    <%-- 投稿がない場合 --%>
    <c:if test="${empty postList}">
        <p>投稿はまだありません。</p>
    </c:if>


    <%-- ========================================
         投稿一覧
    ======================================== --%>

    <c:forEach var="post" items="${postList}">

        <div class="post-item">

            <%-- 投稿の三点メニュー --%>
            <c:if test="${post.editable}">

                <div class="post-menu">

                    <button type="button"
                            class="menu-button"
                            aria-label="投稿メニュー">
                        ⋯
                    </button>

                    <div class="menu-dropdown">

                        <button type="button"
                                class="edit-button"
                                data-edit-type="post"
                                data-post-id="${post.post_id}"
                                data-edit-text="${fn:escapeXml(post.post_text)}">
                            編集
                        </button>

                        <button type="button"
                                class="delete-button"
                                data-delete-type="post"
                                data-post-id="${post.post_id}">
                            削除
                        </button>

                    </div>

                </div>

            </c:if>


            <%-- ========================================
                 投稿者情報
            ======================================== --%>

            <div class="post-author">

                <%-- 投稿者プロフィール画像 --%>
                <c:choose>

                    <%-- デフォルト画像の場合 --%>
                    <c:when test="${empty post.authorImage or post.authorImage eq 'default-profile.png'}">

                        <img
                            class="post-author-icon"
                            src="${pageContext.request.contextPath}/images/default-profile.png"
                            alt="プロフィール画像"
                            style="object-position: ${post.authorImagePositionX}% ${post.authorImagePositionY}%"
                        >

                    </c:when>

                    <%-- 外部フォルダに保存した画像の場合 --%>
                    <c:otherwise>

                        <img
                            class="post-author-icon"
                            src="${pageContext.request.contextPath}/profile-image/${post.authorImage}"
                            alt="プロフィール画像"
                            style="object-position: ${post.authorImagePositionX}% ${post.authorImagePositionY}%"
                            onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/default-profile.png';"
                        >

                    </c:otherwise>

                </c:choose>


                <div class="post-author-info">

                    <strong class="post-author-name">
                        <c:out value="${post.authorName}" />
                    </strong>

                    <c:if test="${post.author_type eq 'ADMIN'}">
                        <span class="post-author-label">
                            管理者
                        </span>
                    </c:if>

                </div>

            </div>


            <%-- ========================================
                 投稿本文
            ======================================== --%>

            <div class="post-text">
                <c:out value="${post.post_text}" />
            </div>


            <%-- ========================================
                 投稿画像
            ======================================== --%>

            <c:if test="${not empty post.postImages}">

                <div class="post-image-list">

                    <c:forEach var="image" items="${post.postImages}">

                        <div class="post-image-wrapper">

                            <img
                                class="post-image"
                                src="${pageContext.request.contextPath}/images/${image}"
                                alt="投稿画像"
                                loading="lazy"
                            >

                        </div>

                    </c:forEach>

                </div>

            </c:if>


            <%-- ========================================
                 コメント一覧
            ======================================== --%>

            <div class="comment-list">

                <h4>コメント</h4>

                <c:choose>

                    <c:when test="${not empty post.comments}">

                        <c:forEach var="comment" items="${post.comments}">

                            <div class="comment-item">

                                <%-- コメント投稿者情報 --%>
                                <div class="comment-author">

                                    <%-- コメント投稿者プロフィール画像 --%>
                                    <c:choose>

                                        <%-- デフォルト画像の場合 --%>
                                        <c:when test="${empty comment.authorImage or comment.authorImage eq 'default-profile.png'}">

                                            <img
                                                class="comment-author-icon"
                                                src="${pageContext.request.contextPath}/images/default-profile.png"
                                                alt="プロフィール画像"
                                            >

                                        </c:when>

                                        <%-- 外部フォルダに保存した画像の場合 --%>
                                        <c:otherwise>

                                            <img
                                                class="comment-author-icon"
                                                src="${pageContext.request.contextPath}/profile-image/${comment.authorImage}"
                                                alt="プロフィール画像"
                                                onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/default-profile.png';"
                                            >

                                        </c:otherwise>

                                    </c:choose>


                                    <div class="comment-author-info">

                                        <strong class="comment-author-name">
                                            <c:out value="${comment.authorName}" />
                                        </strong>

                                        <c:if test="${comment.authorType eq 'ADMIN'}">
                                            <span class="comment-author-label">
                                                管理者
                                            </span>
                                        </c:if>

                                    </div>

                                </div>


                                <%-- コメント三点メニュー --%>
                                <c:if test="${comment.editable}">

                                    <div class="comment-menu">

                                        <button type="button"
                                                class="menu-button"
                                                aria-label="コメントメニュー">
                                            ⋯
                                        </button>

                                        <div class="menu-dropdown">

                                            <button type="button"
                                                    class="edit-button"
                                                    data-edit-type="comment"
                                                    data-comment-id="${comment.commentId}"
                                                    data-edit-text="${fn:escapeXml(comment.commentText)}">
                                                編集
                                            </button>

                                            <button type="button"
                                                    class="delete-button"
                                                    data-delete-type="comment"
                                                    data-comment-id="${comment.commentId}">
                                                削除
                                            </button>

                                        </div>

                                    </div>

                                </c:if>


                                <%-- コメント本文 --%>
                                <p class="comment-text">
                                    <c:out value="${comment.commentText}" />
                                </p>


                                <%-- コメント日時 --%>
                                <small class="comment-date">

                                    <c:if test="${not empty comment.commentDatetime}">

                                        <c:out value="${fn:replace(fn:substring(comment.commentDatetime, 0, 19), 'T', ' ')}" />

                                    </c:if>

                                </small>

                            </div>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <p>コメントはありません。</p>

                    </c:otherwise>

                </c:choose>

            </div>


            <%-- ========================================
                 コメント入力フォーム
            ======================================== --%>

            <form action="${pageContext.request.contextPath}/servlet.CommentServlet.action"
                  method="post"
                  class="comment-form">

                <input type="hidden"
                       name="postId"
                       value="${post.post_id}">

                <textarea name="commentText"
                          placeholder="コメントを入力してください"
                          required></textarea>

                <button type="submit">
                    コメントする
                </button>

            </form>


            <%-- ========================================
                 ブックマーク
            ======================================== --%>

            <form action="${pageContext.request.contextPath}/servlet.BookmarkServlet.action"
                  method="post"
                  class="bookmark-form">

                <input type="hidden"
                       name="postId"
                       value="${post.post_id}">

                <button type="submit"
                        class="bookmark-btn ${post.bookmarked ? 'active' : ''}"
                        aria-label="ブックマーク">

                    <svg viewBox="0 0 24 28"
                         xmlns="http://www.w3.org/2000/svg">

                        <path d="M5 2 H19 V26 L12 21 L5 26 Z" />

                    </svg>

                </button>

            </form>

        </div>

    </c:forEach>

</main>


<%-- ========================================
     削除確認モーダル
======================================== --%>

<div id="deleteModal" class="delete-modal">

    <div class="delete-modal-content">

        <h3>削除確認</h3>

        <p>本当に削除しますか？</p>

        <div class="delete-modal-buttons">

            <button type="button"
                    id="deleteConfirmButton"
                    class="delete-confirm-button">
                削除
            </button>

            <button type="button"
                    id="deleteCancelButton"
                    class="delete-cancel-button">
                キャンセル
            </button>

        </div>

    </div>

</div>


<%-- ========================================
     投稿編集モーダル
======================================== --%>

<div id="postEditModal" class="edit-modal">

    <div class="edit-modal-content">

        <h3>投稿を編集</h3>

        <form action="${pageContext.request.contextPath}/servlet.PostEditServlet.action"
              method="post">

            <input type="hidden"
                   name="postId"
                   id="editPostId">

            <textarea name="postText"
                      id="editPostText"
                      required></textarea>

            <div class="edit-modal-buttons">

                <button type="submit" class="edit-save-button">
                    保存
                </button>

                <button type="button"
                        class="edit-cancel-button"
                        data-close-modal="postEditModal">
                    キャンセル
                </button>

            </div>

        </form>

    </div>

</div>


<%-- ========================================
     コメント編集モーダル
======================================== --%>

<div id="commentEditModal" class="edit-modal">

    <div class="edit-modal-content">

        <h3>コメントを編集</h3>

        <form action="${pageContext.request.contextPath}/servlet.CommentEditServlet.action"
              method="post">

            <input type="hidden"
                   name="commentId"
                   id="editCommentId">

            <textarea name="commentText"
                      id="editCommentText"
                      required></textarea>

            <div class="edit-modal-buttons">

                <button type="submit" class="edit-save-button">
                    保存
                </button>

                <button type="button"
                        class="edit-cancel-button"
                        data-close-modal="commentEditModal">
                    キャンセル
                </button>

            </div>

        </form>

    </div>

</div>


<%-- ========================================
     お知らせ編集モーダル
======================================== --%>

<div id="announcementEditModal" class="edit-modal">

    <div class="edit-modal-content">

        <h3>お知らせを編集</h3>

        <form action="${pageContext.request.contextPath}/servlet.AnnouncementEditServlet.action"
              method="post">

            <input type="hidden"
                   name="announcementId"
                   id="editAnnouncementId">

            <textarea name="announcementText"
                      id="editAnnouncementText"
                      maxlength="200"
                      required></textarea>

            <div class="edit-modal-buttons">

                <button type="submit" class="edit-save-button">
                    保存
                </button>

                <button type="button"
                        class="edit-cancel-button"
                        data-close-modal="announcementEditModal">
                    キャンセル
                </button>

            </div>

        </form>

    </div>

</div>


<%-- 外部JavaScript --%>

<script src="${pageContext.request.contextPath}/js/post.js"></script>

<%@ include file="../footer.jsp" %>
