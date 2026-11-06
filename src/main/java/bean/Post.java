
package bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 投稿情報を管理するBean
public class Post implements Serializable {

    // ユーザーID
    private String user_id;

    // 管理者ID
    private String admin_id;

    // 投稿者の種類（USER / ADMIN）
    private String author_type;

    // 投稿ID
    private String post_id;

    // 投稿日時
    private LocalDateTime post_datetime;

    // 投稿本文
    private String post_text;

    // 投稿画像
    private String post_img;

    // 旧コメントカラム
    private String comment;

    // この投稿に紐づくコメント一覧
    private List<Comment> comments = new ArrayList<>();

    // ブックマーク状態
    private boolean bookmarked;

    // ログイン中の利用者が編集できるか
    private boolean editable;

    // コンストラクタ
    public Post() {
    }

    // ユーザーID
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    // 管理者ID
    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    // 投稿者の種類
    public String getAuthor_type() {
        return author_type;
    }

    public void setAuthor_type(String author_type) {
        this.author_type = author_type;
    }

    // 投稿ID
    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    // 投稿日時
    public LocalDateTime getPost_datetime() {
        return post_datetime;
    }

    public void setPost_datetime(LocalDateTime post_datetime) {
        this.post_datetime = post_datetime;
    }

    // 投稿本文
    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    // 投稿画像
    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    // 旧コメントカラム
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // コメント一覧
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // ブックマーク状態
    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    // 編集・削除可能か
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
