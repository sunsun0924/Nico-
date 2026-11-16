
/* ========================================
   登録情報・家族誕生日画面のJavaScript
======================================== */

// 各要素を取得
const familySaveForm = document.getElementById("family-save-form");

const familyInputList = document.getElementById("family-input-list");

const addFamilyButton = document.getElementById("add-family-button");

// 今日の日付
const today = familySaveForm.dataset.today;


/* ========================================
   子どもの情報の表示・非表示
======================================== */

// 続柄の選択に応じて子どもの情報を切り替える
function changeFamilyType(select) {

    const item = select.closest(".family-input-item");

    const childInformation =
        item.querySelector(".child-information");

    const gender =
        item.querySelector(".child-gender");

    const order =
        item.querySelector(".child-order");

    if (select.value === "子供") {

        // 子どもの情報を表示
        childInformation.style.display = "flex";

        // 性別と順番を必須にする
        gender.required = true;
        order.required = true;

    } else {

        // 子どもの情報を非表示
        childInformation.style.display = "none";

        // 必須設定を解除
        gender.required = false;
        order.required = false;

        // 入力内容をリセット
        gender.value = "";
        order.value = "";

    }

}


/* ========================================
   家族入力欄の追加
======================================== */

function addFamilyRow() {

    // 元になる入力欄を取得
    const original =
        familyInputList.querySelector(".family-input-item");

    // 入力欄を複製
    const newRow = original.cloneNode(true);

    // 入力値を初期化
    newRow.querySelector(".family-type").value = "";

    newRow.querySelector(".child-gender").value = "";

    newRow.querySelector(".child-order").value = "";

    newRow.querySelector(".family-birthday").value = "";

    // 子どもの情報を非表示
    newRow.querySelector(".child-information").style.display = "none";

    // 必須設定を解除
    newRow.querySelector(".child-gender").required = false;

    newRow.querySelector(".child-order").required = false;

    // 入力欄を追加
    familyInputList.appendChild(newRow);

}


/* ========================================
   家族入力欄の削除
======================================== */

function removeFamilyRow(button) {

    // 現在の入力欄を取得
    const rows =
        familyInputList.querySelectorAll(".family-input-item");

    const row =
        button.closest(".family-input-item");

    if (rows.length > 1) {

        // 2つ以上ある場合は入力欄を削除
        row.remove();

    } else {

        // 最後の1つは削除せず、入力内容だけ消す
        row.querySelector(".family-type").value = "";

        row.querySelector(".child-gender").value = "";

        row.querySelector(".child-order").value = "";

        row.querySelector(".family-birthday").value = "";

        // 子どもの情報を非表示
        row.querySelector(".child-information").style.display = "none";

        // 必須設定を解除
        row.querySelector(".child-gender").required = false;

        row.querySelector(".child-order").required = false;

    }

}


/* ========================================
   イベント設定
======================================== */

// 続柄の変更
// 複製して追加した入力欄にも対応
familyInputList.addEventListener("change", function(event) {

    if (event.target.classList.contains("family-type")) {

        changeFamilyType(event.target);

    }

});


// 家族入力欄の追加
addFamilyButton.addEventListener("click", function() {

    addFamilyRow();

});


// 家族入力欄の削除
// 複製して追加した入力欄にも対応
familyInputList.addEventListener("click", function(event) {

    if (event.target.classList.contains("btn-remove-row")) {

        removeFamilyRow(event.target);

    }

});


/* ========================================
   登録済み家族の削除確認
======================================== */

// 削除ボタンを押した場合に確認する
familySaveForm.addEventListener("click", function(event) {

    const deleteButton = event.target.closest(".btn-delete");

    if (!deleteButton) {
        return;
    }

    const result = confirm("この家族の誕生日を削除しますか？");

    if (!result) {

        // キャンセルした場合は送信しない
        event.preventDefault();

    }

});


/* ========================================
   誕生日の入力チェック
======================================== */

// フォーム送信時のチェック
familySaveForm.addEventListener("submit", function(event) {

    // 削除ボタンを押した場合は日付チェックを行わない
    if (event.submitter &&
        event.submitter.classList.contains("btn-delete")) {

        return;

    }

    // すべての家族の誕生日を取得
    const dates =
        familyInputList.querySelectorAll(".family-birthday");

    // 未来の日付が入力されていないか確認
    for (const date of dates) {

        if (date.value !== "" && date.value > today) {

            alert("未来の誕生日は登録できません。");

            event.preventDefault();

            return;

        }

    }

});
