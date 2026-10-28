package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.User;


public class UserDAO extends DAO {
	// ログイン用
    public User search(String userId, String userPass) throws Exception {

        // 戻り値用（最初は見つかっていないのでnull）
        User u = null;

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備（?で値を後からセットする＝SQLインジェクション対策）
        PreparedStatement st = con.prepareStatement(
            "select * from user where userId = ? and userPass = ?"
        );

        // 1つ目の?にログイン名をセット
        st.setString(1, userId);

        // 2つ目の?にパスワードをセット
        st.setString(2, userPass);

        // SQLを実行（SELECTなのでexecuteQuery）
        ResultSet rs = st.executeQuery();

        // 検索結果を1件ずつ取り出す
        if (rs.next()) {

            // データが見つかったらCustomerオブジェクトを作成
            u = new User();

            // カラム名を指定して値を取得 → Beanにセット
            u.setUserId(rs.getString("userId"));
            u.setUserPass(rs.getString("userPass"));
        }
        
        
        // リソースを解放（必ず閉じる）
        rs.close();
        st.close();
        con.close();

        // 見つかればCustomer、なければnullを返す
        return u;
    }
    
    // 新規登録用
    public int insert(User user) throws Exception {

        // DB接続を取得
        Connection con = getConnection();

        // SQLを準備（?で値を後からセットする＝SQLインジェクション対策）
        PreparedStatement st = con.prepareStatement(
            "insert into user (userId, userPass) values(?, ?)"
        );

        // 1つ目の?にログイン名をセット
        st.setString(1, user.getUserId());

        // 2つ目の?にパスワードをセット
        st.setString(2, user.getUserPass());

        // SQLを実行
        int line = st.executeUpdate();

        // リソースを解放（必ず閉じる）
        st.close();
        con.close();


        return line;
    }
    
    // 重複確認用
    public boolean isExist(String userId) throws Exception {
    	
    	// DB接続を取得
        Connection con = getConnection();
        
        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "select * from user where userId = ?"
        );

        // 1つ目の?にログイン名をセット
        st.setString(1, userId);

        // SQLを実行（SELECTなのでexecuteQuery）
        ResultSet rs = st.executeQuery();

        boolean exists = rs.next();

        // リソースを解放（必ず閉じる）
        rs.close();
        st.close();
        con.close();

        return exists;
    }
    
    // プロフィール更新用
    public int update(User user) throws Exception {
    	
    	// DB接続を取得
        Connection con = getConnection();
        
        // SQLを準備
        PreparedStatement st = con.prepareStatement(
            "UPDATE user SET userAddress = ?, userNumber = ?, userMail = ?, " +
            "userBirthday = ?, userName = ?, userImg = ? WHERE userId = ?"
        );
        
        // 1つ目の?に住所をセット
        st.setString(1, user.getUserAddress());
        // 2つ目の?に電話番号をセット
        st.setString(2, user.getUserNumber());
        // 3つ目の?にメールアドレスをセット
        st.setString(3, user.getUserMail());
        // 4つ目の?に誕生日をセット
        st.setDate(4, user.getUserBirthday());
        // 5つ目の?にユーザー名をセット
        st.setString(5, user.getUserName());
        // 6つ目の?にユーザーアイコンをセット
        st.setString(6, user.getUserImg());
        // 7つ目の?にユーザーIDをセット
        st.setString(6, user.getUserId());

        // SQLを実行
        int line = st.executeUpdate();
        
        // リソースを解放（必ず閉じる）
        st.close();
        con.close();

        return line;
    }

}
