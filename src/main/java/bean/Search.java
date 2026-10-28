
package bean;

// 検索情報を管理するBean

public class Search {

    // 検索情報ID
    private String searchId;

    // 検索内容
    private String searchText;

    // ユーザーID
    private String userId;

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

    // 検索内容を取得
    public String getSearchText() {
        return searchText;
    }

    // 検索内容を設定
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    // ユーザーIDを取得
    public String getUserId() {
        return userId;
    }

    // ユーザーIDを設定
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
