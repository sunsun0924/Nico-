<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">

    <div class="page-layout">

        <%@ include file="../sidebar.jsp" %>

        <main class="main-content">

            <div class="chat-container">

                <header class="chat-header">
                    <h2 id="header-title">チャット</h2>

                    <div id="action-buttons" class="action-buttons" style="display: none;">
                        <button type="button" class="btn-action" onclick="handleAction('unblock')">解除</button>
                        <button type="button" class="btn-action block" onclick="handleAction('block')">ブロック</button>
                        <button type="button" class="btn-close-mode" onclick="toggleEditMode()">✕</button>
                    </div>

                    <div class="header-icons" id="header-gear">
                        <span class="icon" onclick="openNewChatModal()" title="新規チャット" style="cursor: pointer; margin-right: 12px; font-size: 20px;">＋</span>
                        <span class="icon" onclick="toggleEditMode()" title="編集モード" style="cursor: pointer;">⚙️</span>
                    </div>
                </header>

                <div class="search-box">
                    <input type="text" name="user_search" placeholder="ユーザー名で検索">
                </div>

                <div class="chat-list" id="chat-list-area">
                    <c:forEach var="chat" items="${chatList}">
                        <div class="chat-item" onclick="onItemClick(event, this)">
                            <input type="checkbox" class="chat-checkbox" value="${chat.userId}">
                            <div class="user-icon">
                                <div class="no-image"></div>
                            </div>

                            <div class="chat-info">
                                <div class="chat-top">
                                    <span class="user-name"><c:out value="${chat.userName}" /></span>
                                    <span class="chat-time"><c:out value="${chat.lastTime}" /></span>
                                </div>

                                <div class="chat-bottom">
                                    <p class="last-message"><c:out value="${chat.lastMessage}" /></p>
                                    <c:if test="${chat.unreadCount > 0}">
                                        <span class="unread-badge"><c:out value="${chat.unreadCount}" /></span>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>

        </main>

    </div>

    <div id="new-chat-modal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.4); justify-content: center; align-items: center; z-index: 1000;">
        <div style="background: #fff; width: 420px; max-height: 80vh; border-radius: 12px; padding: 20px; overflow-y: auto; box-shadow: 0 4px 15px rgba(0,0,0,0.15);">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; border-bottom: 1px solid #eee; padding-bottom: 10px;">
                <h3 style="margin: 0; font-size: 16px; color: #333;">新規チャット相手を選択</h3>
                <button type="button" onclick="closeNewChatModal()" style="background: none; border: none; font-size: 18px; cursor: pointer; color: #666;">✕</button>
            </div>

            <div class="chat-list" id="modal-user-list-area"></div>
        </div>
    </div>

    <script>
        let isEditMode = false;

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

        function onItemClick(event, itemElement) {
            if (event.target.tagName === 'INPUT') return;

            if (isEditMode) {
                const cb = itemElement.querySelector('.chat-checkbox');
                cb.checked = !cb.checked;
            } else {
                const cb = itemElement.querySelector('.chat-checkbox');
                if (cb) {
                    location.href = '${pageContext.request.contextPath}/chat-room?partnerId=' + cb.value;
                }
            }
        }

        function openNewChatModal() {
            const modal = document.getElementById('new-chat-modal');
            const userListArea = document.getElementById('modal-user-list-area');
            
            modal.style.display = 'flex';
            userListArea.innerHTML = '<p style="text-align: center; color: #888; padding: 20px;">読み込み中...</p>';

            // 絶対パス（contextPath付き）で ChatListServlet に ?action=json を送る
            fetch('${pageContext.request.contextPath}/chat-list?action=json')
                .then(response => response.json())
                .then(users => {
                    userListArea.innerHTML = '';

                    if (users.length === 0) {
                        userListArea.innerHTML = '<p style="text-align: center; color: #888; padding: 20px;">チャットできるユーザーがいません。</p>';
                        return;
                    }

                    users.forEach(user => {
                        const item = document.createElement('div');
                        item.className = 'chat-item';
                        item.style.cssText = 'cursor: pointer; padding: 8px 0; border-bottom: 1px solid #f5f5f5; display: flex; align-items: center;';
                        item.onclick = function() {
                            location.href = '${pageContext.request.contextPath}/chat-room?partnerId=' + user.userId;
                        };

                        item.innerHTML = `
                            <div class="user-icon" style="margin-right: 12px;"><div class="no-image"></div></div>
                            <div class="chat-info">
                                <span class="user-name" style="font-weight: bold; color: #333;">` + user.userName + ` さん</span>
                            </div>
                        `;
                        userListArea.appendChild(item);
                    });
                })
                .catch(error => {
                    console.error('通信エラー:', error);
                    userListArea.innerHTML = '<p style="text-align: center; color: #e74c3c; padding: 20px;">ユーザーの取得に失敗しました。</p>';
                });
        }

        function closeNewChatModal() {
            document.getElementById('new-chat-modal').style.display = 'none';
        }

        function handleAction(actionType) {
            const checkedBoxes = document.querySelectorAll('.chat-checkbox:checked');
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
    </script>

</body>
</html>