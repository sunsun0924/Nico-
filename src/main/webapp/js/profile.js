
/* ========================================
   プロフィール編集画面のJavaScript
======================================== */

// 各要素を取得
const imageInput = document.getElementById("profile-image");
const preview = document.getElementById("profile-preview");
const deleteButton = document.getElementById("delete-image");
const deleteFlag = document.getElementById("delete-profile-image");

const nameInput = document.getElementById("user-name");
const clearButton = document.getElementById("clear-name");

const profileForm = document.getElementById("profile-form");

const positionX = document.getElementById("position-x");
const positionY = document.getElementById("position-y");

// 初期画像のパスを取得
const defaultImage = profileForm.dataset.defaultImage;


/* ========================================
   画像の表示位置調整
======================================== */

// 画像表示位置をプレビューに反映
function updateImagePosition() {

    preview.style.objectPosition =
        positionX.value + "% " + positionY.value + "%";

}

// 初期位置を反映
updateImagePosition();

// 横方向の位置を変更
positionX.addEventListener("input", function () {

    updateImagePosition();

});

// 縦方向の位置を変更
positionY.addEventListener("input", function () {

    updateImagePosition();

});


/* ========================================
   プロフィール画像の変更
======================================== */

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

    // 画像を読み込む
    const reader = new FileReader();

    reader.onload = function (event) {

        preview.src = event.target.result;

        // 削除フラグを解除
        deleteFlag.value = "false";

        // 新しい画像は中央から調整
        positionX.value = 50;
        positionY.value = 50;

        updateImagePosition();

    };

    reader.readAsDataURL(file);

});


/* ========================================
   プロフィール画像の削除
======================================== */

deleteButton.addEventListener("click", function () {

    // デフォルト画像に変更
    preview.src = defaultImage;

    // 選択中の画像を解除
    imageInput.value = "";

    // 削除フラグを設定
    deleteFlag.value = "true";

    // 表示位置を中央に戻す
    positionX.value = 50;
    positionY.value = 50;

    updateImagePosition();

});


/* ========================================
   名前の消去
======================================== */

clearButton.addEventListener("click", function () {

    nameInput.value = "";

    nameInput.focus();

});


/* ========================================
   名前の入力チェック
======================================== */

profileForm.addEventListener("submit", function (event) {

    const name = nameInput.value.trim();

    // 2〜20文字か確認
    if (name.length < 2 || name.length > 20) {

        event.preventDefault();

        alert("名前は2〜20文字で入力してください。");

        nameInput.focus();

        return;
    }

    // 前後の空白を削除
    nameInput.value = name;

});
