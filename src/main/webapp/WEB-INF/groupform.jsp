<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
    <title>그룹 생성</title>
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
            <li class="item" id="profile-img-id">
                <div class="left">
                    <span class="name">그룹 사진 변경</span>
                </div>
                <div class="right">
                    <button class="profile-img-submit submit">사진 변경</button>
                </div>
            </li>
            <li class="item nickname">
                <div class="top">
                    <label>
                        그룹 이름
                        <input type="text"/>
                    </label>
                    <button class="profile-name-submit submit">변경</button>
                    <%--                    <button class="edit-btn">--%>
                    <%--                        <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px">--%>
                    <%--                            <path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z"/>--%>
                    <%--                        </svg>--%>
                    <%--                    </button>--%>
                </div>
                <div class="bottom">
                    <label class="message">　</label>
                </div>
            </li>
            <li class="item introduction">
                <div class="top">
                    <label>
                        설명
                        <textarea class="introduction" rows="4"></textarea>
                    </label>
                </div>
                <div class="bottom">
                    <button class="submit">소개 변경</button>
                </div>
            </li>
        </ul>
        <div class="group-submit">
            <button class="create-group">등록</button>
        </div>
    </form>
</div>
<jsp:include page="../components/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>

    const currentPath = window.location.pathname;

    const mypageForm = document.querySelector(".mypage-form");

    mypageForm.addEventListener("submit", (event) => {
        event.preventDefault();
    });

    if ("${sessionScope.member.username}" === "") {
        alert('로그인 후 이용 가능합니다');
        window.location.href = '/signin';
    }

    if (currentPath === '/group/update') {
        fetch("/group/info?id=" + "${requestScope.id}", {
            method: "GET"
        })
            .then((result) => result.json())
            .then((resp) => {
                if (resp.creator !== "${sessionScope.member.username}") {
                    alert('유효하지 않은 접근입니다');
                    window.location.href = '/group/list';
                }

                storeProfileImgSrc(resp);
                setProfileImageFormSrcDefault();
                setProfileValueDefault(resp);

            });

        document.querySelectorAll("button").forEach((button) => {
            button.style.visibility = (button.className !== 'create-group') ? '' : 'hidden';
        });

        document.querySelector(".item.nickname .profile-name-submit").addEventListener("click", onClickUpdate());

        document.querySelector(".item.introduction button.submit").addEventListener("click", (event) => {
            const textarea = document.querySelector(".item.introduction textarea.introduction");
            const value = textarea.value;

            let formData = new FormData();
            formData.append('id', ${requestScope.id});
            formData.append('content', value);

            fetch("/group/crud", {
                method: "PUT",
                body: formData
            })
                .then(result => result.json())
                .then(data => {
                    window.alert("변경되었습니다");
                });
        });

        document.querySelector(".item .profile-img-submit").addEventListener("click", (event) => {
            const content = document.querySelector(".item.introduction textarea.introduction").value;

            const file = document.querySelector('input#profile-img-input').files[0];
            if (!file) {
                window.alert("파일을 업로드 해 주세요");
                return;
            }

            let formData = new FormData();
            formData.append('id', ${requestScope.id});
            formData.append('image', file);

            fetch("/group/crud", {
                method: "PUT",
                body: formData,
            }).then((result) => {
                if (result.ok) {
                    window.alert("그룹 사진이 변경되었습니다");
                }
                return result.json();
            });
        });

    } else if (currentPath === '/group/create') {

        document.querySelectorAll("button").forEach((button) => {
            button.style.visibility = (button.className !== 'create-group') ? 'hidden' : '';
        });
        document.getElementById('profile-img-id').style.visibility = 'hidden';

        document.querySelector(".mypage-form .group-submit button").addEventListener("click", (event) => {

            const groupName = document.querySelector(".profile-info-list .item.nickname input").value;
            const content = document.querySelector(".item.introduction textarea.introduction").value;
            const file = document.querySelector('input#profile-img-input').files[0];

            if (!groupName) {
                window.alert("그룹 이름을 입력해주세요");
                return;
            }

            let formData = new FormData();
            formData.append("name", groupName);
            formData.append("content", content);
            formData.append("image", file);

            fetch("/group/crud", {
                method: "POST",
                body: formData,
            }).then((result) => {
                if (result.ok) {
                    window.alert("그룹이 추가되었습니다");
                    window.location.href = '/group/list';
                }
                return result.json();
            });
        });
    }

    function onClickUpdate() {

        return (event) => {
            const item = document.querySelector(".mypage-form .item.nickname");

            const input = item.querySelector("input");

            let formData = new FormData();
            formData.append('id', ${requestScope.id});
            formData.append('name', input.value);

            fetch("/group/crud", {
                method: "PUT",
                body: formData
            })
                .then(result => result.json())
                .then(data => {
                    window.alert("변경되었습니다");
                });
        }
    }

    function storeProfileImgSrc(resp) {
        localStorage.setItem("profile-img-url", resp.image_url);
    }

    function setProfileImageFormSrcDefault() {
        const src = localStorage.getItem("profile-img-url");
        if (src) {
            const profileFormImgEl = document.querySelector("#profile-img");
            const profileImgWrapperEl = document.querySelector(`.profile-img-wrapper`);
            profileFormImgEl.setAttribute("src",  "/" + src);
            profileImgWrapperEl.classList.add("active");
        }
    }

    function setProfileValueDefault(resp) {
        const nicknameInputEl = document.querySelector(".profile-info-list .item.nickname input");
        nicknameInputEl.value = resp.name;
        const introductionTextareaEl = document.querySelector(".profile-info-list .item.introduction textarea.introduction");
        introductionTextareaEl.textContent = resp.content;
    }

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
</script>
</body>
</html>
