/* ========================================
   チャット画面のJavaScript
======================================== */

document.addEventListener('DOMContentLoaded', function () {

    // ========================================
    // 基本設定
    // ========================================

    const mainContent = document.querySelector('.main-content');

    // JSPからコンテキストパスを取得
    const contextPath = mainContent
        ? mainContent.dataset.contextPath
        : '';

    // 編集モードの状態
    let isEditMode = false;


    // ========================================
    // HTML要素の取得
    // ========================================

    const headerTitle = document.getElementById('header-title');

    const headerGear = document.getElementById('header-gear');

    const actionButtons = document.getElementById('action-buttons');

    const chatListArea = document.getElementById('chat-list-area');

    const newChatModal = document.getElementById('new-chat-modal');

    const userListArea = document.getElementById('modal-user-list-area');


    // ========================================
    // 編集モード切り替え
    // ========================================

    function toggleEditMode() {

        isEditMode = !isEditMode;

        const checkboxes = document.querySelectorAll('.chat-checkbox');

        // チェックボックスの表示・非表示
        checkboxes.forEach(function (checkbox) {

            checkbox.classList.toggle('show', isEditMode);

            // 編集モードを終了したら選択解除
            if (!isEditMode) {
                checkbox.checked = false;
            }

        });

        // ヘッダー表示切り替え
        if (isEditMode) {

            headerTitle.style.display = 'none';

            headerGear.style.display = 'none';

            actionButtons.style.display = 'flex';

        } else {

            headerTitle.style.display = 'block';

            headerGear.style.display = 'flex';

            actionButtons.style.display = 'none';

        }

    }


    // ========================================
    // チャット項目クリック
    // ========================================

    function onItemClick(event, itemElement) {

        // チェックボックス自体のクリックでは何もしない
        if (event.target.tagName === 'INPUT') {
            return;
        }

        const checkbox = itemElement.querySelector('.chat-checkbox');

        if (!checkbox) {
            return;
        }

        // 編集モードの場合は選択状態を切り替える
        if (isEditMode) {

            checkbox.checked = !checkbox.checked;

        } else {

            // 通常モードの場合はチャットルームへ移動（FrontController経由の.actionに修正済み）
            location.href = contextPath
                + '/servlet.ChatRoomServlet.action?partnerId='
                + encodeURIComponent(checkbox.value);

        }

    }


    // ========================================
    // 新規チャットモーダルを開く
    // ========================================

    function openNewChatModal() {

        newChatModal.style.display = 'flex';

        userListArea.innerHTML =
            '<p style="text-align: center; color: #888; padding: 20px;">読み込み中...</p>';

        // ChatListServletへ接続（FrontController経由の.actionに修正済み）
        fetch(contextPath + '/servlet.ChatListServlet.action?action=json')

            .then(function (response) {

                if (!response.ok) {

                    throw new Error('HTTPエラー: ' + response.status);

                }

                return response.json();

            })

            .then(function (users) {

                userListArea.innerHTML = '';

                // ユーザーがいない場合
                if (!Array.isArray(users) || users.length === 0) {

                    userListArea.innerHTML =
                        '<p style="text-align: center; color: #888; padding: 20px;">チャットできるユーザーがいません。</p>';

                    return;

                }

                // ユーザー一覧を作成
                users.forEach(function (user) {

                    const item = document.createElement('div');

                    item.className = 'chat-item';

                    item.style.cssText =
                        'cursor: pointer; padding: 8px 0; border-bottom: 1px solid #f5f5f5; display: flex; align-items: center;';

                    // ユーザーをクリックしたらチャットルームへ移動（FrontController経由の.actionに修正済み）
                    item.addEventListener('click', function () {

                        location.href = contextPath
                            + '/servlet.ChatRoomServlet.action?partnerId='
                            + encodeURIComponent(String(user.userId));

                    });

                    // アイコン
                    const icon = document.createElement('div');

                    icon.className = 'user-icon';

                    icon.style.marginRight = '12px';

                    const noImage = document.createElement('div');

                    noImage.className = 'no-image';

                    icon.appendChild(noImage);


                    // ユーザー情報
                    const info = document.createElement('div');

                    info.className = 'chat-info';


                    // ユーザー名
                    const name = document.createElement('span');

                    name.className = 'user-name';

                    name.style.fontWeight = 'bold';

                    name.style.color = '#333';

                    name.textContent = user.userName + ' さん';


                    // 要素を組み立てる
                    info.appendChild(name);

                    item.appendChild(icon);

                    item.appendChild(info);

                    userListArea.appendChild(item);

                });

            })

            .catch(function (error) {

                console.error('通信エラー:', error);

                userListArea.innerHTML =
                    '<p style="text-align: center; color: #e74c3c; padding: 20px;">ユーザーの取得に失敗しました。</p>';

            });

    }


    // ========================================
    // 新規チャットモーダルを閉じる
    // ========================================

    function closeNewChatModal() {

        newChatModal.style.display = 'none';

    }


    // ========================================
    // ブロック・解除
    // ========================================

    function handleAction(actionType) {

        const checkedBoxes =
            document.querySelectorAll('.chat-checkbox:checked');

        // ユーザーが選択されていない場合
        if (checkedBoxes.length === 0) {

            alert('ユーザーが選択されていません。');

            return;

        }

        // 現在の処理内容
        if (actionType === 'block') {

            alert('選択したユーザーをブロックしました。');

        } else {

            alert('選択したユーザーのブロックを解除しました。');

        }

        // 編集モードを終了
        toggleEditMode();

    }


    // ========================================
    // イベント設定
    // ========================================

    // 編集モード切り替え
    document.getElementById('toggle-edit-mode')
        .addEventListener('click', toggleEditMode);

    document.getElementById('close-edit-mode')
        .addEventListener('click', toggleEditMode);


    // 新規チャット
    document.getElementById('open-new-chat')
        .addEventListener('click', openNewChatModal);

    document.getElementById('close-new-chat')
        .addEventListener('click', closeNewChatModal);


    // チャット一覧のクリック
    chatListArea.addEventListener('click', function (event) {

        const item = event.target.closest('.chat-item');

        if (!item || !chatListArea.contains(item)) {
            return;
        }

        onItemClick(event, item);

    });


    // ブロックボタン・解除ボタン
    actionButtons.addEventListener('click', function (event) {

        const button = event.target.closest('[data-action]');

        if (!button) {
            return;
        }

        handleAction(button.dataset.action);

    });


    // モーダルの外側をクリックしたら閉じる
    newChatModal.addEventListener('click', function (event) {

        if (event.target === newChatModal) {

            closeNewChatModal();

        }

    });

});