<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
    <title>${infoMemberDTO.nickname}</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
</head>
<body>
<jsp:include page="../components/header.jsp"/>
<div class="mypage-container">
    <form class="mypage-form">
        <div class="profile-img-container">
            <div class="profile-img-wrapper">
                <svg focusable="false"
                     class="default"
                     aria-hidden="true" viewBox="0 0 24 24" data-testid="PersonIcon" fill="#fff">
                    <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path>
                </svg>
                <img id="profile-img" src="" alt="profile image"/>
                <label for="profile-img-input">
                    <svg xmlns="http://www.w3.org/2000/svg" height="48px" viewBox="0 -960 960 960" width="48px">
                        <path d="M440-200h80v-167l64 64 56-57-160-160-160 160 57 56 63-63v167ZM240-80q-33 0-56.5-23.5T160-160v-640q0-33 23.5-56.5T240-880h320l240 240v480q0 33-23.5 56.5T720-80H240Zm280-520v-200H240v640h480v-440H520ZM240-800v200-200 640-640Z"/>
                    </svg>
                </label>
            </div>
            <input class="file-input" id="profile-img-input" type="file" onchange="onChangeFileInput(event)"/>
        </div>
        <ul class="profile-info-list">
            <li class="item">
                <div class="left">
                    <span class="name">프로필 사진 변경</span>
                </div>
                <div class="right">
                    <button class="profile-img-submit submit">프로필 변경</button>
                </div>
            </li>
            <li class="item nickname">
                <div class="top">
                    <label>
                        닉네임
                        <input type="text"/>
                    </label>
                    <button class="edit-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px">
                            <path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z"/>
                        </svg>
                    </button>
                </div>
                <div class="bottom">
                    <label class="message">　</label>
                </div>
            </li>
            <li class="item email">
                <div class="top">
                    <label>
                        이메일
                        <input type="text" class="email"/>
                    </label>
                    <button class="edit-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px">
                            <path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z"/>
                        </svg>
                    </button>
                </div>
                <div class="bottom">
                    <label class="message">　</label>
                </div>
            </li>
            <li class="item introduction">
                <div class="top">
                    <label>
                        자기소개
                        <textarea class="introduction" rows="4"></textarea>
                    </label>
                </div>
                <div class="bottom">
                    <button class="submit">소개 변경</button>
                </div>
            </li>
            <li class="item delete-member">
                <label>
                    비밀번호
                    <input type="password" class="password"/>
                </label>
                <button class="delete-btn">회원 탈퇴</button>
            </li>
        </ul>
    </form>
