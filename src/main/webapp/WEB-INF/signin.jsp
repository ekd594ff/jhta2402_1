<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
    <link rel="stylesheet" href="css/global.css"/>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <script src="js/util.js"></script>
</head>
<body>
<div class="signin-container">
    <form class="signin-form">
        <h1>SIGN IN</h1>
        <label class="signin-input-group username">
            계정
            <input type="text" class="signin-input" placeholder="아이디 영문 숫자 조합 6 ~ 10자리">
        </label>
        <label class="signin-input-group password">
            비밀번호
            <input type="password" class="signin-input" placeholder="대소문자, 숫자, 특수문자 포함 10자이상">
        </label>
        <button class="signin-submit-btn">확인</button>
    </form>
    <div class="bg">
        <div class="panel">
            <img src="public/imgs/1.png" alt="panel"/>
        </div>
    </div>
</div>
</body>
</html>
