<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

<!-- 登録情報変更画面のCSSを読み込む -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypage_detail.css">

<!-- サイドバーと登録情報変更画面を横並びにする -->
<div class="page-layout">

    <!-- 左側：サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 右側：登録情報変更画面 -->
    <main class="main-content">

        <div class="container">

            <div class="page-title">ご登録情報の変更</div>

            <div class="page-desc">
                住所、電話番号、メールアドレス、誕生日を入力してください。
            </div>

            <form action="${pageContext.request.contextPath}/UserInfoUpdate"
                  method="post">

                <div class="form-group">
                    <label class="form-label">メールアドレス</label>
                    <input type="email"
                           name="email"
                           value="${user.email}"
                           class="form-input"
                           required>
                </div>

                <div class="form-group">
                    <label class="form-label">電話番号</label>
                    <input type="tel"
                           name="phone"
                           value="${user.phone}"
                           class="form-input">
                </div>

                <div class="form-group">
                    <label class="form-label">住所</label>
                    <input type="text"
                           name="address"
                           value="${user.address}"
                           class="form-input">
                </div>

                <div class="form-group">
                    <label class="form-label">誕生日</label>
                    <input type="date"
                           name="birthday"
                           value="${user.birthday}"
                           class="form-input">
                </div>

                <div class="btn-container">
                    <button type="submit" class="btn-save">
                        保存
                    </button>

                    <a href="${pageContext.request.contextPath}/mypage/mypage_detail.jsp"
                       class="btn-back">
                        戻る
                    </a>
                </div>

            </form>

        </div>

    </main>

</div>

<%@ include file="../footer.jsp" %>