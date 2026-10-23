package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.CompanyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CompanyRegister")
public class CompanyRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // フォームから入力値を取得
        String companyId = request.getParameter("companyId");
        String companyName = request.getParameter("companyName");

        // 未入力チェック
        if (companyId == null || companyName == null
                || companyId.trim().isEmpty()
                || companyName.trim().isEmpty()) {

            request.setAttribute("errorMessage",
                    "企業IDと企業名を入力してください。");

            request.getRequestDispatcher(
                    "/company/company.jsp")
                    .forward(request, response);
            return;
        }

        companyId = companyId.trim();
        companyName = companyName.trim();

        // 企業IDの文字数チェック
        if (companyId.length() != 4) {
            request.setAttribute("errorMessage",
                    "企業IDは4文字で入力してください。");

            request.getRequestDispatcher(
                    "/company/company.jsp")
                    .forward(request, response);
            return;
        }

        // 企業名の文字数チェック
        if (companyName.length() > 50) {
            request.setAttribute("errorMessage",
                    "企業名は50文字以内で入力してください。");

            request.getRequestDispatcher(
                    "/company/company.jsp")
                    .forward(request, response);
            return;
        }

        try {
            // 企業情報をDBに登録
            CompanyDAO dao = new CompanyDAO();
            dao.insert(companyId, companyName);

            // 登録成功
            request.setAttribute("successMessage",
                    "企業を登録しました。");

            request.getRequestDispatcher(
                    "/company/company.jsp")
                    .forward(request, response);

        } catch (SQLException e) {

            // 企業IDが重複している場合
            if ("23505".equals(e.getSQLState())) {
                request.setAttribute("errorMessage",
                        "この企業IDはすでに登録されています。");
            } else {
                e.printStackTrace();
                request.setAttribute("errorMessage",
                        "企業登録に失敗しました。");
            }

            request.getRequestDispatcher(
                    "/company/company.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}