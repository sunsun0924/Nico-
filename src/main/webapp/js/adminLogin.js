
/* ========================================
   管理者ログイン画面のJavaScript
======================================== */

document.addEventListener('DOMContentLoaded', function () {

    // ========================================
    // HTML要素の取得
    // ========================================

    const password = document.getElementById('cpUserpass');

    const togglePassword = document.getElementById('togglePassword');

    const eyeSlash = document.getElementById('eyeSlash');


    // 必要な要素が存在しない場合は処理を終了
    if (!password || !togglePassword || !eyeSlash) {
        return;
    }


    // ========================================
    // パスワード表示・非表示切り替え
    // ========================================

    togglePassword.addEventListener('click', function () {

        // 現在パスワードが非表示か確認
        const isHidden = password.type === 'password';

        // 表示・非表示を切り替える
        password.type = isHidden ? 'text' : 'password';

        // 目の斜線を切り替える
        eyeSlash.style.display = isHidden ? 'none' : 'inline';

        // 読み上げ用ラベルを変更
        togglePassword.setAttribute(
            'aria-label',
            isHidden ? 'パスワードを非表示' : 'パスワードを表示'
        );

        // ボタンの状態を更新
        togglePassword.setAttribute(
            'aria-pressed',
            String(isHidden)
        );

    });

});
