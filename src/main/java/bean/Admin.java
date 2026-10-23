package bean;

// 管理者情報を管理するBean
public class Admin {

    // 管理者ID
    private String cpUserid;

    // パスワード
    private String cpUserpass;

    // メールアドレス
    private String cpUsermail;

    // 企業ID
    private String cpId;

    // コンストラクタ
    public Admin() {
    }

    // 管理者IDを取得
    public String getCpUserid() {
        return cpUserid;
    }

    // 管理者IDを設定
    public void setCpUserid(String cpUserid) {
        this.cpUserid = cpUserid;
    }

    // パスワードを取得
    public String getCpUserpass() {
        return cpUserpass;
    }

    // パスワードを設定
    public void setCpUserpass(String cpUserpass) {
        this.cpUserpass = cpUserpass;
    }

    // メールアドレスを取得
    public String getCpUsermail() {
        return cpUsermail;
    }

    // メールアドレスを設定
    public void setCpUsermail(String cpUsermail) {
        this.cpUsermail = cpUsermail;
    }

    // 企業IDを取得
    public String getCpId() {
        return cpId;
    }

    // 企業IDを設定
    public void setCpId(String cpId) {
        this.cpId = cpId;
    }
}