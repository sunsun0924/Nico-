
package bean;

// ブロック情報を管理するBean

public class BlockList {

    // ブロックID
    private String blockId;

    // ブロックしたユーザーID
    private String blockerUserId;

    // ブロックされたユーザーID
    private String blockedUserId;

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

}
