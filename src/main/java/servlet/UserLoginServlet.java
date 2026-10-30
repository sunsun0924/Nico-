package servlet;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ユーザーログイン処理
public class UserLoginServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // フォームからログインIDとパスワードを取得
        String userId = request.getParameter("userId");
        String userPass = request.getParameter("userPass");

        // 未入力チェック
        if (userId == null || userId.trim().isEmpty()
                || userPass == null || userPass.isEmpty()) {

            request.setAttribute("error",
                    "＊ログインIDとパスワードを入力してください");

            return "/login/userLogin.jsp";
        }

        // ユーザーIDの前後の空白を削除
        userId = userId.trim();

        // DAOを使ってDBからユーザーを検索
        // DAOの中でパスワードをハッシュ化して照合する
        UserDAO dao = new UserDAO();

        User user = dao.search(userId, userPass);

        // ログイン成功
        if (user != null) {

            // セッションを取得
            HttpSession session = request.getSession();

            // セッションにユーザー情報を保存
            session.setAttribute("user", user);

            return "/post/post.jsp";

        } else {

            // ログイン失敗
            request.setAttribute("error",
                    "＊ログインに失敗しました\n＊ログインIDまたはパスワードが違います");

            return "/login/userLogin.jsp";
        }
    }
}