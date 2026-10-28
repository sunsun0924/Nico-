package bean;

import java.io.Serializable;

// チャットルームのメッセージ情報を管理するBean
public class ChatRoomBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // メッセージID
    private int messageId;

    // 送信者ID
    private int senderId;

    // 受信者ID
    private int receiverId;

    // メッセージ本文
    private String message;

    // 送信日時
    private String sentAt;

    // デフォルトコンストラクタ
    public ChatRoomBean() {
    }

    // 全フィールド初期化用コンストラクタ
    public ChatRoomBean(int messageId, int senderId, int receiverId, String message, String sentAt) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = sentAt;
    }

    // メッセージIDを取得
    public int getMessageId() {
        return messageId;
    }

    // メッセージIDを設定
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    // 送信者IDを取得
    public int getSenderId() {
        return senderId;
    }

    // 送信者IDを設定
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    // 受信者IDを取得
    public int getReceiverId() {
        return receiverId;
    }

    // 受信者IDを設定
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    // メッセージ本文を取得
    public String getMessage() {
        return message;
    }

    // メッセージ本文を設定
    public void setMessage(String message) {
        this.message = message;
    }

    // 送信日時を取得
    public String getSentAt() {
        return sentAt;
    }

    // 送信日時を設定
    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}