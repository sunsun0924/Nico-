
package servlet;

import bean.Admin;
import dao.AdminDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

// 管理者新規登録Servlet
public class AdminRegisterServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // フォームから管理者IDを取得
        String userId = request.getParameter("userId");

        // フォームからパスワードを取得
        String password = request.getParameter("password");

        // フォームからメールアドレスを取得
        String email = request.getParameter("email");

        // フォームから企業IDを取得
        String companyId = request.getParameter("companyId");

        // 未入力チェック
        if (userId == null || userId.trim().isEmpty()
                || password == null || password.isEmpty()
                || email == null || email.trim().isEmpty()
                || companyId == null || companyId.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "＊未入力があります\n＊すべて入力してください");

            return "/register/adminRegister.jsp";
        }

        // 前後の空白を除去
        userId = userId.trim();
        email = email.trim();
        companyId = companyId.trim();

        // 管理者IDは8文字
        if (userId.length() != 8) {
            request.setAttribute(
                    "errorMessage",
                    "＊管理者IDは8文字で入力してください");

            return "/register/adminRegister.jsp";
        }

        // パスワードは8～15文字の半角英数字
        if (!password.matches("[a-zA-Z0-9]{8,15}")) {
            request.setAttribute(
                    "errorMessage",
                    "＊パスワードは8～15文字の半角英数字で入力してください");

            return "/register/adminRegister.jsp";
        }

        // メールアドレスは100文字以内
        if (email.length() > 100) {
            request.setAttribute(
                    "errorMessage",
                    "＊メールアドレスは100文字以内で入力してください");

            return "/register/adminRegister.jsp";
        }

        // 企業IDは4文字
        if (companyId.length() != 4) {
            request.setAttribute(
                    "errorMessage",
                    "＊企業IDは4文字で入力してください");

            return "/register/adminRegister.jsp";
        }

        // 管理者情報を管理するBeanを作成
        Admin admin = new Admin();

        // 管理者IDを設定
        admin.setCpUserid(userId);

        // パスワードを設定
        admin.setCpUserpass(password);

        // メールアドレスを設定
        admin.setCpUsermail(email);

        // 企業IDを設定
        admin.setCpId(companyId);

        // データベース操作を行うDAOを作成
        AdminDAO dao = new AdminDAO();

        try {
            // 管理者情報をデータベースに登録
            int line = dao.insert(admin);

            // 登録結果を確認
            if (line > 0) {
                request.setAttribute(
                        "message",
                        "＊管理者の新規登録が完了しました");

                // 登録完了後は管理者ログイン画面へ
                return "/register/adminLogin.jsp";

            } else {
                request.setAttribute(
                        "errorMessage",
                        "＊管理者の登録に失敗しました");

                return "/register/adminRegister.jsp";
            }

        } catch (java.sql.SQLException e) {

            // 主キーや一意制約違反など
            if ("23505".equals(e.getSQLState())) {
                request.setAttribute(
                        "errorMessage",
                        "＊この管理者IDは既に使われています");

                return "/register/adminRegister.jsp";

            // 外部キー制約違反
            } else if ("23503".equals(e.getSQLState())) {
                request.setAttribute(
                        "errorMessage",
                        "＊登録されていない企業IDです");

                return "/register/adminRegister.jsp";

            } else {
                throw e;
            }
        }
    }
}
