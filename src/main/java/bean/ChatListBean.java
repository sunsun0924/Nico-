package bean;

import java.io.Serializable;

public class ChatListBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;           
    private String userName;      
    private String lastMessage;   
    private String lastTime;     
    private int unreadCount;    

    public ChatListBean() {}

    public ChatListBean(int userId, String userName, String lastMessage, String lastTime, int unreadCount) {
        this.userId = userId;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
        this.unreadCount = unreadCount;
    }

    // Getter
    public int getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getLastMessage() { return lastMessage; }
    public String getLastTime() { return lastTime; }
    public int getUnreadCount() { return unreadCount; }

    // Setter
    public void setUserId(int userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public void setLastTime(String lastTime) { this.lastTime = lastTime; }
    public void setUnreadCount(int unreadCount) { this.unreadCount = unreadCount; }
}