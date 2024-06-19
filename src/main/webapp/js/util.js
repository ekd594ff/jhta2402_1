const REG_USERNAME = `^[a-z]{1}[a-z0-9]{5,10}$`;
const REG_PASSWORD = `^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,128}$`;
const REG_EMAIL = `^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]{1,10}$`;
const REG_NICKNAME = ``;

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

function throttler(callback, delay) {
    let timer;
    return function () {
        if (!timer) {
            timer = setTimeout(_ => {
                callback.apply(this, arguments);
                timer = undefined;
            }, delay);
        }
    }
}

function debouncer(callback, delay) {
    let timeout;
    return function (...args) {
        const context = this;
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            callback.apply(context, args);
        }, delay);
    };
}

function regexCheck(type, value) {
    let regex;
    switch (type) {
        case "username":
            regex = new RegExp(REG_USERNAME);
            break;
        case "password":
            regex = new RegExp(REG_PASSWORD);
            break;
        case "email" :
            regex = new RegExp(REG_EMAIL);
            break;
        case "nickname" :
            regex = new RegExp(REG_NICKNAME);
            break;
        default:
            break;
    }
    if(!regex) {
        throw new Error("invalid regular expression type");
    }
    return regex.test(value);
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