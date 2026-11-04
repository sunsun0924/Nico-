<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">

       <!-- 左側サイドバー -->
    <%@ include file="../sidebar.jsp" %>

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
                    <div class="menu-icon-wrapper red-heart"><i class="fa-solid fa-bookmark"></i></div>
                    <span class="menu-item-title">ブックマークした投稿</span>
                </div>
                <i class="fa-solid fa-chevron-right arrow-icon"></i>
            </a>
            
			            <!-- ギフトの履歴 -->
			<a href="${pageContext.request.contextPath}/mypage/gift_history.jsp" class="menu-item-card">
			    <div class="menu-item-left">
			        <div class="menu-icon-wrapper red-heart">
			            <i class="fa-solid fa-gift"></i>
			        </div>
			        <span class="menu-item-title">ギフト履歴</span>
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