</div>
<jsp:include page="../components/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>

    function softDeleteMember() {
        if (!confirm("삭제하시겠습니까?")) return;
        const password = document.querySelector('.password').value;
        const bodyObject = {};
        bodyObject["password"] = password;
        fetch("/member/delete", {
            method: "POST",
            body: JSON.stringify(bodyObject),
        }).then(response => response.json())
            .then((result) => {
                if (result.success === "ok") {
                    window.alert(result.message);
                    window.location.href = "/signin";
                } else {
                    window.alert(result.message);
                }
            return result.json();
        });
    }

    function genOnChangeInput(className) {
        const item = document.querySelector('.item.' + className);
        const inputMsgLabel = item.querySelector('label.message');
        const msg = genMsg(className);
        const regexChecker = debouncer(async (value) => {
            const valid = regexCheck(className, value);
            if (valid) {
                //This string is valid for the regular expression only, not for duplication
                const data = await duplicateCheck(className, value, inputMsgLabel);
                if (!data) {
                    item.classList.add("valid");
                    item.classList.remove("invalid");
                    inputMsgLabel.textContent = msg[valid ? "valid" : "invalid"];
                    return;
                }
                if (!data.isDuplication) {
                    item.classList.remove("invalid");
                    item.classList.add("valid");
                    inputMsgLabel.textContent = msg["valid"];
                } else {
                    item.classList.remove("valid");
                    item.classList.add("invalid");
                    inputMsgLabel.textContent = "중복된 " + enToKr(className) + "입니다";
                }
            } else {
                item.classList.remove("valid");
                item.classList.add("invalid");
                inputMsgLabel.textContent = msg[valid ? "valid" : "invalid"];
            }

        }, 400);
        return (event) => {
            const value = event.target.value;
            if (value && value.trim()) {
                regexChecker(value);
            } else {
                inputMsgLabel.textContent = "ㅤ";
                item.classList.remove("invalid");
                item.classList.remove("valid");
            }
        }
    }

    function onClickUpdate(className) {
        return (event) => {
            const item = document.querySelector(".mypage-form .item." + className);
            if(!item.classList.contains("valid")) {
                window.alert("변경할 수 없습니다");
            } else {
                const input = item.querySelector("input");
                const bodyObject = {};
                bodyObject[className] = input.value;
                fetch("/update/" + className, {
                    method : "POST",
                    body: JSON.stringify(bodyObject)
                })
                    .then(result => result.json())
                    .then(data => {
                        window.alert("변경되었습니다");
                    });
            }
        }
    }

    document.querySelector(".item.delete-member .delete-btn").addEventListener("click", softDeleteMember);



    document.querySelector(".item.nickname input").addEventListener("keyup",genOnChangeInput("nickname"));
    document.querySelector(".item.email input").addEventListener("keyup",genOnChangeInput("email"));

    document.querySelector(".item.nickname .edit-btn").addEventListener("click",onClickUpdate("nickname"));
    document.querySelector(".item.email .edit-btn").addEventListener("click",onClickUpdate("email"));

    document.querySelector(".item.introduction button.submit").addEventListener("click", (event) => {
       const textarea = document.querySelector(".item.introduction textarea.introduction");
       const value = textarea.value;
       fetch("/update/introduction", {
           method : "POST",
           body : JSON.stringify({
               introduction : value,
           })
       })
           .then(result => result.json())
           .then(data => {
                window.alert("변경되었습니다");
           });
    });

    function storeProfileImgSrc(data) {
        const {profileImgUrl} = data;
        localStorage.setItem("profile-img-url", profileImgUrl);
    }

    function setProfileImageFormSrcDefault() {
        const src = localStorage.getItem("profile-img-url");
        if (src) {
            const profileFormImgEl = document.querySelector("#profile-img");
            const profileImgWrapperEl = document.querySelector(`.profile-img-wrapper`);
            profileFormImgEl.setAttribute("src", src);
            profileImgWrapperEl.classList.add("active");
        }
    }

    function setProfileValueDefault(data) {
        const {nickname, email, introduction} = data;
        const nicknameInputEl = document.querySelector(".profile-info-list .item.nickname input");
        nicknameInputEl.value = nickname;
        const emailInputEl = document.querySelector(".profile-info-list .item.email input");
        emailInputEl.value = email;
        const introductionTextareaEl = document.querySelector(".profile-info-list .item.introduction textarea.introduction");
        introductionTextareaEl.textContent = introduction;
    }

    fetch("/memberinfo", {
        method: "GET"
    })
        .then((result) => result.json())
        .then((resp) => {
            const {data} = resp;
            if (data) {
                updateHeaderProfileImage(data);
                storeProfileImgSrc(data);
                setProfileImageFormSrcDefault();
                setHeaderDropdownMenu(true);
                setProfileValueDefault(data);
            } else {
                setHeaderDropdownMenu(false);
            }
        });

    const mypageForm = document.querySelector(".mypage-form");

    mypageForm.addEventListener("submit", (event) => {
        event.preventDefault();
    });

    function onChangeFileInput(event) {
        const file = event.target.files?.[0];
        if (!file) {
            window.alert("파일을 업로드 해 주세요");
            const profileImgWrapperEl = document.querySelector(`.profile-img-wrapper`);
            profileImgWrapperEl.classList.remove("active");
            setProfileImageFormSrcDefault();
            return;
        }
        const fileReader = new FileReader();
        fileReader.readAsDataURL(file);
        fileReader.onload = (data) => {
            const imgSrc = data.target?.result;
            const profileImgEl = document.querySelector(`img#profile-img`);
            const profileImgWrapperEl = document.querySelector(`.profile-img-wrapper`);
            profileImgWrapperEl.classList.add("active");
            profileImgEl.setAttribute("src", imgSrc);
        }
    }

    document.querySelector(".item .profile-img-submit").addEventListener("click", (event) => {
        const formData = new FormData();
        const file = document.querySelector('input#profile-img-input').files[0];
        if (!file) {
            window.alert("파일을 업로드 해 주세요");
            return;
        }
        formData.append("image", file);
        fetch("/update/imageUrl", {
            method: "POST",
            body: formData,
        }).then((result) => {
            if (result.ok) {
                window.alert("프로필 사진이 변경되었습니다");
            }
            return result.json();
        });
    });
</script>
</body>
</html>

