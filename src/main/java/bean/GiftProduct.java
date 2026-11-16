package bean;

// ギフト商品情報を管理するBean

public class GiftProduct {

    private String giftProductId;
    private String giftName;
    private String giftText;
    private String giftImage;
    private String giftCategory;
    private Integer targetAgeMin;
    private Integer targetAgeMax;

    // ギフト商品ID
    public String getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId(String giftProductId) {
        this.giftProductId = giftProductId;
    }
    
    // ギフト名
    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    // ギフト本文
    public String getGiftText() {
        return giftText;
    }

    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }

    // 商品画像
    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    // 商品カテゴリ
    public String getGiftCategory() {
        return giftCategory;
    }

    public void setGiftCategory(String giftCategory) {
        this.giftCategory = giftCategory;
    }

    // おすすめの対象年齢の下限
    public Integer getTargetAgeMin() {
        return targetAgeMin;
    }

    public void setTargetAgeMin(Integer targetAgeMin) {
        this.targetAgeMin = targetAgeMin;
    }

    // // おすすめの対象年齢の上限
    public Integer getTargetAgeMax() {
        return targetAgeMax;
    }

    public void setTargetAgeMax(Integer targetAgeMax) {
        this.targetAgeMax = targetAgeMax;
    }
}