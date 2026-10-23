package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dao.GiftDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GiftServlet")
public class GiftServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // ギフト一覧をDBから取得
            GiftDAO dao = new GiftDAO();

            List<Map<String, String>> giftList = dao.selectAll();

            // JSPにギフト一覧を渡す
            request.setAttribute("giftList", giftList);

            // ギフト画面を表示
            request.getRequestDispatcher("/gift/gift.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("ギフト一覧の取得に失敗しました。", e);
        }
    }
}