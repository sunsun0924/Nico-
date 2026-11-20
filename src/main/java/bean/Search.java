package bean;

// 検索情報を管理するBean
public class Search {

    // 検索情報ID
    private String searchId;

    // 検索タイトル
    private String searchTitle;

    // ユーザーID
    private String userId;

    // 検索作成日時
    private String searchCreatedAt;

    // 検索更新日時
    private String searchUpdatedAt;

    // コンストラクタ
    public Search() {
    }

    // 検索情報IDを取得
    public String getSearchId() {
        return searchId;
    }

    // 検索情報IDを設定
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    // 検索タイトルを取得
    public String getSearchTitle() {
        return searchTitle;
    }

    // 検索タイトルを設定
    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    // ユーザーIDを取得
    public String getUserId() {
        return userId;
    }

    // ユーザーIDを設定
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 検索作成日時を取得
    public String getSearchCreatedAt() {
        return searchCreatedAt;
    }

    // 検索作成日時を設定
    public void setSearchCreatedAt(String searchCreatedAt) {
        this.searchCreatedAt = searchCreatedAt;
    }

    // 検索更新日時を取得
    public String getSearchUpdatedAt() {
        return searchUpdatedAt;
    }

    // 検索更新日時を設定
    public void setSearchUpdatedAt(String searchUpdatedAt) {
        this.searchUpdatedAt = searchUpdatedAt;
    }
}
