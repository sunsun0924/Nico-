<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>パスワードの変更</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_detail.css">
</head>
<body>

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

</body>
</html>