<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 6/18/24
  Time: 11:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="icon" href="${pageContext.request.contextPath}/public/favicon/favicon.ico" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.js"></script>
</head>
<body>
<jsp:include page="../components/header.jsp"/>

<main class="container-xl">
    <div id="div-group" class="d-flex flex-wrap gap-2 justify-content-center my-4"></div>

    <div id='calendar'></div>
</main>

<!-- Modal -->
<div class="modal fade" id="modal-add-event" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="modal-title">일정 추가</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="input-event-id" name="input-event-id">

                <div class="row m-3">
                    <label class="col-4" for="input-start-date">시작시간</label>
                    <input class="col-8" type="date" id="input-start-date" name="input-start-date">
                </div>
                <div class="row m-3">
                    <label class="col-4" for="input-end-date">종료시간</label>
                    <input class="col-8" type="date" id="input-end-date" name="input-end-date">
                </div>
                <div class="row m-3">
                    <label class="col-4" for="input-title">제목</label>
                    <input class="col-8" type="text" id="input-title" name="input-title">
                </div>
                <div class="row m-3">
                    <label class="col-4" for="input-content">내용</label>
                    <textarea class="col-8" cols="4" id="input-content" name="input-content"></textarea>
                </div>
                <div class="row m-3" id="div-input-group">
                    <label class="col-4" for="input-group">그룹</label>
                    <select class="col-8" id="input-group" name="input-group">
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="button-event-add">추가</button>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/schedule.js" defer></script>

<jsp:include page="../components/footer.jsp"/>
</body>
</html>
