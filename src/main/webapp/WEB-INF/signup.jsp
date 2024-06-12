<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head>
    <title>Sign up</title>
    <link rel="stylesheet" href="css/global.css">
</head>
<body>
<div class="signup-container">
    <form class="signup-form">
        <label class="signup-input-group username">
            계정
            <input type="text" class="signup-input" placeholder="계정">
            <label class="input-msg"></label>
        </label>
        <label class="signup-input-group password">
            비밀번호
            <input type="password" class="signup-input" placeholder="비밀번호">
            <label class="input-msg"></label>
        </label>
        <label class="signup-input-group password-confirm">
            비밀번호 확인
            <input type="password" class="signup-input" placeholder="">
            <label class="input-msg"></label>
        </label>
        <label class="signup-input-group nickname">
            닉네임
            <input type="text" class="signup-input" placeholder="닉네임">
            <label class="input-msg"></label>
        </label>
        <label class="signup-input-group email">
            이메일
            <input type="text" class="signup-input">
            <label class="input-msg"></label>
        </label>
        <label class="signup-input-group introduction">
            소개
            <textarea class="signup-input"></textarea>
            <label class="input-msg"></label>
        </label>
        <button class="signup-submit-btn">확인</button>
    </form>
</div>

<script>
    const signupForm = document.querySelector(".signup-form");
    const submitBtn = document.querySelector(".signup-submit-btn");
    signupForm.addEventListener("submit",(event) => {
        event.preventDefault();
    });
    function onClickSubmitBtn(event) {
        const usernameValue = document.querySelector(".signup-input-group.username > input").value;
        const passwordValue = document.querySelector(".signup-input-group.password > input").value;
        const nicknameValue = document.querySelector(".signup-input-group.nickname > input").value;
        const emailValue = document.querySelector(".signup-input-group.email > input").value;
        const introductionValue = document.querySelector(".signup-input-group.introduction > textarea").value;
        const data = {
            username: usernameValue,
            password: passwordValue,
            nickname: nicknameValue,
            email: emailValue,
            introduction: introductionValue
        }
        fetch("/insert", {
            headers: {
                "Content-Type": `application/json`
            },
            method: "POST",
            body: JSON.stringify(data)
        }).then((result) => {
            console.log(result);
        });
    }

    submitBtn.addEventListener("click", onClickSubmitBtn);
</script>
</body>
</html>