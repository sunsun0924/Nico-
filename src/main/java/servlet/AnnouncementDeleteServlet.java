
package servlet;

import bean.Admin;
import dao.AnnouncementDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// お知らせの削除処理を行うクラス
public class AnnouncementDeleteServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // ========================================
        // 管理者ログイン確認
        // ========================================

        HttpSession session = request.getSession(false);

        // 管理者以外は削除できない
        if (session == null ||
            !(session.getAttribute("admin") instanceof Admin)) {

            return "/login/userLogin.jsp";
        }

        // ========================================
        // 削除対象のお知らせIDを取得
        // ========================================

        String announcementIdText =
                request.getParameter("announcementId");

        if (announcementIdText == null ||
            announcementIdText.trim().isEmpty()) {

            session.setAttribute(
                "message",
                "削除するお知らせが指定されていません。"
            );

            return "/servlet.PostServlet.action";
        }

        // ========================================
        // お知らせIDの確認
        // ========================================

        long announcementId;

        try {

            announcementId = Long.parseLong(announcementIdText);

            if (announcementId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            session.setAttribute(
                "message",
                "お知らせの指定が正しくありません。"
            );

            return "/servlet.PostServlet.action";
        }

        // ========================================
        // お知らせを削除
        // ========================================

        AnnouncementDAO dao = new AnnouncementDAO();

        int result = dao.delete(announcementId);

        // ========================================
        // 削除結果
        // ========================================

        if (result > 0) {

            session.setAttribute(
                "deleteMessage",
                "お知らせを削除しました！"
            );

        } else {

            session.setAttribute(
                "message",
                "お知らせを削除できませんでした。"
            );

        }

        // 投稿画面に戻る
        return "/servlet.PostServlet.action";
    }
}
