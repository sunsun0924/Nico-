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

@WebServlet("/chat-list")
public class ChatListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // セッションからログイン中のユーザーIDを取得
        HttpSession session = request.getSession();
        Integer myUserId = (Integer) session.getAttribute("userId");
        
        // ログインしていない場合はログイン画面等へリダイレクト
        if (myUserId == null) {
            response.sendRedirect("login/userLogin.jsp");
            return;
        }

        try {
            // ChatListDAOを使ってチャット一覧を取得
            ChatListDAO dao = new ChatListDAO();
            List<ChatListBean> chatList = dao.getChatList(myUserId);

            // リクエストスコープにリストを格納
            request.setAttribute("chatList", chatList);

            // チャット一覧のJSPへフォワード
            request.getRequestDispatcher("/chat/chat_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}