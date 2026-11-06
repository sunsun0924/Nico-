package bean;

import java.time.LocalDateTime;

public class Notification {

    // 通知ID
    private String notificationId;

    // ユーザーID（通知先）
    private String userId;

    // 管理者ID（通知先）
    private String adminId;

    // 送信者ID（ユーザー）
    private String senderId;

    // 送信者ID（管理者）
    private String senderAdminId;

    // 通知タイプ
    private String notificationType;

    // メッセージ内容
    private String message;

    // 通知日時
    private LocalDateTime notificationDatetime;

    // 投稿ID
    private String postId;

    // ギフトID
    private String giftId;

    // コメントID
    private String commentId;


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


    // 管理者IDを取得
    public String getAdminId() {
        return adminId;
    }

    // 管理者IDを設定
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    // 送信者ID（ユーザー）を取得
    public String getSenderId() {
        return senderId;
    }

    // 送信者ID（ユーザー）を設定
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    // 送信者ID（管理者）を取得
    public String getSenderAdminId() {
        return senderAdminId;
    }

    // 送信者ID（管理者）を設定
    public void setSenderAdminId(String senderAdminId) {
        this.senderAdminId = senderAdminId;
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
    public void setNotificationDatetime(LocalDateTime notificationDatetime) {
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


    // コメントIDを取得
    public String getCommentId() {
        return commentId;
    }

    // コメントIDを設定
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}