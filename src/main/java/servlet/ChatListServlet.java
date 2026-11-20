
package servlet;

import java.util.List;

import bean.ChatListBean;
import bean.User;
import dao.ChatListDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ChatListServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "/login/userLogin.jsp";
        }

        // ログイン中のユーザーを取得
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "/login/userLogin.jsp";
        }

        String myUserId = user.getUserId();

        String action = request.getParameter("action");

        ChatListDAO dao = new ChatListDAO();


        // ========================================
        // ① チャット一覧取得（JSON）
        // ========================================

        if ("list".equals(action)) {

            List<ChatListBean> chatList =
                    dao.getChatList(myUserId);

            response.setContentType("application/json; charset=UTF-8");

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < chatList.size(); i++) {

                ChatListBean chat = chatList.get(i);

                if (i > 0) {
                    json.append(",");
                }

                json.append("{");

                json.append("\"userId\":\"")
                    .append(escapeJson(chat.getUserId()))
                    .append("\",");

                json.append("\"userName\":\"")
                    .append(escapeJson(chat.getUserName()))
                    .append("\",");

                json.append("\"lastMessage\":\"")
                    .append(escapeJson(chat.getLastMessage()))
                    .append("\",");

                json.append("\"lastTime\":\"")
                    .append(escapeJson(chat.getLastTime()))
                    .append("\",");

                json.append("\"unreadCount\":")
                    .append(chat.getUnreadCount());

                json.append("}");
            }

            json.append("]");

            response.getWriter().write(json.toString());

            return null;
        }


        // ========================================
        // ② 新規チャット用ユーザー一覧（JSON）
        // ========================================

        if ("json".equals(action)) {

            List<ChatListBean> userList =
                    dao.getAllUsers(myUserId);

            response.setContentType("application/json; charset=UTF-8");

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < userList.size(); i++) {

                ChatListBean u = userList.get(i);

                if (i > 0) {
                    json.append(",");
                }

                json.append("{");

                json.append("\"userId\":\"")
                    .append(escapeJson(u.getUserId()))
                    .append("\",");

                json.append("\"userName\":\"")
                    .append(escapeJson(u.getUserName()))
                    .append("\"");

                json.append("}");
            }

            json.append("]");

            response.getWriter().write(json.toString());

            return null;
        }


        // ========================================
        // ③ 通常のチャット一覧画面表示
        // ========================================

        request.setAttribute(
                "chatList",
                dao.getChatList(myUserId)
        );

        return "/chat/chat_list.jsp";
    }


    // ========================================
    // JSON文字列のエスケープ
    // ========================================

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (char c : value.toCharArray()) {

            switch (c) {

                case '"':
                    sb.append("\\\"");
                    break;

                case '\\':
                    sb.append("\\\\");
                    break;

                case '\b':
                    sb.append("\\b");
                    break;

                case '\f':
                    sb.append("\\f");
                    break;

                case '\n':
                    sb.append("\\n");
                    break;

                case '\r':
                    sb.append("\\r");
                    break;

                case '\t':
                    sb.append("\\t");
                    break;

                default:

                    if (c < 0x20) {
                        sb.append(
                            String.format("\\u%04x", (int) c)
                        );
                    } else {
                        sb.append(c);
                    }

                    break;
            }
        }

        return sb.toString();
    }
}
