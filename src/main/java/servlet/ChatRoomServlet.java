package servlet;

import bean.ChatRoomBean;
import bean.User;
import dao.ChatRoomDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ChatRoomServlet extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // セッションを取得
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "/login/userLogin.jsp";
        }

        // ログイン中のユーザー情報を取得
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "/login/userLogin.jsp";
        }

        String myUserId = user.getUserId();
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
            response.sendRedirect(request.getContextPath() + "/servlet.ChatRoomServlet.action?partnerId=" + partnerUserId);
            return null;
        }

        if (partnerUserId == null || partnerUserId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/servlet.ChatListServlet.action");
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
        
        // ▼ 自分のユーザーIDをJSPに渡す設定を追加
        request.setAttribute("myUserId", myUserId);

        return "/chat/chat_room.jsp";
    }
}