<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>設定</title>
  <!-- 外部CSSの読み込み -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_detail.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

  <div class="settings-container">
    
    <!-- ヘッダー（戻るボタン＆タイトル） -->
    <div class="header">
      <a href="${pageContext.request.contextPath}/Mypage" id="back-button" class="back-btn" aria-label="戻る">
        <i class="fa-solid fa-chevron-left"></i>
      </a>
      <div class="header-title" id="header-title">設定</div>
    </div>

    <!-- 【① 設定メニュー一覧（初期は表示）】 -->
    <div id="settings-menu-section" class="settings-card">
      
      <!-- パスワードリンク -->
      <a href="${pageContext.request.contextPath}/PasswordChange" class="setting-item">
        <div class="icon-wrapper icon-pink">
          <i class="fa-solid fa-lock"></i>
        </div>
        <div class="text-content">
          <div class="item-title">パスワード</div>
          <div class="item-desc">アカウントのパスワードを変更できます。</div>
        </div>
        <i class="fa-solid fa-chevron-right arrow-icon"></i>
      </a>

      <!-- 住所・連絡先登録・誕生日リンク -->
      <a href="${pageContext.request.contextPath}/UserInfo" class="setting-item">
        <div class="icon-wrapper icon-pink">
          <i class="fa-solid fa-house"></i>
        </div>
        <div class="text-content">
          <div class="item-title">住所・連絡先・誕生日</div>
          <div class="item-desc">住所、電話番号、メールアドレス、誕生日を確認・変更できます。</div>
        </div>
        <i class="fa-solid fa-chevron-right arrow-icon"></i>
      </a>

      <!-- ブロックリストリンク（★JSで同一画面内切り替え） -->
      <a href="#" class="setting-item" onclick="showBlockList(event)">
        <div class="icon-wrapper icon-purple">
          <i class="fa-solid fa-user-slash"></i>
        </div>
        <div class="text-content">
          <div class="item-title">ブロックリスト</div>
          <div class="item-desc">ブロックしたユーザーの一覧を確認・管理できます。</div>
        </div>
        <i class="fa-solid fa-chevron-right arrow-icon"></i>
      </a>

    </div>

    <!-- 【② ブロックリスト画面（初期は非表示）】 -->
    <div id="block-list-section" style="display: none;">
      
      <!-- ユーザー一覧カード -->
      <div class="settings-card">
        
        <!-- ユーザーA -->
        <div class="block-item">
          <div class="icon-wrapper icon-purple" style="width: 36px; height: 36px;">
            <i class="fa-solid fa-user" style="font-size: 14px;"></i>
          </div>
          <div class="block-user-name">ユーザーA</div>
          <button type="button" class="btn-unblock" onclick="unblockUser('ユーザーA')">解除</button>
        </div>

        <!-- ユーザーB -->
        <div class="block-item">
          <div class="icon-wrapper icon-purple" style="width: 36px; height: 36px;">
            <i class="fa-solid fa-user" style="font-size: 14px;"></i>
          </div>
          <div class="block-user-name">ユーザーB</div>
          <button type="button" class="btn-unblock" onclick="unblockUser('ユーザーB')">解除</button>
        </div>

        <!-- ユーザーC -->
        <div class="block-item">
          <div class="icon-wrapper icon-purple" style="width: 36px; height: 36px;">
            <i class="fa-solid fa-user" style="font-size: 14px;"></i>
          </div>
          <div class="block-user-name">ユーザーC</div>
          <button type="button" class="btn-unblock" onclick="unblockUser('ユーザーC')">解除</button>
        </div>

      </div>

      <!-- 下部の戻るボタン -->
      <button type="button" class="btn-back-bottom" onclick="hideBlockList()">戻る</button>

    </div>

  </div>

  <!-- JavaScriptによる画面切り替えと解除処理 -->
  <script>
    function showBlockList(event) {
      event.preventDefault();
      document.getElementById('settings-menu-section').style.display = 'none';
      document.getElementById('block-list-section').style.display = 'block';
      document.getElementById('header-title').innerText = 'ブロックリスト';
    }

    function hideBlockList() {
      document.getElementById('block-list-section').style.display = 'none';
      document.getElementById('settings-menu-section').style.display = 'block';
      document.getElementById('header-title').innerText = '設定';
    }

    function unblockUser(userName) {
      // 後々ここにチャット機能のブロック解除APIやサーブレットへの送信処理を記述できます
      alert(userName + ' のブロックを解除しました');
    }
  </script>

</body>
</html>