<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Jalendar</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>
<main class="index">
    <div class="index-container">
        <div class="landing l-1">
            <h1>쉽고 편한 일정 관리 플랫폼</h1>
            <h3>
                <img src="${pageContext.request.contextPath}/public/imgs/jalendar.svg" height="36px" alt="logo"/>
            </h3>
        </div>
        <div class="landing l-2">
            <h2>드래그 앤 드롭으로 편리하게</h2>
            <img src="${pageContext.request.contextPath}/public/imgs/landing-2.webp" width="100%" alt="landing"/>
        </div>
        <div class="landing l-3">
            <h2>그룹으로 일정을 공유해 보세요</h2>
            <img src="${pageContext.request.contextPath}/public/imgs/landing-1.png" width="100%" alt="landing"/>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<jsp:include page="components/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
</body>
</html>