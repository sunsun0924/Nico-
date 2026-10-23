
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

// データベースへの接続を管理するクラス
public class DAO {

    // データベースの接続先URL
    private static final String URL =
            "jdbc:postgresql://localhost:5432/nico_db";

    // データベースのユーザー名
    private static final String USER = "postgres";

    // データベースのパスワード
    private static final String PASSWORD = "post";

    // データベースに接続し、Connectionを返す
    public Connection getConnection() throws Exception {

        // PostgreSQLのJDBCドライバを読み込む
        Class.forName("org.postgresql.Driver");

        // データベースに接続する
        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
