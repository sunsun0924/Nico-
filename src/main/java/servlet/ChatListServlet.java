package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bean.ChatListBean;
import dao.ChatListDAO;
import dao.UserDAO; // 追加

@WebServlet("/chat-list") // ※ご自身の環境のパスに合わせてください
public class ChatListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer myUserId = (Integer) session.getAttribute("userId"); // セッションからログイン中のユーザーIDを取得
        
        if (myUserId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // 1. 既存のチャット一覧を取得
            ChatListDAO chatListDAO = new ChatListDAO();
            List<ChatListBean> chatList = chatListDAO.getChatList(myUserId);
            request.setAttribute("chatList", chatList);

            // 2. 【追加】新規チャット用に自分以外の全ユーザーを取得
            UserDAO userDAO = new UserDAO();
            List<ChatListBean> allUsers = userDAO.getAllUsers(myUserId);
            request.setAttribute("allUsers", allUsers);

            // 3. JSPへフォワード
            request.getRequestDispatcher("/chat/chat_list.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}