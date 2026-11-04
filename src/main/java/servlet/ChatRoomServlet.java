package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.User;
import bean.ChatRoomBean;
import dao.ChatRoomDAO;

@WebServlet("/chat-room")
public class ChatRoomServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET：チャットルーム表示
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login/userLogin.jsp"
            );
            return;
        }

        User loginUser = (User) session.getAttribute("user");
        String myUserId = loginUser.getUserId();

        String partnerUserId = request.getParameter("partnerId");

        try {
            if (partnerUserId == null
                    || partnerUserId.trim().isEmpty()) {

                response.sendRedirect(
                    request.getContextPath() + "/chat-list"
                );
                return;
            }

            partnerUserId = partnerUserId.trim();

            if (partnerUserId.equals(myUserId)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            ChatRoomDAO dao = new ChatRoomDAO();

            List<ChatRoomBean> messageList =
                dao.getMessageList(myUserId, partnerUserId);

            request.setAttribute("messageList", messageList);
            request.setAttribute("partnerUserId", partnerUserId);

            request.getRequestDispatcher(
                "/chat/chat_room.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            throw new ServletException("チャットルームの表示に失敗しました。", e);
        }
    }

    // POST：メッセージ送信
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login/userLogin.jsp"
            );
            return;
        }

        User loginUser = (User) session.getAttribute("user");
        String myUserId = loginUser.getUserId();

        String partnerUserId = request.getParameter("partnerId");
        String messageText = request.getParameter("message");

        try {
            if (partnerUserId == null
                    || partnerUserId.trim().isEmpty()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            partnerUserId = partnerUserId.trim();

            if (partnerUserId.equals(myUserId)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (messageText != null && !messageText.trim().isEmpty()) {

                ChatRoomBean chat = new ChatRoomBean();

                chat.setSenderId(myUserId);
                chat.setReceiverId(partnerUserId);
                chat.setMessage(messageText.trim());

                ChatRoomDAO dao = new ChatRoomDAO();

                boolean success = dao.insertMessage(chat);

                if (!success) {
                    throw new ServletException(
                        "メッセージの登録に失敗しました。"
                    );
                }
            }

            response.sendRedirect(
                request.getContextPath()
                + "/chat-room?partnerId="
                + partnerUserId
            );

        } catch (Exception e) {
            throw new ServletException("メッセージの送信に失敗しました。", e);
        }
    }
}