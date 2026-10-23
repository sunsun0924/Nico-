package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 管理者新規登録Servlet
@WebServlet("/AdminRegisterServlet")
public class AdminRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // フォームから入力値を取得
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String companyId = request.getParameter("companyId");

        // 未入力チェック
        if (userId == null || password == null
                || email == null || companyId == null
                || userId.trim().isEmpty()
                || password.isEmpty()
                || email.trim().isEmpty()
                || companyId.trim().isEmpty()) {

            showError(request, response,
                    "すべての項目を入力してください。");
            return;
        }

        userId = userId.trim();
        email = email.trim();
        companyId = companyId.trim();

        // 管理者IDは8文字
        if (userId.length() != 8) {
            showError(request, response,
                    "管理者IDは8文字で入力してください。");
            return;
        }

        // パスワードは8～15文字の半角英数字
        if (!password.matches("[a-zA-Z0-9]{8,15}")) {
            showError(request, response,
                    "パスワードは8～15文字の半角英数字で入力してください。");
            return;
        }

        // メールアドレスは100文字以内
        if (email.length() > 100) {
            showError(request, response,
                    "メールアドレスは100文字以内で入力してください。");
            return;
        }

        // 企業IDは4文字
        if (companyId.length() != 4) {
            showError(request, response,
                    "企業IDは4文字で入力してください。");
            return;
        }

        try {
            // 管理者情報をDBに登録
            AdminDAO dao = new AdminDAO();
            dao.register(userId, password, email, companyId);

            // 登録成功
            request.setAttribute("successMessage",
                    "管理者登録が完了しました。");

            request.getRequestDispatcher(
                    "/register/adminRegister.jsp")
                    .forward(request, response);

        } catch (SQLException e) {

            // 管理者IDが重複している場合
            if ("23505".equals(e.getSQLState())) {

                showError(request, response,
                        "この管理者IDはすでに登録されています。");

            // 存在しない企業IDが指定された場合
            } else if ("23503".equals(e.getSQLState())) {

                showError(request, response,
                        "登録されていない企業IDです。");

            } else {
                e.printStackTrace();

                showError(request, response,
                        "管理者登録に失敗しました。");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // エラーメッセージを設定して登録画面に戻す
    private void showError(HttpServletRequest request,
                           HttpServletResponse response,
                           String message)
            throws ServletException, IOException {

        request.setAttribute("errorMessage", message);

        request.getRequestDispatcher(
                "/register/adminRegister.jsp")
                .forward(request, response);
    }
}