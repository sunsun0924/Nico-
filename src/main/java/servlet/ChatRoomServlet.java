
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


        // ========================================
        // ① セッション確認
        // ========================================

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "/login/userLogin.jsp";
        }


        // ========================================
        // ② ログインユーザー取得
        // ========================================

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "/login/userLogin.jsp";
        }

        String myUserId = user.getUserId();

        if (myUserId == null || myUserId.trim().isEmpty()) {
            return "/login/userLogin.jsp";
        }

        myUserId = myUserId.trim();


        // チャット相手のユーザーID
        String partnerUserId = request.getParameter("partnerId");


        // ========================================
        // ③ POST：メッセージ送信
        // ========================================

        if ("POST".equalsIgnoreCase(request.getMethod())) {

            // 相手のIDを確認
            if (partnerUserId == null
                    || partnerUserId.trim().isEmpty()
                    || partnerUserId.trim().equals(myUserId)) {

                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                return null;

            }

            partnerUserId = partnerUserId.trim();


            // 送信メッセージを取得
            String messageText = request.getParameter("message");


            // 空メッセージでなければDBに保存
            if (messageText != null && !messageText.trim().isEmpty()) {

                ChatRoomBean chat = new ChatRoomBean();

                chat.setSenderId(myUserId);
                chat.setReceiverId(partnerUserId);
                chat.setMessage(messageText.trim());


                // メッセージ保存
                ChatRoomDAO dao = new ChatRoomDAO();

                boolean result = dao.insertMessage(chat);

                if (!result) {

                    throw new ServletException(
                        "メッセージの登録に失敗しました。"
                    );

                }

            }


            // 保存処理後、同じ相手とのチャット画面へ戻る
            response.sendRedirect(
                request.getContextPath()
                + "/servlet.ChatRoomServlet.action?partnerId="
                + java.net.URLEncoder.encode(
                    partnerUserId,
                    "UTF-8"
                )
            );

            return null;

        }


        // ========================================
        // ④ GET：チャット画面を開く
        // ========================================

        // 相手のIDが指定されていない場合は一覧へ戻す
        if (partnerUserId == null
                || partnerUserId.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath()
                + "/servlet.ChatListServlet.action"
            );

            return null;

        }

        partnerUserId = partnerUserId.trim();


        // 自分自身とのチャットは禁止
        if (partnerUserId.equals(myUserId)) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST
            );

            return null;

        }


        // ========================================
        // ⑤ 過去のメッセージを取得
        // ========================================

        ChatRoomDAO dao = new ChatRoomDAO();

        request.setAttribute(
            "messageList",
            dao.getMessageList(myUserId, partnerUserId)
        );


        // ========================================
        // ⑥ 相手のユーザー名を取得
        // ========================================

        String partnerUserName = dao.getUserName(partnerUserId);

        request.setAttribute(
            "partnerUserId",
            partnerUserId
        );

        request.setAttribute(
            "partnerUserName",
            (partnerUserName != null
                && !partnerUserName.trim().isEmpty())
                    ? partnerUserName
                    : partnerUserId
        );


        // ========================================
        // ⑦ 自分のユーザーIDをJSPに渡す
        // ========================================

        request.setAttribute(
            "myUserId",
            myUserId
        );


        // ========================================
        // ⑧ チャット画面を表示
        // ========================================

        // 重要：
        // GETではchatテーブルにもchat_messageテーブルにも
        // INSERTしない。
        // メッセージ送信時のPOSTだけで登録する。

        return "/chat/chat_room.jsp";

    }

}
