package bean;

import java.io.Serializable;

// チャット一覧のユーザー情報や最新メッセージを管理するBean
public class ChatListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userName;
    private String lastMessage;
    private String lastTime;
    private int unreadCount;

    public ChatListBean() {
    }

    public ChatListBean(String userId, String userName,
            String lastMessage, String lastTime, int unreadCount) {

        this.userId = userId;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
        this.unreadCount = unreadCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}