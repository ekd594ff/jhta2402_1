<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<script type="text/javascript">
    function searchGroup() {
        alert("searchGroup");
        var searchQuery = document.getElementById("searchQuery").value;
        var form = document.getElementById("searchForm");
        form.action = "/group/search";
        form.submit();

    }
</script>
<form id="searchForm" method="post">
    <header class="header" style="box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;">
        <div class="header-container" style="display: flex;justify-content: space-between;padding: 0 16px">
            <div class="left">
                <a href="/">
                    <img src="${pageContext.request.contextPath}/public/imgs/jalendar.svg" height="36px"/>
                </a>
            </div>
            <div class="right">
                <input class="search-box" type="search" id="searchQuery" name="searchQuery" placeholder="검색어를 입력하세요." autocomplete="off" value="${param.query}">
                <button class="search-btn" type="button" onclick="searchGroup();">클릭</button>
                <div class="avatar" style="width: 36px; height: 36px; border-radius: 50%;background-color: #909090; display: flex !important; justify-content: center; align-items: center;">
                    <svg focusable="false"
                         aria-hidden="true" viewBox="0 0 24 24" data-testid="PersonIcon" width="28px" height="28px" fill="#fff">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path>
                    </svg>
                    <img class="avatar-img" src="" alt="avatar image"/>
                </div>
                <div class="dropdown header-dropdown">
                    <ul class="dropdown-menu">
                        <div class="not-login">
                            <li><a class="dropdown-item" href="/signin">로그인</a></li>
                            <li><a class="dropdown-item" href="/signup">회원가입</a></li>
                        </div>
                        <div class="login">
                            <li><a class="dropdown-item" href="/mypage">마이페이지</a></li>
                            <li><a class="dropdown-item" href="/schedule/my">내 일정</a></li>
                            <li><a class="dropdown-item" onclick="logoutHandler(event)">로그아웃</a></li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </header>
</form>
</html>
