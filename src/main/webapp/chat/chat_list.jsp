<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">

    <!-- サイドバーとチャット画面全体 -->
    <div class="page-layout">

        <!-- 左側：サイドバー -->
        <%@ include file="../sidebar.jsp" %>

        <!-- 右側：チャット一覧 -->
        <main class="main-content">

            <div class="chat-container">

                <!-- ヘッダー -->
                <header class="chat-header">
                    <!-- 通常時のタイトル -->
                    <h2 id="header-title">チャット</h2>

                    <!-- 編集モード時に出現するアクションボタン群（解除・ブロック・閉じる） -->
                    <div id="action-buttons" class="action-buttons" style="display: none;">
                        <button type="button" class="btn-action" onclick="handleAction('unblock')">解除</button>
                        <button type="button" class="btn-action block" onclick="handleAction('block')">ブロック</button>
                        <button type="button" class="btn-close-mode" onclick="toggleEditMode()">✕</button>
                    </div>

                    <!-- ヘッダーアイコン（設定ボタンで編集モード切替） -->
                    <div class="header-icons" id="header-gear">
                        <span class="icon" onclick="toggleEditMode()" title="編集モード">⚙️</span>
                    </div>
                </header>

                <!-- ユーザー検索エリア -->
                <div class="search-box">
                    <input type="text"
                           name="user_search"
                           placeholder="ユーザー名で検索">
                </div>

                <!-- チャットリスト一覧 -->
                <div class="chat-list" id="chat-list-area">

                    <!-- リスト1件目 -->
                    <div class="chat-item" onclick="onItemClick(event, this)">
                        <input type="checkbox" class="chat-checkbox" value="みさき">
                        <div class="user-icon">
                            <div class="no-image"></div>
                        </div>

                        <div class="chat-info">
                            <div class="chat-top">
                                <span class="user-name">みさき</span>
                                <span class="chat-time">10:24</span>
                            </div>

                            <div class="chat-bottom">
                                <p class="last-message">またカフェ行こうね☕</p>
                                <span class="unread-badge">1</span>
                            </div>
                        </div>
                    </div>

                    <!-- リスト2件目 -->
                    <div class="chat-item" onclick="onItemClick(event, this)">
                        <input type="checkbox" class="chat-checkbox" value="たくみ">
                        <div class="user-icon">
                            <div class="no-image"></div>
                        </div>

                        <div class="chat-info">
                            <div class="chat-top">
                                <span class="user-name">たくみ</span>
                                <span class="chat-time">昨日</span>
                            </div>

                            <div class="chat-bottom">
                                <p class="last-message">了解です！👍</p>
                                <span class="unread-badge">1</span>
                            </div>
                        </div>
                    </div>

                    <!-- リスト3件目 -->
                    <div class="chat-item" onclick="onItemClick(event, this)">
                        <input type="checkbox" class="chat-checkbox" value="ゆい">
                        <div class="user-icon">
                            <div class="no-image"></div>
                        </div>

                        <div class="chat-info">
                            <div class="chat-top">
                                <span class="user-name">ゆい</span>
                                <span class="chat-time">昨日</span>
                            </div>

                            <div class="chat-bottom">
                                <p class="last-message">スタンプを送信しました</p>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </main>

    </div>

    <!-- JavaScriptによる画面遷移なしの編集・チェック処理 -->
    <script>
        let isEditMode = false;

        // 編集モードの切り替え
        function toggleEditMode() {
            isEditMode = !isEditMode;
            const checkboxes = document.querySelectorAll('.chat-checkbox');
            const actionButtons = document.getElementById('action-buttons');
            const headerTitle = document.getElementById('header-title');
            const headerGear = document.getElementById('header-gear');

            checkboxes.forEach(cb => {
                cb.classList.toggle('show', isEditMode);
                if (!isEditMode) cb.checked = false;
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

        // リスト項目をクリックしたときの挙動
        function onItemClick(event, itemElement) {
            // チェックボックス自体をクリックした場合は重複処理を防止
            if (event.target.tagName === 'INPUT') return;

            if (isEditMode) {
                const cb = itemElement.querySelector('.chat-checkbox');
                cb.checked = !cb.checked;
            } else {
                // 通常時は個別チャットルームへ遷移する処理などをここに記述
            }
        }

        // 「解除」や「ブロック」ボタンが押されたときの処理
        function handleAction(actionType) {
            const checkedBoxes = document.querySelectorAll('.chat-checkbox:checked');
            const names = Array.from(checkedBoxes).map(cb => cb.value);

            if (names.length === 0) {
                alert('ユーザーが選択されていません。');
                return;
            }

            if (actionType === 'block') {
                alert(names.join(', ') + ' をブロックしました。');
            } else {
                alert(names.join(', ') + ' のブロックを解除しました。');
            }

            // 処理後に編集モードを終了
            toggleEditMode();
        }
    </script>

</body>
</html>