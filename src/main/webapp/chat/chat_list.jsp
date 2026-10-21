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
                    <h2>チャット</h2>
                    <div class="header-icons">
                        <span class="icon">⚙️</span>
                    </div>
                </header>

                <!-- ユーザー検索エリア -->
                <div class="search-box">
                    <input type="text"
                           name="user_search"
                           placeholder="ユーザー名で検索">
                </div>

                <!-- チャットリスト一覧 -->
                <div class="chat-list">

                    <!-- リスト1件目 -->
                    <div class="chat-item">
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
                    <div class="chat-item">
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
                    <div class="chat-item">
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

</body>
</html>