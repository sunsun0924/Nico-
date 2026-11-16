
/* ========================================
   企業登録画面のJavaScript
======================================== */

document.addEventListener('DOMContentLoaded', function () {

    // 戻るボタンを取得
    const backButton = document.getElementById('back-button');

    // 戻るボタンのクリック処理
    if (backButton) {

        backButton.addEventListener('click', function () {

            // 1つ前のページへ戻る
            history.back();

        });

    }

});
