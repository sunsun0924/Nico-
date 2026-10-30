package servlet;

import java.util.List;

import bean.Post;
import dao.PostDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 投稿の一覧表示を行うServlet
public class PostServlet extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // ログイン状態を確認
        if (session == null
                || (session.getAttribute("user") == null
                && session.getAttribute("admin") == null)) {

            return "/login/userLogin.jsp";
        }

        try {

            PostDAO dao = new PostDAO();

            List<Post> postList = dao.findAll();

            request.setAttribute("postList", postList);

            return "/post/post.jsp";

        } catch (Exception e) {

            e.printStackTrace();

            throw new Exception("投稿一覧の取得に失敗しました。", e);
        }
    }
}