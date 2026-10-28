package servlet;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UserLoginServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        // セッションを取得（なければ新しく作成）
        HttpSession session = request.getSession();

        // フォームから送られてきたログインIDとパスワードを取得
        String userId = request.getParameter("userId");
        String userPass = request.getParameter("userPass");
        
        // 未入力チェック（最小）
        if (userId == null || userId.equals("") ||
        		userPass == null || userPass.equals("")) {
        	request.setAttribute("error",
        			"＊ログインIDとパスワードを入力してください");
            return "/login/userLogin.jsp";
        }
        
        // パスワードをハッシュ化
        //String hashpassword = PasswordUtil.hash(userPass);

        // DAOを使ってDBからユーザー検索（認証処理）
        UserDAO dao = new UserDAO();
        User user = dao.search(userId, userPass);

        // ユーザーが見つかった場合（ログイン成功）
        if (user != null) {
            // セッションにユーザー情報を保存（ログイン状態を保持）
            session.setAttribute("user", user);
            // ログイン成功ページへ遷移
            return "/post/post.jsp";
        } else {
        	// ログインIDとパスワードが一致しない場合(ログイン失敗)
        	// ユーザーが見つからなかった場合(ログイン失敗)
        	request.setAttribute("error",
        			"＊ログインに失敗しました\n＊ログインIDまたはパスワードが違います");
        	return "/login/userLogin.jsp";
        }
    }
}