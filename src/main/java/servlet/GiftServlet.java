
package servlet;

import java.util.List;

import bean.Gift;
import dao.GiftDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// ギフト一覧・詳細を表示するServlet
public class GiftServlet extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 文字化けを防ぐ
        request.setCharacterEncoding("UTF-8");

        // ログイン状態を確認する
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {

            // ログインしていない場合はログイン画面へ
            return "/login/userLogin.jsp";
        }

        try {

            // URLからギフトIDを取得
            String gift = request.getParameter("gift");

            // ギフト詳細画面の場合
            if (gift != null && !gift.isEmpty()) {

                String giftName = "花束";
                String giftPrice = "3,000";
                String giftDescription =
                        "やさしい色合いの花束です。";

                // ギフトの種類ごとに情報を設定
                if ("sweets".equals(gift)) {

                    giftName = "スイーツセット";
                    giftPrice = "2,500";
                    giftDescription =
                            "いろいろな味を楽しめるスイーツセットです。";

                } else if ("cafe".equals(gift)) {

                    giftName = "カフェチケット";
                    giftPrice = "1,500";
                    giftDescription =
                            "カフェでゆっくり過ごせるチケットです。";

                } else if ("handcream".equals(gift)) {

                    giftName = "ハンドクリーム";
                    giftPrice = "2,000";
                    giftDescription =
                            "手肌をいたわるハンドクリームです。";

                } else if ("photo".equals(gift)) {

                    giftName = "フォトフレーム";
                    giftPrice = "2,800";
                    giftDescription =
                            "大切な思い出を飾れるフォトフレームです。";

                } else if ("goods".equals(gift)) {

                    giftName = "雑貨セット";
                    giftPrice = "3,500";
                    giftDescription =
                            "日常で使える雑貨を集めたセットです。";

                } else if (!"flower".equals(gift)) {

                    // 存在しないギフトの場合
                    request.setAttribute(
                            "errorMessage",
                            "指定されたギフトが見つかりません。");

                    return "/gift/gift.jsp";
                }

                // ギフト詳細情報をJSPに渡す
                request.setAttribute("giftName", giftName);
                request.setAttribute("giftPrice", giftPrice);
                request.setAttribute(
                        "giftDescription", giftDescription);

                // ギフト詳細画面へ
                return "/gift/giftdetail.jsp";

            } else {

                // ギフト一覧をDBから取得
                GiftDAO dao = new GiftDAO();

                List<Gift> giftList = dao.selectAll();

                // ギフト一覧をJSPに渡す
                request.setAttribute("giftList", giftList);

                // ギフト一覧画面へ
                return "/gift/gift.jsp";
            }

        } catch (Exception e) {

            throw new Exception(
                    "ギフト情報の取得に失敗しました。", e);
        }
    }
}
