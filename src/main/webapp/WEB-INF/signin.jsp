<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
    <link rel="stylesheet" href="css/global.css"/>
    <link rel="icon" href="public/favicon/favicon.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="js/util.js"></script>
</head>
<body>
<div class="signin-container">
    <form class="signin-form">
        <div class="logo">
            <a href="/">
                <img src="public/imgs/jalendar.svg" height="48px"/>
            </a>
        </div>
        <h1>SIGN IN</h1>
        <label class="signin-input-group username">
            계정
            <input type="text" class="signin-input">
        </label>
        <label class="signin-input-group password">
            비밀번호
            <input type="password" class="signin-input">
        </label>
        <button class="signin-submit-btn" onclick="onClickSubmit()">확인</button>
        <button class="signup-btn" onclick="(()=>{window.location.href=`/signup`})()">회원가입</button>
    </form>
    <div class="bg">
        <div class="panel">
            <img src="public/imgs/1.png" alt="panel"/>
        </div>
    </div>
</div>
<script>
    const signInForm = document.querySelector(".signin-form");
    const usernameInput = document.querySelector(".signin-input-group.username > input");
    const passwordInput = document.querySelector(".signin-input-group.password > input");

    signInForm.addEventListener("submit", (event) => {
        event.preventDefault();
    });

    function onClickSubmit(event) {
        const usernameValue = usernameInput.value;
        const passwordValue = passwordInput.value;

        if (usernameValue &&
            passwordValue &&
            usernameValue.trim() &&
            passwordValue.trim()) {
            const data = {
                username: usernameValue,
                password: passwordValue
            }
            fetch("/login", {
                headers: {
                    "Content-Type": "application/json"
                },
                method: "POST",
                body: JSON.stringify(data)
            })
                .then((result) => {
                    if(!result.ok) {
                        throw new Error("");
                    }
                    return result.json();
                })
                .then(data => {
                    window.location.href = "/";
                })
                .catch(err => {
                    window.alert("로그인에 실패했습니다");
                })
        } else {
            window.alert("비밀번호와 계정을 입력해 주세요");
        }
    }
</script>
</body>
</html>
