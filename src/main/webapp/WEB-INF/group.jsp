<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
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
    <script src="${pageContext.request.contextPath}/js/group-schedule.js" defer></script>
    <meta name="viewport" content="width=device-width,initial-scale=1">
</head>
<body>
<div id="event-popover">
    <ul class="popover-list">
        <li class="event-title">
            <input type="text" id="event-title"/>
        </li>
        <li class="event-term">
            <label>
                시작일
                <input type="date" id="event-start-date"/>
            </label>
            <label>
                종료일
                <input type="date" id="event-end-date"/>
            </label>
        </li>
        <li class="event-content">
            <label>
                내용
                <textarea id="event-content" rows="3"></textarea>
            </label>
        </li>
        <li class="event-id">
            <label>
                <input id="event-id"/>
            </label>
        </li>
        <li class="popover-btn-group">
            <button class="popover-btn event-edit-btn" onclick="onClickEventEditBtn(event)">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px">
                    <path d="M200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h357l-80 80H200v560h560v-278l80-80v358q0 33-23.5 56.5T760-120H200Zm280-360ZM360-360v-170l367-367q12-12 27-18t30-6q16 0 30.5 6t26.5 18l56 57q11 12 17 26.5t6 29.5q0 15-5.5 29.5T897-728L530-360H360Zm481-424-56-56 56 56ZM440-440h56l232-232-28-28-29-28-231 231v57Zm260-260-29-28 29 28 28 28-28-28Z"/>
                </svg>
                일정 수정
            </button>
            <button class="popover-btn event-delete-btn" onclick="onClickEventDeleteBtn(event)">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px">
                    <path d="M280-120q-33 0-56.5-23.5T200-200v-520h-40v-80h200v-40h240v40h200v80h-40v520q0 33-23.5 56.5T680-120H280Zm400-600H280v520h400v-520ZM360-280h80v-360h-80v360Zm160 0h80v-360h-80v360ZM280-720v520-520Z"/>
                </svg>
                일정 삭제
            </button>
        </li>
    </ul>
    <ul class="popover-list add">
        <li class="event-title">
            <input type="text" id="add-event-title" placeholder="새 일정을 입력해 주세요"/>
        </li>
        <li class="event-term">
            <label>
                시작일
                <input type="date" id="add-event-start-date"/>
            </label>
            <label>
                종료일
                <input type="date" id="add-event-end-date"/>
            </label>
        </li>
        <li class="event-content">
            <label>
                내용
                <textarea id="add-event-content" rows="3"></textarea>
            </label>
        </li>
        <li class="event-id">
            <label>
                <input id="add-event-id"/>
            </label>
        </li>
        <li class="popover-btn-group">
            <button class="popover-btn event-add-btn" onclick="onClickCreateEvent(event)">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px">
                    <path d="M680-80v-120H560v-80h120v-120h80v120h120v80H760v120h-80Zm-480-80q-33 0-56.5-23.5T120-240v-480q0-33 23.5-56.5T200-800h40v-80h80v80h240v-80h80v80h40q33 0 56.5 23.5T760-720v244q-20-3-40-3t-40 3v-84H200v320h280q0 20 3 40t11 40H200Zm0-480h480v-80H200v80Zm0 0v-80 80Z"/>
                </svg>
                일정 추가
            </button>
        </li>
    </ul>
</div>
<jsp:include page="../components/header.jsp"/>
<main class="myschedule">
    <div class="myschedule-container">
        <div class="group-info">
            <div class="left">
                <div class="group-img-container">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960">
                        <path d="M0-240v-63q0-43 44-70t116-27q13 0 25 .5t23 2.5q-14 21-21 44t-7 48v65H0Zm240 0v-65q0-32 17.5-58.5T307-410q32-20 76.5-30t96.5-10q53 0 97.5 10t76.5 30q32 20 49 46.5t17 58.5v65H240Zm540 0v-65q0-26-6.5-49T754-397q11-2 22.5-2.5t23.5-.5q72 0 116 26.5t44 70.5v63H780Zm-455-80h311q-10-20-55.5-35T480-370q-55 0-100.5 15T325-320ZM160-440q-33 0-56.5-23.5T80-520q0-34 23.5-57t56.5-23q34 0 57 23t23 57q0 33-23 56.5T160-440Zm640 0q-33 0-56.5-23.5T720-520q0-34 23.5-57t56.5-23q34 0 57 23t23 57q0 33-23 56.5T800-440Zm-320-40q-50 0-85-35t-35-85q0-51 35-85.5t85-34.5q51 0 85.5 34.5T600-600q0 50-34.5 85T480-480Zm0-80q17 0 28.5-11.5T520-600q0-17-11.5-28.5T480-640q-17 0-28.5 11.5T440-600q0 17 11.5 28.5T480-560Zm1 240Zm-1-280Z"/>
                    </svg>
                    <img src="" class="group-img"/>
                </div>
            </div>
            <div class="right">
                <h2 class="group-name" id="div-group-name">그룹 이름</h2>
                <pre class="group-content" id="div-group-content">그룹 내용</pre>
                <div class="follow-btn-group">
                    <button class="follow">팔로우</button>
                    <button class="unfollow">언팔로우</button>
                    <button class="group-edit">그룹수정</button>
                </div>
            </div>
        </div>
        <div id="div-group" class="d-flex flex-wrap gap-2 justify-content-center my-4"></div>
        <div id='calendar' class="jalendar"></div>
    </div>
</main>
<jsp:include page="../components/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
</body>
</html>
