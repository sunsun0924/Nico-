<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.jsp" %>

  <div class="container">
    <div class="page-title">パスワードの変更</div>
    <div class="page-desc">現在のパスワードと新しいパスワードを入力してください。</div>

    <form action="${pageContext.request.contextPath}/PasswordUpdate" method="post">
      
      <div class="form-group">
        <label class="form-label">現在のパスワード</label>
        <input type="password" name="currentPassword" placeholder="現在のパスワードを入力" class="form-input" required>
      </div>

      <div class="form-group">
        <label class="form-label">新しいパスワード</label>
        <input type="password" name="newPassword" placeholder="新しいパスワードを入力" class="form-input" required>
      </div>

      <div class="form-group">
        <label class="form-label">新しいパスワード（確認）</label>
        <input type="password" name="confirmPassword" placeholder="新しいパスワードを再入力" class="form-input" required>
      </div>

      <div class="btn-container">
        <button type="submit" class="btn-save">保存</button>
        <a href="${pageContext.request.contextPath}/Setting" class="btn-back">戻る</a>
      </div>

    </form>
  </div>

<%@ include file="../footer.jsp" %>
