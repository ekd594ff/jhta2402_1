<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="../components/header.jsp"/>
<div class="groupSearch-container">
    <form action="/group/search" class="custom-search-form" id="customSearchForm" method="get">
        <div class="input-group">
            <select class="custom-search-filter" id="customSearchFilter" name="searchFilter">
                <option value="all">전체</option>
                <option value="name">제목</option>
                <option value="creator">작성자</option>
                <option value="content">내용</option>
            </select>
            <input class="custom-search-box form-control" type="search" id="customSearchQuery" name="searchValue"
                   placeholder="검색어를 입력하세요." autocomplete="off" value="${param.query}">
            <div class="input-group-append">
                <button class="custom-search-btn" type="button" onclick="clickSearchGroup();">Click</button>
            </div>
        </div>
    </form>
    <ul class="list" id="content"></ul>
</div>
<jsp:include page="../components/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/components/groupitem.js"></script>
<script>

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
    let debounceTimer;
    window.addEventListener('scroll', debounce(handleScroll, 200));
    async function handleScroll(){
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

    function clickSearchGroup() {
        const searchFilter = document.getElementById('customSearchFilter').value;
        const searchQuery = document.getElementById('customSearchQuery').value;

        // 검색 필터와 검색어를 URL 파라미터로 추가하여 이동
        // const url = "/group/search?searchFilter="+searchFilter+"&searchValue="+searchQuery;
        window.location.search = "?searchFilter="+searchFilter+"&searchValue="+searchQuery;
    }
    // function setGroupInfoDefault(data) {
    //     const {creator, name, imageUrl, content, created_at} = data;
    //     const creatorEl = document.querySelector(".group-info .item.group-editor");
    //     console.log(creatorEl);
    //     creatorEl.textContent = creator;
    //     const nameEl = document.querySelector(".group-info .item.group-name");
    //     nameEl.textContent = name;
    //     const imageUrlEl = document.querySelector(".group-info .item.group-img");
    //     if (imageUrl === undefined) {
    //         // imageUrlEl.textContent =
    //     } else {
    //         imageUrlEl.textContent = imageUrl;
    //     }
    //     const contentEl = document.querySelector(".group-info .item.group-content");
    //     contentEl.textContent = content;
    //     const created_atEL = document.querySelector(".group-info .item.group-createdAt");
    //     created_atEL.textContent = created_at;
    // }
</script>
</html>