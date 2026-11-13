package bean;

import java.sql.Timestamp;

// お知らせ情報を管理するBean
public class Announcement {

    // お知らせID
    private long announcementId;

    // お知らせ本文
    private String announcementText;

    // 投稿日時
    private Timestamp announcementDatetime;

    // お知らせIDを取得
    public long getAnnouncementId() {
        return announcementId;
    }

    // お知らせIDを設定
    public void setAnnouncementId(long announcementId) {
        this.announcementId = announcementId;
    }

    // お知らせ本文を取得
    public String getAnnouncementText() {
        return announcementText;
    }

    // お知らせ本文を設定
    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    // 投稿日時を取得
    public Timestamp getAnnouncementDatetime() {
        return announcementDatetime;
    }

    // 投稿日時を設定
    public void setAnnouncementDatetime(Timestamp announcementDatetime) {
        this.announcementDatetime = announcementDatetime;
    }
}