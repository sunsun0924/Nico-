package bean;

public class ChatRoomBean {
    private String messageId;
    private String senderId;
    private String receiverId;
    private String message;
    private String sentAt;

    // ゲッター・セッター
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
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