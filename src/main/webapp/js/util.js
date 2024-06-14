const REG_USERNAME = `^[a-z]{1}[a-z0-9]{5,10}$`;
const REG_PASSWORD = `^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,128}$`;
const REG_EMAIL = `^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]{1,10}$`;
const REG_NICKNAME = ``;

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