```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypage.css">

<div class="page-layout">

    <%@ include file="../sidebar.jsp" %>

    <main class="main-content">

        <div class="mypage-container">

            <!-- ページヘッダー -->
            <div class="mypage-header">
                <i class="fa-regular fa-user header-icon"></i>
                <h2>マイページ</h2>
            </div>

            <!-- ユーザープロフィールカード -->
            <div class="profile-card">

                <!-- ユーザーアイコン -->
                <div class="profile-avatar">
                    <img
                        src="${pageContext.request.contextPath}/images/sample-icon.png"
                        alt="ユーザーアイコン">
                </div>

                <div class="profile-info">

                    <!-- ユーザー名 -->
                    <p class="user-name">みさき</p>

                    <!-- プロフィール編集リンク -->
                    <a
                        href="${pageContext.request.contextPath}/profile_edit.jsp"
                        class="edit-profile-btn">
                        <i class="fa-solid fa-pen"></i>
                        プロフィールを編集
                    </a>

                </div>
            </div>

            <!-- メニューリスト -->
            <div class="menu-list">

                <!-- いいねした投稿 -->
                <a
                    href="${pageContext.request.contextPath}/bookmark_list.jsp"
                    class="menu-item">

                    <div class="menu-left">
                        <span class="icon-circle heart-icon">
                            <i class="fa-regular fa-heart"></i>
                        </span>

                        <span class="menu-text">いいねした投稿</span>
                    </div>

                    <i class="fa-solid fa-chevron-right arrow-icon"></i>
                </a>

                <!-- 設定 -->
                <a
                    href="${pageContext.request.contextPath}/mypage/mypage_detail.jsp"
                    class="menu-item">

                    <div class="menu-left">
                        <span class="icon-circle gear-icon">
                            <i class="fa-solid fa-gear"></i>
                        </span>

                        <span class="menu-text">設定</span>
                    </div>

                    <i class="fa-solid fa-chevron-right arrow-icon"></i>
                </a>

                <!-- ログアウト -->
                <a
                    href="${pageContext.request.contextPath}/LogoutServlet"
                    class="menu-item logout-item">

                    <div class="menu-left">
                        <span class="icon-circle logout-icon">
                            <i class="fa-solid fa-right-from-bracket"></i>
                        </span>

                        <span class="menu-text">ログアウト</span>
                    </div>

                    <i class="fa-solid fa-chevron-right arrow-icon"></i>
                </a>

            </div>
        </div>

    </main>
</div>

<%@ include file="../footer.jsp" %>
```