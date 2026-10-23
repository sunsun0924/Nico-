package tool;

// SHA-256を使用するためのインポート
import java.security.MessageDigest;

public class PasswordUtil {
    // パスワードをハッシュ化するメソッド
    public static String hash(String password) throws Exception {

        // ハッシュアルゴリズムを取得(今回はSHA-256)
        MessageDigest md =
            MessageDigest.getInstance("SHA-256");

        // 文字列をバイト列に変換してハッシュ化
        byte[] bytes =
            md.digest(password.getBytes("UTF-8"));

        // ハッシュ値を文字列として組み立てるための箱
        StringBuilder sb = new StringBuilder();

        // byte配列を16進数文字列に変換
        for (byte b : bytes) {

            // %02x → 16進数2桁で表示という意味
            // 例: 0a / ff など
            sb.append(String.format("%02x", b));
        }

        // ハッシュ化された文字列を返す
        return sb.toString();
    }
}
