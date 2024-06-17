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
        <div class="profile-image-wrapper">
        </div>
    </form>
</div>
<jsp:include page="../components/footer.jsp"/>
<script>
    const mypageForm = document.querySelector(".mypage-form");
    mypageForm.addEventListener("submit", (event) => {
        event.preventDefault();
    });
</script>
</body>
</html>

