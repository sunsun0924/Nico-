package servlet;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.PasswordUtil;


public class RegisterServlet extends Action {

	// executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
    	
    	// フォームから送信されたユーザーIDを取得
        String user_id = request.getParameter("user_id");
        
        // フォームから送信されたパスワードを取得
        String user_pass = request.getParameter("user_pass");
    
        // データベース操作(登録･検索)を行うためのDAOを作成
        UserDAO dao = new UserDAO();
            
        // 未入力チェック（最小）
        if (user_id == null || user_id.equals("") ||
        		user_pass == null || user_pass.equals("")) {
        	request.setAttribute("error",
        			"＊未入力があります\n＊すべて入力してください");
            return "/register/register.jsp";
        }
            
        // 重複確認
        if (dao.isExist(user_id)) {
        	request.setAttribute("error1", "＊このユーザーIDは既に使われています");
            return "/register/register.jsp";
        }
        
        // パスワードをハッシュ化
        String hashpassword = PasswordUtil.hash(user_pass);

        // 登録処理
        // データ保存用オブジェクトを作成
        User staff = new User(); 
         
        // 1番目の ? にユーザーIDを設定
        staff.setUser_id(user_id);
        
        // 2番目の ? にパスワードを設定
        staff.setUser_pass(user_pass);

        // SQLを実行
        int line = dao.insert(user);

        // lineは「更新された行数」
        if (line > 0) {
        	return "/post/post.jsp";
        } else {
            request.setAttribute("error", "＊登録に失敗しました");
            return "/register/register.jsp";
        }

       
    }
}