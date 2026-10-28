package bean;

import java.io.Serializable;

// チャット一覧のユーザー情報や最新メッセージを管理するBean
public class ChatListBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // ユーザーID
    private int userId;

    // ユーザー名
    private String userName;

    // 最後のメッセージ本文
    private String lastMessage;

    // 最後のメッセージの送信日時（または時間）
    private String lastTime;

    // 未読件数
    private int unreadCount;

    // デフォルトコンストラクタ
    public ChatListBean() {
    }

    // 全フィールド初期化用コンストラクタ
    public ChatListBean(int userId, String userName, String lastMessage, String lastTime, int unreadCount) {
        this.userId = userId;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
        this.unreadCount = unreadCount;
    }

    // ユーザーIDを取得
    public int getUserId() {
        return userId;
    }

    // ユーザーIDを設定
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // ユーザー名を取得
    public String getUserName() {
        return userName;
    }

    // ユーザー名を設定
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // 最後のメッセージを取得
    public String getLastMessage() {
        return lastMessage;
    }

    // 最後のメッセージを設定
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    // 最後のメッセージの時間を取得
    public String getLastTime() {
        return lastTime;
    }

    // 最後のメッセージの時間設定
    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    // 未読件数を取得
    public int getUnreadCount() {
        return unreadCount;
    }

    // 未読件数を設定
    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}