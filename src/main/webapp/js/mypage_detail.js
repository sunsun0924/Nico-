// ========================================
// マイページ設定画面
// ブロックリストの表示・非表示
// ========================================

document.addEventListener("DOMContentLoaded", function () {

    // ========================================
    // HTML要素の取得
    // ========================================

    const settingsMenuSection =
        document.getElementById("settings-menu-section");

    const blockListSection =
        document.getElementById("block-list-section");

    const headerTitleText =
        document.getElementById("header-title-text");

    const headerTitleIcon =
        document.getElementById("header-title-icon");

    const mypageBackArea =
        document.getElementById("mypageBackArea");

    const showBlockListButton =
        document.getElementById("showBlockListButton");

    const hideBlockListButton =
        document.getElementById("hideBlockListButton");

    // ========================================
    // 必要な要素が存在するか確認
    // ========================================

    if (
        !settingsMenuSection ||
        !blockListSection ||
        !headerTitleText ||
        !headerTitleIcon ||
        !mypageBackArea ||
        !showBlockListButton ||
        !hideBlockListButton
    ) {
        console.error("設定画面のHTML要素が見つかりません。");
        return;
    }

    // ========================================
    // ブロックリストを表示する
    // ========================================

    function showBlockList(event) {

        event.preventDefault();

        // 設定メニューを非表示
        settingsMenuSection.style.display = "none";

        // ブロックリストを表示
        blockListSection.style.display = "block";

        // タイトルを変更
        headerTitleText.innerText = "ブロックリスト";

        // アイコンを変更
        headerTitleIcon.className = "fa-solid fa-user-slash";

        // マイページに戻るボタンを非表示
        mypageBackArea.style.display = "none";
    }

    // ========================================
    // 設定メニューに戻る
    // ========================================

    function hideBlockList() {

        // ブロックリストを非表示
        blockListSection.style.display = "none";

        // 設定メニューを表示
        settingsMenuSection.style.display = "block";

        // タイトルを元に戻す
        headerTitleText.innerText = "設定";

        // アイコンを元に戻す
        headerTitleIcon.className = "fa-solid fa-gear";

        // マイページに戻るボタンを再表示
        mypageBackArea.style.display = "flex";
    }

    // ========================================
    // ブロック解除
    // ========================================

    function unblockUser(userName) {

        alert(userName + " のブロックを解除しました");

    }

    // ========================================
    // イベント設定
    // ========================================

    // ブロックリストを開く
    showBlockListButton.addEventListener("click", showBlockList);

    // 設定メニューに戻る
    hideBlockListButton.addEventListener("click", hideBlockList);

    // ブロック解除ボタン
    const unblockButtons =
        document.querySelectorAll(".btn-unblock");

    unblockButtons.forEach(function (button) {

        button.addEventListener("click", function () {

            const userName = button.dataset.userName;

            unblockUser(userName);

        });

    });

});