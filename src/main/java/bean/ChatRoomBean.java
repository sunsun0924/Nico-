package beanl;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int messageId;     
    private int senderId;      
    private int receiverId;    
    private String message;    
    private String sentAt;     

    public ChatMessage() {}

    public ChatMessage(int messageId, int senderId, int receiverId, String message, String sentAt) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = sentAt;
    }

    // Getter
    public int getMessageId() { return messageId; }
    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public String getMessage() { return message; }
    public String getSentAt() { return sentAt; }

    // Setter
    public void setMessageId(int messageId) { this.messageId = messageId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }
    public void setMessage(String message) { this.message = message; }
    public void setSentAt(String sentAt) { this.sentAt = sentAt; }
}