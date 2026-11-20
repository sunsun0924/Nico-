
/* ========================================
   チャット画面のJavaScript
======================================== */

document.addEventListener('DOMContentLoaded', function () {

    // ========================================
    // 基本設定
    // ========================================

    const mainContent = document.querySelector('.main-content');

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

    // ユーザー名検索欄
    const chatSearch = document.getElementById('chat-search');


    // ========================================
    // ユーザー名検索
    // ========================================

    function searchChatUsers() {

        const keyword = chatSearch.value.trim().toLocaleLowerCase();

        const chatItems =
            chatListArea.querySelectorAll('.chat-item');

        let visibleCount = 0;

        chatItems.forEach(function (item) {

            const userNameElement =
                item.querySelector('.user-name');

            if (!userNameElement) {
                return;
            }

            const userName =
                userNameElement.textContent.trim().toLocaleLowerCase();

            // ユーザー名に検索文字が含まれているか確認
            const matches = userName.includes(keyword);

            // 該当する相手だけ表示
            item.style.display = matches ? '' : 'none';

            if (matches) {
                visibleCount++;
            }

        });

        // 検索結果がない場合のメッセージ
        let noResults =
            document.getElementById('chat-search-no-results');

        if (!noResults) {

            noResults = document.createElement('p');

            noResults.id = 'chat-search-no-results';

            noResults.textContent =
                '該当するユーザーが見つかりません。';

            noResults.style.cssText =
                'display:none; text-align:center; color:#888; padding:25px 10px;';

            chatListArea.appendChild(noResults);
        }

        if (keyword !== '' && visibleCount === 0) {

            noResults.style.display = 'block';

        } else {

            noResults.style.display = 'none';

        }

    }


    // ========================================
    // 編集モード切り替え
    // ========================================

    function toggleEditMode() {

        isEditMode = !isEditMode;

        const checkboxes =
            document.querySelectorAll('.chat-checkbox');

        checkboxes.forEach(function (checkbox) {

            checkbox.classList.toggle('show', isEditMode);

            if (!isEditMode) {
                checkbox.checked = false;
            }

        });

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

        if (event.target.tagName === 'INPUT') {
            return;
        }

        const checkbox =
            itemElement.querySelector('.chat-checkbox');

        if (!checkbox) {
            return;
        }

        if (isEditMode) {

            checkbox.checked = !checkbox.checked;

        } else {

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
            '<p style="text-align:center; color:#888; padding:20px;">読み込み中...</p>';

        fetch(contextPath + '/servlet.ChatListServlet.action?action=json')

            .then(function (response) {

                if (!response.ok) {
                    throw new Error('HTTPエラー: ' + response.status);
                }

                return response.json();

            })

            .then(function (users) {

                userListArea.innerHTML = '';

                if (!Array.isArray(users) || users.length === 0) {

                    userListArea.innerHTML =
                        '<p style="text-align:center; color:#888; padding:20px;">チャットできるユーザーがいません。</p>';

                    return;

                }

                users.forEach(function (user) {

                    const item = document.createElement('div');

                    item.className = 'chat-item';

                    item.style.cssText =
                        'cursor:pointer; padding:8px 0; border-bottom:1px solid #f5f5f5; display:flex; align-items:center;';

                    item.addEventListener('click', function () {

                        location.href = contextPath
                            + '/servlet.ChatRoomServlet.action?partnerId='
                            + encodeURIComponent(String(user.userId));

                    });

                    const icon = document.createElement('div');

                    icon.className = 'user-icon';

                    icon.style.marginRight = '12px';

                    const noImage = document.createElement('div');

                    noImage.className = 'no-image';

                    icon.appendChild(noImage);

                    const info = document.createElement('div');

                    info.className = 'chat-info';

                    const name = document.createElement('span');

                    name.className = 'user-name';

                    name.style.fontWeight = 'bold';
                    name.style.color = '#333';

                    name.textContent = user.userName + ' さん';

                    info.appendChild(name);

                    item.appendChild(icon);
                    item.appendChild(info);

                    userListArea.appendChild(item);

                });

            })

            .catch(function (error) {

                console.error('通信エラー:', error);

                userListArea.innerHTML =
                    '<p style="text-align:center; color:#e74c3c; padding:20px;">ユーザーの取得に失敗しました。</p>';

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

        if (checkedBoxes.length === 0) {

            alert('ユーザーが選択されていません。');

            return;

        }

        if (actionType === 'block') {

            alert('選択したユーザーをブロックしました。');

        } else {

            alert('選択したユーザーのブロックを解除しました。');

        }

        toggleEditMode();

    }


    // ========================================
    // イベント設定
    // ========================================

    // ユーザー名検索
    if (chatSearch) {

        chatSearch.addEventListener('input', searchChatUsers);

    }


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
