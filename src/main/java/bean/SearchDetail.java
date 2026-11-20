package bean;

// 検索詳細情報を管理するBean
public class SearchDetail {

    // 検索詳細ID
    private String searchDetailId;

    // 検索情報ID
    private String searchId;

    // 送信者の種類（user / assistant）
    private String senderType;

    // メッセージ内容
    private String searchDetailText;

    // 送信日時
    private String sentAt;

    // コンストラクタ
    public SearchDetail() {
    }

    // 検索詳細IDを取得
    public String getSearchDetailId() {
        return searchDetailId;
    }

    // 検索詳細IDを設定
    public void setSearchDetailId(String searchDetailId) {
        this.searchDetailId = searchDetailId;
    }

    // 検索情報IDを取得
    public String getSearchId() {
        return searchId;
    }

    // 検索情報IDを設定
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    // 送信者の種類を取得
    public String getSenderType() {
        return senderType;
    }

    // 送信者の種類を設定
    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    // メッセージ内容を取得
    public String getSearchDetailText() {
        return searchDetailText;
    }

    // メッセージ内容を設定
    public void setSearchDetailText(String searchDetailText) {
        this.searchDetailText = searchDetailText;
    }

    // 送信日時を取得
    public String getSentAt() {
        return sentAt;
    }

    // 送信日時を設定
    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}