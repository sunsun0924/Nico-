
package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

// コメント情報を管理するBean
public class Comment implements Serializable {

    // コメントID
    private String commentId;

    // 投稿ID
    private String postId;

    // ユーザーID
    private String userId;

    // 管理者ID
    private String adminId;

    // 投稿者の種類（USER / ADMIN）
    private String authorType;

    // コメント本文
    private String commentText;

    // コメント日時
    private LocalDateTime commentDatetime;

    // コメントした人の名前
    private String authorName;

    // コメントした人のプロフィール画像
    private String authorImage;

    // 自分のコメントかどうか
    private boolean editable;

    // コンストラクタ
    public Comment() {
    }

    // コメントID
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    // 投稿ID
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    // ユーザーID
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 管理者ID
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    // 投稿者の種類
    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    // コメント本文
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    // コメント日時
    public LocalDateTime getCommentDatetime() {
        return commentDatetime;
    }

    public void setCommentDatetime(LocalDateTime commentDatetime) {
        this.commentDatetime = commentDatetime;
    }

    // コメントした人の名前
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    // コメントした人のプロフィール画像
    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    // 編集・削除可能かどうか
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
