<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="css/global.css"/>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <script src="js/util.js"></script>
</head>
<body>
<div class="signup-container">
    <div class="bg">

    </div>
    <form class="signup-form">
        <h1>SIGN UP</h1>
        <label class="signup-input-group username">
            계정
            <input type="text" class="signup-input" placeholder="아이디 영문 숫자 조합 6 ~ 10자리">
            <label class="input-msg">ㅤ</label>
        </label>
        <label class="signup-input-group password">
            비밀번호
            <input type="password" class="signup-input" placeholder="대소문자, 숫자, 특수문자 포함 10자이상">
            <label class="input-msg">ㅤ</label>
        </label>
        <label class="signup-input-group password-confirm">
            비밀번호 확인
            <input type="password" class="signup-input" placeholder="">
            <label class="input-msg">ㅤ</label>
        </label>
        <label class="signup-input-group nickname">
            닉네임
            <input type="text" class="signup-input" placeholder="닉네임">
            <label class="input-msg">ㅤ</label>
        </label>
        <label class="signup-input-group email">
            이메일
            <input type="text" class="signup-input">
            <label class="input-msg">ㅤ</label>
        </label>
        <label class="signup-input-group introduction">
            소개
            <textarea class="signup-input" rows="4"></textarea>
            <label class="input-msg">ㅤ</label>
        </label>
        <button class="signup-submit-btn">확인</button>
    </form>
</div>
<script>
    const signupForm = document.querySelector(".signup-form");
    const submitBtn = document.querySelector(".signup-submit-btn");
    const usernameInput = document.querySelector(".signup-input-group.username > input");
    const passwordInput = document.querySelector(".signup-input-group.password > input");
    const emailInput = document.querySelector(".signup-input-group.email > input");
    const nicknameInput = document.querySelector(".signup-input-group.nickname > input");
    const passwordConfirmInput = document.querySelector(".signup-input-group.password-confirm > input");

    signupForm.addEventListener("submit", (event) => {
        event.preventDefault();
    });

    function onClickSubmitBtn(event) {
        const usernameValue = usernameInput.value;
        const passwordValue = passwordInput.value;
        const nicknameValue = nicknameInput.value;
        const emailValue = emailInput.value;
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
        }).then((result) => result.json());
    }

    function genMsg(className) {
        const msg = {
            valid: "",
            invalid: ""
        }
        switch (className) {
            case "username" :
                msg.valid = "사용 가능한 계정 입니다";
                msg.invalid = "사용할 수 없는 계정 입니다";
                break;
            case "password" :
                msg.valid = "사용 가능한 비밀번호 입니다";
                msg.invalid = "사용할 수 없는 비밀번호 입니다";
                break;
            case "email":
                msg.valid = "사용 가능한 이메일 주소 입니다";
                msg.invalid = "사용할 수 없는 이메일 주소 입니다";
                break;
            case "nickname":
                msg.valid = "사용 가능한 닉네임 입니다";
                msg.invalid = "사용할 수 없는 닉네임 입니다";
                break;
            default:
                break;
        }
        return msg;
    }

    function enToKr(className) {
        let name = "";
        switch (className) {
            case "username" :
                name = '계정'
                break;
            case "password" :
                name = "비밀번호";
                break;
            case "email":
                name = "이메일"
                break;
            case "nickname":
                name = "닉네임"
                break;
            default:
                break;
        }
        return name;
    }

    async function duplicateCheck(className, value) {
        let URL = '/duplicate/';
        const columnSet = new Set(['username', 'email', 'nickname']);
        if (columnSet.has(className)) {
            URL += className;

            const body = {};
            body[className] = value;

            return fetch(URL, {
                headers: {
                    "Content-Type": "application/json"
                },
                method: "POST",
                body: JSON.stringify(body)
            })
                .then((result) => result.json())
        } else {
            //throw new Error("wrong pathname");
            return;
        }
    }

    function genOnChangeInput(className) {
        const inputMsgLabel = document.querySelector('.signup-input-group.' + className + ' label');
        const msg = genMsg(className);
        const inputGroup = document.querySelector('.signup-input-group.' + className);
        const regexChecker = debouncer(async (value) => {
            const valid = regexCheck(className, value);
            if (valid) {
                //This string is valid for the regular expression only, not for duplication
                const data = await duplicateCheck(className, value, inputMsgLabel);

                if (!data) {
                    inputGroup.classList.add("valid");
                    inputGroup.classList.remove("invalid");
                    inputMsgLabel.textContent = msg[valid ? "valid" : "invalid"];
                    return;
                }

                if (!data.isDuplication) {
                    inputGroup.classList.remove("invalid");
                    inputGroup.classList.add("valid");
                    inputMsgLabel.textContent = msg["valid"];
                } else {
                    inputGroup.classList.remove("valid");
                    inputGroup.classList.add("invalid");
                    inputMsgLabel.textContent = "중복된 " + enToKr(className) + "입니다";
                }
            } else {
                inputGroup.classList.remove("valid");
                inputGroup.classList.add("invalid");
                inputMsgLabel.textContent = msg[valid ? "valid" : "invalid"];
            }

        }, 400);
        return (event) => {
            const value = event.target.value;
            if (value && value.trim()) {
                regexChecker(value);
            } else {
                inputMsgLabel.textContent = "ㅤ";
                inputGroup.classList.remove("invalid");
                inputGroup.classList.remove("valid");
            }
        }
    }

    usernameInput.addEventListener("keyup", genOnChangeInput("username"));
    passwordInput.addEventListener("keyup", genOnChangeInput("password"));
    emailInput.addEventListener("keyup", genOnChangeInput("email"));
    nicknameInput.addEventListener("keyup", genOnChangeInput("nickname"));

    passwordConfirmInput.addEventListener("keyup", debouncer((event) => {
        const inputGroup = document.querySelector('.signup-input-group.password-confirm');
        const value = event.target.value;
        const msgLabel = passwordConfirmInput.parentNode.querySelector('.input-msg');
        if (value === passwordInput.value) {
            inputGroup.classList.remove("invalid");
            inputGroup.classList.add("valid");
            msgLabel.textContent = "비밀번호와 일치합니다"
        } else {
            inputGroup.classList.remove("valid");
            inputGroup.classList.add("invalid");
            msgLabel.textContent = "비밀번호와 일치하지 않습니다"
        }

        if (!value) {
            inputGroup.classList.remove("valid");
            inputGroup.classList.remove("invalid");
            msgLabel.textContent = "ㅤ";
        }
    }, 400));

    submitBtn.addEventListener("click", onClickSubmitBtn);
</script>
</body>
</html>