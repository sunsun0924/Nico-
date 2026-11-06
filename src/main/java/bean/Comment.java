
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

    // 自分のコメントかどうか
    private boolean editable;

    // コンストラクタ
    public Comment() {
    }

    // コメントIDを取得
    public String getCommentId() {
        return commentId;
    }

    // コメントIDを設定
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    // 投稿IDを取得
    public String getPostId() {
        return postId;
    }

    // 投稿IDを設定
    public void setPostId(String postId) {
        this.postId = postId;
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

    // 投稿者の種類を取得
    public String getAuthorType() {
        return authorType;
    }

    // 投稿者の種類を設定
    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    // コメント本文を取得
    public String getCommentText() {
        return commentText;
    }

    // コメント本文を設定
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    // コメント日時を取得
    public LocalDateTime getCommentDatetime() {
        return commentDatetime;
    }

    // コメント日時を設定
    public void setCommentDatetime(LocalDateTime commentDatetime) {
        this.commentDatetime = commentDatetime;
    }

    // 編集・削除可能かどうかを取得
    public boolean isEditable() {
        return editable;
    }

    // 編集・削除可能かどうかを設定
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
