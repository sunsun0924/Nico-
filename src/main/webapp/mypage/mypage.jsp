<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypage.css">

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

        <%-- ユーザー情報 --%>
        <div class="user-profile-section">

            <%-- ユーザーアイコン --%>
            <div class="user-avatar">
                <img
                    src="${empty user.userImg
                        ? pageContext.request.contextPath.concat('/images/default-profile.jpg')
                        : pageContext.request.contextPath.concat('/images/').concat(user.userImg)}"
                    alt="プロフィール画像"
                    style="object-position: ${user.userImgPositionX}% ${user.userImgPositionY}%;"
                >
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
        <a href="${pageContext.request.contextPath}/logout"
           class="menu-item-card">

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

    </div>

</main>

<%@ include file="../footer.jsp" %>