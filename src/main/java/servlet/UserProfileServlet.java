package servlet;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/UserProfileUpdateServlet")
@MultipartConfig(
    maxFileSize = 1048576,
    maxRequestSize = 2097152
)
public class UserProfileServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, java.io.IOException {

        req.setCharacterEncoding("UTF-8");

        // セッションからログインユーザーを取得
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            User user = (User) session.getAttribute("user");

            // JSPからユーザー名を取得
            String userName = req.getParameter("userName");

            // 画像削除フラグを取得
            String deleteImage =
                    req.getParameter("deleteProfileImage");

            // ユーザー名の入力チェック
            if (userName == null
                    || userName.trim().length() < 2
                    || userName.trim().length() > 20) {

                req.setAttribute(
                    "error",
                    "ユーザー名は2～20文字で入力してください。"
                );

                req.getRequestDispatcher(
                    "/mypage/profile.jsp"
                ).forward(req, resp);

                return;
            }

            userName = userName.trim();

            // 現在登録されている画像名
            String userImg = user.getUserImg();

            // 画像の保存先
            String savePath =
                    getServletContext().getRealPath("/images");

            File saveDirectory = new File(savePath);

            if (!saveDirectory.exists()) {
                saveDirectory.mkdirs();
            }

            // 画像削除ボタンが押された場合
            if ("true".equals(deleteImage)) {

                userImg = "default-profile.png";

            } else {

                // 選択された画像を取得
                Part imagePart = req.getPart("profileImage");

                // 新しい画像が選択されている場合
                if (imagePart != null && imagePart.getSize() > 0) {

                    // ファイルサイズチェック
                    if (imagePart.getSize() > 1048576) {

                        req.setAttribute(
                            "error",
                            "画像は1MB以内にしてください。"
                        );

                        req.getRequestDispatcher(
                            "/mypage/profile.jsp"
                        ).forward(req, resp);

                        return;
                    }

                    // ファイル名を取得
                    String fileName = imagePart.getSubmittedFileName();

                    if (fileName == null || fileName.trim().isEmpty()) {

                        req.setAttribute(
                            "error",
                            "画像ファイルを選択してください。"
                        );

                        req.getRequestDispatcher(
                            "/mypage/profile.jsp"
                        ).forward(req, resp);

                        return;
                    }

                    // ファイル名から拡張子を取得
                    fileName = Paths.get(
                        fileName.replace("\\", "/")
                    ).getFileName().toString();

                    String extension = "";

                    int dot = fileName.lastIndexOf(".");

                    if (dot >= 0) {
                        extension =
                                fileName.substring(dot).toLowerCase();
                    }

                    // 画像形式を確認
                    if (!extension.matches(
                            "\\.(jpg|jpeg|png|gif|webp)")) {

                        req.setAttribute(
                            "error",
                            "jpg、jpeg、png、gif、webp形式の画像を選択してください。"
                        );

                        req.getRequestDispatcher(
                            "/mypage/profile.jsp"
                        ).forward(req, resp);

                        return;
                    }

                    // 重複しないファイル名を作成
                    String newFileName =
                            UUID.randomUUID().toString() + extension;

                    // 画像を保存
                    File saveFile = new File(
                        saveDirectory,
                        newFileName
                    );

                    imagePart.write(saveFile.getAbsolutePath());

                    // DBにはファイル名だけ保存
                    userImg = newFileName;
                }
            }

            // Userに更新内容をセット
            user.setUserName(userName);
            user.setUserImg(userImg);

            // DBを更新
            UserDAO dao = new UserDAO();

            int result = dao.update(user);

            if (result > 0) {

                // セッション情報を更新
                session.setAttribute("user", user);

                resp.sendRedirect(
                    req.getContextPath() + "/mypage/profile.jsp"
                );

            } else {

                req.setAttribute(
                    "error",
                    "プロフィールの変更に失敗しました。"
                );

                req.getRequestDispatcher(
                    "/mypage/profile.jsp"
                ).forward(req, resp);
            }

        } catch (Exception e) {

            e.printStackTrace();

            req.setAttribute(
                "error",
                "プロフィールの更新中にエラーが発生しました。"
            );

            req.getRequestDispatcher(
                "/mypage/profile.jsp"
            ).forward(req, resp);
        }
    }

    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, java.io.IOException {

        resp.sendRedirect(
            req.getContextPath() + "/mypage/profile.jsp"
        );
    }
}