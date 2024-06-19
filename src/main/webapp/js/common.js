function updateHeaderProfileImage(data) {
    const { profileImgUrl } = data;
    if(profileImgUrl) {
        const avatarEl = document.querySelector(".header .avatar");
        avatarEl.classList.add("active");
        const avatarImageEl = avatarEl.querySelector(".avatar-img");
        avatarImageEl.setAttribute("src", profileImgUrl);
    }
}

function removeHeaderProfileImage() {
    const avatarEl = document.querySelector(".header .avatar");
    avatarEl.classList.remove("active");
    const avatarImageEl = avatarEl.querySelector(".avatar-img");
    avatarImageEl.setAttribute("src", "");
}

function toggleDropdownMenu(event) {
    event.stopPropagation();
    const headerDropdownMenu = document.querySelector(".header .dropdown-menu");
    const toggleValue = headerDropdownMenu.classList.contains("show");
    if(toggleValue) {
        headerDropdownMenu.classList.remove("show");
    } else {
        headerDropdownMenu.classList.add("show");
    }
}

function addAvatarHeaderDropdownToggleButtonHandler() {
    const avatar = document.querySelector(".header .avatar");
    avatar.addEventListener("click", toggleDropdownMenu);
}

function addDropdownItemDropdownOffHandler() {
    const headerDropdownMenu = document.querySelector(".header .dropdown-menu");
    const items = headerDropdownMenu.querySelectorAll("li");
    for(const item of items) {
        item.addEventListener("click", toggleDropdownMenu);
    }
}

function setHeaderDropdownMenu(isCookie) {
    const headerDropdownMenuLoginPartEl = document.querySelector(".header .dropdown-menu > .not-login");
    const headerDropdownMenuNotLoginPartEl = document.querySelector(".header .dropdown-menu > .login");
    if(isCookie) {
        headerDropdownMenuLoginPartEl.classList.add("disabled");
        headerDropdownMenuNotLoginPartEl.classList.remove("disabled");
    } else {
        headerDropdownMenuLoginPartEl.classList.remove("disabled");
        headerDropdownMenuNotLoginPartEl.classList.add("disabled");
    }
}

function logoutHandler(event) {
    fetch("/logout", {
        method : "GET"
    }).then((result) => {
       if(result.ok) {
           window.alert("로그아웃 되었습니다");
           window.location.reload();
       }
    }).catch((err) => {

    });
}

function shutdownDropdownMenu() {
    const headerDropdownMenu = document.querySelector(".header .dropdown-menu");
    if(headerDropdownMenu.classList.contains("show")) {
        headerDropdownMenu.classList.remove("show");
    }
}

document.addEventListener("DOMContentLoaded", (event) => {
    addAvatarHeaderDropdownToggleButtonHandler();
    addDropdownItemDropdownOffHandler();
});

document.addEventListener("click", (event) => {
    shutdownDropdownMenu();
});

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