<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>그룹 생성</title>
</head>
<body>
<jsp:include page="../components/header.jsp"/>
<main class="group-container">
    <form class="group-form">
        <h2>그룹 생성</h2>
        <ul class="list">
            <li class="item group-img-title">
                <div class="left">
                    <div class="group-img-container">
                        <label class="group-img-wrapper" for="group-img-input">
                            <svg class="upload-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960">
                                <path d="M440-200h80v-167l64 64 56-57-160-160-160 160 57 56 63-63v167ZM240-80q-33 0-56.5-23.5T160-160v-640q0-33 23.5-56.5T240-880h320l240 240v480q0 33-23.5 56.5T720-80H240Zm280-520v-200H240v640h480v-440H520ZM240-800v200-200 640-640Z"/>
                            </svg>
                        </label>
                        <img src="" id="group-img" alt="group image"/>
                    </div>
                    <input id="group-img-input" type="file" onchange="onChangeFileInput(event)"/>
                </div>
                <div class="right">
                    <label class="group-input-group">
                        그룹 이름
                        <input type="text" class="group-input" id="group-name">
                        <label class="input-msg">ㅤ</label>
                    </label>
                </div>
            </li>
            <li class="item">
                <label class="group-input-group">
                    그룹 설명
                    <textarea rows="4" class="group-input" id="group-content"></textarea>
                </label>
            </li>
            <li class="item btn-group">
                <div></div>
                <button class="submit">그룹 생성</button>
            </li>
        </ul>
    </form>

</main>
<jsp:include page="../components/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    const groupForm = document.querySelector(".group-form");

    groupForm.addEventListener("submit", (event) => {
        event.preventDefault();
    });
  
    if ("${sessionScope.member.username}" === "") {
        alert('로그인 후 이용 가능합니다');
        window.location.href = '/signin';
    }

    // document.querySelectorAll("button").forEach((button) => {
    //     button.style.visibility = (button.className !== 'create-group') ? 'hidden' : '';
    // });

    document.querySelector(".btn-group .submit").addEventListener("click", (event) => {

        const groupName = document.querySelector("#group-name").value;
        const content = document.querySelector("#group-content").value;
        const file = document.querySelector('#group-img-input').files[0];

        if (!groupName) {
            window.alert("그룹 이름을 입력해주세요");
            return;
        }

        const formData = new FormData();

        formData.append("name", groupName);
        formData.append("content", content);
        formData.append("image", file);

        fetch("/group/crud", {
            method: "POST",
            body: formData,
        }).then((result) => {
            if (result.ok) {
                window.alert("그룹이 추가되었습니다");
                window.location.href = '/group/my';
            }
            return result.json();
        });
    });

    function setGroupImageFormSrcDefault(resp) {
        const imageUrl = resp.image_url;
        if (imageUrl) {
            const groupFormImgEl = document.querySelector("#group-img");
            const groupImgWrapperEl = document.querySelector('.group-img-wrapper');
            groupFormImgEl.setAttribute("src",  "/" + imageUrl);
            groupFormImgEl.style.display = "block";
            groupImgWrapperEl.classList.add("active");
        }
    }

    function setGroupValueDefault(resp) {
        const groupNameInputEl = document.querySelector("#group-name");
        groupNameInputEl.value = resp.name;
        const groupContentTextarea = document.querySelector("#group-content");
        groupContentTextarea.textContent = resp.content;
    }

    function onChangeFileInput(event) {
        const file = event.target.files?.[0];
        const groupImgContainerEl = document.querySelector(`.group-img-container`);
        const groupImgEl = document.querySelector(`img#group-img`);
        if (!file) {
            window.alert("파일을 업로드 해 주세요");
            groupImgContainerEl.classList.remove("active");
            groupImgEl.setAttribute("src", "");
            return;
        }
        const fileReader = new FileReader();
        fileReader.readAsDataURL(file);
        fileReader.onload = (data) => {
            const imgSrc = data.target?.result;
            groupImgContainerEl.classList.add("active");
            groupImgEl.setAttribute("src", imgSrc);
        }
    }
</script>
</body>
</html>
