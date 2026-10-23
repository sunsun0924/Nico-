<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<%-- プロフィール編集画面のCSS --%>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/profile.css">

<%-- サイドバーとメイン画面 --%>
<div class="page-layout">

    <%@ include file="../sidebar.jsp" %>

    <main class="main-content">

        <div class="profile-container">

            <%-- ヘッダー --%>
            <div class="profile-header">

                <a href="${pageContext.request.contextPath}/mypage/mypage.jsp"
                   class="back-btn"
                   aria-label="戻る">
                    <i class="fa-solid fa-chevron-left"></i>
                </a>

                <h1 class="header-title">プロフィール編集</h1>

            </div>

            <%-- プロフィール編集フォーム --%>
            <form action="${pageContext.request.contextPath}/ProfileEditServlet"
                  method="post"
                  enctype="multipart/form-data"
                  id="profile-form">

                <%-- プロフィール画像 --%>
                <section class="profile-card">

                    <h2 class="section-title">プロフィール画像</h2>

                    <div class="image-edit-area">

                        <div class="profile-image-wrapper">

                            <img id="profile-preview"
                                 src="${empty user.userImg
                                     ? pageContext.request.contextPath.concat('/images/default-profile.png')
                                     : pageContext.request.contextPath.concat('/').concat(user.userImg)}"
                                 alt="プロフィール画像">

                        </div>

                        <div class="image-controls">

                            <%-- 写真を変更 --%>
                            <label for="profile-image"
                                   class="image-btn change-btn">

                                <i class="fa-solid fa-camera"></i>
                                <span>写真を変更</span>

                            </label>

                            <input type="file"
                                   id="profile-image"
                                   name="profileImage"
                                   accept="image/*"
                                   hidden>

                            <%-- 写真を削除 --%>
                            <button type="button"
                                    class="image-btn delete-btn"
                                    id="delete-image">

                                <i class="fa-regular fa-trash-can"></i>
                                <span>削除</span>

                            </button>

                            <input type="hidden"
                                   name="deleteProfileImage"
                                   id="delete-profile-image"
                                   value="false">

                            <p class="image-note">
                                ※ 画像は正方形で、<br>
                                1 MB以内の画像を設定してください。
                            </p>

                        </div>

                    </div>

                </section>

                <%-- 名前 --%>
                <section class="profile-card name-card">

                    <h2 class="section-title">名前</h2>

                    <div class="name-input-wrapper">

                        <input type="text"
                               id="user-name"
                               name="userName"
                               class="name-input"
                               value="${user.userName}"
                               placeholder="名前を入力してください"
                               minlength="2"
                               maxlength="20"
                               required>

                        <button type="button"
                                class="clear-btn"
                                id="clear-name"
                                aria-label="名前を消去">

                            <i class="fa-solid fa-circle-xmark"></i>

                        </button>

                    </div>

                    <p class="input-note">
                        ※ 2〜20文字で入力してください。
                    </p>

                </section>

                <%-- 保存ボタン --%>
                <button type="submit" class="save-btn">
                    保存する
                </button>

            </form>

        </div>

    </main>

</div>

<%-- 画像プレビュー・入力チェック --%>
<script>
    const imageInput = document.getElementById("profile-image");
    const preview = document.getElementById("profile-preview");
    const deleteButton = document.getElementById("delete-image");
    const deleteFlag = document.getElementById("delete-profile-image");
    const nameInput = document.getElementById("user-name");
    const clearButton = document.getElementById("clear-name");
    const profileForm = document.getElementById("profile-form");

    // 初期画像
    const defaultImage =
        "${pageContext.request.contextPath}/images/default-profile.png";

    // 選択した画像をプレビュー表示
    imageInput.addEventListener("change", function () {

        const file = this.files[0];

        if (!file) {
            return;
        }

        // 画像ファイルか確認
        if (!file.type.startsWith("image/")) {
            alert("画像ファイルを選択してください。");
            this.value = "";
            return;
        }

        // 1MB以内か確認
        if (file.size > 1024 * 1024) {
            alert("画像は1MB以内にしてください。");
            this.value = "";
            return;
        }

        const reader = new FileReader();

        reader.onload = function (event) {
            preview.src = event.target.result;
            deleteFlag.value = "false";
        };

        reader.readAsDataURL(file);
    });

    // プロフィール画像を削除する指定
    deleteButton.addEventListener("click", function () {

        preview.src = defaultImage;
        imageInput.value = "";
        deleteFlag.value = "true";

    });

    // 名前を消去
    clearButton.addEventListener("click", function () {

        nameInput.value = "";
        nameInput.focus();

    });

    // 名前の入力チェック
    profileForm.addEventListener("submit", function (event) {

        const name = nameInput.value.trim();

        if (name.length < 2 || name.length > 20) {
            event.preventDefault();
            alert("名前は2〜20文字で入力してください。");
            nameInput.focus();
            return;
        }

        nameInput.value = name;

    });
</script>

<%@ include file="../footer.jsp" %>