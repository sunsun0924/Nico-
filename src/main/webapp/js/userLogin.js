// ========================================
// パスワードの表示・非表示切り替え
// ========================================

document.addEventListener("DOMContentLoaded", function () {

    const password = document.getElementById("userPass");
    const togglePassword = document.getElementById("togglePassword");
    const eyeSlash = document.getElementById("eyeSlash");

    // 必要な要素がそろっているか確認
    if (!password || !togglePassword || !eyeSlash) {
        console.error("パスワード表示切り替え用のHTMLが見つかりません。");
        return;
    }

    // ボタンをクリックしたときの処理
    togglePassword.addEventListener("click", function () {

        const isHidden = password.type === "password";

        // パスワードの表示・非表示を切り替える
        password.type = isHidden ? "text" : "password";

        // 斜線の表示・非表示を切り替える
        eyeSlash.style.display = isHidden ? "none" : "inline";

        // アクセシビリティ設定を更新
        togglePassword.setAttribute(
            "aria-label",
            isHidden ? "パスワードを非表示" : "パスワードを表示"
        );

        togglePassword.setAttribute(
            "aria-pressed",
            String(isHidden)
        );

    });

});