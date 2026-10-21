package bean;

import java.time.LocalDateTime;


public class Post implements java.io.Serializable {
    // 登録番号 privateにすることで外部から直接アクセスできないようにする
	
	// ユーザーID
    private String user_id;
    // 投稿ID
    private String post_id;
    // 投稿時間
    private LocalDateTime post_datetime;
    // 本文
    private String post_text;
    // 画像
    private String post_img;
    // コメント
    private String comment;
    
    // getterメソッド（値を取得するメソッド）登録番号を取得する
    // ユーザーIDを取得する
    public String getUser_id() {
        return user_id;
    }
    // 投稿IDを取得する
    public String getPost_id() {
        return post_id;
    }
    // 投稿時間を取得する
    public LocalDateTime getPost_datetime() {
        return post_datetime;
    }
    // 本文を取得する
    public String getPost_text() {
        return post_text;
    }
    // 画像を取得する
    public String getPost_img() {
        return post_img;
    }
    // コメントを取得する
    public String getComment() {
        return comment;
    }

    // setterメソッド（値を設定するメソッド） 登録番号を設定する
    // ユーザーIDを設定する
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }   
    // 投稿IDを設定する
    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
    // 投稿時間を設定する
    public void setPost_datetime(LocalDateTime post_datetime) {
        this.post_datetime = post_datetime;
    }
    // 本文を設定する
    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }
    // 画像を設定する
    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }
    // コメントを設定する
    public void setComment(String comment) {
        this.comment = comment;
    }
 
}