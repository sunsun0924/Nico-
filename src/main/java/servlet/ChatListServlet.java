package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatListBean;
import dao.UserDAO;

@WebServlet("/chat-list")
public class ChatListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer myUserId = (Integer) session.getAttribute("userId");
        if (myUserId == null) {
            myUserId = 3; // テスト用フォールバック
        }

        String action = request.getParameter("action");

        try {
            UserDAO userDAO = new UserDAO();

            // 「＋」ボタンからの非同期リクエスト（JSONを返す）
            if ("json".equals(action)) {
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();
                List<ChatListBean> allUsers = userDAO.getAllUsers(myUserId);

                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < allUsers.size(); i++) {
                    ChatListBean u = allUsers.get(i);
                    json.append("{")
                        .append("\"userId\":\"").append(u.getUserId()).append("\",")
                        .append("\"userName\":\"").append(u.getUserName()).append("\"")
                        .append("}");
                    if (i < allUsers.size() - 1) json.append(",");
                }
                json.append("]");
                out.write(json.toString());
                return;
            }

            // 通常のページ表示リクエスト
            List<ChatListBean> chatList = userDAO.getChatList(myUserId);
            request.setAttribute("chatList", chatList);
            request.getRequestDispatcher("/chat/chat_list.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            if ("json".equals(action)) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("[]");
            } else {
                throw new ServletException(e);
            }
        }
    }
}