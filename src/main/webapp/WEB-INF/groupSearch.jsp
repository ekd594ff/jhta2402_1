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
    <ul class="list" id="content"></ul>
</div>
<jsp:include page="../components/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/js/components/groupitem.js"></script>
<script>
    let debounceTimer;
    let isListOver = false;
    let searchParams = new URLSearchParams(window.location.search);
    let searchFilter = searchParams.get("searchFilter");
    let searchValue = searchParams.get("searchValue");
    let isLoading = false;
    let total;
    let inputdata = {
        "searchFilter" : searchFilter,
        "searchValue" : searchValue,
        "page" : 1
    }
    document.addEventListener('DOMContentLoaded',()=> {
        fetchData(inputdata);
    });
    function fetchData(inputdata){ //데이터 값 부르고 포스트 추가
        if(isListOver) {
            return;
        }
        fetch("/group/search", {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(inputdata)
        })
            .then((response) => {
                return response.json();
            })
            .then((resp) => {
                const {result} = resp;
                total = resp;
                console.log(total);
                console.log(result);
                if (Array.isArray(result)){
                    console.log(result.length)
                    if (result.length === 0) {
                        isListOver = true;
                    }
                    const list = document.querySelector(".list");
                    for(const data of result) {
                        const groupitem = groupItem(data);
                        list.appendChild(groupitem);
                    }
                }
            });
    }

    window.addEventListener('scroll', debounce(handleScroll, 200));
    async function handleScroll(){
        const loading = document.getElementById('loading');
        if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100 && !isLoading) {
            isLoading = true;
            inputdata.page++;
            await fetchData(inputdata);
            isLoading = false;
        }
    }

    function debounce(func, wait) {
        return function(...args) {
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(() => func.apply(this, args), wait);
        };
    }

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