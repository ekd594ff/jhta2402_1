<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/global.css">
    <link rel="icon" href="public/favicon/favicon.ico" type="image/x-icon">
    <title>Jalendar</title>
</head>
<body>
<jsp:include page="components/header.jsp"/>
<div class="body-container"></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<jsp:include page="components/footer.jsp"/>
<script src="js/common.js"></script>
<script>
    fetch("/memberinfo", {
        method: "GET"
    })
        .then((result) => result.json())
        .then((resp) => {
            const {data} = resp;
            if (data) {
                updateHeaderProfileImage(data);
                setHeaderDropdownMenu(true);
            } else {
                setHeaderDropdownMenu(false);
            }
        });
</script>
</body>
</html>