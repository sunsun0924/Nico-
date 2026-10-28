package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        
        if (myUserId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
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

    // tryの二重構造をなくし、1つにまとめたメソッド
    private void checkAndInsertTestUser() {
        String url = "jdbc:postgresql://localhost:5432/nico_db";
        String dbUser = "postgres";
        String dbPass = "password"; // 環境に合わせて変更してください

        // データベース接続、カウント取得、インサートを1つの try-with-resources で処理
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
             PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT COUNT(*) FROM public.\"user\"; " +
                 "INSERT INTO public.\"user\" (user_id, user_pass, user_name) " +
                 "SELECT 'user2', 'pass123', 'みさき' WHERE NOT EXISTS (SELECT 1 FROM public.\"user\" LIMIT 1);"
             )) {
            
            // まとめて実行
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}