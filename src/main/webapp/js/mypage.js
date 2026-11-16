// ========================================
// マイページ
// ログアウト確認ポップアップ
// ========================================

document.addEventListener("DOMContentLoaded", function () {

    // ========================================
    // HTML要素の取得
    // ========================================

    const logoutModal = document.getElementById("logout-modal");

    const openButtons =
        document.querySelectorAll(".js-open-logout-modal");

    const closeButton =
        document.getElementById("closeLogoutModalButton");

    // ========================================
    // 必要な要素が存在するか確認
    // ========================================

    if (!logoutModal || !closeButton || openButtons.length === 0) {
        console.error("ログアウトポップアップのHTML要素が見つかりません。");
        return;
    }

    // ========================================
    // ログアウト確認ポップアップを表示
    // ========================================

    function openLogoutModal() {
        logoutModal.style.display = "flex";
    }

    // ========================================
    // ログアウト確認ポップアップを閉じる
    // ========================================

    function closeLogoutModal() {
        logoutModal.style.display = "none";
    }

    // ========================================
    // ログアウトボタンのクリックイベント
    // ========================================

    openButtons.forEach(function (button) {

        button.addEventListener("click", function (event) {

            // リンク先への移動を防止
            event.preventDefault();

            // ポップアップを表示
            openLogoutModal();

        });

    });

    // ========================================
    // キャンセルボタンのクリックイベント
    // ========================================

    closeButton.addEventListener("click", function () {
        closeLogoutModal();
    });

    // ========================================
    // ポップアップの背景をクリックした場合
    // ========================================

    logoutModal.addEventListener("click", function (event) {

        // ポップアップの外側をクリックした場合だけ閉じる
        if (event.target === logoutModal) {
            closeLogoutModal();
        }

    });

});