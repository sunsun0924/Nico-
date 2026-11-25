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
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "/login/userLogin.jsp";
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "/login/userLogin.jsp";
        }

        String myUserId = user.getUserId();

        if (myUserId == null || myUserId.trim().isEmpty()) {
            return "/login/userLogin.jsp";
        }

        myUserId = myUserId.trim();

        String partnerUserId = request.getParameter("partnerId");

        // POST：メッセージ送信
        if ("POST".equalsIgnoreCase(request.getMethod())) {

            if (partnerUserId == null
                    || partnerUserId.trim().isEmpty()
                    || partnerUserId.trim().equals(myUserId)) {

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

                ChatRoomDAO dao = new ChatRoomDAO();

                boolean result = dao.insertMessage(chat);

                if (!result) {
                    throw new ServletException(
                        "メッセージの登録に失敗しました。"
                    );
                }
            }

            response.sendRedirect(
                request.getContextPath()
                + "/servlet.ChatRoomServlet.action?partnerId="
                + java.net.URLEncoder.encode(partnerUserId, "UTF-8")
            );

            return null;
        }

        // GET：チャット画面を開く
        if (partnerUserId == null || partnerUserId.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath()
                + "/servlet.ChatListServlet.action"
            );

            return null;
        }

        partnerUserId = partnerUserId.trim();

        if (partnerUserId.equals(myUserId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        ChatRoomDAO dao = new ChatRoomDAO();

        // ① 相手から届いた未読メッセージを既読にする
        dao.markMessagesAsRead(myUserId, partnerUserId);

        // ② メッセージ一覧を取得
        request.setAttribute(
            "messageList",
            dao.getMessageList(myUserId, partnerUserId)
        );

        // ③ 相手のユーザー名を取得
        String partnerUserName = dao.getUserName(partnerUserId);

        request.setAttribute("partnerUserId", partnerUserId);

        request.setAttribute(
            "partnerUserName",
            (partnerUserName != null && !partnerUserName.trim().isEmpty())
                ? partnerUserName
                : partnerUserId
        );

        // ④ 自分のユーザーIDをJSPに渡す
        request.setAttribute("myUserId", myUserId);

        return "/chat/chat_room.jsp";
    }
}