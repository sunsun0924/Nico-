package bean;

// ブックマーク情報を管理するBean
public class Bookmark {

    // ブックマークID
    private String bookmarkId;

    // ユーザーID
    private String userId;

    // 投稿ID
    private String postId;

    // コンストラクタ
    public Bookmark() {
    }

    // ブックマークIDを取得
    public String getBookmarkId() {
        return bookmarkId;
    }

    // ブックマークIDを設定
    public void setBookmarkId(String bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    // ユーザーIDを取得
    public String getUserId() {
        return userId;
    }

    // ユーザーIDを設定
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 投稿IDを取得
    public String getPostId() {
        return postId;
    }

    // 投稿IDを設定
    public void setPostId(String postId) {
        this.postId = postId;
    }
}