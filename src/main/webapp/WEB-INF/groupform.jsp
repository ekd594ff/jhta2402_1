<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
    <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <form action="/group/crud" method="post">
        <div class="form-group">
            <label for="name">Name : </label>
            <input type="text" class="form-control" placeholder="Enter name" id="name">
        </div>
        <div class="form-group">
            <label for="creater">Creater : </label>
            <input type="text" class="form-control" placeholder="Enter creater" id="creater">
        </div>
        <div class="form-group">
            <label for="content">Content : </label>
            <input type="text" class="form-control" placeholder="Enter content" id="content">
        </div>

    </form>
    <button class="btn btn-primary" id="btn-save">그룹생성 완료</button>
</div>
<script>
    let index = {
        init: function () {
            $("#btn-save").on("click", () => {
                this.save();
            });
        },
        save: function() {
            // alert('user의 save 함수 호출 됨');
            let data = {
                name: $("#name").val(),
                creater: $("#creater").val(),
                content: $("#content").val()
            };
            // console.log(data);

            $.ajax({
                type: "POST",
                url: "/group/crud",
                data: JSON.stringify(data),
                contentType: "aplication/json; charset=utf-8",
                dataType: "json"
            }).done(function (resp) {
                console.log(resp);
                alert("그룹 생성이 완료되었습니다.");
                location.href = "/";
            }).fail(function (error) {
                alert("그룹 생성 실패했습니다.");
            });
        }
    }
    index.init();

</script>
</body>
</html>
