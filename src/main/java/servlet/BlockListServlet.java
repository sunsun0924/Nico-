
package servlet;

import java.io.IOException;

import bean.User;
import dao.BlockListDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class BlockListServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        // ========================================
        // ① ログインユーザーを取得
        // ========================================

        HttpSession session = request.getSession(false);

        if (session == null) {
            writeJson(response, false, "ログイン情報がありません。");
            return null;
        }

        User loginUser = (User) session.getAttribute("user");

        if (loginUser == null || loginUser.getUserId() == null) {
            writeJson(response, false, "ログイン情報がありません。");
            return null;
        }

        String myUserId = loginUser.getUserId().trim();

        // ========================================
        // ② 処理内容を取得
        // ========================================

        String action = request.getParameter("action");

        if (action == null) {
            writeJson(response, false, "処理内容が指定されていません。");
            return null;
        }

        BlockListDAO dao = new BlockListDAO();

        try {

            // ========================================
            // ③ ブロック登録
            // ========================================

            if ("block".equals(action)) {

                String[] userIds =
                    request.getParameterValues("userId");

                if (userIds == null || userIds.length == 0) {
                    writeJson(response, false,
                        "ブロックするユーザーが選択されていません。");
                    return null;
                }

                int count = 0;

                for (String userId : userIds) {

                    if (userId == null || userId.trim().isEmpty()) {
                        continue;
                    }

                    String blockedUserId = userId.trim();

                    // 自分自身はブロックできない
                    if (myUserId.equals(blockedUserId)) {
                        continue;
                    }

                    // DBに登録
                    dao.blockUser(myUserId, blockedUserId);

                    count++;
                }

                if (count == 0) {
                    writeJson(response, false,
                        "有効なブロック対象がありません。");
                    return null;
                }

                writeJson(response, true, "ブロックしました。");
                return null;
            }

            // ========================================
            // ④ ブロック解除
            // ========================================

            if ("unblock".equals(action)) {

                String blockedUserId =
                    request.getParameter("userId");

                if (blockedUserId == null
                        || blockedUserId.trim().isEmpty()) {

                    writeJson(response, false,
                        "解除するユーザーが指定されていません。");
                    return null;
                }

                dao.unblockUser(
                    myUserId,
                    blockedUserId.trim()
                );

                writeJson(response, true, "ブロックを解除しました。");
                return null;
            }

            // ========================================
            // ⑤ 不明な処理
            // ========================================

            writeJson(response, false, "不明な処理です。");
            return null;

        } catch (Exception e) {

            request.getServletContext().log(
                "BlockListServletでエラーが発生しました。",
                e
            );

            writeJson(response, false,
                "ブロック処理中にDBエラーが発生しました。");

            return null;
        }
    }

    // ========================================
    // JSONレスポンス
    // ========================================

    private void writeJson(
            HttpServletResponse response,
            boolean success,
            String message) throws IOException {

        String escapedMessage = message
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r");

        response.getWriter().write(
            "{\"success\":" + success +
            ",\"message\":\"" + escapedMessage + "\"}"
        );
    }
}
