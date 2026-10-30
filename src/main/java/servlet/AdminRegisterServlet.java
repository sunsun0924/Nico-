package servlet;

import bean.Admin;
import dao.AdminDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class AdminRegisterServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        // フォームから管理者IDを取得
        String cpUserid = request.getParameter("cpUserid");

        // フォームからパスワードを取得
        String cpUserpass = request.getParameter("cpUserpass");

        // フォームからメールアドレスを取得
        String cpUsermail = request.getParameter("cpUsermail");

        // フォームから企業IDを取得
        String cpId = request.getParameter("cpId");

        // データベース操作（登録・検索）を行うためのDAOを作成
        AdminDAO dao = new AdminDAO();

        // 未入力チェック
        if (cpUserid == null || cpUserid.equals("") ||
            cpUserpass == null || cpUserpass.equals("") ||
            cpUsermail == null || cpUsermail.equals("") ||
            cpId == null || cpId.equals("")) {

            request.setAttribute("errorMessage",
                "＊未入力があります\n＊すべて入力してください");

            return "/register/adminRegister.jsp";
        }

        // 前後の空白を除去
        cpUserid = cpUserid.trim();
        cpUsermail = cpUsermail.trim();
        cpId = cpId.trim();

        // 管理者IDは8文字
        if (cpUserid.length() != 8) {
            request.setAttribute("errorMessage",
                "＊管理者IDは8文字で入力してください");

            return "/register/adminRegister.jsp";
        }

        // パスワードは8～15文字の半角英数字
        if (!cpUserpass.matches("[a-zA-Z0-9]{8,15}")) {
            request.setAttribute("errorMessage",
                "＊パスワードは8～15文字の半角英数字で入力してください");

            return "/register/adminRegister.jsp";
        }

        // メールアドレスは100文字以内
        if (cpUsermail.length() > 100) {
            request.setAttribute("errorMessage",
                "＊メールアドレスは100文字以内で入力してください");

            return "/register/adminRegister.jsp";
        }

        // 企業IDは4文字
        if (cpId.length() != 4) {
            request.setAttribute("errorMessage",
                "＊企業IDは4文字で入力してください");

            return "/register/adminRegister.jsp";
        }

        // 管理者IDの重複確認
        if (dao.isExist(cpUserid)) {
            request.setAttribute("errorMessage",
                "＊この管理者IDは既に使われています");

            return "/register/adminRegister.jsp";
        }

        // 管理者情報を保存するオブジェクトを作成
        Admin admin = new Admin();

        // 管理者IDを設定
        admin.setCpUserid(cpUserid);

        // パスワードを設定
        admin.setCpUserpass(cpUserpass);

        // メールアドレスを設定
        admin.setCpUsermail(cpUsermail);

        // 企業IDを設定
        admin.setCpId(cpId);

        try {

            // 登録処理
            int line = dao.insert(admin);

            // lineは「更新された行数」
            if (line > 0) {
                request.setAttribute("message",
                    "＊管理者の新規登録が完了しました");

                return "/login/adminLogin.jsp";

            } else {
                request.setAttribute("errorMessage",
                    "＊管理者の登録に失敗しました");

                return "/register/adminRegister.jsp";
            }

        } catch (java.sql.SQLException e) {

            // 主キーや一意制約違反
            if ("23505".equals(e.getSQLState())) {

                request.setAttribute("errorMessage",
                    "＊この管理者IDは既に使われています");

                return "/register/adminRegister.jsp";

            // 外部キー制約違反
            } else if ("23503".equals(e.getSQLState())) {

                request.setAttribute("errorMessage",
                    "＊登録されていない企業IDです");

                return "/register/adminRegister.jsp";

            } else {
                throw e;
            }
        }
    }
}
