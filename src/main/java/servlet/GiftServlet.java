package servlet;

import java.io.IOException;
import java.util.List;

import bean.Gift;
import dao.GiftDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// ギフト一覧を表示するServlet
@WebServlet("/GiftServlet")
public class GiftServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // DBからギフト一覧を取得
            GiftDAO dao = new GiftDAO();
            List<Gift> giftList = dao.selectAll();

            // ギフト一覧をJSPに渡す
            request.setAttribute("giftList", giftList);

            // ギフト画面を表示
            request.getRequestDispatcher("/gift/gift.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "ギフト一覧の取得に失敗しました。", e);
        }
    }
}