
/* ========================================
   ユーザー新規登録画面
   パスワード表示・非表示切り替え
======================================== */

// HTML要素を取得
const password = document.getElementById("userPass");

const togglePassword =
    document.getElementById("togglePassword");

const eyeSlash =
    document.getElementById("eyeSlash");


// 必要な要素がそろっているか確認
if (!password || !togglePassword || !eyeSlash) {

    console.error(
        "パスワード表示切り替え用のHTMLが見つかりません。"
    );

} else {

    // パスワード表示切り替え
    togglePassword.addEventListener("click", function() {

        // 現在パスワードが非表示か確認
        const isHidden = password.type === "password";

        // パスワードの表示・非表示を切り替える
        password.type = isHidden ? "text" : "password";

        // 目の斜線を切り替える
        eyeSlash.style.display = isHidden ? "none" : "inline";

        // ボタンの説明を変更する
        togglePassword.setAttribute(
            "aria-label",
            isHidden ? "パスワードを非表示" : "パスワードを表示"
        );

        // ボタンの状態を更新する
        togglePassword.setAttribute(
            "aria-pressed",
            String(isHidden)
        );

    });

}
