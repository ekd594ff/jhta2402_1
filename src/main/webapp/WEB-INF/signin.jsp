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
            <input type="text" class="signin-input">
        </label>
        <label class="signin-input-group password">
            비밀번호
            <input type="password" class="signin-input">
        </label>
        <button class="signin-submit-btn" onclick="onClickSubmit()">확인</button>
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

        if(usernameValue &&
            passwordValue &&
            usernameValue.trim() &&
            passwordValue.trim()) {
            const data = {
                username : usernameValue,
                password : passwordValue
            }
            fetch("/login", {
                headers : {
                    "Content-Type" : "application/json"
                },
                method : "POST",
                body : JSON.stringify(data)
            }).then((result) => result.json())
                .then(data => {
                window.location.href = "/";
            })
        } else {
            window.alert("비밀번호와 계정을 입력해 주세요");
        }
    }
</script>
</body>
</html>
