package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// ユーザーログイン処理を行うServlet
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {

    // ログインフォームから送信された情報を処理する
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化けを防ぐ
        request.setCharacterEncoding("UTF-8");

        // ログイン画面から入力値を取得する
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        try {
            // 入力内容が空の場合
            if (userId == null || userId.trim().isEmpty()
                    || password == null || password.isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "ユーザーIDとパスワードを入力してください。");

                request.getRequestDispatcher(
                        "/login/userLogin.jsp")
                        .forward(request, response);
                return;
            }

            // DBからユーザー情報を検索する
            UserDAO dao = new UserDAO();
            User user = dao.login(userId.trim(), password);

            // ログイン成功
            if (user != null) {

                // セッションにユーザー情報を保存する
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // ログイン後の画面へ移動
                response.sendRedirect(
                        request.getContextPath() + "/post.jsp");

            } else {
                // ログイン失敗
                request.setAttribute(
                        "errorMessage",
                        "ユーザーIDまたはパスワードが違います。");

                request.getRequestDispatcher(
                        "/login/userLogin.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            // DB接続などでエラーが発生した場合
            throw new ServletException(
                    "ログイン処理に失敗しました。", e);
        }
    }
}