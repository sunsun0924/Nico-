
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/post.css">

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/post-delete.css">

<%-- サイドバー --%>
<%@ include file="../sidebar.jsp" %>

<main class="home-header">

    <h1>投稿</h1>

    <%-- 新規投稿 --%>
    <a href="${pageContext.request.contextPath}/post/postnew.jsp"
       class="reserve-btn">
        投稿する
    </a>

    <hr>

    <h3>投稿一覧</h3>

    <%-- 処理結果メッセージ --%>
    <c:if test="${not empty message}">
        <div class="success-message">
            <c:out value="${message}" />
        </div>
    </c:if>

    <%-- 投稿がない場合 --%>
    <c:if test="${empty postList}">
        <p>投稿はまだありません。</p>
    </c:if>

    <%-- 投稿一覧 --%>
    <c:forEach var="post" items="${postList}">

        <div class="post-item">

            <%-- 投稿者 --%>
            <c:choose>
                <c:when test="${post.author_type eq 'ADMIN'}">
                    <strong>管理者</strong>
                </c:when>
                <c:otherwise>
                    <strong>
                        <c:out value="${post.user_id}" />
                    </strong>
                </c:otherwise>
            </c:choose>

            <%-- 投稿の三点ボタン --%>
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

            <%-- 投稿日時 --%>
            <p>
                <c:out value="${post.post_datetime}" />
            </p>

            <%-- 投稿本文 --%>
            <p>
                <c:out value="${post.post_text}" />
            </p>

            <%-- 投稿画像 --%>
            <c:if test="${not empty post.post_img}">
                <img src="${pageContext.request.contextPath}/images/${post.post_img}"
                     alt="投稿画像">
            </c:if>

            <%-- コメント一覧 --%>
            <div class="comment-list">

                <h4>コメント</h4>

                <c:choose>

                    <c:when test="${not empty post.comments}">

                        <c:forEach var="comment" items="${post.comments}">

                            <div class="comment-item">

                                <%-- コメント投稿者 --%>
                                <c:choose>
                                    <c:when test="${comment.authorType eq 'ADMIN'}">
                                        <strong>管理者</strong>
                                    </c:when>
                                    <c:otherwise>
                                        <strong>
                                            <c:out value="${comment.userId}" />
                                        </strong>
                                    </c:otherwise>
                                </c:choose>

                                <%-- コメントの三点ボタン --%>
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
                                <p>
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
                        <p>コメントはありません。</p>
                    </c:otherwise>

                </c:choose>

            </div>

            <%-- コメント入力フォーム --%>
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

            <%-- ブックマーク --%>
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


<script>

/* ========================================
   三点メニューの開閉
======================================== */

document.querySelectorAll(".menu-button").forEach(button => {

    button.addEventListener("click", function(event) {

        event.stopPropagation();

        const dropdown = this.nextElementSibling;

        document.querySelectorAll(".menu-dropdown").forEach(menu => {
            if (menu !== dropdown) {
                menu.classList.remove("show");
            }
        });

        dropdown.classList.toggle("show");

    });

});


/* ========================================
   メニュー外をクリックしたとき
======================================== */

document.addEventListener("click", function() {

    document.querySelectorAll(".menu-dropdown").forEach(menu => {
        menu.classList.remove("show");
    });

});


/* ========================================
   削除処理
======================================== */

const deleteModal = document.getElementById("deleteModal");

const deleteConfirmButton =
    document.getElementById("deleteConfirmButton");

const deleteCancelButton =
    document.getElementById("deleteCancelButton");

let deleteType = "";
let deleteId = "";


/* 削除ボタンを押したとき */

document.querySelectorAll(".delete-button").forEach(button => {

    button.addEventListener("click", function(event) {

        event.stopPropagation();

        deleteType = this.dataset.deleteType;

        if (deleteType === "post") {
            deleteId = this.dataset.postId;
        } else {
            deleteId = this.dataset.commentId;
        }

        document.querySelectorAll(".menu-dropdown").forEach(menu => {
            menu.classList.remove("show");
        });

        deleteModal.classList.add("show");

    });

});


/* 削除を確定 */

deleteConfirmButton.addEventListener("click", function() {

    if (!deleteType || !deleteId) {
        return;
    }

    const form = document.createElement("form");

    form.method = "post";

    const contextPath = "${pageContext.request.contextPath}";

    const idInput = document.createElement("input");

    idInput.type = "hidden";

    if (deleteType === "post") {

        form.action =
            contextPath + "/servlet.PostDeleteServlet.action";

        idInput.name = "postId";

    } else {

        form.action =
            contextPath + "/servlet.CommentDeleteServlet.action";

        idInput.name = "commentId";

    }

    idInput.value = deleteId;

    form.appendChild(idInput);

    document.body.appendChild(form);

    form.submit();

});


/* 削除キャンセル */

deleteCancelButton.addEventListener("click", function() {

    deleteModal.classList.remove("show");

    deleteType = "";
    deleteId = "";

});


/* ========================================
   編集処理
======================================== */

document.querySelectorAll(".edit-button").forEach(button => {

    button.addEventListener("click", function(event) {

        event.stopPropagation();

        const editType = this.dataset.editType;

        const editText = this.dataset.editText || "";

        document.querySelectorAll(".menu-dropdown").forEach(menu => {
            menu.classList.remove("show");
        });

        // 投稿編集
        if (editType === "post") {

            document.getElementById("editPostId").value =
                this.dataset.postId;

            document.getElementById("editPostText").value =
                editText;

            document.getElementById("postEditModal")
                .classList.add("show");

        }

        // コメント編集
        if (editType === "comment") {

            document.getElementById("editCommentId").value =
                this.dataset.commentId;

            document.getElementById("editCommentText").value =
                editText;

            document.getElementById("commentEditModal")
                .classList.add("show");

        }

    });

});


/* ========================================
   編集モーダルを閉じる
======================================== */

document.querySelectorAll("[data-close-modal]").forEach(button => {

    button.addEventListener("click", function() {

        const modalId = this.dataset.closeModal;

        document.getElementById(modalId)
            .classList.remove("show");

    });

});


/* ========================================
   モーダル背景をクリックして閉じる
======================================== */

document.querySelectorAll(".edit-modal").forEach(modal => {

    modal.addEventListener("click", function(event) {

        if (event.target === this) {
            this.classList.remove("show");
        }

    });

});


deleteModal.addEventListener("click", function(event) {

    if (event.target === this) {
        this.classList.remove("show");
    }

});

</script>

<%@ include file="../footer.jsp" %>
