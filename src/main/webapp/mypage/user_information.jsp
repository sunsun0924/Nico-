
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.FamilyBD" %>

<%@ include file="../header.jsp" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/user_information.css">

<%
    List<FamilyBD> familyList =
        (List<FamilyBD>) request.getAttribute("familyList");

    boolean hasFamily =
        familyList != null && !familyList.isEmpty();
%>

<div class="page-layout">

    <!-- 左側サイドバー -->
    <%@ include file="../sidebar.jsp" %>

    <!-- 登録情報画面 -->
    <main class="main-content">

        <div class="information-container">

            <!-- タイトル -->
            <div class="page-header">
                <h2 class="page-title">住所・連絡先・誕生日</h2>

                <p class="page-desc">
                    登録情報を確認・変更できます。
                </p>
            </div>

            <!-- エラーメッセージ -->
            <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </p>
            <% } %>

            <!-- 完了メッセージ -->
            <% if (request.getAttribute("message") != null) { %>
                <p class="success-message">
                    <%= request.getAttribute("message") %>
                </p>
            <% } %>

            <!-- 登録情報・家族誕生日の保存フォーム -->
            <form action="${pageContext.request.contextPath}/servlet.FamilyBDServlet.action"
                  method="post"
                  id="family-save-form"
                  data-today="<%= LocalDate.now() %>">

                <!-- =========================
                     自分の登録情報
                ========================== -->
                <section class="profile-section">

                    <h3 class="section-title">自分の登録情報</h3>

                    <!-- メールアドレス -->
                    <div class="form-group">

                        <label class="form-label">
                            メールアドレス
                        </label>

                        <input type="email"
                               name="userMail"
                               class="form-input"
                               value="${sessionScope.user.userMail}"
                               placeholder="メールアドレスを入力">

                    </div>

                    <!-- 電話番号 -->
                    <div class="form-group">

                        <label class="form-label">
                            電話番号
                        </label>

                        <input type="tel"
                               name="userNumber"
                               class="form-input"
                               value="${sessionScope.user.userNumber}"
                               placeholder="電話番号を入力">

                    </div>

                    <!-- 住所 -->
                    <div class="form-group">

                        <label class="form-label">
                            住所
                        </label>

                        <input type="text"
                               name="userAddress"
                               class="form-input"
                               value="${sessionScope.user.userAddress}"
                               placeholder="住所を入力">

                    </div>

                    <!-- 自分の誕生日 -->
                    <div class="form-group">

                        <label class="form-label">
                            自分の誕生日
                        </label>

                        <p class="input-note">
                            ※自分の誕生日は変更できません。
                        </p>

                        <input type="date"
                               name="userBirthday"
                               class="form-input birthday-input"
                               value="${sessionScope.user.userBirthday}"
                               max="<%= LocalDate.now() %>"
                               readonly>

                    </div>

                </section>

                <!-- =========================
                     登録済み家族情報
                ========================== -->
                <% if (hasFamily) { %>

                    <section class="family-list-section">

                        <h3 class="section-title">
                            登録済みの家族の誕生日
                        </h3>

                        <% for (FamilyBD family : familyList) { %>

                            <div class="family-list-item">

                                <!-- 家族情報 -->
                                <div class="family-information">

                                    <span class="family-relation">
                                        <%= family.getRelation() %>
                                    </span>

                                    <span class="family-birthday">
                                        <%= family.getBirthday() %>
                                    </span>

                                </div>

                                <!-- 削除ボタン -->
                                <button type="submit"
                                        class="btn-delete"
                                        formaction="${pageContext.request.contextPath}/servlet.FamilyBDDeleteServlet.action"
                                        formmethod="post"
                                        formnovalidate
                                        name="familyBirthdayId"
                                        value="<%= family.getFamilyBirthdayId() %>">

                                    削除

                                </button>

                            </div>

                        <% } %>

                    </section>

                <% } else { %>

                    <!-- 家族情報がない場合 -->
                    <p class="family-empty">
                        家族の登録情報はありません。
                    </p>

                <% } %>

                <!-- =========================
                     家族の誕生日登録
                ========================== -->
                <section class="family-register-section">

                    <h3 class="section-title">
                        家族の誕生日を登録
                    </h3>

                    <p class="section-description">
                        家族の続柄と誕生日を入力してください。
                    </p>

                    <!-- 家族入力欄 -->
                    <div id="family-input-list">

                        <!-- 最初の入力欄 -->
                        <div class="family-input-item">

                            <!-- 続柄 -->
                            <div class="form-group">

                                <label class="form-label">
                                    続柄
                                </label>

                                <select name="familyType"
                                        class="form-input family-type">

                                    <option value="">選択してください</option>
                                    <option value="夫">夫</option>
                                    <option value="妻">妻</option>
                                    <option value="子供">子ども</option>

                                </select>

                            </div>

                            <!-- 子どもの場合だけ表示 -->
                            <div class="child-information"
                                 style="display:none;">

                                <!-- 子どもの性別 -->
                                <div class="form-group">

                                    <label class="form-label">
                                        子どもの性別
                                    </label>

                                    <select name="childGender"
                                            class="form-input child-gender">

                                        <option value="">選択してください</option>
                                        <option value="男">男</option>
                                        <option value="女">女</option>

                                    </select>

                                </div>

                                <!-- 子どもの順番 -->
                                <div class="form-group">

                                    <label class="form-label">
                                        子どもの順番
                                    </label>

                                    <select name="childOrder"
                                            class="form-input child-order">

                                        <option value="">選択してください</option>

                                        <% for (int i = 1; i <= 10; i++) { %>
                                            <option value="<%= i %>">
                                                <%= i %>人目
                                            </option>
                                        <% } %>

                                    </select>

                                </div>

                            </div>

                            <!-- 家族の誕生日 -->
                            <div class="form-group">

                                <label class="form-label">
                                    誕生日
                                </label>

                                <input type="date"
                                       name="familyBirthday"
                                       class="form-input family-birthday"
                                       max="<%= LocalDate.now() %>">

                            </div>

                            <!-- 入力欄削除 -->
                            <button type="button"
                                    class="btn-remove-row">

                                この入力欄を削除

                            </button>

                        </div>

                    </div>

                    <!-- 家族追加 -->
                    <button type="button"
                            class="btn-add-family"
                            id="add-family-button">

                        ＋ 家族を追加

                    </button>

                </section>

                <!-- =========================
                     保存・戻る
                ========================== -->
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

<!-- 外部JavaScript -->
<script src="${pageContext.request.contextPath}/js/user_information.js" defer></script>

<%@ include file="../footer.jsp" %>
