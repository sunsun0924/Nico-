<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypage_detail.css">

<div class="page-layout">

    <%-- サイドバー --%>
    <%@ include file="../sidebar.jsp" %>

    <%-- メイン画面 --%>
    <main class="main-content">

        <div class="settings-container">

            <%-- ページタイトル --%>
            <div class="header">

                <div class="header-title" id="header-title">

                    <i id="header-title-icon" class="fa-solid fa-gear"></i>

                    <span id="header-title-text">設定</span>

                </div>

            </div>

            <%-- 完了メッセージ --%>
            <%
                String mypageMessage =
                    (String) session.getAttribute("mypageMessage");

                if (mypageMessage != null) {
            %>

                <p class="success-message">
                    <%= mypageMessage %>
                </p>

            <%
                    session.removeAttribute("mypageMessage");
                }
            %>

            <%-- ① 設定メニュー --%>
            <div id="settings-menu-section" class="settings-card">

                <%-- パスワード変更 --%>
                <a href="${pageContext.request.contextPath}/mypage/password_change.jsp"
                   class="setting-item">

                    <div class="icon-wrapper icon-pink">
                        <i class="fa-solid fa-lock"></i>
                    </div>

                    <div class="text-content">
                        <div class="item-title">パスワード</div>

                        <div class="item-desc">
                            アカウントのパスワードを変更できます。
                        </div>
                    </div>

                    <i class="fa-solid fa-chevron-right arrow-icon"></i>

                </a>

                <%-- 住所・連絡先・誕生日 --%>
                <a href="${pageContext.request.contextPath}/servlet.FamilyBDPageServlet.action"
                   class="setting-item">

                    <div class="icon-wrapper icon-pink">
                        <i class="fa-solid fa-house"></i>
                    </div>

                    <div class="text-content">
                        <div class="item-title">
                            住所・連絡先・誕生日
                        </div>

                        <div class="item-desc">
                            住所、電話番号、メールアドレス、誕生日を確認・変更できます。
                        </div>
                    </div>

                    <i class="fa-solid fa-chevron-right arrow-icon"></i>

                </a>

                <%-- ブロックリスト --%>
                <a href="#"
                   class="setting-item"
                   id="showBlockListButton">

                    <div class="icon-wrapper icon-purple">
                        <i class="fa-solid fa-user-slash"></i>
                    </div>

                    <div class="text-content">
                        <div class="item-title">ブロックリスト</div>

                        <div class="item-desc">
                            ブロックしたユーザーの一覧を確認・管理できます。
                        </div>
                    </div>

                    <i class="fa-solid fa-chevron-right arrow-icon"></i>

                </a>

            </div>

            <%-- ② ブロックリスト --%>
            <div id="block-list-section" style="display: none;">

                <div class="settings-card">

                    <%-- ユーザーA --%>
                    <div class="block-item">

                        <div class="icon-wrapper icon-purple"
                             style="width: 36px; height: 36px;">

                            <i class="fa-solid fa-user"
                               style="font-size: 14px;"></i>

                        </div>

                        <div class="block-user-name">
                            ユーザーA
                        </div>

                        <button type="button"
                                class="btn-unblock"
                                data-user-name="ユーザーA">
                            解除
                        </button>

                    </div>

                    <%-- ユーザーB --%>
                    <div class="block-item">

                        <div class="icon-wrapper icon-purple"
                             style="width: 36px; height: 36px;">

                            <i class="fa-solid fa-user"
                               style="font-size: 14px;"></i>

                        </div>

                        <div class="block-user-name">
                            ユーザーB
                        </div>

                        <button type="button"
                                class="btn-unblock"
                                data-user-name="ユーザーB">
                            解除
                        </button>

                    </div>

                    <%-- ユーザーC --%>
                    <div class="block-item">

                        <div class="icon-wrapper icon-purple"
                             style="width: 36px; height: 36px;">

                            <i class="fa-solid fa-user"
                               style="font-size: 14px;"></i>

                        </div>

                        <div class="block-user-name">
                            ユーザーC
                        </div>

                        <button type="button"
                                class="btn-unblock"
                                data-user-name="ユーザーC">
                            解除
                        </button>

                    </div>

                </div>

                <%-- 設定メニューに戻る --%>
                <button type="button"
                        class="btn-back-bottom"
                        id="hideBlockListButton">
                    戻る
                </button>

            </div>

            <%-- マイページに戻る --%>
            <div class="back-area" id="mypageBackArea">

                <a href="${pageContext.request.contextPath}/mypage/mypage.jsp"
                   class="back-button">
                    戻る
                </a>

            </div>

        </div>

    </main>

</div>

<%-- 外部JavaScript --%>
<script src="${pageContext.request.contextPath}/js/mypage_detail.js" defer></script>

<%@ include file="../footer.jsp" %>