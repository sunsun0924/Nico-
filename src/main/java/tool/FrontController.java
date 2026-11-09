package tool;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// *.action へのリクエストを受け取る
@WebServlet(urlPatterns = {"*.action"})

// ファイルアップロードの設定
@MultipartConfig(
    // アップロードするファイル1枚の最大サイズ：5MB
    maxFileSize = 5 * 1024 * 1024,

    // リクエスト全体の最大サイズ：21MB
    maxRequestSize = 21 * 1024 * 1024,

    // メモリ上に保持するファイルサイズのしきい値：1MB
    fileSizeThreshold = 1024 * 1024
)
public class FrontController extends HttpServlet {

    // POSTリクエスト
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        process(req, resp);
    }

    // GETリクエスト
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        process(req, resp);
    }

    // POST・GET共通処理
    private void process(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        // 文字コードの設定
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        try {

            // URLからActionクラス名を取得
            String path = req.getServletPath().substring(1);

            // パッケージ名とクラス名に変換
            String name = path
                    .replace(".action", "")
                    .replace("/", ".");

            // Actionクラスを生成
            Action action = (Action) Class.forName(name)
                    .getDeclaredConstructor()
                    .newInstance();

            // Actionを実行
            String url = action.execute(req, resp);

            // 遷移先がある場合は画面遷移
            if (url != null && !url.isEmpty()) {

                req.getRequestDispatcher(url).forward(req, resp);
            }

        } catch (Exception e) {

            // エラー内容を出力
            e.printStackTrace(out);
        }
    }
}