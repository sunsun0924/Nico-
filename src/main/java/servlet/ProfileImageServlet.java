package servlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Tomcat外部に保存したプロフィール画像を表示する
@WebServlet("/profile-image/*")
public class ProfileImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 画像の保存先
    private static final Path IMAGE_DIRECTORY =
            Paths.get("C:/NicoData/images")
                 .toAbsolutePath()
                 .normalize();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        // URLから画像ファイル名を取得
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String fileName = pathInfo.substring(1);

        // ファイル名以外を指定できないようにする
        if (fileName.contains("/")
                || fileName.contains("\\")
                || fileName.equals(".")
                || fileName.equals("..")) {

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 画像の実際のパスを作成
        Path imagePath = IMAGE_DIRECTORY
                .resolve(fileName)
                .normalize();

        // 保存先フォルダの外を参照していないか確認
        if (!imagePath.startsWith(IMAGE_DIRECTORY)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // ファイルが存在するか確認
        if (!Files.isRegularFile(imagePath)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 拡張子に応じてContent-Typeを設定
        String lowerFileName = fileName.toLowerCase();

        if (lowerFileName.endsWith(".jpg")
                || lowerFileName.endsWith(".jpeg")) {

            resp.setContentType("image/jpeg");

        } else if (lowerFileName.endsWith(".png")) {

            resp.setContentType("image/png");

        } else if (lowerFileName.endsWith(".gif")) {

            resp.setContentType("image/gif");

        } else if (lowerFileName.endsWith(".webp")) {

            resp.setContentType("image/webp");

        } else {

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // ブラウザのキャッシュを抑制
        resp.setHeader("Cache-Control", "no-cache");

        // ファイルサイズを設定
        resp.setContentLengthLong(Files.size(imagePath));

        // 画像をブラウザに返す
        Files.copy(imagePath, resp.getOutputStream());
    }
}