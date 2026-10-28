
package servlet;

import dao.CompanyDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

// 企業新規登録Servlet
public class CompanyRegisterServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // フォームから企業IDを取得
        String companyId = request.getParameter("companyId");

        // フォームから企業名を取得
        String companyName = request.getParameter("companyName");

        // データベース操作を行うDAOを作成
        CompanyDAO dao = new CompanyDAO();

        // 未入力チェック
        if (companyId == null || companyId.trim().isEmpty()
                || companyName == null || companyName.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "＊未入力があります\n＊すべて入力してください");

            return "/company/company.jsp";
        }

        // 前後の空白を除去
        companyId = companyId.trim();
        companyName = companyName.trim();

        // 企業IDは4文字
        if (companyId.length() != 4) {

            request.setAttribute(
                    "errorMessage",
                    "＊企業IDは4文字で入力してください");

            return "/company/company.jsp";
        }

        // 企業名は50文字以内
        if (companyName.length() > 50) {

            request.setAttribute(
                    "errorMessage",
                    "＊企業名は50文字以内で入力してください");

            return "/company/company.jsp";
        }

        // 企業IDの重複確認
        if (dao.isExist(companyId)) {

            request.setAttribute(
                    "errorMessage",
                    "＊この企業IDは既に登録されています");

            return "/company/company.jsp";
        }

        // 企業情報の登録処理
        int line = dao.insert(companyId, companyName);

        // lineは更新された行数
        if (line > 0) {

            request.setAttribute(
                    "message",
                    "＊企業の新規登録が完了しました");

            return "/company/company.jsp";

        } else {

            request.setAttribute(
                    "errorMessage",
                    "＊企業の登録に失敗しました");

            return "/company/company.jsp";
        }
    }
}
