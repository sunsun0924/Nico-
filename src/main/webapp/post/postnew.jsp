
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                enctype="multipart/form-data">

                <%-- 投稿内容 --%>
                <textarea
                    id="post_text"
                    name="post_text"
                    placeholder="ここに投稿内容を入力してください..."
                    maxlength="200"
                    rows="8"
                    required
                    oninput="updateCount()"></textarea>

                <%-- 文字数表示 --%>
                <p class="count-area">
                    <span id="count">0</span>/200
                </p>

                <%-- 画像選択 --%>
                <div class="image-select-area">

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
                        onchange="previewImage(event)"
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

<script>

    // 画像の制限
    const MAX_IMAGE_COUNT = 5;
    const MAX_IMAGE_SIZE = 5 * 1024 * 1024;
    const MAX_TOTAL_SIZE = 20 * 1024 * 1024;

    // 選択された画像を管理
    let selectedImages = [];

    // 文字数カウント
    function updateCount() {

        const text = document.getElementById("post_text").value;

        document.getElementById("count").textContent = text.length;
    }

    // 画像選択
    function previewImage(event) {

        const input = event.target;
        const newFiles = Array.from(input.files);

        if (newFiles.length === 0) {
            return;
        }

        let addedCount = 0;
        let errorMessages = [];

        newFiles.forEach(function(file) {

            // 画像形式の確認
            const allowedExtensions = /\.(jpg|jpeg|png|gif|webp)$/i;

            if (!allowedExtensions.test(file.name)) {
                errorMessages.push(file.name + "：対応していない画像形式です。");
                return;
            }

            // 1枚あたりの容量確認
            if (file.size > MAX_IMAGE_SIZE) {
                errorMessages.push(file.name + "：5MBを超えています。");
                return;
            }

            // 5枚制限
            if (selectedImages.length >= MAX_IMAGE_COUNT) {
                errorMessages.push("画像は最大5枚までです。");
                return;
            }

            // 合計容量確認
            const currentTotal = selectedImages.reduce(
                (total, image) => total + image.size,
                0
            );

            if (currentTotal + file.size > MAX_TOTAL_SIZE) {
                errorMessages.push("画像の合計容量は20MBまでです。");
                return;
            }

            // 画像を追加
            selectedImages.push(file);
            addedCount++;

        });

        // 入力欄のファイル情報を更新
        updateFileInput();

        // プレビュー更新
        renderPreviews();

        // エラーメッセージ
        if (errorMessages.length > 0) {
            alert(errorMessages.join("\n"));
        }
    }

    // inputのファイル一覧を更新
    function updateFileInput() {

        const input = document.getElementById("post_image");
        const dataTransfer = new DataTransfer();

        selectedImages.forEach(function(file) {
            dataTransfer.items.add(file);
        });

        input.files = dataTransfer.files;
    }

    // プレビュー表示
    function renderPreviews() {

        const previewArea = document.getElementById("previewArea");
        const previewList = document.getElementById("previewList");
        const imageStatus = document.getElementById("imageStatus");

        // プレビューを初期化
        previewList.innerHTML = "";

        if (selectedImages.length === 0) {

            previewArea.style.display = "none";
            imageStatus.textContent = "選択されていません";

            return;
        }

        previewArea.style.display = "flex";

        imageStatus.textContent =
            selectedImages.length + "枚選択中";

        selectedImages.forEach(function(file, index) {

            const wrapper = document.createElement("div");
            wrapper.className = "preview-image-wrapper";

            const image = document.createElement("img");
            image.alt = "選択した画像 " + (index + 1);

            // プレビュー画像を読み込む
            const reader = new FileReader();

            reader.onload = function(e) {
                image.src = e.target.result;
            };

            reader.readAsDataURL(file);

            // 削除ボタン
            const removeButton = document.createElement("button");

            removeButton.type = "button";
            removeButton.className = "remove-image-btn";
            removeButton.textContent = "×";
            removeButton.setAttribute(
                "aria-label",
                "画像" + (index + 1) + "を削除"
            );

            removeButton.onclick = function() {
                removeImage(index);
            };

            wrapper.appendChild(image);
            wrapper.appendChild(removeButton);

            previewList.appendChild(wrapper);

        });
    }

    // 画像を個別に削除
    function removeImage(index) {

        selectedImages.splice(index, 1);

        // inputのファイル一覧も更新
        updateFileInput();

        // プレビュー更新
        renderPreviews();
    }

</script>

<%@ include file="../footer.jsp" %>
