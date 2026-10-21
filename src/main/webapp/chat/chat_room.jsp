<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>チャットルーム</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">
</head>
<body>

    <div class="room-container">
        
        <header class="room-header">
            <a href="chat_list.jsp" class="back-btn">&larr;</a>
            <div class="header-title">
                <h2>みさき</h2>
                <span class="online-status">オンライン ●</span>
            </div>
            <div class="header-menu">...</div>
        </header>

        <div class="message-area">
            
            <div class="message-row other">
                <div class="no-image"></div>
                <div class="message-content">
                    <div class="bubble">
                        今日はやっとカフェでゆっくりできた☕<br>
                        やっぱり好きな時間を過ごすのって最高だな～
                    </div>
                    <span class="message-time">10:24</span>
                </div>
            </div>

            <div class="message-row self">
                <div class="message-content">
                    <span class="read-status">既読<br>10:27</span>
                    <div class="bubble">
                        いいね～！どこのカフェ行ったの？😉
                    </div>
                </div>
            </div>

            <div class="message-row other">
                <div class="no-image"></div>
                <div class="message-content">
                    <div class="bubble">
                        駅の近くのカフェだよ！<br>
                        ラテがめっちゃおいしかった☕また行きたいな～
                    </div>
                    <span class="message-time">10:32</span>
                </div>
            </div>

            <div class="message-row self">
                <div class="message-content">
                    <span class="read-status">既読<br>10:35</span>
                    <div class="bubble">
                        いいなぁ～！今度一緒に行こっか？😉
                    </div>
                </div>
            </div>

            <div class="message-row other">
                <div class="no-image"></div>
                <div class="message-content">
                    <div class="bubble">
                        ぜひぜひ！また予定合わせようね🌷
                    </div>
                    <span class="message-time">10:36</span>
                </div>
            </div>

        </div>

        <div class="input-area">
            <button class="plus-btn">+</button>
            <input type="text" name="message_text" placeholder="メッセージを入力...">
            <button class="send-btn">&#10148;</button>
        </div>

    </div>

</body>
</html>