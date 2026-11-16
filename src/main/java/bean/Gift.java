package bean;

import java.sql.Date;

// ギフト履歴情報を管理するBean

public class Gift {
    private String giftId;
    private String userId;
    private String familyBirthdayId;
    private String giftProductId;
    private String giftName;
    private String giftText;
    private String giftImage;
    private Date selectedDate;

    // 家族の続柄
    private String relation;

    // ギフトID
    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    // ユーザーID
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 家族誕生日ID
    public String getFamilyBirthdayId() {
        return familyBirthdayId;
    }

    public void setFamilyBirthdayId(String familyBirthdayId) {
        this.familyBirthdayId = familyBirthdayId;
    }

    // ギフト商品ID
    public String getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(String giftProductId) {
        this.giftProductId = giftProductId;
    }

    // ギフト名（選択時点の情報）
    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    // ギフト本文（選択時点の情報）
    public String getGiftText() {
        return giftText;
    }

    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }

    // 商品画像（選択時点の情報）
    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    // ギフトを選択した日
    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    // 家族の続柄
    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}