<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ご登録情報の変更</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_detail.css">
</head>
<body>

  <div class="container">
    <div class="page-title">ご登録情報の変更</div>
    <div class="page-desc">住所、電話番号、メールアドレス、誕生日を入力してください。</div>

    <form action="${pageContext.request.contextPath}/UserInfoUpdate" method="post">
      
      <div class="form-group">
        <label class="form-label">メールアドレス</label>
        <input type="email" name="email" value="${user.email}" class="form-input" required>
      </div>

      <div class="form-group">
        <label class="form-label">電話番号</label>
        <input type="tel" name="phone" value="${user.phone}" class="form-input">
      </div>

      <div class="form-group">
        <label class="form-label">住所</label>
        <input type="text" name="address" value="${user.address}" class="form-input">
      </div>

      <div class="form-group">
        <label class="form-label">誕生日</label>
        <input type="date" name="birthday" value="${user.birthday}" class="form-input">
      </div>

      <div class="btn-container">
        <button type="submit" class="btn-save">保存</button>
        <a href="${pageContext.request.contextPath}/Setting" class="btn-back">戻る</a>
      </div>

    </form>
  </div>

</body>
</html>