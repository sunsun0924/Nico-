package servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.ChatRoomBean;
import dao.ChatRoomDAO;

@WebServlet("/chat-room")
public class ChatRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer myUserId = (Integer) session.getAttribute("userId");
        
        if (myUserId == null) {
            response.sendRedirect(request.getContextPath() + "/login/userLogin.jsp");
            return;
        }

        String partnerIdStr = request.getParameter("partnerId");
        if (partnerIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/chat-list");
            return;
        }
        int partnerUserId = Integer.parseInt(partnerIdStr);

        try {
            ChatRoomDAO dao = new ChatRoomDAO();
            List<ChatRoomBean> messageList = dao.getMessageList(myUserId, partnerUserId);

            request.setAttribute("messageList", messageList);
            request.setAttribute("partnerUserId", partnerUserId);

            request.getRequestDispatcher("/chat/chat_room.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Integer myUserId = (Integer) session.getAttribute("userId");
        
        if (myUserId == null) {
            response.sendRedirect(request.getContextPath() + "/login/userLogin.jsp");
            return;
        }

        String partnerIdStr = request.getParameter("partnerId");
        String messageText = request.getParameter("message");

        if (partnerIdStr != null && messageText != null && !messageText.trim().isEmpty()) {
            int partnerUserId = Integer.parseInt(partnerIdStr);

            try {
                ChatRoomBean chat = new ChatRoomBean();
                chat.setSenderId(myUserId);
                chat.setReceiverId(partnerUserId);
                chat.setMessage(messageText);

                ChatRoomDAO dao = new ChatRoomDAO();
                dao.insertMessage(chat);

            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }

        response.sendRedirect(request.getContextPath() + "/chat-room?partnerId=" + partnerIdStr);
    }
}