
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/postnew.css">

<div class="page-layout">

    <%@ include file="../sidebar.jsp" %>

    <main class="main-content">

        <div class="home-header">

            <h1>投稿</h1>

            <%-- 登録結果・エラーメッセージ --%>
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>

            <%-- 投稿フォーム --%>
            <form
                action="${pageContext.request.contextPath}/servlet.PostInsertServlet.action"
                method="post"
                enctype="multipart/form-data"
                id="post-form">

                <%-- 投稿内容 --%>
                <textarea
                    id="post_text"
                    name="post_text"
                    placeholder="ここに投稿内容を入力してください..."
                    maxlength="200"
                    rows="8"
                    required></textarea>

                <%-- 文字数表示 --%>
                <p class="count-area">
                    <span id="count">0</span>/200
                </p>

                <%-- 管理者だけに表示するお知らせ設定 --%>
                <c:if test="${not empty sessionScope.admin}">

                    <div class="announcement-option">

                        <label>
                            <input
                                type="checkbox"
                                id="isAnnouncement"
                                name="isAnnouncement"
                                value="true">

                            お知らせとして投稿する
                        </label>

                        <p>
                            チェックを入れると、お知らせとして登録されます。
                            お知らせには画像を添付できません。
                        </p>

                    </div>

                </c:if>

                <%-- 画像選択 --%>
                <div class="image-select-area" id="imageSelectArea">

                    <label for="post_image" class="image-select-label">
                        <span>▧</span>
                        画像を追加する
                    </label>

                    <input
                        type="file"
                        id="post_image"
                        name="post_image"
                        accept=".jpg,.jpeg,.png,.gif,.webp,image/jpeg,image/png,image/gif,image/webp"
                        multiple
                        hidden>

                    <p class="image-status" id="imageStatus">
                        選択されていません
                    </p>

                    <p class="image-limit">
                        最大5枚・1枚5MBまで（合計20MBまで）
                    </p>

                </div>

                <%-- 選択画像のプレビュー --%>
                <div class="image-preview-area"
                     id="previewArea"
                     style="display: none;">

                    <div class="preview-image-list" id="previewList">
                    </div>

                    <div class="preview-text">
                        <strong>選択した画像</strong>
                        <span>（プレビュー）</span>
                    </div>

                </div>

                <%-- 投稿ボタン --%>
                <input
                    type="submit"
                    value="投稿する ➤"
                    class="reserve-btn">

            </form>

            <%-- 投稿一覧に戻る --%>
            <a class="back-link"
               href="${pageContext.request.contextPath}/servlet.PostServlet.action">

                投稿一覧に戻る

            </a>

        </div>

    </main>

</div>

<%-- 外部JavaScript --%>
<script src="${pageContext.request.contextPath}/js/postnew.js" defer></script>

<%@ include file="../footer.jsp" %>
