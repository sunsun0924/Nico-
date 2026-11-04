
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../header.jsp" %>

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

                            <!-- コメント一覧 -->
                            <div class="comment-list">

                                <h4>コメント</h4>

                                <c:choose>
                                    <c:when test="${not empty post.comments}">

                                        <c:forEach var="comment"
                                                   items="${post.comments}">

                                            <div class="comment-item">

                                                <strong>
                                                    <c:choose>
                                                        <c:when test="${comment.authorType == 'ADMIN'}">
                                                            管理者
                                                        </c:when>
                                                        <c:otherwise>
                                                            ユーザーID：${comment.userId}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </strong>

                                                <p>${comment.commentText}</p>

                                                <small>
                                                    ${comment.commentDatetime}
                                                </small>

                                            </div>

                                        </c:forEach>

                                    </c:when>

                                    <c:otherwise>
                                        <p>まだコメントはありません。</p>
                                    </c:otherwise>
                                </c:choose>

                            </div>

                            <!-- コメント入力フォーム -->
                            <form
                                action="${pageContext.request.contextPath}/servlet.CommentServlet.action"
                                method="post"
                                class="comment-form">

                                <input type="hidden"
                                       name="postId"
                                       value="${post.post_id}">

                                <textarea
                                    name="commentText"
                                    placeholder="コメントを入力してください"
                                    maxlength="1000"
                                    required></textarea>

                                <button type="submit">
                                    コメントする
                                </button>

                            </form>

                            <!-- ブックマークフォーム -->
                            <form
                                action="${pageContext.request.contextPath}/servlet.BookmarkServlet.action"
                                method="post"
                                class="bookmark-form">

                                <input type="hidden"
                                       name="postId"
                                       value="${post.post_id}">

                                <button
                                    type="submit"
                                    class="bookmark-btn ${post.bookmarked ? 'active' : ''}"
                                    aria-label="ブックマーク">

                                    <!-- しおりアイコン -->
                                    <svg
                                        width="24"
                                        height="28"
                                        viewBox="0 0 24 28"
                                        xmlns="http://www.w3.org/2000/svg">

                                        <path
                                            d="M5 2 H19 V26 L12 21 L5 26 Z"
                                            fill="none"
                                            stroke="currentColor"
                                            stroke-width="2"
                                            stroke-linejoin="round"/>

                                    </svg>

                                </button>

                            </form>

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


<script>
    // ブックマーク送信前に現在のスクロール位置を保存
    document.querySelectorAll(".bookmark-form").forEach(form => {

        form.addEventListener("submit", function() {
            sessionStorage.setItem(
                "bookmarkScrollPosition",
                window.scrollY
            );
        });

    });

    // ページ読み込み後に保存した位置へ戻す
    window.addEventListener("load", function() {

        const savedPosition =
            sessionStorage.getItem("bookmarkScrollPosition");

        if (savedPosition !== null) {

            window.scrollTo(
                0,
                Number(savedPosition)
            );

            sessionStorage.removeItem("bookmarkScrollPosition");
        }

    });
</script>


<%@ include file="../footer.jsp" %>
