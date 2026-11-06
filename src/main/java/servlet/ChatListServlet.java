
package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.ChatListBean;
import bean.User;
import dao.ChatListDAO;

@WebServlet("/chat-list")
public class ChatListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET：チャット一覧・新規チャット用ユーザー一覧取得
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        boolean jsonRequest = "json".equals(action);

        HttpSession session = request.getSession(false);

        User loginUser = null;

        if (session != null) {
            loginUser = (User) session.getAttribute("user");
        }

        ChatListDAO dao = new ChatListDAO();

        try {

            // 新規チャット用ユーザー一覧
            if (jsonRequest) {

                List<ChatListBean> userList;

                // ログインしている場合は自分以外を取得
                if (loginUser != null
                        && loginUser.getUserId() != null
                        && !loginUser.getUserId().trim().isEmpty()) {

                    userList = dao.getAllUsers(
                        loginUser.getUserId().trim()
                    );

                } else {

                    // ログインしていない場合は全ユーザーを取得
                    userList = dao.getAllUsers();
                }

                response.setContentType(
                    "application/json; charset=UTF-8"
                );

                StringBuilder json = new StringBuilder();

                json.append("[");

                for (int i = 0; i < userList.size(); i++) {

                    ChatListBean user = userList.get(i);

                    if (i > 0) {
                        json.append(",");
                    }

                    json.append("{");

                    json.append("\"userId\":\"")
                        .append(escapeJson(user.getUserId()))
                        .append("\",");

                    json.append("\"userName\":\"")
                        .append(escapeJson(user.getUserName()))
                        .append("\"");

                    json.append("}");
                }

                json.append("]");

                response.getWriter().write(json.toString());

                return;
            }

            // 通常のチャット一覧はログイン必須
            if (loginUser == null
                    || loginUser.getUserId() == null
                    || loginUser.getUserId().trim().isEmpty()) {

                response.sendRedirect(
                    request.getContextPath() + "/login/userLogin.jsp"
                );

                return;
            }

            String myUserId = loginUser.getUserId().trim();

            // 通常のチャット一覧取得
            List<ChatListBean> chatList =
                dao.getChatList(myUserId);

            request.setAttribute("chatList", chatList);

            request.getRequestDispatcher(
                "/chat/chat_list.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            if (jsonRequest) {

                response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );

                response.setContentType(
                    "application/json; charset=UTF-8"
                );

                response.getWriter().write(
                    "{\"error\":\"ユーザー一覧の取得に失敗しました。\"}"
                );

            } else {

                throw new ServletException(
                    "チャット一覧の取得に失敗しました。", e
                );
            }
        }
    }

    // JSON用の文字列エスケープ
    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {

            char c = value.charAt(i);

            switch (c) {

                case '"':
                    result.append("\\\"");
                    break;

                case '\\':
                    result.append("\\\\");
                    break;

                case '\b':
                    result.append("\\b");
                    break;

                case '\f':
                    result.append("\\f");
                    break;

                case '\n':
                    result.append("\\n");
                    break;

                case '\r':
                    result.append("\\r");
                    break;

                case '\t':
                    result.append("\\t");
                    break;

                default:

                    if (c < 0x20) {
                        result.append(
                            String.format("\\u%04x", (int) c)
                        );
                    } else {
                        result.append(c);
                    }

                    break;
            }
        }

        return result.toString();
    }
}
