package servlet;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import tool.Action;

// プロフィール変更処理
public class UserProfileServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest req,
            HttpServletResponse resp) throws Exception {

        req.setCharacterEncoding("UTF-8");

        // セッションからログインユーザーを取得
        HttpSession session = req.getSession(false);

        // ログイン確認
        if (session == null || session.getAttribute("user") == null) {
            return "/login/userLogin.jsp";
        }

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

            return "/mypage/profile.jsp";
        }

        userName = userName.trim();

        // 現在登録されている画像名
        String userImg = user.getUserImg();

        // 現在の画像表示位置
        int positionX = user.getUserImgPositionX();
        int positionY = user.getUserImgPositionY();

        // 画像削除ボタンが押された場合
        if ("true".equals(deleteImage)) {

            userImg = "default-profile.png";

            // デフォルト画像は中央表示
            positionX = 50;
            positionY = 50;

        } else {

            // 表示位置を取得
            try {
                positionX = Integer.parseInt(
                    req.getParameter("userImgPositionX")
                );

                positionY = Integer.parseInt(
                    req.getParameter("userImgPositionY")
                );

            } catch (NumberFormatException e) {

                req.setAttribute(
                    "error",
                    "画像の表示位置を正しく指定してください。"
                );

                return "/mypage/profile.jsp";
            }

            // 表示位置の範囲チェック
            if (positionX < 0 || positionX > 100
                    || positionY < 0 || positionY > 100) {

                req.setAttribute(
                    "error",
                    "画像の表示位置が正しくありません。"
                );

                return "/mypage/profile.jsp";
            }

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

                    return "/mypage/profile.jsp";
                }

                // ファイル名を取得
                String fileName = imagePart.getSubmittedFileName();

                if (fileName == null || fileName.trim().isEmpty()) {

                    req.setAttribute(
                        "error",
                        "画像ファイルを選択してください。"
                    );

                    return "/mypage/profile.jsp";
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

                    return "/mypage/profile.jsp";
                }

                // 画像の保存先
                String savePath =
                        req.getServletContext().getRealPath("/images");

                if (savePath == null) {

                    req.setAttribute(
                        "error",
                        "画像の保存先を取得できませんでした。"
                    );

                    return "/mypage/profile.jsp";
                }

                File saveDirectory = new File(savePath);

                if (!saveDirectory.exists()) {
                    saveDirectory.mkdirs();
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
        user.setUserImgPositionX(positionX);
        user.setUserImgPositionY(positionY);

        // DBを更新
        UserDAO dao = new UserDAO();

        int result = dao.update(user);

        // 更新成功
        if (result > 0) {

            // セッション内のユーザー情報を更新
            session.setAttribute("user", user);

            // マイページに表示する完了メッセージ
            session.setAttribute(
                "mypageMessage",
                "＊プロフィールを変更しました"
            );

            // マイページに戻る
            return "/mypage/mypage.jsp";

        } else {

            req.setAttribute(
                "error",
                "プロフィールの変更に失敗しました。"
            );

            return "/mypage/profile.jsp";
        }
    }
}