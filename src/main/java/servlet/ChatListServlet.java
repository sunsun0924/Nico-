package servlet;

import java.io.IOException;
import java.util.ArrayList; // ★追加
import java.util.List;

import bean.ChatListBean;
import dao.ChatListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        // ==========================================
        // ★ テスト用ダミーユーザーの設定
        // ==========================================
        String myUserId = "userA";

        ChatListDAO dao = new ChatListDAO();

        try {

            // 新規チャット用ユーザー一覧（モーダル用JSONなど）
            if (jsonRequest) {
                List<ChatListBean> userList = dao.getAllUsers(myUserId);

                response.setContentType("application/json; charset=UTF-8");
                StringBuilder json = new StringBuilder();
                json.append("[");
                for (int i = 0; i < userList.size(); i++) {
                    ChatListBean user = userList.get(i);
                    if (i > 0) {
                        json.append(",");
                    }
                    json.append("{");
                    json.append("\"userId\":\"").append(escapeJson(user.getUserId())).append("\",");
                    json.append("\"userName\":\"").append(escapeJson(user.getUserName())).append("\"");
                    json.append("}");
                }
                json.append("]");
                response.getWriter().write(json.toString());
                return;
            }

            // ==========================================
            // ★ テーブル未作成時の画面表示テスト用（空のリストを渡す）
            // ==========================================
            List<ChatListBean> chatList = new ArrayList<>();
            
            /* -- DB接続を有効にする場合はこちらのコメントアウトを解除 --
            List<ChatListBean> chatList = dao.getChatList(myUserId);
            ----------------------------------------------------- */

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
                case '"': result.append("\\\""); break;
                case '\\': result.append("\\\\"); break;
                case '\b': result.append("\\b"); break;
                case '\f': result.append("\\f"); break;
                case '\n': result.append("\\n"); break;
                case '\r': result.append("\\r"); break;
                case '\t': result.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        result.append(String.format("\\u%04x", (int) c));
                    } else {
                        result.append(c);
                    }
                    break;
            }
        }
        return result.toString();
    }
}