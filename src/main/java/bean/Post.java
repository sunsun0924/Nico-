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

    // ログイン中の利用者がブックマークしているか
    private boolean bookmarked;

    // コンストラクタ
    public Post() {
    }

    // ユーザーIDを取得
    public String getUser_id() {
        return user_id;
    }

    // ユーザーIDを設定
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    // 管理者IDを取得
    public String getAdmin_id() {
        return admin_id;
    }

    // 管理者IDを設定
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    // 投稿者の種類を取得
    public String getAuthor_type() {
        return author_type;
    }

    // 投稿者の種類を設定
    public void setAuthor_type(String author_type) {
        this.author_type = author_type;
    }

    // 投稿IDを取得
    public String getPost_id() {
        return post_id;
    }

    // 投稿IDを設定
    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    // 投稿日時を取得
    public LocalDateTime getPost_datetime() {
        return post_datetime;
    }

    // 投稿日時を設定
    public void setPost_datetime(LocalDateTime post_datetime) {
        this.post_datetime = post_datetime;
    }

    // 投稿本文を取得
    public String getPost_text() {
        return post_text;
    }

    // 投稿本文を設定
    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    // 投稿画像を取得
    public String getPost_img() {
        return post_img;
    }

    // 投稿画像を設定
    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    // 旧コメントカラムを取得
    public String getComment() {
        return comment;
    }

    // 旧コメントカラムを設定
    public void setComment(String comment) {
        this.comment = comment;
    }

    // コメント一覧を取得
    public List<Comment> getComments() {
        return comments;
    }

    // コメント一覧を設定
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // ブックマーク状態を取得
    public boolean isBookmarked() {
        return bookmarked;
    }

    // ブックマーク状態を設定
    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}