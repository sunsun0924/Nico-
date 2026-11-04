package servlet;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
import tool.PasswordUtil;

// パスワード変更処理
public class UserPasswordChangeServlet extends Action {

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

        // JSPから入力内容を取得
        String currentPassword =
                req.getParameter("currentPassword");

        String newPassword =
                req.getParameter("newPassword");

        String confirmPassword =
                req.getParameter("confirmPassword");

        // 未入力チェック
        if (currentPassword == null || currentPassword.isEmpty()
                || newPassword == null || newPassword.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()) {

            req.setAttribute(
                "error",
                "すべての項目を入力してください。"
            );

            return "/mypage/password_change.jsp";
        }

        // 新しいパスワードの文字数チェック
        if (newPassword.length() < 8 || newPassword.length() > 15) {

            req.setAttribute(
                "error",
                "新しいパスワードは8～15文字で入力してください。"
            );

            return "/mypage/password_change.jsp";
        }

        // 新しいパスワードと確認用パスワードの一致確認
        if (!newPassword.equals(confirmPassword)) {

            req.setAttribute(
                "error",
                "新しいパスワードと確認用パスワードが一致しません。"
            );

            return "/mypage/password_change.jsp";
        }

        // 現在のパスワードと同じものは設定できない
        if (currentPassword.equals(newPassword)) {

            req.setAttribute(
                "error",
                "現在のパスワードとは別のものを設定してください。"
            );

            return "/mypage/password_change.jsp";
        }

        UserDAO dao = new UserDAO();

        // 現在のパスワードが正しいか確認
        User checkUser = dao.search(
            user.getUserId(),
            currentPassword
        );

        if (checkUser == null) {

            req.setAttribute(
                "error",
                "現在のパスワードが正しくありません。"
            );

            return "/mypage/password_change.jsp";
        }

        // パスワードをDBに更新
        int result = dao.updatePassword(
            user.getUserId(),
            newPassword
        );

        // 更新成功
        if (result > 0) {

            // セッション内のパスワードも更新
            user.setUserPass(PasswordUtil.hash(newPassword));

            session.setAttribute("user", user);

            // 設定画面に表示する完了メッセージ
            session.setAttribute(
                "mypageMessage",
                "＊パスワードを変更しました"
            );

            // 一つ前の設定画面に戻る
            return "/mypage/mypage_detail.jsp";

        } else {

            // 更新失敗
            req.setAttribute(
                "error",
                "パスワードの変更に失敗しました。"
            );

            return "/mypage/password_change.jsp";
        }
    }
}