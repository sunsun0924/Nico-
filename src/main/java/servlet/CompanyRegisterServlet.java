
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
        String cpId = request.getParameter("cpId");

        // フォームから企業名を取得
        String cpName = request.getParameter("cpName");

        // データベース操作を行うDAOを作成
        CompanyDAO dao = new CompanyDAO();

        // 未入力チェック
        if (cpId == null || cpId.trim().isEmpty()
                || cpName == null || cpName.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "＊未入力があります\n＊すべて入力してください");

            return "/company/company.jsp";
        }

        // 前後の空白を除去
        cpId = cpId.trim();
        cpName = cpName.trim();

        // 企業IDは4文字
        if (cpId.length() != 4) {

            request.setAttribute(
                    "errorMessage",
                    "＊企業IDは4文字で入力してください");

            return "/company/company.jsp";
        }

        // 企業名は50文字以内
        if (cpName.length() > 50) {

            request.setAttribute(
                    "errorMessage",
                    "＊企業名は50文字以内で入力してください");

            return "/company/company.jsp";
        }

        // 企業IDの重複確認
        if (dao.isExist(cpId)) {

            request.setAttribute(
                    "errorMessage",
                    "＊この企業IDは既に登録されています");

            return "/company/company.jsp";
        }

        // 企業情報の登録処理
        int line = dao.insert(cpId, cpName);

        // lineは更新された行数
        if (line > 0) {

            request.setAttribute(
                    "message",
                    "＊企業の新規登録が完了しました");

            return "/post/post.jsp";

        } else {

            request.setAttribute(
                    "errorMessage",
                    "＊企業の登録に失敗しました");

            return "/company/company.jsp";
        }
    }
}
