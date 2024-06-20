<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="../components/header.jsp"/>
<div class="groupSearch-container">
<%--    <div class="group-container">--%>
<%--        <div class="group-info">--%>
<%--            <div class="item group-img">--%>
<%--                <div class="img-container">--%>
<%--                    <svg focusable="false"--%>
<%--                         class="default"--%>
<%--                         aria-hidden="true" viewBox="0 0 24 24" data-testid="PersonIcon" fill="#fff">--%>
<%--                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path>--%>
<%--                    </svg>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <ul>--%>
<%--                <li class="item group-name">　</li>--%>
<%--                <li class="item group-editor">　</li>--%>
<%--                <li class="item group-content">　</li>--%>
<%--                <li class="item group-createdAt">　</li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--        <button class="btn btn-primary">follow</button>--%>
<%--    </div>--%>
    <ul class="list"></ul>
</div>
<jsp:include page="../components/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/js/components/groupitem.js"></script>
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
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(inputdata)
    })
        .then((result) => {
            return result.json();
        })
        .then((resp) => {
            const {result} = resp;
            console.log(result);
            if (Array.isArray(result)){
                const list = document.querySelector(".list");
                for(const data of result) {

                    const groupitem = groupItem(data);
                    list.appendChild(groupitem);
                }
            }
        });
    function setProfileValueDefault(data) {
        const {creator, name, imageUrl, content, created_at} = data;
        const creatorEl = document.querySelector(".group-info .item.group-editor");
        console.log(creatorEl);
        creatorEl.textContent = creator;
        const nameEl = document.querySelector(".group-info .item.group-name");
        nameEl.textContent = name;
        const imageUrlEl = document.querySelector(".group-info .item.group-img");
        if (imageUrl === undefined) {
            // imageUrlEl.textContent =
        } else {
            imageUrlEl.textContent = imageUrl;
        }
        const contentEl = document.querySelector(".group-info .item.group-content");
        contentEl.textContent = content;
        const created_atEL = document.querySelector(".group-info .item.group-createdAt");
        created_atEL.textContent = created_at;
    }
</script>
</html>