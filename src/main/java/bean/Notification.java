package bean;

import java.time.LocalDateTime;

// 通知情報を管理するBean
public class Notification {

    // 通知ID
    private String notificationId;

    // ユーザーID（通知を受け取るユーザー）
    private String userId;

    // 送信者ID
    private String senderId;

    // 通知タイプ（comment / gift）
    private String notificationType;

    // メッセージ内容
    private String message;

    // 通知日時
    private LocalDateTime notificationDatetime;

    // 投稿ID
    private String postId;

    // ギフトID
    private String giftId;

    // コンストラクタ
    public Notification() {
    }

    // 通知IDを取得
    public String getNotificationId() {
        return notificationId;
    }

    // 通知IDを設定
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    // ユーザーIDを取得
    public String getUserId() {
        return userId;
    }

    // ユーザーIDを設定
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 送信者IDを取得
    public String getSenderId() {
        return senderId;
    }

    // 送信者IDを設定
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    // 通知タイプを取得
    public String getNotificationType() {
        return notificationType;
    }

    // 通知タイプを設定
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    // メッセージ内容を取得
    public String getMessage() {
        return message;
    }

    // メッセージ内容を設定
    public void setMessage(String message) {
        this.message = message;
    }

    // 通知日時を取得
    public LocalDateTime getNotificationDatetime() {
        return notificationDatetime;
    }

    // 通知日時を設定
    public void setNotificationDatetime(
            LocalDateTime notificationDatetime) {
        this.notificationDatetime = notificationDatetime;
    }

    // 投稿IDを取得
    public String getPostId() {
        return postId;
    }

    // 投稿IDを設定
    public void setPostId(String postId) {
        this.postId = postId;
    }

    // ギフトIDを取得
    public String getGiftId() {
        return giftId;
    }

    // ギフトIDを設定
    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }
}