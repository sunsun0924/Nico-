package bean;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {

    private String userId;
    private String userPass;
    private String userAddress;
    private String userNumber;
    private String userMail;
    private Date userBirthday;
    private String userName;
    private String userImg;

    // プロフィール画像の表示位置（初期値は中央）
    private int userImgPositionX = 50;
    private int userImgPositionY = 50;

    // コンストラクタ
    public User() {
    }

    // ユーザーID
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // パスワード
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    // 住所
    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    // 電話番号
    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    // メールアドレス
    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    // 生年月日
    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    // 名前
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // 画像
    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    // 画像の横方向の表示位置
    public int getUserImgPositionX() {
        return userImgPositionX;
    }

    public void setUserImgPositionX(int userImgPositionX) {
        this.userImgPositionX = userImgPositionX;
    }

    // 画像の縦方向の表示位置
    public int getUserImgPositionY() {
        return userImgPositionY;
    }

    public void setUserImgPositionY(int userImgPositionY) {
        this.userImgPositionY = userImgPositionY;
    }
}