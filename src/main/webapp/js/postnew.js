
/* ========================================
   投稿画面のJavaScript
======================================== */


/* ========================================
   画像の制限
======================================== */

// 最大画像枚数
const MAX_IMAGE_COUNT = 5;

// 1枚あたりの最大容量（5MB）
const MAX_IMAGE_SIZE = 5 * 1024 * 1024;

// 合計最大容量（20MB）
const MAX_TOTAL_SIZE = 20 * 1024 * 1024;


// 選択された画像を管理
let selectedImages = [];


/* ========================================
   HTML要素の取得
======================================== */

const postText = document.getElementById("post_text");
const count = document.getElementById("count");

const announcementCheckbox =
    document.getElementById("isAnnouncement");

const imageSelectArea =
    document.getElementById("imageSelectArea");

const previewArea =
    document.getElementById("previewArea");

const imageInput =
    document.getElementById("post_image");

const previewList =
    document.getElementById("previewList");

const imageStatus =
    document.getElementById("imageStatus");


/* ========================================
   文字数カウント
======================================== */

// 投稿本文の文字数を表示
function updateCount() {

    count.textContent = postText.value.length;

}

// 入力するたびに文字数を更新
postText.addEventListener("input", updateCount);


/* ========================================
   お知らせ投稿の画像欄切り替え
======================================== */

// 管理者の場合のみチェックボックスが存在する
if (announcementCheckbox) {

    announcementCheckbox.addEventListener("change", function() {

        if (this.checked) {

            // お知らせの場合は画像欄を非表示
            imageSelectArea.style.display = "none";

            previewArea.style.display = "none";

            // 選択済み画像をクリア
            selectedImages = [];

            // ファイル入力欄もクリア
            imageInput.value = "";

            // 選択画像の表示をリセット
            renderPreviews();

        } else {

            // 通常投稿の場合は画像欄を表示
            imageSelectArea.style.display = "block";

        }

    });

}


/* ========================================
   画像選択
======================================== */

// ファイル選択時の処理
function previewImage(event) {

    const input = event.target;

    const newFiles = Array.from(input.files);

    if (newFiles.length === 0) {
        return;
    }

    let errorMessages = [];

    newFiles.forEach(function(file) {

        // 画像形式の確認
        const allowedExtensions =
            /\.(jpg|jpeg|png|gif|webp)$/i;

        if (!allowedExtensions.test(file.name)) {

            errorMessages.push(
                file.name + "：対応していない画像形式です。"
            );

            return;
        }

        // 1枚あたりの容量確認
        if (file.size > MAX_IMAGE_SIZE) {

            errorMessages.push(
                file.name + "：5MBを超えています。"
            );

            return;
        }

        // 5枚制限
        if (selectedImages.length >= MAX_IMAGE_COUNT) {

            errorMessages.push(
                "画像は最大5枚までです。"
            );

            return;
        }

        // 合計容量確認
        const currentTotal = selectedImages.reduce(
            (total, image) => total + image.size,
            0
        );

        if (currentTotal + file.size > MAX_TOTAL_SIZE) {

            errorMessages.push(
                "画像の合計容量は20MBまでです。"
            );

            return;
        }

        // 画像を追加
        selectedImages.push(file);

    });

    // inputのファイル情報を更新
    updateFileInput();

    // プレビュー更新
    renderPreviews();

    // エラーメッセージ
    if (errorMessages.length > 0) {

        alert(errorMessages.join("\n"));

    }

}


// 画像選択時に実行
imageInput.addEventListener("change", previewImage);


/* ========================================
   inputのファイル一覧を更新
======================================== */

function updateFileInput() {

    const dataTransfer = new DataTransfer();

    selectedImages.forEach(function(file) {

        dataTransfer.items.add(file);

    });

    imageInput.files = dataTransfer.files;

}


/* ========================================
   プレビュー表示
======================================== */

function renderPreviews() {

    // プレビューを初期化
    previewList.innerHTML = "";

    // 画像が選択されていない場合
    if (selectedImages.length === 0) {

        previewArea.style.display = "none";

        imageStatus.textContent = "選択されていません";

        return;

    }

    // プレビューを表示
    previewArea.style.display = "flex";

    imageStatus.textContent =
        selectedImages.length + "枚選択中";


    // 選択画像を1枚ずつ表示
    selectedImages.forEach(function(file, index) {

        const wrapper = document.createElement("div");

        wrapper.className = "preview-image-wrapper";


        // プレビュー画像
        const image = document.createElement("img");

        image.alt = "選択した画像 " + (index + 1);


        // 画像を読み込む
        const reader = new FileReader();

        reader.onload = function(event) {

            image.src = event.target.result;

        };

        reader.readAsDataURL(file);


        // 削除ボタン
        const removeButton =
            document.createElement("button");

        removeButton.type = "button";

        removeButton.className = "remove-image-btn";

        removeButton.textContent = "×";

        removeButton.setAttribute(
            "aria-label",
            "画像" + (index + 1) + "を削除"
        );


        // 要素を追加
        wrapper.appendChild(image);

        wrapper.appendChild(removeButton);

        previewList.appendChild(wrapper);

    });

}


/* ========================================
   画像を個別に削除
======================================== */

function removeImage(index) {

    // 指定された画像を削除
    selectedImages.splice(index, 1);

    // inputのファイル一覧を更新
    updateFileInput();

    // プレビュー更新
    renderPreviews();

}


// 削除ボタンのクリックを検知
// プレビューを再作成しても動作するように設定
previewList.addEventListener("click", function(event) {

    const removeButton =
        event.target.closest(".remove-image-btn");

    if (!removeButton) {
        return;
    }

    // 押されたボタンの位置から画像番号を取得
    const wrapper = removeButton.closest(".preview-image-wrapper");

    const wrappers = Array.from(
        previewList.querySelectorAll(".preview-image-wrapper")
    );

    const index = wrappers.indexOf(wrapper);

    if (index !== -1) {

        removeImage(index);

    }

});
