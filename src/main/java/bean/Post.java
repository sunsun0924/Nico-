
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

    // 投稿画像（DB保存用・カンマ区切り）
    private String post_img;

    // 投稿画像一覧
    private List<String> postImages = new ArrayList<>();

    // 旧コメントカラム
    private String comment;

    // この投稿に紐づくコメント一覧
    private List<Comment> comments = new ArrayList<>();

    // ブックマーク状態
    private boolean bookmarked;

    // ログイン中の利用者が編集できるか
    private boolean editable;

    // ========================================
    // 投稿者のプロフィール情報
    // ========================================

    // 投稿者名
    private String authorName;

    // 投稿者プロフィール画像
    private String authorImage;

    // プロフィール画像の横位置
    private int authorImagePositionX = 50;

    // プロフィール画像の縦位置
    private int authorImagePositionY = 50;

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

    // 投稿画像（DB保存用）
    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;

        // カンマ区切りの画像ファイル名をリストに変換
        postImages = new ArrayList<>();

        if (post_img != null && !post_img.trim().isEmpty()) {

            String[] images = post_img.split(",");

            for (String image : images) {

                if (image != null && !image.trim().isEmpty()) {
                    postImages.add(image.trim());
                }
            }
        }
    }

    // 投稿画像一覧
    public List<String> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<String> postImages) {

        this.postImages = postImages != null
                ? new ArrayList<>(postImages)
                : new ArrayList<>();

        // リストをカンマ区切りに変換
        if (this.postImages.isEmpty()) {
            this.post_img = null;
        } else {
            this.post_img = String.join(",", this.postImages);
        }
    }

    // 画像があるか確認
    public boolean hasPostImages() {
        return postImages != null && !postImages.isEmpty();
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

    // ========================================
    // 投稿者のプロフィール情報 Getter / Setter
    // ========================================

    // 投稿者名
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    // 投稿者プロフィール画像
    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    // プロフィール画像の横位置
    public int getAuthorImagePositionX() {
        return authorImagePositionX;
    }

    public void setAuthorImagePositionX(int authorImagePositionX) {
        this.authorImagePositionX = authorImagePositionX;
    }

    // プロフィール画像の縦位置
    public int getAuthorImagePositionY() {
        return authorImagePositionY;
    }

    public void setAuthorImagePositionY(int authorImagePositionY) {
        this.authorImagePositionY = authorImagePositionY;
    }
}
