<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="bean.Admin" %>
<%@ page import="bean.User" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypage.css">

<%
    // セッションからログイン情報を取得
    Admin admin = (Admin) session.getAttribute("admin");
    User user = (User) session.getAttribute("user");

    // 管理者IDを取得
    String adminId = "";

    if (admin != null && admin.getCpUserid() != null) {
        adminId = admin.getCpUserid();
    }

    // 管理者専用マイページか判定
    // IDの文字ではなく、adminセッションの有無で判断
    boolean adminMyPage = (admin != null);

    // 管理者プロフィール画像
    String adminImage = admin != null
            ? admin.getCpUserimg()
            : null;

    if (adminImage == null || adminImage.isEmpty()) {
        adminImage = "default-profile.png";
    }

    // 管理者プロフィール名
    String adminName = admin != null
            ? admin.getCpUsername()
            : null;

    if (adminName == null || adminName.isEmpty()) {
        adminName = adminId;
    }

    // 管理者画像表示位置
    int adminPositionX = admin != null
            ? admin.getCpUserimgPositionX()
            : 50;

    int adminPositionY = admin != null
            ? admin.getCpUserimgPositionY()
            : 50;
%>

<%-- 左側サイドバー --%>
<%@ include file="../sidebar.jsp" %>

<main class="main-container">

    <div class="content-card">

        <%-- ページタイトル --%>
        <div class="card-header">
            <i class="fa-regular fa-user"></i>
            <h2>マイページ</h2>
        </div>

        <%-- プロフィール変更完了メッセージ --%>
        <%
            String mypageMessage =
                (String) session.getAttribute("mypageMessage");

            if (mypageMessage != null) {
                session.removeAttribute("mypageMessage");
        %>

            <div class="success-message">
                <%= mypageMessage %>
            </div>

        <%
            }
        %>

        <%-- 管理者専用マイページ --%>
        <% if (adminMyPage) { %>

            <%-- 管理者情報 --%>
            <div class="user-profile-section">

                <%-- 管理者アイコン --%>
                <div class="user-avatar">
                    <img
                        src="${pageContext.request.contextPath}/images/<%= adminImage %>"
                        alt="プロフィール画像"
                        style="object-position: <%= adminPositionX %>% <%= adminPositionY %>%">
                </div>

                <%-- 管理者名・編集ボタン --%>
                <div class="user-info-text">

                    <span class="user-name">
                        <%= adminName %>
                    </span>

                    <a href="${pageContext.request.contextPath}/mypage/profile.jsp"
                       class="btn-edit-profile">

                        <i class="fa-solid fa-pen"></i>
                        プロフィールを編集

                    </a>

                </div>

            </div>

            <%-- ログアウト --%>
            <a href="#"
               class="menu-item-card"
               onclick="openLogoutModal(); return false;">

                <div class="menu-item-left">

                    <div class="menu-icon-wrapper orange-logout">
                        <i class="fa-solid fa-arrow-right-from-bracket"></i>
                    </div>

                    <span class="menu-item-title">
                        ログアウト
                    </span>

                </div>

                <i class="fa-solid fa-chevron-right arrow-icon"></i>

            </a>

        <% } else { %>

            <%-- 一般ユーザー用マイページ --%>

            <%-- ユーザー情報 --%>
            <div class="user-profile-section">

                <%-- ユーザーアイコン --%>
                <div class="user-avatar">

                    <img
                        src="${empty user.userImg
                            ? pageContext.request.contextPath.concat('/images/default-profile.png')
                            : pageContext.request.contextPath.concat('/images/').concat(user.userImg)}"
                        alt="プロフィール画像"
                        style="object-position: ${user.userImgPositionX}% ${user.userImgPositionY}%;">

                </div>

                <%-- ユーザー名・編集ボタン --%>
                <div class="user-info-text">

                    <span class="user-name">
                        ${user.userName}
                    </span>

                    <a href="${pageContext.request.contextPath}/mypage/profile.jsp"
                       class="btn-edit-profile">

                        <i class="fa-solid fa-pen"></i>
                        プロフィールを編集

                    </a>

                </div>

            </div>

            <%-- ブックマーク --%>
            <a href="${pageContext.request.contextPath}/user/likes"
               class="menu-item-card">

                <div class="menu-item-left">

                    <div class="menu-icon-wrapper red-heart">
                        <i class="fa-solid fa-bookmark"></i>
                    </div>

                    <span class="menu-item-title">
                        ブックマークした投稿
                    </span>

                </div>

                <i class="fa-solid fa-chevron-right arrow-icon"></i>

            </a>

            <%-- ギフトの履歴 --%>
            <a href="${pageContext.request.contextPath}/mypage/gift_history.jsp"
               class="menu-item-card">

                <div class="menu-item-left">

                    <div class="menu-icon-wrapper red-heart">
                        <i class="fa-solid fa-gift"></i>
                    </div>

                    <span class="menu-item-title">
                        ギフト履歴
                    </span>

                </div>

                <i class="fa-solid fa-chevron-right arrow-icon"></i>

            </a>

            <%-- 設定 --%>
            <a href="${pageContext.request.contextPath}/mypage/mypage_detail.jsp"
               class="menu-item-card">

                <div class="menu-item-left">

                    <div class="menu-icon-wrapper purple-gear">
                        <i class="fa-solid fa-gear"></i>
                    </div>

                    <span class="menu-item-title">
                        設定
                    </span>

                </div>

                <i class="fa-solid fa-chevron-right arrow-icon"></i>

            </a>

            <%-- ログアウト --%>
            <a href="#"
               class="menu-item-card"
               onclick="openLogoutModal(); return false;">

                <div class="menu-item-left">

                    <div class="menu-icon-wrapper orange-logout">
                        <i class="fa-solid fa-arrow-right-from-bracket"></i>
                    </div>

                    <span class="menu-item-title">
                        ログアウト
                    </span>

                </div>

                <i class="fa-solid fa-chevron-right arrow-icon"></i>

            </a>

        <% } %>

    </div>

    <%-- ログアウト確認ポップアップ --%>
    <div id="logout-modal" class="logout-modal">

        <div class="logout-modal-content">

            <h3>ログアウト</h3>

            <p>本当にログアウトしますか？</p>

            <div class="logout-modal-buttons">

                <%-- キャンセル --%>
                <button type="button"
                        class="btn-cancel"
                        onclick="closeLogoutModal()">

                    キャンセル

                </button>

                <%-- ログアウト --%>
                <a href="${pageContext.request.contextPath}/servlet.LogoutServlet.action"
                   class="btn-logout">

                    ログアウト

                </a>

            </div>

        </div>

    </div>

</main>

<script>

    // ログアウト確認ポップアップを表示
    function openLogoutModal() {
        document.getElementById("logout-modal").style.display = "flex";
    }

    // ログアウト確認ポップアップを閉じる
    function closeLogoutModal() {
        document.getElementById("logout-modal").style.display = "none";
    }

    // ポップアップの背景をクリックした場合も閉じる
    document.getElementById("logout-modal").addEventListener("click", function(event) {

        if (event.target === this) {
            closeLogoutModal();
        }

    });

</script>

<%@ include file="../footer.jsp" %>