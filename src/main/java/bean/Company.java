package bean;

// 企業情報を管理するBean
public class Company {

    // 企業ID
    private String cpId;

    // 企業名
    private String cpName;

    // コンストラクタ
    public Company() {
    }

    // 企業IDを取得
    public String getCpId() {
        return cpId;
    }

    // 企業IDを設定
    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    // 企業名を取得
    public String getCpName() {
        return cpName;
    }

    // 企業名を設定
    public void setCpName(String cpName) {
        this.cpName = cpName;
    }
}