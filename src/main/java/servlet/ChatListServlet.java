package servlet;

import java.util.List;
import bean.ChatListBean;
import dao.ChatListDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ChatListServlet extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        boolean jsonRequest = "json".equals(request.getParameter("action"));
        String myUserId = "userA";
        ChatListDAO dao = new ChatListDAO();

        if (jsonRequest) {
            List<ChatListBean> userList = dao.getAllUsers(myUserId);
            response.setContentType("application/json; charset=UTF-8");
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < userList.size(); i++) {
                ChatListBean u = userList.get(i);
                if (i > 0) json.append(",");
                json.append("{\"userId\":\"").append(escapeJson(u.getUserId()))
                    .append("\",\"userName\":\"").append(escapeJson(u.getUserName())).append("\"}");
            }
            json.append("]");
            response.getWriter().write(json.toString());
            return null;
        }

        request.setAttribute("chatList", dao.getChatList(myUserId));
        return "/chat/chat_list.jsp";
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }
}