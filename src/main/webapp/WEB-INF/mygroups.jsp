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
    </div>
    <div class="groupSearch-container">
        <ul class="list" id="followgroups">
            <h2>팔로우 한 그룹</h2>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-icon followgroups-nothing" style="width: 32px; height: 32px;vertical-align: middle;fill: currentColor;overflow: hidden; display: none" viewBox="0 0 1024 1024" version="1.1">
                <path d="M512 64c-247.4 0-448 200.6-448 448 0 247.4 200.6 448 448 448s448-200.6 448-448C960 264.6 759.4 64 512 64L512 64zM118.3 512c0-217.4 176.3-393.7 393.7-393.7 94.3 0 180.9 33.2 248.7 88.5 10.3 8.4 20.1 17.3 29.5 26.6L220.8 777c-1-1.1-2-2.2-2.9-3.3C155.9 704.2 118.3 612.5 118.3 512L118.3 512zM512 905.7c-94.8 0-181.8-33.5-249.7-89.3l565.4-539.7c49 65.6 78 147.1 78 235.3C905.7 729.4 729.4 905.7 512 905.7L512 905.7zM512 905.7"/></svg>
        </ul>
    </div>
<jsp:include page="../components/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/js/components/groupitem.js"></script>
<script>
    let isMyGroupBlank = false;
    let isFollowGroupBlank = false;


    fetch("/group/follow", {
        method: "GET",
    })
        .then((response) => {
            return response.json();
        })
        .then((resp) => {
            const {myGroups,followGroups} = resp;
            console.log(myGroups);
            console.log(followGroups);
            if (Array.isArray(myGroups)){
                console.log(myGroups.length)
                if (myGroups.length === 0) {
                    document.querySelector('.mygroups-nothing').style.display='flex';
                }
                const list = document.querySelector("#mygroups ");
                for(const data of myGroups) {
                    const groupitem = groupItem(data);
                    list.appendChild(groupitem);
                }
            }
            if (Array.isArray(followGroups)){
                console.log(followGroups.length)
                if (followGroups.length === 0) {
                    document.querySelector('.followgroups-nothing').style.display = 'flex';
                }
                const list = document.querySelector("#followgroups");
                for(const data of followGroups) {
                    const groupitem = groupItem(data);
                    list.appendChild(groupitem);
                }
            }
        });

</script>
</html>
