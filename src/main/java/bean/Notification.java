package bean;

import java.time.LocalDateTime;

// 通知情報を管理するBean
public class Notification {

    private String notificationId;
    private String userId;
    private String adminId;
    private String senderId;
    private String senderAdminId;
    private String notificationType;
    private String message;
    private LocalDateTime notificationDatetime;
    private String postId;
    private String giftId;
    private String commentId;

    // コンストラクタ
    public Notification() {
    }

    // 通知IDを取得・設定
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    // ユーザーIDを取得・設定
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 管理者IDを取得・設定
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    // 送信者ID（ユーザー）を取得・設定
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    // 送信者ID（管理者）を取得・設定
    public String getSenderAdminId() {
        return senderAdminId;
    }

    public void setSenderAdminId(String senderAdminId) {
        this.senderAdminId = senderAdminId;
    }

    // 通知タイプを取得・設定
    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    // メッセージ内容を取得・設定
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // 通知日時を取得・設定
    public LocalDateTime getNotificationDatetime() {
        return notificationDatetime;
    }

    public void setNotificationDatetime(LocalDateTime notificationDatetime) {
        this.notificationDatetime = notificationDatetime;
    }

    // 投稿IDを取得・設定
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    // ギフトIDを取得・設定
    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    // コメントIDを取得・設定
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}