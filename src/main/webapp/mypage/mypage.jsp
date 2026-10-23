<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>マイページ - Nico+</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <!-- ひとつ上の階層にある共通パーツを読み込む -->
    <jsp:include page="../header.jsp" />
    <jsp:include page="../sidebar.jsp" />

    <main class="main-container">
        <div class="content-card">
            
            <div class="card-header">
                <i class="fa-regular fa-user"></i>
                <h2>マイページ</h2>
            </div>

            <!-- ユーザー情報（湖面アバター） -->
            <div class="user-profile-section">
                <div class="user-avatar lake-avatar"></div>
                <div class="user-info-text">
                    <span class="user-name">みさき 🌷</span>
                    <a href="${pageContext.request.contextPath}/mypage/profile.jsp" class="btn-edit-profile">
                        <i class="fa-solid fa-pen"></i> プロフィールを編集
                    </a>
                </div>
            </div>

            <!-- メニュー項目 -->
            <a href="${pageContext.request.contextPath}/user/likes" class="menu-item-card">
                <div class="menu-item-left">
                    <div class="menu-icon-wrapper red-heart"><i class="fa-regular fa-heart"></i></div>
                    <span class="menu-item-title">ブックマークした投稿</span>
                </div>
                <i class="fa-solid fa-chevron-right arrow-icon"></i>
            </a>

            <a href="${pageContext.request.contextPath}/mypage/mypage_detail.jsp" class="menu-item-card">
                <div class="menu-item-left">
                    <div class="menu-icon-wrapper purple-gear"><i class="fa-solid fa-gear"></i></div>
                    <span class="menu-item-title">設定</span>
                </div>
                <i class="fa-solid fa-chevron-right arrow-icon"></i>
            </a>

            <a href="${pageContext.request.contextPath}/logout" class="menu-item-card">
                <div class="menu-item-left">
                    <div class="menu-icon-wrapper orange-logout"><i class="fa-solid fa-arrow-right-from-bracket"></i></div>
                    <span class="menu-item-title">ログアウト</span>
                </div>
                <i class="fa-solid fa-chevron-right arrow-icon"></i>
            </a>

        </div>
    </main>

    <jsp:include page="../footer.jsp" />

</body>
</html>