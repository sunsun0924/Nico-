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

    // 管理者名
    private String cpUsername;

    // プロフィール画像
    private String cpUserimg;

    // 画像表示位置
    private int cpUserimgPositionX = 50;
    private int cpUserimgPositionY = 50;

    // コンストラクタ
    public Admin() {
    }

    public String getCpUserid() {
        return cpUserid;
    }

    public void setCpUserid(String cpUserid) {
        this.cpUserid = cpUserid;
    }

    public String getCpUserpass() {
        return cpUserpass;
    }

    public void setCpUserpass(String cpUserpass) {
        this.cpUserpass = cpUserpass;
    }

    public String getCpUsermail() {
        return cpUsermail;
    }

    public void setCpUsermail(String cpUsermail) {
        this.cpUsermail = cpUsermail;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpUsername() {
        return cpUsername;
    }

    public void setCpUsername(String cpUsername) {
        this.cpUsername = cpUsername;
    }

    public String getCpUserimg() {
        return cpUserimg;
    }

    public void setCpUserimg(String cpUserimg) {
        this.cpUserimg = cpUserimg;
    }

    public int getCpUserimgPositionX() {
        return cpUserimgPositionX;
    }

    public void setCpUserimgPositionX(int cpUserimgPositionX) {
        this.cpUserimgPositionX = cpUserimgPositionX;
    }

    public int getCpUserimgPositionY() {
        return cpUserimgPositionY;
    }

    public void setCpUserimgPositionY(int cpUserimgPositionY) {
        this.cpUserimgPositionY = cpUserimgPositionY;
    }
}