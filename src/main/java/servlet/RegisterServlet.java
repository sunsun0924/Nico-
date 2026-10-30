package servlet;

import bean.Admin;
import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class RegisterServlet extends Action {

    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // ログイン中の管理者情報を取得
        HttpSession session = request.getSession(false);

        // 管理者がログインしていない場合
        if (session == null || session.getAttribute("admin") == null) {
            request.setAttribute("errorMessage",
                    "＊管理者ログインをしてください");
            return "/login/adminLogin.jsp";
        }

        // セッションから管理者情報を取得
        Admin admin = (Admin) session.getAttribute("admin");

        // 管理者の企業コードを取得
        String cpId = admin.getCpId();

        // 企業コードが取得できない場合
        if (cpId == null || cpId.length() != 4) {
            request.setAttribute("errorMessage",
                    "＊企業コードを取得できませんでした");
            return "/register/register.jsp";
        }

        // 登録画面から入力された情報を取得
        String userId = request.getParameter("userId");
        String userPass = request.getParameter("userPass");

        // 未入力チェック
        if (userId == null || userId.trim().isEmpty()
                || userPass == null || userPass.isEmpty()) {

            request.setAttribute("errorMessage",
                    "＊未入力があります\n＊すべて入力してください");

            return "/register/register.jsp";
        }

        // ユーザーIDの前後の空白を削除
        userId = userId.trim();

        // ユーザーIDは8文字
        if (userId.length() != 8) {

            request.setAttribute("errorMessage",
                    "＊ユーザーIDは8文字で入力してください");

            return "/register/register.jsp";
        }

        // 最初の4文字が企業コードと一致するか確認
        if (!userId.substring(0, 4).equals(cpId)) {

            request.setAttribute("errorMessage",
                    "＊ユーザーIDの最初の4文字は企業コード「"
                    + cpId + "」にしてください");

            return "/register/register.jsp";
        }

        // 5～8文字目が半角英数字か確認
        if (!userId.substring(4).matches("[a-zA-Z0-9]{4}")) {

            request.setAttribute("errorMessage",
                    "＊ユーザーIDの5～8文字目は半角英数字で入力してください");

            return "/register/register.jsp";
        }

        // パスワードは8～15文字の半角英数字
        if (!userPass.matches("[a-zA-Z0-9]{8,15}")) {

            request.setAttribute("errorMessage",
                    "＊パスワードは8～15文字の半角英数字で入力してください");

            return "/register/register.jsp";
        }

        // DAOを使ってデータベースを操作
        UserDAO dao = new UserDAO();

        // ユーザーIDがすでに使われていないか確認
        if (dao.isExist(userId)) {

            request.setAttribute("errorMessage",
                    "＊このユーザーIDは既に使われています");

            return "/register/register.jsp";
        }

        // User Beanに登録情報を設定
        User user = new User();

        user.setUserId(userId);
        user.setUserPass(userPass);

        // データベースに登録
        int line = dao.insert(user);

        // 登録成功
        if (line > 0) {

            request.setAttribute("message",
                    "＊ユーザーの新規登録が完了しました");

            return "/post/post.jsp";

        } else {

            // 登録失敗
            request.setAttribute("errorMessage",
                    "＊登録に失敗しました");

            return "/register/register.jsp";
        }
    }
}