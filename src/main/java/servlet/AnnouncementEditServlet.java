

package servlet;

import bean.Admin;
import bean.Announcement;
import dao.AnnouncementDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// お知らせの編集処理を行うクラス
public class AnnouncementEditServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // ========================================
        // 管理者ログイン確認
        // ========================================

        HttpSession session = request.getSession(false);

        // 管理者以外は編集できない
        if (session == null ||
            !(session.getAttribute("admin") instanceof Admin)) {

            return "/login/userLogin.jsp";
        }

        // ========================================
        // フォームから編集内容を取得
        // ========================================

        String announcementIdText =
                request.getParameter("announcementId");

        String announcementText =
                request.getParameter("announcementText");

        // ========================================
        // 入力チェック
        // ========================================

        if (announcementIdText == null ||
            announcementText == null ||
            announcementText.trim().isEmpty()) {

            session.setAttribute(
                "message",
                "お知らせの内容を入力してください。"
            );

            return "/servlet.PostServlet.action";
        }

        announcementText = announcementText.trim();

        // お知らせ本文は200文字以内
        if (announcementText.length() > 200) {

            session.setAttribute(
                "message",
                "お知らせは200文字以内で入力してください。"
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
        // お知らせを更新
        // ========================================

        Announcement announcement = new Announcement();

        announcement.setAnnouncementId(announcementId);
        announcement.setAnnouncementText(announcementText);

        AnnouncementDAO dao = new AnnouncementDAO();

        int result = dao.update(announcement);

        // ========================================
        // 更新結果
        // ========================================

        if (result > 0) {

            session.setAttribute(
                "message",
                "お知らせを更新しました！"
            );

        } else {

            session.setAttribute(
                "message",
                "お知らせを更新できませんでした。"
            );

        }

        // 投稿画面に戻る
        return "/servlet.PostServlet.action";
    }
}
