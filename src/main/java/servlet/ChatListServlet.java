package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatListBean;
import dao.ChatListDAO;
import dao.UserDAO;

@WebServlet("/chat-list")
public class ChatListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer myUserId = (Integer) session.getAttribute("userId");
        
        // ログイン画面のパスを /login/userLogin.jsp に統一
        if (myUserId == null) {
            response.sendRedirect(request.getContextPath() + "/login/userLogin.jsp");
            return;
        }

        try {
            // ユーザーが0件の場合にテストデータを自動追加
            checkAndInsertTestUser();

            // チャット一覧の取得
            ChatListDAO chatListDAO = new ChatListDAO();
            List<ChatListBean> chatList = chatListDAO.getChatList(myUserId);
            request.setAttribute("chatList", chatList);

            // 全ユーザー一覧の取得（新規チャット用）
            UserDAO userDAO = new UserDAO();
            List<ChatListBean> allUsers = userDAO.getAllUsers(myUserId);
            request.setAttribute("allUsers", allUsers);

            // JSPへフォワード
            request.getRequestDispatcher("/chat/chat_list.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    // テストデータの自動追加メソッド
    private void checkAndInsertTestUser() {
        String sql = "INSERT INTO public.\"user\" (user_id, user_pass, user_name) " +
                     "SELECT 'user2', 'pass123', 'みさき' WHERE NOT EXISTS (SELECT 1 FROM public.\"user\" LIMIT 1);";


    }
}