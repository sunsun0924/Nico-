package servlet;

import java.util.List;
import bean.ChatRoomBean;
import dao.ChatRoomDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ChatRoomServlet extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String myUserId = "userA";
        String partnerUserId = request.getParameter("partnerId");

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            if (partnerUserId == null || partnerUserId.trim().isEmpty() || partnerUserId.trim().equals(myUserId)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
            partnerUserId = partnerUserId.trim();
            String messageText = request.getParameter("message");

            if (messageText != null && !messageText.trim().isEmpty()) {
                ChatRoomBean chat = new ChatRoomBean();
                chat.setSenderId(myUserId);
                chat.setReceiverId(partnerUserId);
                chat.setMessage(messageText.trim());
                if (!new ChatRoomDAO().insertMessage(chat)) {
                    throw new ServletException("メッセージの登録に失敗しました。");
                }
            }
            response.sendRedirect(request.getContextPath() + "/chat.ChatRoomServlet.action?partnerId=" + partnerUserId);
            return null;
        }

        if (partnerUserId == null || partnerUserId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/chat.ChatListServlet.action");
            return null;
        }
        partnerUserId = partnerUserId.trim();

        if (partnerUserId.equals(myUserId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        ChatRoomDAO dao = new ChatRoomDAO();
        request.setAttribute("messageList", dao.getMessageList(myUserId, partnerUserId));
        
        String partnerUserName = dao.getUserName(partnerUserId);
        request.setAttribute("partnerUserId", partnerUserId);
        request.setAttribute("partnerUserName", (partnerUserName != null && !partnerUserName.trim().isEmpty()) ? partnerUserName : partnerUserId);

        return "/chat/chat_room.jsp";
    }
}