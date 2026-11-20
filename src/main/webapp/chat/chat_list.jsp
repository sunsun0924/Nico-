
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/chat.css">

<div class="page-layout">

    <%@ include file="../sidebar.jsp" %>

    <main class="main-content"
          data-context-path="${pageContext.request.contextPath}">

        <div class="chat-container">

            <!-- チャット画面ヘッダー -->
            <header class="chat-header">

                <h2 id="header-title">チャット</h2>

                <!-- 編集モード時に表示するボタン -->
                <div id="action-buttons"
                     class="action-buttons"
                     style="display: none;">

                    <button type="button"
                            class="btn-action"
                            data-action="unblock">
                        解除
                    </button>

                    <button type="button"
                            class="btn-action block"
                            data-action="block">
                        ブロック
                    </button>

                    <button type="button"
                            class="btn-close-mode"
                            id="close-edit-mode">
                        ✕
                    </button>

                </div>

                <!-- 通常モード時のアイコン -->
                <div class="header-icons" id="header-gear">

                    <span class="icon"
                          id="open-new-chat"
                          title="新規チャット"
                          style="cursor: pointer; margin-right: 12px; font-size: 20px;">
                        ＋
                    </span>

                    <span class="icon"
                          id="toggle-edit-mode"
                          title="編集モード"
                          style="cursor: pointer;">
                        ⚙️
                    </span>

                </div>

            </header>

            <!-- ユーザー名検索欄 -->
            <div class="search-box">

                <input type="text"
                       id="chat-search"
                       name="user_search"
                       placeholder="ユーザー名で検索"
                       autocomplete="off">

            </div>

            <!-- チャット一覧 -->
            <div class="chat-list" id="chat-list-area">

                <c:forEach var="chat" items="${chatList}">

                    <div class="chat-item">

                        <input type="checkbox"
                               class="chat-checkbox"
                               value="<c:out value='${chat.userId}'/>">

                        <div class="user-icon">
                            <div class="no-image"></div>
                        </div>

                        <div class="chat-info">

                            <div class="chat-top">

                                <span class="user-name">
                                    <c:out value="${chat.userName}" />
                                </span>

                                <span class="chat-time">
                                    <c:out value="${chat.lastTime}" />
                                </span>

                            </div>

                            <div class="chat-bottom">

                                <p class="last-message">
                                    <c:out value="${chat.lastMessage}" />
                                </p>

                                <c:if test="${chat.unreadCount > 0}">
                                    <span class="unread-badge">
                                        <c:out value="${chat.unreadCount}" />
                                    </span>
                                </c:if>

                            </div>

                        </div>

                    </div>

                </c:forEach>

            </div>

        </div>

    </main>

</div>


<!-- ========================================
     新規チャットモーダル
======================================== -->

<div id="new-chat-modal"
     style="display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.4);
            justify-content: center;
            align-items: center;
            z-index: 1000;">

    <div style="background: #fff;
                width: 420px;
                max-height: 80vh;
                border-radius: 12px;
                padding: 20px;
                overflow-y: auto;
                box-shadow: 0 4px 15px rgba(0,0,0,0.15);">

        <div style="display: flex;
                    justify-content: space-between;
                    align-items: center;
                    margin-bottom: 15px;
                    border-bottom: 1px solid #eee;
                    padding-bottom: 10px;">

            <h3 style="margin: 0; font-size: 16px; color: #333;">
                新規チャット相手を選択
            </h3>

            <button type="button"
                    id="close-new-chat"
                    style="background: none;
                           border: none;
                           font-size: 18px;
                           cursor: pointer;
                           color: #666;">
                ✕
            </button>

        </div>

        <!-- JavaScriptでユーザー一覧を表示 -->
        <div class="chat-list" id="modal-user-list-area"></div>

    </div>

</div>


<!-- JavaScriptファイルの読み込み -->
<script src="${pageContext.request.contextPath}/js/chat_list.js" defer></script>

</body>
</html>
