package servlet;

import java.io.IOException;

import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 管理者ログイン処理
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化けを防ぐ
        request.setCharacterEncoding("UTF-8");

        // ログイン画面から入力値を取得
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        // 未入力チェック
        if (userId == null || userId.trim().isEmpty()
                || password == null || password.isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "IDとパスワードを入力してください。");

            request.getRequestDispatcher(
                    "/login/adminLogin.jsp")
                    .forward(request, response);
            return;
        }

        try {
            // 管理者情報をDBで確認
            AdminDAO dao = new AdminDAO();
            boolean loginSuccess =
                    dao.login(userId.trim(), password);

            if (loginSuccess) {
                // ログイン成功
                HttpSession session = request.getSession();
                session.setAttribute("adminUserId", userId.trim());

                // 管理者画面へ移動
                response.sendRedirect(
                        request.getContextPath() + "/post.jsp");

            } else {
                // ログイン失敗
                request.setAttribute(
                        "errorMessage",
                        "IDまたはパスワードが違います。");

                request.getRequestDispatcher(
                        "/login/adminLogin.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(
                    "管理者ログイン処理に失敗しました。", e);
        }
    }
}