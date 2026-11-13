
package servlet;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bean.Admin;
import bean.Announcement;
import bean.Post;
import bean.User;
import dao.AnnouncementDAO;
import dao.PostDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import tool.Action;

// 新規投稿・お知らせをDBに登録するServlet
public class PostInsertServlet extends Action {

    // 画像の制限
    private static final int MAX_IMAGE_COUNT = 5;
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;
    private static final long MAX_TOTAL_SIZE = 20 * 1024 * 1024;

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // ログイン状態を確認
        HttpSession session = request.getSession(false);

        if (session == null) {
            request.setAttribute("message", "ログインしてください。");
            return "/post/postnew.jsp";
        }

        // ユーザー情報と管理者情報を取得
        User user = (User) session.getAttribute("user");
        Admin admin = (Admin) session.getAttribute("admin");

        // どちらもログインしていない場合
        if (user == null && admin == null) {
            request.setAttribute("message", "ログインしてください。");
            return "/post/postnew.jsp";
        }

        // 保存した画像ファイルのパス一覧
        List<Path> savedImagePaths = new ArrayList<>();

        try {

            // 投稿本文を取得
            String postText = request.getParameter("post_text");

            // 未入力チェック
            if (postText == null || postText.trim().isEmpty()) {
                request.setAttribute(
                    "message",
                    "投稿内容を入力してください。"
                );
                return "/post/postnew.jsp";
            }

            // 前後の空白を削除
            postText = postText.trim();

            // 200文字以内か確認
            if (postText.length() > 200) {
                request.setAttribute(
                    "message",
                    "投稿内容は200文字以内で入力してください。"
                );
                return "/post/postnew.jsp";
            }

            // ========================================
            // お知らせとして投稿するか確認
            // ========================================

            boolean isAnnouncement =
                "true".equals(request.getParameter("isAnnouncement"));

            // お知らせとして投稿する場合
            if (isAnnouncement) {

                // 管理者以外はお知らせを登録できない
                if (admin == null) {
                    request.setAttribute(
                        "message",
                        "お知らせを投稿できるのは管理者のみです。"
                    );
                    return "/post/postnew.jsp";
                }

                // お知らせには画像を添付できない
                for (Part part : request.getParts()) {

                    if ("post_image".equals(part.getName())
                            && part.getSize() > 0) {

                        request.setAttribute(
                            "message",
                            "お知らせには画像を添付できません。"
                        );
                        return "/post/postnew.jsp";
                    }
                }

                // お知らせBeanを作成
                Announcement announcement = new Announcement();

                // お知らせ本文を設定
                announcement.setAnnouncementText(postText);

                // お知らせをDBに登録
                AnnouncementDAO announcementDAO =
                    new AnnouncementDAO();

                int result = announcementDAO.insert(announcement);

                // 登録成功
                if (result > 0) {

                    session.setAttribute(
                        "message",
                        "お知らせを登録しました！"
                    );

                    return "/servlet.PostServlet.action";

                } else {

                    request.setAttribute(
                        "message",
                        "お知らせの登録に失敗しました。"
                    );

                    return "/post/postnew.jsp";
                }
            }

            // ========================================
            // ここから通常投稿の処理
            // ========================================

            // 画像ファイルを取得
            List<Part> imageParts = new ArrayList<>();

            // 合計画像サイズ
            long totalImageSize = 0;

            // 画像ファイルのPartを取得
            for (Part part : request.getParts()) {

                if (!"post_image".equals(part.getName())) {
                    continue;
                }

                // 未選択の場合は除外
                if (part.getSize() <= 0) {
                    continue;
                }

                imageParts.add(part);
                totalImageSize += part.getSize();
            }

            // ========================================
            // 画像枚数チェック
            // ========================================

            if (imageParts.size() > MAX_IMAGE_COUNT) {
                request.setAttribute(
                    "message",
                    "画像は最大5枚まで選択できます。"
                );
                return "/post/postnew.jsp";
            }

            // ========================================
            // 合計容量チェック
            // ========================================

            if (totalImageSize > MAX_TOTAL_SIZE) {
                request.setAttribute(
                    "message",
                    "画像の合計容量は20MB以内にしてください。"
                );
                return "/post/postnew.jsp";
            }

            // ========================================
            // 画像の内容を確認
            // ========================================

            List<String> imageFileNames = new ArrayList<>();

            for (Part imagePart : imageParts) {

                // 1枚あたりの容量チェック
                if (imagePart.getSize() > MAX_IMAGE_SIZE) {
                    request.setAttribute(
                        "message",
                        "画像1枚のサイズは5MB以内にしてください。"
                    );
                    return "/post/postnew.jsp";
                }

                // ファイル名を取得
                String originalFileName =
                    imagePart.getSubmittedFileName();

                // 拡張子を取得
                String extension = "";

                if (originalFileName != null
                        && originalFileName.lastIndexOf(".") >= 0) {

                    extension = originalFileName
                        .substring(originalFileName.lastIndexOf("."))
                        .toLowerCase();
                }

                // 使用可能な拡張子を確認
                if (!extension.matches(
                        "\\.(jpg|jpeg|png|gif|webp)")) {

                    request.setAttribute(
                        "message",
                        "JPG・JPEG・PNG・GIF・WEBP形式の画像を選択してください。"
                    );
                    return "/post/postnew.jsp";
                }

                // Content-Typeを確認
                String contentType = imagePart.getContentType();

                if (contentType == null
                        || !contentType.startsWith("image/")) {

                    request.setAttribute(
                        "message",
                        "画像ファイルを選択してください。"
                    );
                    return "/post/postnew.jsp";
                }

                // UUIDで重複しないファイル名を作成
                String imageFileName =
                    UUID.randomUUID().toString() + extension;

                imageFileNames.add(imageFileName);
            }

            // ========================================
            // 画像を保存
            // ========================================

            if (!imageParts.isEmpty()) {

                // imagesフォルダの保存先を取得
                String imageDirectory =
                    request.getServletContext().getRealPath("/images");

                // 保存先が取得できない場合
                if (imageDirectory == null) {
                    throw new Exception(
                        "画像の保存先を取得できません。"
                    );
                }

                // 保存先フォルダを作成
                Path directoryPath = Paths.get(imageDirectory);

                Files.createDirectories(directoryPath);

                // 画像を1枚ずつ保存
                for (int i = 0; i < imageParts.size(); i++) {

                    Part imagePart = imageParts.get(i);

                    String imageFileName = imageFileNames.get(i);

                    // 保存する画像のパス
                    Path savedImagePath =
                        directoryPath.resolve(imageFileName);

                    // 画像を保存
                    try (InputStream inputStream =
                            imagePart.getInputStream()) {

                        Files.copy(inputStream, savedImagePath);
                    }

                    // 保存したパスを記録
                    savedImagePaths.add(savedImagePath);
                }
            }

            // ========================================
            // 投稿Beanを作成
            // ========================================

            Post post = new Post();

            // 管理者がログインしている場合
            if (admin != null) {

                // 管理者IDを保存
                post.setUser_id(null);
                post.setAdmin_id(admin.getCpUserid());
                post.setAuthor_type("ADMIN");

            } else {

                // ユーザーIDを保存
                post.setUser_id(user.getUserId());
                post.setAdmin_id(null);
                post.setAuthor_type("USER");
            }

            // 投稿内容を設定
            post.setPost_text(postText);

            // 画像ファイル名をカンマ区切りで設定
            if (imageFileNames.isEmpty()) {

                post.setPost_img(null);

            } else {

                post.setPost_img(
                    String.join(",", imageFileNames)
                );
            }

            // コメントは初期状態では空
            post.setComment(null);

            // ========================================
            // DBに登録
            // ========================================

            PostDAO dao = new PostDAO();

            boolean result = dao.insert(post);

            // 投稿登録成功
            if (result) {

                session.setAttribute(
                    "message",
                    "投稿を登録しました！"
                );

                return "/servlet.PostServlet.action";

            } else {

                // DB登録に失敗した場合は画像を削除
                deleteSavedImages(savedImagePaths);

                request.setAttribute(
                    "message",
                    "投稿の登録に失敗しました。"
                );

                return "/post/postnew.jsp";
            }

        } catch (Exception e) {

            // エラー内容を表示
            e.printStackTrace();

            // エラー時に保存済み画像を削除
            deleteSavedImages(savedImagePaths);

            request.setAttribute(
                "message",
                "投稿の登録中にエラーが発生しました。"
            );

            return "/post/postnew.jsp";
        }
    }

    // 保存した画像を削除する
    private void deleteSavedImages(List<Path> savedImagePaths) {

        for (Path imagePath : savedImagePaths) {

            try {

                Files.deleteIfExists(imagePath);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
