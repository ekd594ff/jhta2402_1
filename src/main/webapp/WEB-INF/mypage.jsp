<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
    <title>${infoMemberDTO.nickname}</title>
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
            <button class="profile-img-submit">✓</button>
            <input class="file-input" id="profile-img-input" type="file" onchange="onChangeFileInput(event)"/>
        </div>
    </form>
</div>
<jsp:include page="../components/footer.jsp"/>
<script>
    localStorage.removeItem("profileImg");

    const mypageForm = document.querySelector(".mypage-form");
    mypageForm.addEventListener("submit", (event) => {
        event.preventDefault();
    });

    function onChangeFileInput(event) {
        const file = event.target.files?.[0];
        if (!file) {
            window.alert("파일을 업로드 해 주세요");
            const profileImgWrapperEl = document.querySelector(`.profile-img-wrapper`);
            const profileImgEl = document.querySelector(`img#profile-img`);
            profileImgWrapperEl.classList.remove("active");
            profileImgEl.setAttribute("src", "");
            localStorage.removeItem("profileImg");
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
            localStorage.setItem("profileImg", file);
        }
    }

    document.querySelector("button.profile-img-submit").addEventListener("click", (event) => {
            const formData = new FormData();
            const file = document.querySelector('input#profile-img-input').files[0];
            if(!file) {
                window.alert("파일을 업로드 해 주세요");
                return;
            }
            formData.append("image", file);
            fetch("/update/imageUrl", {
                method : "POST",
                body: formData,
            }).then((result) => {
                return result.json();
            }).then((data) => {
                console.log(data);
            });
    });
</script>
</body>
</html>

