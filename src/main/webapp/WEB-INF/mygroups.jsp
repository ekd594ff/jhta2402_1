<%--
  Created by IntelliJ IDEA.
  User: jhta
  Date: 2024-06-21
  Time: 오후 2:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="../components/header.jsp"/>
    <div class="groupSearch-container">
        <ul class="list" id="mygroups">
            <h2>내가 생성한 그룹</h2>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-icon mygroups-nothing" style="width: 32px; height: 32px;vertical-align: middle;fill: currentColor;overflow: hidden; display: none" viewBox="0 0 1024 1024" version="1.1">
                <path d="M512 64c-247.4 0-448 200.6-448 448 0 247.4 200.6 448 448 448s448-200.6 448-448C960 264.6 759.4 64 512 64L512 64zM118.3 512c0-217.4 176.3-393.7 393.7-393.7 94.3 0 180.9 33.2 248.7 88.5 10.3 8.4 20.1 17.3 29.5 26.6L220.8 777c-1-1.1-2-2.2-2.9-3.3C155.9 704.2 118.3 612.5 118.3 512L118.3 512zM512 905.7c-94.8 0-181.8-33.5-249.7-89.3l565.4-539.7c49 65.6 78 147.1 78 235.3C905.7 729.4 729.4 905.7 512 905.7L512 905.7zM512 905.7"/></svg>
        </ul>
        <div class="hidden-item-btn-group">
            <button class="btn show-myitem-btn hidden" >더보기</button>
            <button class="btn hidden-myitem-btn hidden" >숨기기</button>
        </div>
    </div>
    <div class="groupSearch-container">
        <ul class="list" id="followgroups">
            <h2>팔로우 한 그룹</h2>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-icon followgroups-nothing" style="width: 32px; height: 32px;vertical-align: middle;fill: currentColor;overflow: hidden; display: none" viewBox="0 0 1024 1024" version="1.1">
                <path d="M512 64c-247.4 0-448 200.6-448 448 0 247.4 200.6 448 448 448s448-200.6 448-448C960 264.6 759.4 64 512 64L512 64zM118.3 512c0-217.4 176.3-393.7 393.7-393.7 94.3 0 180.9 33.2 248.7 88.5 10.3 8.4 20.1 17.3 29.5 26.6L220.8 777c-1-1.1-2-2.2-2.9-3.3C155.9 704.2 118.3 612.5 118.3 512L118.3 512zM512 905.7c-94.8 0-181.8-33.5-249.7-89.3l565.4-539.7c49 65.6 78 147.1 78 235.3C905.7 729.4 729.4 905.7 512 905.7L512 905.7zM512 905.7"/></svg>
        </ul>
        <div class="hidden-item-btn-group">
            <button class="btn show-item-btn hidden" >더보기</button>
            <button class="btn hidden-item-btn hidden" >숨기기</button>
        </div>
    </div>
<jsp:include page="../components/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/js/components/groupitem.js"></script>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    let isMyGroupBlank = false;
    let isFollowGroupBlank = false;
    const showMyItemBtn = document.querySelector('.show-myitem-btn');
    const hiddenMyItemBtn = document.querySelector('.hidden-myitem-btn');
    const showItemBtn = document.querySelector('.show-item-btn');
    const hiddenItemBtn = document.querySelector('.hidden-item-btn');

    fetch("/group/follow", {
        method: "GET",
    })
        .then((response) => {
            return response.json();
        })
        .then((resp) => {
            const {myGroups,followGroups} = resp;
            if (Array.isArray(myGroups)){
                if (myGroups.length === 0) {
                    document.querySelector('.mygroups-nothing').style.display='flex';
                }
                const list = document.querySelector("#mygroups ");
                myGroups.forEach((data, index) => {
                    const groupitem = groupItem(data);
                    list.appendChild(groupitem);
                    if (index > 2) {
                        groupitem.classList.add("hidden");
                        groupitem.classList.add("hidden-myitem");
                    }
                })
                hiddenMyItems=document.querySelectorAll('.hidden-myitem');
                if (myGroups.length > 3) {
                    showMyItemBtn.classList.remove("hidden");
                }
            }
            if (Array.isArray(followGroups)){
                if (followGroups.length === 0) {
                    document.querySelector('.followgroups-nothing').style.display = 'flex';
                }
                const list = document.querySelector("#followgroups");
                followGroups.forEach((data, index) => {
                    const groupitem = groupItem(data);
                    list.appendChild(groupitem);
                    if (index > 2) {
                        groupitem.classList.add("hidden");
                        groupitem.classList.add("hidden-followitem");
                    }
                })
                hiddenFollowItems=document.querySelectorAll('.hidden-followitem');
                if (followGroups.length > 3) {
                    showItemBtn.classList.remove("hidden");
                }
            }
        });

    showMyItemBtn.onclick = () => {
        showMyItemBtn.classList.add("hidden");
        hiddenMyItemBtn.classList.remove("hidden");
        hiddenMyItems.forEach(element =>{
            element.classList.remove("hidden");
        });
    }
    hiddenMyItemBtn.onclick = () => {
        hiddenMyItemBtn.classList.add("hidden");
        showMyItemBtn.classList.remove("hidden");
        hiddenMyItems.forEach(element =>{
            element.classList.add("hidden");
        });
    }
    showItemBtn.onclick = () => {
        showItemBtn.classList.add("hidden");
        hiddenItemBtn.classList.remove("hidden");
        hiddenFollowItems.forEach(element =>{
            element.classList.remove("hidden");
        });
    }
    hiddenItemBtn.onclick = () => {
        hiddenItemBtn.classList.add("hidden");
        showItemBtn.classList.remove("hidden");
        hiddenFollowItems.forEach(element =>{
            element.classList.add("hidden");
        });
    }
</script>
</html>
