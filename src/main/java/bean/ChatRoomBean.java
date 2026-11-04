package bean;

import java.io.Serializable;

// チャットルームのメッセージ情報を管理するBean
public class ChatRoomBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // メッセージID
    private int messageId;

    // 送信者ID
    private String senderId;

    // 受信者ID
    private String receiverId;

    // メッセージ本文
    private String message;

    // 送信日時
    private String sentAt;

    // デフォルトコンストラクタ
    public ChatRoomBean() {
    }

    // 全フィールド初期化用コンストラクタ
    public ChatRoomBean(int messageId, String senderId,
            String receiverId, String message, String sentAt) {

        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = sentAt;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}