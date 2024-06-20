<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="../components/header.jsp"/>
<div class="groupSearch-container">
    <div class="group-container">
        <div class="group-info">
            <div class="group-img">
                <svg focusable="false"
                     class="default"
                     aria-hidden="true" viewBox="0 0 24 24" data-testid="PersonIcon" fill="#fff">
                    <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path>
                </svg>
            </div>
            <ul>
                <li class="group-name">그룹 이름</li>
                <li class="group-editor">그룹 장</li>
                <li class="group-content">그룹 내용</li>
                <li class="group-createdAt">그룹 생성일</li>
            </ul>
        </div>
        <button class="btn btn-primary">follow</button>
    </div>

</div>
<jsp:include page="../components/footer.jsp"/>
</body>
<script>
    function updateSearchGroupList(data) {
        const {profileImgUrl} = data;
        localStorage.setItem("profile-img-url", profileImgUrl);
    }
    let searchParams = new URLSearchParams(window.location.search);
    let searchFilter = searchParams.get("searchFilter");
    let searchValue = searchParams.get("searchValue");
    let page = searchParams.get("page");
    let inputdata = {
        "searchFilter" : searchFilter,
        "searchValue" : searchValue,
        "page" : page
    }
    fetch("/group/search", {
        method: "POST",
        headers : {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(inputdata)
    })
        .then((result) => {
            return result.json();
        })
        .then((resp) => {
            console.log(resp);
        });
</script>
</html>