package bean;

// ブロック情報を管理するBean

public class BlockList {

    // ブロックID
    private String blockId;

    // ブロックしたユーザーID
    private String blockerUserId;

    // ブロックされたユーザーID
    private String blockedUserId;

    // ブロックした相手のユーザー名（画面表示用）
    private String blockedUserName;

    // コンストラクタ
    public BlockList() {

    }

    // ブロックIDを取得
    public String getBlockId() {
        return blockId;
    }

    // ブロックIDを設定
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    // ブロックしたユーザーIDを取得
    public String getBlockerUserId() {
        return blockerUserId;
    }

    // ブロックしたユーザーIDを設定
    public void setBlockerUserId(String blockerUserId) {
        this.blockerUserId = blockerUserId;
    }

    // ブロックされたユーザーIDを取得
    public String getBlockedUserId() {
        return blockedUserId;
    }

    // ブロックされたユーザーIDを設定
    public void setBlockedUserId(String blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    // ブロックされた相手の名前を取得
    public String getBlockedUserName() {
        return blockedUserName;
    }

    // ブロックされた相手の名前を設定
    public void setBlockedUserName(String blockedUserName) {
        this.blockedUserName = blockedUserName;
    }
}