package bean;

// ギフト情報を管理するBean
public class Gift {

    private String giftId;
    private String giftName;
    private String giftText;

    // ギフトID
    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    // ギフト名
    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    // ギフト説明
    public String getGiftText() {
        return giftText;
    }

    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }
}