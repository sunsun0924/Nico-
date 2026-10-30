package servlet;

import java.sql.Date;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UserInformationServlet extends Action {

    public String execute(
            HttpServletRequest req,
            HttpServletResponse resp) throws Exception {

        req.setCharacterEncoding("UTF-8");

        // セッションからログインユーザーを取得
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return "/login.jsp";
        }

        User user = (User) session.getAttribute("user");

        // JSPから入力内容を取得
        String userMail = req.getParameter("userMail");
        String userNumber = req.getParameter("userNumber");
        String userAddress = req.getParameter("userAddress");
        String birthday = req.getParameter("userBirthday");

        // 未入力チェック
        if (userMail == null || userMail.isEmpty()
                || userNumber == null || userNumber.isEmpty()
                || userAddress == null || userAddress.isEmpty()
                || birthday == null || birthday.isEmpty()) {

            req.setAttribute(
                "error",
                "すべての項目を入力してください。"
            );

            return "/mypage/user_information.jsp";
        }

        // 誕生日をDate型に変換
        Date userBirthday;

        try {
            userBirthday = Date.valueOf(birthday);

        } catch (IllegalArgumentException e) {

            req.setAttribute(
                "error",
                "誕生日を正しい形式で入力してください。"
            );

            return "/mypage/user_information.jsp";
        }

        // 更新する情報をUserにセット
        user.setUserMail(userMail);
        user.setUserNumber(userNumber);
        user.setUserAddress(userAddress);
        user.setUserBirthday(userBirthday);

        // DBを更新
        UserDAO dao = new UserDAO();

        int result = dao.updateUserInfo(user);

        if (result > 0) {

            // セッション情報を更新
            session.setAttribute("user", user);

            req.setAttribute(
                "message",
                "ユーザー情報を変更しました。"
            );

        } else {

            req.setAttribute(
                "error",
                "ユーザー情報の変更に失敗しました。"
            );
        }

        return "/mypage/user_information.jsp";
    }
}