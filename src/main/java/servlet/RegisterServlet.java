package servlet;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;


public class RegisterServlet extends Action {

	// executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
    	
    	// フォームから送信されたユーザーIDを取得
        String userId = request.getParameter("userId");
        
        // フォームから送信されたパスワードを取得
        String userPass = request.getParameter("userPass");
    
        // データベース操作(登録･検索)を行うためのDAOを作成
        UserDAO dao = new UserDAO();
            
        // 未入力チェック（最小）
        if (userId == null || userId.equals("") ||
        		userPass == null || userPass.equals("")) {
        	request.setAttribute("errorMessage",
        			"＊未入力があります\n＊すべて入力してください");
            return "/register/register.jsp";
        }
            
        // 重複確認
        if (dao.isExist(userId)) {
        	request.setAttribute("errorMessage", "＊このユーザーIDは既に使われています");
            return "/register/register.jsp";
        }
        
        // パスワードをハッシュ化
        // String hashpassword = PasswordUtil.hash(userPass);

        // 登録処理
        // データ保存用オブジェクトを作成
        User user = new User(); 
         
        // 1番目の ? にユーザーIDを設定
        user.setUserId(userId);
        
        // 2番目の ? にパスワードを設定
        user.setUserPass(userPass);

        // SQLを実行
        int line = dao.insert(user);

        // lineは「更新された行数」
        if (line > 0) {
        	request.setAttribute("message", "＊ユーザーの新規登録が完了しました");
        	return "/post/post.jsp";
        } else {
            request.setAttribute("errorMessage", "＊登録に失敗しました");
            return "/register/register.jsp";
        }

       
    }
}