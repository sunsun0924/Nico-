
/* ========================================
   管理者新規登録画面
   パスワード表示・非表示切り替え
======================================== */

// HTML要素を取得
const password = document.getElementById("password");

const togglePassword =
    document.getElementById("togglePassword");

const eyeOpen =
    document.getElementById("eyeOpen");

const eyeClosed =
    document.getElementById("eyeClosed");


// パスワード表示切り替え
togglePassword.addEventListener("click", function() {

    // 現在パスワードが非表示か確認
    const isHidden = password.type === "password";

    // パスワードの表示・非表示を切り替え
    password.type = isHidden ? "text" : "password";

    // 目のアイコンを切り替え
    eyeOpen.style.display = isHidden ? "inline" : "none";

    eyeClosed.style.display = isHidden ? "none" : "inline";

    // 読み上げ用ラベルを変更
    togglePassword.setAttribute(
        "aria-label",
        isHidden ? "パスワードを非表示" : "パスワードを表示"
    );

    // ボタンの状態を更新
    togglePassword.setAttribute(
        "aria-pressed",
        String(isHidden)
    );

});
